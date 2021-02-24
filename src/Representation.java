import java.util.ArrayList;

// Representation of a tree in a certain notation: 
// "postfix: 3,0,4,8,5,8,1,6,9"
public class Representation {

    // the type of the representation:
    // infix, preafix or postfix
    private RepresentationType type;

    // the elements of the representation
    private String[] content;

    // information about the tree
    private String node;
    private String[] leftTree;
    private String[] rightTree;

    // Construct a new Representation instance
    // from a string like:
    // "postfix: 3,0,4,8,5,8,1,6,9"
    //
    // this constructor proves if the passed string is valid
    public Representation(String representation) {

        this.leftTree = new String[0];
        this.rightTree = new String[0];

        // remove all whitespace from representation
        representation = representation.replaceAll("\\s+", "");

        // check if the representation is valid with regex
        if (!representation.matches("(pos|pre|inf)=([a-zA-Z0-9]+|[\\+\\-\\/\\*])(,([a-zA-Z0-9]+|[\\+\\-\\/\\*]))*$")) {
            return;
        }

        // determine and set the representation type
        // by looking at the second character
        // -> pOst, pRae, iNfi
        switch (representation.charAt(1)) {
            case 'o':
                this.type = RepresentationType.POSTFIX;
                break;
            case 'r':
                this.type = RepresentationType.PREFIX;
                break;
            case 'n':
                this.type = RepresentationType.INFIX;
                break;
            default:
                return;
        }

        // remove the representation type part from the representation
        representation = representation.replaceAll("(pos|pre|inf)=", "");

        // split the representation string by the commata and assemble the content
        this.content = representation.split(",");

        // determine the node (if possible)
        // if POSTFIX -> last char
        if (this.type == RepresentationType.POSTFIX) {
            this.node = this.content[this.content.length - 1];
        }

        // if PREFIX -> first char
        if (this.type == RepresentationType.PREFIX) {
            this.node = this.content[0];
        }

        // collect the tree information from the content
        this.setNode(this.node);
    }

    @Override
    public String toString() {
        String out = "";

        // add the representation name
        switch (this.type) {
            case INFIX:
                out += "inf=";
                break;
            case POSTFIX:
                out += "pos=";
                break;
            case PREFIX:
                out += "pre=";
        }

        // if the trees are defined
        // -> assemble the strings with the join method
        String leftTreeString = "";
        String rightTreeString = "";
        String nodeString = "";

        if (this.leftTree.length != 0) {
            leftTreeString = String.format("(%s)", String.join(", ", this.leftTree));
        }

        if (this.rightTree.length != 0) {
            rightTreeString = String.format("(%s)", String.join(", ", this.rightTree));
        }

        if (this.node != "") {
            nodeString = String.format("[%s]", this.node);
        }

        // maximal one tree can be undefined because the
        // node can be at the very end or the very beginning of the content
        if (nodeString != "" && (leftTreeString != "" || rightTreeString != "")) {

            // check the representation type
            // and build the result string with format
            // also remove the unnecessary commata that occured
            // because of empty tree strings with replace
            switch (this.type) {
                case INFIX:

                    return String.format("%s%s, %s, %s", out, leftTreeString, nodeString, rightTreeString)
                            .replaceAll("(,\\ $)", "").replaceAll("(=,\\ )", "=");
                case POSTFIX:

                    return String.format("%s%s, %s, %s", out, leftTreeString, rightTreeString, nodeString)
                            .replaceAll("(,\\ $)", "").replaceAll("(=,\\ )", "=");
                case PREFIX:

                    return String.format("%s%s, %s, %s", out, nodeString, leftTreeString, rightTreeString)
                            .replaceAll("(,\\ $)", "").replaceAll("(=,\\ )", "=");
            }
        }

        // if no relevant information is known, just add the elements to out
        // iterate over the contents and add the elements to out
        for (int i = 0; i < this.content.length; i++) {
            // define the format
            String format = "%s, ";

            // if on last element
            if (i == this.content.length - 1) {
                format = "%s";
            }

            // if at the node
            if (this.content[i].equals(this.node)) {
                format = "[%s], ";

                // if the type is POSTFIX, set another format 
                // -> the last element is the node 
                if (this.type == RepresentationType.POSTFIX) {
                    format = "[%s]";
                }
            }

            out += String.format(format, this.content[i]);
        }

        return out;
    }

    // getter for the representation information
    // that can be extracted from the content

    // get the node
    public String getNode() {
        return this.node;
    }

    public String[] getContent() {
        return this.content;
    }

    // get the right subtree (if possible)
    public String[] getRightTree(String node) {

        // if the type allows to extract the subtree information
        if (this.type == RepresentationType.INFIX) {

            // isInRightSubtree tells if the iteration variable i
            // passed the node -> if i is within the right subtree
            boolean isInRightSubtree = false;
            ArrayList<String> rightSubtree = new ArrayList<String>();

            // search for the node in the content
            for (int i = 0; i < this.content.length; i++) {

                // if the node was found, set isInRightSubtree to true
                if (this.content[i].equals(node)) {
                    isInRightSubtree = true;
                    continue;
                }

                // begin writing the values from content to the rightSubtree
                if (isInRightSubtree) {
                    rightSubtree.add(this.content[i]);
                }
            }
            return rightSubtree.toArray(new String[0]);
        }

        // if the representation is not INFIX, return an empty array
        return new String[0];
    }

    // get the left subtree (if possible)
    public String[] getLeftTree(String node) {

        // if the type allows to extract the subtree information
        if (this.type == RepresentationType.INFIX) {

            ArrayList<String> leftSubtree = new ArrayList<String>();

            // iterate over all elements until the
            // node is found
            for (int i = 0; i < this.content.length; i++) {

                // check if the current element is a node
                if (this.content[i].equals(node)) {
                    return leftSubtree.toArray(new String[0]);
                }

                // add the current element to leftSubtree
                leftSubtree.add(this.content[i]);
            }

        }

        // if representation is not INFIX, return an empty array
        return new String[0];
    }

    public String getLeftTree() {
        return String.join(", ", this.leftTree);
    }

    public String getRightTree() {
        return String.join(", ", this.rightTree);
    }

    // setter
    public void setNode(String node) {
        this.node = node;

        // if the node is know and the type is INFIX
        // then both subtrees are determinable
        this.leftTree = this.getLeftTree(this.node);
        this.rightTree = this.getRightTree(this.node);
    }

    public void setLeftTree(String[] leftTree) {
        this.leftTree = leftTree;
    }

    public void setRightTree(String[] rightTree) {
        this.rightTree = rightTree;
    }
}
