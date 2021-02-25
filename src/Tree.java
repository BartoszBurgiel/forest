import java.util.ArrayList;

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

    // list to store all nodes of the tree
    // as a list for easier access
    private ArrayList<Tree> nodeList;

    // This tree constructor takes in two parameter, an infix representation (to
    // determine the left and right subtrees)
    // and one other representation to determine the root
    public Tree(Representation infix, Representation rep) {

        nodeList = new ArrayList<Tree>();
        this.nodeList.add(this);

        // get the root of the tree
        this.content = rep.getNode(infix);

        // set the coordinates
        this.xPos = 0;
        this.yPos = 0;

        // pass the node to the infix
        infix.setNode(this.content);

        // if the right tree exists
        if (infix.hasRightTree()) {

            // set the right tree to the tree instance created of
            // the information of the right tree's infix
            this.rightTree = new Tree(infix.getRightTree(), rep, this.xPos + 1, this.yPos + 1, this.nodeList);
        }

        // if the left tree exists
        if (infix.hasLeftTree()) {

            // set the left tree to the tree instance createf of
            // the information of the left tree's infix
            this.leftTree = new Tree(infix.getLeftTree(), rep, this.xPos - 1, this.yPos + 1, this.nodeList);
        }
    }

    // private constructor for the recursive subtree generation
    // with the x and y coordinates
    private Tree(Representation infix, Representation rep, int x, int y, ArrayList<Tree> nodeList) {
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
            this.rightTree = new Tree(infix.getRightTree(), rep, this.xPos + 1, this.yPos + 1, nodeList);
        }

        // if the left tree exists
        if (infix.hasLeftTree()) {

            // set the left tree to the tree instance createf of
            // the information of the left tree's infix
            this.leftTree = new Tree(infix.getLeftTree(), rep, this.xPos - 1, this.yPos + 1, nodeList);
        }
        nodeList.add(this);
    }

    public String toString() {

        // variables to store the max
        // vertical and horizontal spead
        int maxY = 0;
        int maxX = 0;
        int minX = 0;
        for (Tree tree : this.nodeList) {

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


        // fill the matrix with the values from the nodeList
        for (Tree tree : this.nodeList) {

            matrix[tree.yPos * 2][tree.xPos+Math.abs(minX)] = tree.content;
        }

        // iterate over the matrix and print
        for (int i = 0; i < matrix.length; i += 2) {
            for (int j = 0; j < matrix[i].length; j++) {

                if (matrix[i][j] != null) {
                    // get the current node
                    Tree currentNode = this.nodeList.get(0);

                    // search the nodeList for this node
                    for (Tree node : this.nodeList) {
                        if (matrix[i][j].equals(node.content)) {
                            currentNode = node;
                        }
                    }

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

                    matrix[i][j] = String.format(" %s ", matrix[i][j]);
                }
            }
        }

        // join the matrix to string
        StringBuilder builder = new StringBuilder();
        for (String[] row : matrix) {
            builder.append(String.join("", row).replaceAll("null", "   ") + "\n");
        }
        return builder.toString();
    }

}

/*

-5; 2 
abs = 7 

in -5 => 0 
in 2 => 7 




*/