import java.util.ArrayList;
import java.util.HashMap;

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
    public Representation(String representation) throws Exception {

        this.leftTree = new String[0];
        this.rightTree = new String[0];

        // remove all whitespace from representation
        representation = representation.replaceAll("\\s+", "");

        // check if the representation is valid with regex
        if (!representation.matches("(pos|pre|inf)=([a-zA-Z0-9]+|[\\+\\-\\/\\*])(,([a-zA-Z0-9]+|[\\+\\-\\/\\*]))*$")) {
            throw new Exception("False representation syntax: " + representation);
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

        // check if there are any duplicates
        findDuplicatesInContent(content);

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

    // generate a representation with the contents of the contents string in a
    // randomized order of the given type
    public static String generateRandomRepresentation(String type, String[] content) throws Exception {
        // check if there are any duplicates
        findDuplicatesInContent(content);

        // creare a copy of the content
        // so that the original content doesn't get affected by the future element
        // deletion
        String[] tempContent = content.clone();

        // create a string builder for the representation string
        // with the size of 256
        StringBuilder builder = new StringBuilder(256);

        // check if the type is legal
        if (!type.matches("(pos|pre|inf)")) {
            throw new Exception("False type representation for the randomized representation.");
        }

        // write the type of the representation into the builder
        builder.append(type + "=");

        // iterate over the content and build the representation string
        // in a random order

        // elementsAppended tells how many elements from the content
        // were appended to the representation
        // -> if elementsAppended == content.length -> all elements are in the
        // representation
        int elementsAppended = 0;
        int index = 0;
        // append the elements of the content to the representation
        while (elementsAppended != tempContent.length) {

            // get the index of the element to append
            index = (int) (Math.random() * (double) (tempContent.length));

            // check if the tempContent[index] != null
            if (tempContent[index] != null) {

                // append the element with a seperating comma
                builder.append(tempContent[index] + ",");

                // set the element to null to not add the element again
                tempContent[index] = null;

                // increase the elementsAppended
                elementsAppended++;
            }

        }

        // trim the last character
        // -> last character is a comma
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }

    // private representation constructor for the assembly of subtrees
    private Representation(RepresentationType type, String[] content) {
        this.type = type;
        this.content = content;

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

        // if (this.node != "") {
        // nodeString = String.format("[%s]", this.node);
        // }

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

            // // if at the node
            // if (this.content[i].equals(this.node)) {
            // format = "[%s], ";

            // // if the type is POSTFIX, set another format
            // // -> the last element is the node
            // if (this.type == RepresentationType.POSTFIX) {
            // format = "[%s]";
            // }
            // }

            // if on last element
            if (i == this.content.length - 1) {
                format = format.replaceAll(",\\ ", "");
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

    // getNode of the given infix subtree
    // based on the contents of this' tree
    // -> return the frontest/rearest element
    // in this' content
    public String getNode(Representation infixSubtree) {

        // for the PREFIX return the frontest element
        if (this.type == RepresentationType.PREFIX) {

            // iterate over this' contents untill an element
            // from the infixSubtree is found
            for (int i = 0; i < this.content.length; i++) {

                // iterate over infixSubtree elements and check
                // if the current element exists in the array
                for (int j = 0; j < infixSubtree.getContent().length; j++) {
                    if (this.content[i].equals(infixSubtree.getContent()[j])) {
                        return this.content[i];
                    }
                }
            }
        } else if (this.type == RepresentationType.POSTFIX) {

            // iterate over this' contents untill an element
            // from the infixSubtree is found
            for (int i = this.content.length - 1; i >= 0; i--) {

                // iterate over infixSubtree elements and check
                // if the current element exists in the array
                for (int j = 0; j < infixSubtree.getContent().length; j++) {
                    if (this.content[i].equals(infixSubtree.getContent()[j])) {
                        return this.content[i];
                    }
                }
            }
        }

        return "";
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

    public Representation getLeftTree() {
        return new Representation(this.type, this.getLeftTreeString().replaceAll(" ", "").split(","));
    }

    public Representation getRightTree() {
        return new Representation(this.type, this.getRightTreeString().replaceAll(" ", "").split(","));
    }

    public String getLeftTreeString() {
        return String.join(", ", this.leftTree);
    }

    public String getRightTreeString() {
        return String.join(", ", this.rightTree);
    }

    public boolean hasRightTree() {
        return this.rightTree.length != 0;
    }

    public boolean hasLeftTree() {
        return this.leftTree.length != 0;
    }

    // return if the representation doesn't have
    // any elements in it
    public boolean isEmpty() {
        return (this.content.length == 0);
    }

    // tell the kind of the representation
    public boolean isInfix() {
        return this.type == RepresentationType.INFIX;
    }

    public boolean isPostfix() {
        return this.type == RepresentationType.POSTFIX;
    }

    public boolean isPrefix() {
        return this.type == RepresentationType.PREFIX;
    }

    // get the type of the representation as a string
    public String typeToString() {
        if (this.isInfix()) {
            return "infix";
        }
        if (this.isPostfix()) {
            return "postfix";
        }
        return "prefix";
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

    // helper functions
    static private void findDuplicatesInContent(String[] content) throws Exception {
        // search for duplicates within the content
        // create a hashmap for the elements
        HashMap<String, Boolean> contentHashMap = new HashMap<String, Boolean>(content.length);
        for (String s : content) {

            // check if the element exists in the map
            if (contentHashMap.containsKey(s)) {
                throw new Exception("No duplicates allowed within a representation: " + s + " occurs at least twice.");
            }
            contentHashMap.put(s, true);
        }
    }
}
