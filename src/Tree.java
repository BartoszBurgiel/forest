import java.util.HashMap;

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

    // This tree constructor takes in two parameter, an infix representation (to
    // determine the left and right subtrees)
    // and one other representation to determine the root
    public Tree(Representation infix, Representation rep) {

        // initialize the hashmap with the needed capacity
        nodeMap = new HashMap<String, Tree>(rep.getContent().length);

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

        // variables to store the max
        // vertical and horizontal spead
        int maxY = 0;
        int maxX = 0;
        int minX = 0;

        // iterate over the keys
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
    public String toInfix() {
        String out = "";

        // if left tree exists
        if (this.leftTree != null) {
            out += this.leftTree.toInfix();
        }

        // add the content
        out += this.content;

        // if the right tree exists 
        if (this.rightTree != null) {
            out += this.rightTree.toInfix();
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
}