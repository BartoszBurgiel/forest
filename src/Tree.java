// Tree represents a binary tree
public class Tree {
    private String content;
    private Tree leftTree;
    private Tree rightTree;

    // This tree constructor takes in two parameter, an infix representation (to
    // determine the left and right subtrees)
    // and one other representation to determine the root
    public Tree(Representation infix, Representation rep) {

        // get the root of the tree
        this.content = rep.getNode(infix);


        // pass the node to the infix 
        infix.setNode(this.content);

        // if the right tree exists
        if (infix.hasRightTree()) {

            // set the right tree to the tree instance created of
            // the information of the right tree's infix
            this.rightTree = new Tree(infix.getRightTree(), rep);
        }

        // if the left tree exists
        if (infix.hasLeftTree()) {

            // set the left tree to the tree instance createf of 
            // the information of the left tree's infix
            this.leftTree = new Tree(infix.getLeftTree(), rep);
        }
    }
}