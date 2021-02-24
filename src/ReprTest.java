public class ReprTest {
    public static void main(String[] args) {

        // test the infix definition
        String infixTest = "inf=1, 2, 3, 4, 5, 6, 7, 8, 9";

        // set of all possible arrangements of the trees
        // when node goes from 1 to 9
        String[] infixTestToStringWant = { "inf=[1], (2, 3, 4, 5, 6, 7, 8, 9)", "inf=(1), [2], (3, 4, 5, 6, 7, 8, 9)",
                "inf=(1, 2), [3], (4, 5, 6, 7, 8, 9)", "inf=(1, 2, 3), [4], (5, 6, 7, 8, 9)",
                "inf=(1, 2, 3, 4), [5], (6, 7, 8, 9)", "inf=(1, 2, 3, 4, 5), [6], (7, 8, 9)",
                "inf=(1, 2, 3, 4, 5, 6), [7], (8, 9)", "inf=(1, 2, 3, 4, 5, 6, 7), [8], (9)",
                "inf=(1, 2, 3, 4, 5, 6, 7, 8), [9]", };

        // create an infix representation for the test
        Representation infixRepr = new Representation(infixTest);

        String infixReprNodeTestResult = "";
        // iterate over 0 to 9 and compare the want string with the have string
        for (int i = 0; i < 9; i++) {
            String want = infixTestToStringWant[i];

            // set node to i+1
            infixRepr.setNode(Integer.toString(i + 1));

            // convert to string
            String have = infixRepr.toString();

            // compare
            if (!have.equals(want)) {
                infixReprNodeTestResult += String.format(" - TEST FAILED -\n\twant: %s\n\thave: %s\n", want, have);
                infixReprNodeTestResult += String.format(
                        "\tRepresentation data: \n\t\tnode: %s\n\t\tltree: %s\n\t\trtree: %s\n\n", infixRepr.getNode(),
                        infixRepr.getLeftTree(), infixRepr.getRightTree());
            }
        }
        if (infixReprNodeTestResult != "") {
            System.out.println(infixReprNodeTestResult);
        } else {
            System.out.println("All tests for the toString method for the infix representation passed.");
        }

        // test the postfix definition
        String postfixTest = "pos=1, 2, 3, 4, 5, 6, 7, 8, 9";

        // set of all possible arrangements of the trees
        // when node goes from 1 to 9
        String postfixTestToStringWant = "pos=1, 2, 3, 4, 5, 6, 7, 8, [9]";

        // create an postfix representation for the test
        Representation postfixRepr = new Representation(postfixTest);

        String postfixReprNodeTestResult = "";

        String want = postfixTestToStringWant;

        // convert to string
        String have = postfixRepr.toString();

        // compare
        if (!have.equals(want)) {
            postfixReprNodeTestResult += String.format(" - TEST FAILED -\n\twant: %s\n\thave: %s\n", want, have);
            postfixReprNodeTestResult += String.format(
                    "\tRepresentation data: \n\t\tnode: %s\n\t\tltree: %s\n\t\trtree: %s\n\n", postfixRepr.getNode(),
                    postfixRepr.getLeftTree(), postfixRepr.getRightTree());
        }

        if (postfixReprNodeTestResult != "") {
            System.out.println(postfixReprNodeTestResult);
        } else {
            System.out.println("All tests for the toString method for the postfix representation passed.");
        }

        // test the postfix definition
        String prefixTest = "pre=1, 2, 3, 4, 5, 6, 7, 8, 9";

        // set of all possible arrangements of the trees
        // when node goes from 1 to 9
        String prefixTestToStringWant = "pre=[1], 2, 3, 4, 5, 6, 7, 8, 9";

        // create an prefix representation for the test
        Representation prefixRepr = new Representation(prefixTest);

        String prefixReprNodeTestResult = "";

        want = prefixTestToStringWant;

        // convert to string
        have = prefixRepr.toString();

        // compare
        if (!have.equals(want)) {
            prefixReprNodeTestResult += String.format(" - TEST FAILED -\n\twant: %s\n\thave: %s\n", want, have);
            prefixReprNodeTestResult += String.format(
                    "\tRepresentation data: \n\t\tnode: %s\n\t\tltree: %s\n\t\trtree: %s\n\n", prefixRepr.getNode(),
                    prefixRepr.getLeftTree(), prefixRepr.getRightTree());
        }

        if (prefixReprNodeTestResult != "") {
            System.out.println(prefixReprNodeTestResult);
        } else {
            System.out.println("All tests for the toString method for the prefix representation passed.");
        }

    }
}