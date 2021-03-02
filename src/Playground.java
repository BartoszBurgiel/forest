import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Playground represents the cli playground to 
// define and create trees for practise
public class Playground {

    // memory to store the tree variables
    // key = the name of the variable
    // value = the tree
    private HashMap<String, Tree> treeMemory;

    // memory to store the representation variables
    // key = name of the variable
    // value = the representation
    private HashMap<String, Representation> representationMemory;

    // valid inputs stores the regex to describe a valid inputs into the
    // plauground
    // key = expression enum
    // value = regex to verify
    private HashMap<PlaygroundExpression, String> validInputs;

    // add authors note
    private final String authorsNode = "Thank you for using my lil' program. I hope you like it, and that it helps you learn the tree traversals.\nIn case you encounter any bugs, problems, crashes or in general things that look out of place, feel free to file an issue on the project's github repository at https://github.com/BartoszBurgiel/forest\n\nAlso any improvement ideas, constructive critique and contributions are more than welcome.";

    // constant messages
    private final String greeting = "Welcome to the forest - a tree traversal playground\nType help to get information on the usage, quit to exit the program or author to read the author's note.\n";

    // the manual of the playground
    private final String man = new StringBuilder().append("The usage of the forest playground \n")
            .append("\ttree constructors: \n").append("\t\t- newTree((<representation1>), (<representation2>))\n")
            .append("\t\t\t- define a new tree and save it under a variable\n").append("\t\t\t- usage:\n")
            .append("\t\t\t\t- <variableName> = newTree(<infixRepresentation1>, <representation2>)\n")
            .append("\t\t\t\t\t- the <variableName> cannot be empty\n")
            .append("\t\t\t\t\t- the <variableName> can consist of letters and numbers\n").append("\t\n")
            .append("\t\t\t- proper syntax of a tree representation: (pos|inf|pre)=<element>,<element>(...), \n")
            .append("\t\t\t\t- the <element> cannot be empty \n").append("\t\t\t\t- no elements can repeat\n")
            .append("\t\t\t\t- no ',' as element allowed\n")
            .append("\t\t\t\t- both representations must contain the same characters \n")
            .append("\t\t\t\t- an infix and one postfix or prefix representation is required\n")
            .append("\t\t\t\t- the first representation must be the infix\n")
            .append("\t\t\t\t- examples of proper tree representation: \n").append("\t\t\t\t\t- pre=1,2,3,4,5,6,7\n")
            .append("\t\t\t\t\t- pos=a,b,c,d,e,f,g\n").append("\t\t\t\t\t- inf=1,+,5,*,3,/,2\n").append("\t")
            .append("\t\t\t\t- the representations can also be saved as variables:\n")
            .append("\t\t\t\t\t\t- rep(<variableName>)=<representation>\n")
            .append("\t\t\t\t\t\t- example of the proper representation declaration:\n")
            .append("\t\t\t\t\t\t\t- rep(r1)=inf=42,2,3,4,5,6\n").append("\t\t\t\t\t\t\t- rep(r2)=pos=42,2,3,4,5,6\n\n")
            .append("\t\t\t- example of proper tree definition:\n")
            .append("\t\t\t\t- t = newTree((inf=a,42,c,d,e,f,g), (pre=42,f,e,d,a,c,g))\n")
            .append("\t\t\t\t- t = newTree(rep(r1), rep(r2))\n").append("\t\t- newRandomTree(<elements>)\n")
            .append("\t\t\t- define a tree with the nodes listed in the elements \n").append("\t\t\t- usage:\n")
            .append("\t\t\t\t- <variableName> = newRandomTree(<elements>)\n")
            .append("\t\t\t\t- the <element> cannot be empty \n").append("\t\t\t\t- no elements can repeat\n")
            .append("\t\t\t\t- no ',' as element allowed\n")
            .append("\t\t\t\t- both representations must contain the same characters \n")
            .append("\t\t\t\t- an infix and one postfix or prefix representation is required\n")
            .append("\t\t\t\t- examples of proper definition of a random tree: \n")
            .append("\t\t\t\t\t- t = newRandomTree(42, b, c, d, e, f)\n").append("\t\n")
            .append("\tutility functions:\n").append("\t\t- <treeVariable>.draw()\n")
            .append("\t\t\t- draw the tree in the console without any further information \n").append("\t\n")
            .append("\t\t- <treeVariable>.information()\n")
            .append("\t\t\t- draw the tree and print the tree information in the console \n").append("\t\n")
            .append("\t\t- <treeVariable>.explain()\n")
            .append("\t\t\t- assemble the tree and explain step by step how it was assembled \n").append("\t\n")
            .append("\t\n").append("demo program: \n").append(">t = newRandomTree(a,b,c,d,e,f)\n")
            .append(">t.information()\n").append("\t\n").append("output: \n").append("%s\n").append("\t\n")
            .append("tl;dr: \n").append("\t- new tree:\t \n")
            .append("\t\t- t = newTree((inf=a,42,c,d,e,f,g), (pre=42,f,e,d,a,c,g))\n")
            .append("\t\t- t = newTree(rep(r1), rep(r2))\n")
            .append("\t\t\t- if and only if r1 and r2 are defined and of the proper type (r1 = infix, r2 = postfix/prefix)\n")
            .append("\t\t- you need to define the infix and prefix or postfix\n")
            .append("\t\t- first representation must be the infix \n\n").append("\t-new representation\n")
            .append("\t\t - rep(r1)=inf=42,6,7,4,3\n").append("\t\n").append("\t- newrandom tree:\n")
            .append("\t\t- t = newRandomTree(42, b, c, d, e, f)\n").append("\t\n").append("\t- t.draw():\n")
            .append("\t\t- draw the tree in the console\n").append("\t\t\n").append("\t- t.information():\n")
            .append("\t\t- draw the tree and print information \n").append("\t\n").append("\t- t.explain():\n")
            .append("\t\t- explain how the tree was assembled\n").toString();

    // construct a new playground instance and initialize the variables
    public Playground() {

        // initialize the memory map
        this.treeMemory = new HashMap<String, Tree>();
        this.representationMemory = new HashMap<String, Representation>();

        // initialize the valid input regex
        this.validInputs = new HashMap<PlaygroundExpression, String>();

        // program keywords
        this.validInputs.put(PlaygroundExpression.HELP, "help");
        this.validInputs.put(PlaygroundExpression.QUIT, "quit");
        this.validInputs.put(PlaygroundExpression.AUTHOR, "author");

        // random tree declaration
        this.validInputs.put(PlaygroundExpression.NEW_RANDOM_TREE,
                "([a-zA-Z0-9]+)=newRandomTree\\(([,a-zA-Z0-9\\+\\-\\/\\*]+)\\)$");

        // regular tree declaration
        this.validInputs.put(PlaygroundExpression.NEW_TREE,
                "([a-zA-Z0-9]+)=(newTree\\(((\\((inf)=[a-zA-Z0-9\\+\\-\\/\\*]+(,[a-zA-Z0-9\\+\\-\\/\\*]+)+\\))|(rep\\([a-zA-Z0-9]+\\))),((\\((pre|pos)=[a-zA-Z0-9\\+\\-\\/\\*]+(,[a-zA-Z0-9\\+\\-\\/\\*]+)+\\))|(rep\\([a-zA-Z0-9]+\\))))\\)");

        // new representation declaration
        this.validInputs.put(PlaygroundExpression.NEW_REPRESENTATION,
                "rep\\(([a-zA-Z0-9]+)\\)=((inf|pos|pre)=[a-zA-Z0-9\\+\\-\\/\\*]+(,[a-zA-Z0-9\\+\\-\\/\\*]+)+$)");

        // tree method
        this.validInputs.put(PlaygroundExpression.TREE_METHOD,
                "([a-zA-Z0-9]+)\\.(draw\\(\\)|information\\(\\)|explain\\(\\))$");
    }

    public void run() {

        // print the greeting as the first thing after the start
        System.out.println(this.greeting);

        // execute for ever
        while (true) {

            System.out.print(">");
            String input = "";

            // get the line from the system.in
            try {
                input = System.console().readLine();

            } catch (Exception e) {
                return;
            }

            // strip the input off any whitespace
            input = input.replaceAll("\\s", "");

            // if the input is empty -> just skip
            if (input.equals("")) {
                continue;
            }

            // get the expression tag
            PlaygroundExpression expression = getExpressionFromInput(input);

            // check if the input couldn't be recognized
            if (expression == PlaygroundExpression.UNKNOWN_EXPRESSION) {
                System.out.println(
                        "invalid expression. maybe forgot a bracket? or accidentally used an illegal character?\n");
                continue;
            }

            // check which expression was called and
            // call the handling function
            switch (expression) {
                case QUIT:
                    System.exit(0);
                    break;
                case HELP:

                    try {

                        System.out.printf(this.man,
                                new Tree(new String[] { "a", "b", "c", "d", "e", "f" }).informationToString());
                    } catch (Exception e) {
                        // Here the exception can be ignored, since there is no way for an exception to
                        // occur
                        // -> an exception from this constructor occurs only if there exist duplicate
                        // elements
                    }
                    break;
                case AUTHOR:
                    System.out.println(this.authorsNode);
                    break;
                case NEW_TREE:
                    this.handleNewTree(input);
                    break;
                case TREE_METHOD:
                    this.handleTreeMethods(input);
                    break;
                case NEW_RANDOM_TREE:
                    this.handleNewRandomTree(input);
                    break;
                case NEW_REPRESENTATION:
                    this.handleNewRepresentation(input);
                    break;
                default:
                    break;
            }
            System.out.println("");
        }
    }

    // check if the input matches any of the allowed expressions
    private PlaygroundExpression getExpressionFromInput(String input) {

        // iterate over the values of the validInputs and check the regex
        for (HashMap.Entry<PlaygroundExpression, String> entry : this.validInputs.entrySet()) {

            // check if the input matches the value
            if (input.matches(entry.getValue())) {
                return entry.getKey();
            }
        }
        return PlaygroundExpression.UNKNOWN_EXPRESSION;
    }

    // handlers for the expressions

    // handle the new random tree expression
    // -> create a new random tree based on the input
    private void handleNewRandomTree(String input) {

        // create a matcher to extract the data from the input
        Pattern termsPattern = Pattern.compile(this.validInputs.get(PlaygroundExpression.NEW_RANDOM_TREE));
        Matcher matcher = termsPattern.matcher(input);

        // if the matcher found a match
        if (matcher.find()) {

            // first match represents the variable name
            // second match represents the content for the representation
            String variableName = matcher.group(1);

            // split after the comma
            String[] content = matcher.group(2).split(",");

            // create the tree
            Tree t = null;
            try {
                t = new Tree(content);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return;
            }

            // put the variable into the memory
            this.treeMemory.put(variableName, t);

            System.out.println("Successfully created and added a new tree to the memory");
        }

    }

    // handle the new tree expression
    // -> create a new tree based on the input
    private void handleNewTree(String input) {

        // get the variable name for the new tree
        String variableName = "";

        // infix and the second representation of the tree
        String repString = "";
        String infixString = "";

        // create a matcher to extract the data from the input
        Pattern termsPattern = Pattern.compile(this.validInputs.get(PlaygroundExpression.NEW_TREE));
        Matcher matcher = termsPattern.matcher(input);

        // if the matcher found subgroups
        if (matcher.find()) {

            // the 1 group is the variable name
            variableName = matcher.group(1);

            // the groups 3 and 6 are other representation groups
            // -> determine which is an infix representation and
            // assign and remove the brackets
            infixString = matcher.group(3);
            repString = matcher.group(8);
        }

        // create the Representations from the information
        Representation infix = null;
        Representation rep = null;

        // check if the infix representation strings
        // are new defined representations or called variables

        // check the infix representation
        if (infixString.matches("rep\\([a-zA-Z0-9]+\\)")) {

            // extract the variable name
            infixString = infixString.replaceAll("rep\\(|\\)", "");

            // check if the variable is defined
            if (!this.representationMemory.containsKey(infixString)) {
                System.out.println("The representation " + infixString + " is not defined.");
                return;
            }

            // get the representation from the memory and set it to the infix
            infix = this.representationMemory.get(infixString);
            System.out.println(infix.toString());
        } else if (infixString.matches("\\(((inf)=[a-zA-Z0-9\\+\\-\\/\\*]+(,[a-zA-Z0-9\\+\\-\\/\\*]+)+)\\)")) {

            // remove all brackets
            infixString = infixString.replaceAll("\\(|\\)", "");

            // create a new representation and set it to the infix
            try {
                infix = new Representation(infixString);

            } catch (Exception e) {
                System.out.println(e.getMessage());
                return;
            }
        }

        // check the other representation
        if (repString.matches("rep\\([a-zA-Z0-9]+\\)")) {

            // extract the variable name
            repString = repString.replaceAll("rep\\(", "").replaceAll("\\)", "");

            // check if the variable is defined
            if (!this.representationMemory.containsKey(repString)) {
                System.out.println("The representation " + repString + " is not defined.");
                return;
            }

            // get the representation from the memory and set it to the infix
            rep = this.representationMemory.get(repString);
        } else if (repString.matches("\\(((pos|pre)=[a-zA-Z0-9\\+\\-\\/\\*]+(,[a-zA-Z0-9\\+\\-\\/\\*]+)+)\\)")) {

            repString = repString.replaceAll("\\(|\\)", "");

            // create a new representation and set it to the infix
            try {
                rep = new Representation(repString);

            } catch (Exception e) {
                System.out.println(e.getMessage());
                return;
            }
        }

        Tree t = null;

        // construct the tree with the representations
        try {
            t = new Tree(infix, rep);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        // add t and the variable to the memory
        this.treeMemory.put(variableName, t);

        System.out.println("Successfully created and added a new tree to the memory");
    }

    // handle the tree functions draw, information and explain
    private void handleTreeMethods(String input) {

        // create a matcher to extract the data from the input
        Pattern termsPattern = Pattern.compile(this.validInputs.get(PlaygroundExpression.TREE_METHOD));
        Matcher matcher = termsPattern.matcher(input);

        // if a match is found
        // -> get the subgroups
        if (matcher.find()) {

            // the first submatch is the tree name
            String treeName = matcher.group(1);

            // check if the name exists
            if (!this.treeMemory.containsKey(treeName)) {
                System.out.println("The tree with the name " + treeName + " is undefined");
                return;
            }

            Tree t = this.treeMemory.get(treeName);

            // get the method
            String method = matcher.group(2);

            // check the method and execute the tree's method
            switch (method) {
                case "draw()":
                    System.out.println(t.toString());
                    return;

                case "information()":
                    System.out.println(t.informationToString());
                    return;

                case "explain()":
                    System.out.println(t.getExplanation());
                    return;
                default:
                    break;
            }
        }

    }

    // handle the declaration of a new representation
    // -> extract the information via the regex and write it to the memory
    private void handleNewRepresentation(String input) {

        // create a matcher to extract the data from the input
        Pattern termsPattern = Pattern.compile(this.validInputs.get(PlaygroundExpression.NEW_REPRESENTATION));
        Matcher matcher = termsPattern.matcher(input);

        // if the matcher found a match
        if (matcher.find()) {

            // the first subgroup is the variable name
            String variableName = matcher.group(1);

            // the second subgroup is the representation
            String representationString = matcher.group(2);

            // construct the representation
            Representation rep = null;
            try {
                rep = new Representation(representationString);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return;
            }

            // put the representation to the memory
            this.representationMemory.put(variableName, rep);
            System.out.println(variableName);
            System.out.println("Successfully added a new representation to the memory");
        }
    }

}
