import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Tree represents a binary tree
public class Tree {
    private String content;
    private Tree leftTree;
    private Tree rightTree;

    // position of the node on the matrix
    // for future printing
    // -> 0,0 = origin
    // -> -1, 1 = first left node
    // -> 1, 1 = rirst right node
    private int xPos;
    private int yPos;

    // map to store all nodes of the tree
    // as a map for easier and faster access
    // key -> the content of the node
    // value -> the tree reference
    private HashMap<String, Tree> nodeMap;

    // the original infix and rep representations
    // for the funture tree expansion when turining to a string
    private Representation infix, rep;

    // this tree constructor creates a random tree with the
    // characters defined in the content
    public Tree(String[] content) throws Exception {

        // generate two necessary representations
        this.infix = new Representation(Representation.generateRandomRepresentation("inf", content));

        // determine the representation type
        String type = (Math.random() > 0.5) ? "pre" : "pos";

        this.rep = new Representation(Representation.generateRandomRepresentation(type, content));

        // initialize the hashmap with the needed capacity
        this.nodeMap = new HashMap<String, Tree>(rep.getContent().length);

        // get the root of the tree
        this.content = rep.getNode(infix);

        // put into the map
        this.nodeMap.put(this.content, this);

        // set the coordinates
        this.xPos = 0;
        this.yPos = 0;

        // pass the node to the infix
        infix.setNode(this.content);

        // if the right tree exists
        if (infix.hasRightTree()) {

            // set the right tree to the tree instance created of
            // the information of the right tree's infix
            this.rightTree = new Tree(infix.getRightTree(), rep, this.xPos + 1, this.yPos + 1, this.nodeMap);
        }

        // if the left tree exists
        if (infix.hasLeftTree()) {

            // set the left tree to the tree instance createf of
            // the information of the left tree's infix
            this.leftTree = new Tree(infix.getLeftTree(), rep, this.xPos - 1, this.yPos + 1, this.nodeMap);
        }
    }

    // This tree constructor takes in two parameter, an infix representation (to
    // determine the left and right subtrees)
    // and one other representation to determine the root
    public Tree(Representation infix, Representation rep) {

        // set the infix and rep
        this.infix = infix;
        this.rep = rep;

        // initialize the hashmap with the needed capacity
        this.nodeMap = new HashMap<String, Tree>(rep.getContent().length);

        // get the root of the tree
        this.content = rep.getNode(infix);

        // put into the map
        this.nodeMap.put(this.content, this);

        // set the coordinates
        this.xPos = 0;
        this.yPos = 0;

        // pass the node to the infix
        infix.setNode(this.content);

        // if the right tree exists
        if (infix.hasRightTree()) {

            // set the right tree to the tree instance created of
            // the information of the right tree's infix
            this.rightTree = new Tree(infix.getRightTree(), rep, this.xPos + 1, this.yPos + 1, this.nodeMap);
        }

        // if the left tree exists
        if (infix.hasLeftTree()) {

            // set the left tree to the tree instance createf of
            // the information of the left tree's infix
            this.leftTree = new Tree(infix.getLeftTree(), rep, this.xPos - 1, this.yPos + 1, this.nodeMap);
        }
    }

    // private constructor for the recursive subtree generation
    // with the x and y coordinates
    private Tree(Representation infix, Representation rep, int x, int y, HashMap<String, Tree> nodeMap) {
        // get the root of the tree
        this.content = rep.getNode(infix);

        // set the coordinates
        this.xPos = x;
        this.yPos = y;

        // pass the node to the infix
        infix.setNode(this.content);

        // if the right tree exists
        if (infix.hasRightTree()) {

            // set the right tree to the tree instance created of
            // the information of the right tree's infix
            this.rightTree = new Tree(infix.getRightTree(), rep, this.xPos + 1, this.yPos + 1, nodeMap);
        }

        // if the left tree exists
        if (infix.hasLeftTree()) {

            // set the left tree to the tree instance createf of
            // the information of the left tree's infix
            this.leftTree = new Tree(infix.getLeftTree(), rep, this.xPos - 1, this.yPos + 1, nodeMap);
        }

        // put the node into the map
        nodeMap.put(this.content, this);
    }

    public String toString() {

        // check for duplicate positions
        // and while the duplicates exist, rebuild the tree
        // increasing the spead on the initial left and right trees
        int spread = 1;
        while (this.hasDuplicateCoordinates()) {
            spread++;
            // if the right tree exists
            if (infix.hasRightTree()) {

                // set the right tree to the tree instance created of
                // the information of the right tree's infix
                this.rightTree = new Tree(infix.getRightTree(), rep, this.xPos + spread, this.yPos + 1, this.nodeMap);
            }

            // if the left tree exists
            if (infix.hasLeftTree()) {

                // set the left tree to the tree instance createf of
                // the information of the left tree's infix
                this.leftTree = new Tree(infix.getLeftTree(), rep, this.xPos - spread, this.yPos + 1, this.nodeMap);
            }
        }

        // variables to store the max
        // vertical and horizontal spead
        int maxY = 0;
        int maxX = 0;
        int minX = 0;

        // iterate over the values and find the max and min
        for (Tree tree : this.nodeMap.values()) {

            // set the maximum y value
            maxY = Integer.max(tree.yPos, maxY);
            maxX = Integer.max(tree.xPos, maxX);
            minX = Integer.min(tree.xPos, minX);
        }

        // the size of the matrix on the vertical axis is the
        // sum of abs(maxX) and abs(minX)
        // -> max spread to the right + max spread to the left
        int xSize = Math.abs(minX) + Math.abs(maxX);
        int ySize = maxY;

        // increase xSize and ySize by one
        // for the array creaction
        xSize++;
        ySize++;

        // multiply ySize by 2 to add a line for each node
        // for the branches
        ySize *= 2;

        // create a matrix with the size of maxY and maxX
        // where the values of the nodes will be stored
        // at the accodring positions
        //
        // first y and then x due to the printing reason
        String[][] matrix = new String[ySize][xSize];

        // fill the matrix with the values from the nodeMap
        for (Tree tree : this.nodeMap.values()) {

            matrix[tree.yPos * 2][tree.xPos + Math.abs(minX)] = tree.content;
        }

        // iterate over the matrix and print
        for (int i = 0; i < matrix.length; i += 2) {
            for (int j = 0; j < matrix[i].length; j++) {

                // if the element of the matrix is defined
                if (matrix[i][j] != null) {

                    // get the current node
                    Tree currentNode = this.nodeMap.get(matrix[i][j]);

                    // check if subtrees of the current node exist
                    if (currentNode.leftTree != null) {

                        // set the diagonal position on the matrix to a branch
                        matrix[i + 1][j - 1] = " / ";

                    }

                    // check if subtrees of the current node exist
                    if (currentNode.rightTree != null) {

                        // set the diagonal position on the matrix to a branch
                        matrix[i + 1][j + 1] = " \\ ";

                    }

                    matrix[i][j] = String.format("(%s)", matrix[i][j]);
                }
            }
        }

        // join the matrix to string
        StringBuilder builder = new StringBuilder();
        for (String[] row : matrix) {

            // replace all nulls with blank space
            builder.append(String.join("", row).replaceAll("null", "   ") + "\n");
        }
        return builder.toString();
    }

    // hasDuplicateCoordinates checks if there exist tree nodes that
    // would be allocated at the same position on the matrix
    private boolean hasDuplicateCoordinates() {

        // create a hashmap for the points
        // key -> the coordinates of the node
        // value -> dummy
        HashMap<String, Boolean> pointMap = new HashMap<String, Boolean>();

        // iterate over the elements of the nodeMap and
        // add the coodrinates to the map
        for (Tree node : this.nodeMap.values()) {

            // convert the x and y coordinates to a string
            String coordinates = String.format("%d|%d", node.xPos, node.yPos);

            // check if the coorinates are already in the map
            if (pointMap.containsKey(coordinates)) {

                // there exist duplicate coordinates
                // -> return true
                return true;
            }
            pointMap.put(coordinates, true);
        }
        return false;
    }

    // conversion functions to convert a tree
    // into the infix, postfix, and prefix notations
    //
    // the brackets parameter tell if the brackets should
    // be put around the subtrees
    public String toInfix(boolean brackets) {
        String out = "";

        // if left tree exists
        if (this.leftTree != null) {
            out += this.leftTree.toInfix(brackets);
        }

        // add the content
        out += this.content;

        // if the right tree exists
        if (this.rightTree != null) {
            out += this.rightTree.toInfix(brackets);
        }

        if (this.isLeaf()) {

            // if is a leaf -> return out without brackets regardless
            return out;
        }

        // if brackets -> enclose the subtree in brackets
        if (brackets) {

            return "(" + out + ")";
        }
        return out;
    }

    public String toPostfix() {
        String out = "";

        // if left tree exists
        if (this.leftTree != null) {
            out += this.leftTree.toPostfix();
        }

        // if the right tree exists
        if (this.rightTree != null) {
            out += this.rightTree.toPostfix();
        }

        // add the content
        out += this.content;

        return out;
    }

    public String toPrefix() {
        String out = "";

        // add the content
        out += this.content;

        // if left tree exists
        if (this.leftTree != null) {
            out += this.leftTree.toPrefix();
        }

        // if the right tree exists
        if (this.rightTree != null) {
            out += this.rightTree.toPrefix();
        }

        return out;
    }

    // tell if the this is a leaf
    public Boolean isLeaf() {
        return (this.rightTree == null && this.leftTree == null);
    }

    // tell if this is a calculable mathematical expression
    // -> all leaves are numerals
    // -> all nodes are operators
    public Boolean isMathExpression() {

        // tell if all leafs are numerals and
        // the nodes are mathematical operators
        String operatorsRegex = "[\\+\\-\\/\\*]";
        String numeralsRegex = "[0-9]+";

        // iterate over all nodes
        for (Tree node : this.nodeMap.values()) {

            // if is a leaf
            if (node.isLeaf()) {
                // and is not a numeral
                if (!node.content.matches(numeralsRegex)) {
                    return false;
                }
            }

            // if is a node
            if (!node.isLeaf()) {
                // and is not an operator
                if (!node.content.matches(operatorsRegex)) {
                    return false;
                }
            }

        }
        return true;
    }

    // calculate the value if the
    // tree is an expression tree
    public int calculate() {
        if (!this.isMathExpression()) {
            return -1;
        }

        // get the expression in infix
        String expression = this.toInfix(true);

        // the regex to match a mathematical expression
        Pattern termsPattern = Pattern.compile("\\([0-9]+[\\+\\-\\/\\*][0-9]+\\)");
        Matcher matcher = termsPattern.matcher(expression);

        // replace the expressions untill only the solutions is left
        // -> while the expression isn't like (<number>)
        while (!expression.matches("[0-9]+")) {

            // redefine the matcher with the new expression
            matcher = termsPattern.matcher(expression);

            // pick the expressions with regex and replace them with numbers
            if (matcher.find()) {

                // strip the match of the brackets
                String match = matcher.group(0).replaceAll("\\(|\\)", "");

                // prepare the match for the replacement
                // -> replace dangerous characters: *, +, (, ) by \\+, \\* etc.
                String toReplace = matcher.group(0).replaceAll("\\+", "\\\\+").replaceAll("\\*", "\\\\*")
                        .replaceAll("\\(", "\\\\(").replaceAll("\\)", "\\\\)");

                // replace the calculated value in the expression
                expression = expression.replaceAll(toReplace, String.valueOf(calculateExpression(match)));
            }
        }

        return Integer.valueOf(expression);
    }

    // calculate an expression of 2 numbers and an operator
    // -> 3 + 4 or 5 / 3
    public int calculateExpression(String expr) {

        // regex to get the terms and operator in groupings
        Pattern termsPattern = Pattern.compile("([0-9]+)([\\*\\+\\-\\/])([0-9]+)");
        Matcher matcher = termsPattern.matcher(expr);

        // get the terms

        // first term -> 0;<expr[i]==operator>
        if (matcher.matches()) {

            int firstTerm = Integer.valueOf(matcher.group(1));
            String operator = matcher.group(2);
            int secondTerm = Integer.valueOf(matcher.group(3));

            // choose the right operation and return the calculated result
            switch (operator) {
                case "+":
                    return firstTerm + secondTerm;
                case "-":
                    return firstTerm - secondTerm;
                case "/":
                    return firstTerm / secondTerm;
                case "*":
                    return firstTerm * secondTerm;
                default:
                    return -1;
            }
        }
        return -1;
    }

    // gather all information about the tree and
    // return a formatted result
    // ->
    // <drawn tree>
    // <tree in infix>
    // <tree in postfix>
    // <tree in prefix>
    // <result if tree is mathematical>
    public String informationToString() {
        // create a string builder with the size of 1024
        // to store the result
        StringBuilder builder = new StringBuilder(1024);

        // write the tree as string to the builder
        builder.append(this.toString() + "\n");

        // write the representations to the builder
        builder.append("infix = " + this.toInfix(false) + "\n");
        builder.append("postfix = " + this.toPostfix() + "\n");
        builder.append("prefix = " + this.toPrefix() + "\n");

        // if the tree is a mathematical expression
        if (this.isMathExpression()) {
            builder.append(this.toInfix(true) + " = " + this.calculate());
        }

        return builder.toString();
    }
}