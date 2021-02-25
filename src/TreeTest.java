public class TreeTest {

    // class for each test case
    static private class Testcase {

        // the two representation to build the tree
        private Representation infixRepr, rep;

        // the infix postfix and prefix notations of the tree
        private String infix, postfix, prefix;

        // the tree matrix as a string
        private String treeString;

        // should the tree be a math expression
        private Boolean isMathExpression;

        // the actual tree
        private Tree t;

        // the duration of the tree construction
        // and the toString conversion
        private long duration, toStringDuration;

        public Testcase(String infixRepr, String rep) {

            // build the representations
            try {
                this.rep = new Representation(rep);

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            try {
                this.infixRepr = new Representation(infixRepr);

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            // build the tree
            long startTime = System.currentTimeMillis();
            this.t = new Tree(this.infixRepr, this.rep);
            this.duration = System.currentTimeMillis() - startTime;
        }

        public void setNotationStrings(String infix, String postfix, String prefix) {
            this.infix = infix;
            this.postfix = postfix;
            this.prefix = prefix;
        }

        public void setTreeMatrixString(String treeMatrix) {
            this.treeString = treeMatrix;
        }

        public void setIsMathExpression(Boolean isMathExpression) {
            this.isMathExpression = isMathExpression;
        }

        // run the tests and compare and finally print the outcome
        public void test() {
            System.out.printf("The tree assembly took %dms\n", this.duration);

            // convert the tree to string
            this.toStringDuration = System.currentTimeMillis();
            String treeStringTemp = t.toString();
            this.toStringDuration = System.currentTimeMillis() - this.toStringDuration;

            // print the result of the to tree convertion
            System.out.println("\t" + (this.treeString.equals(treeStringTemp)
                    ? String.format("successfully converted the tree to string. duration: %dms", this.toStringDuration)
                    : "failed converting to string"));

            // check the infix, prefix and postfix notations
            System.out
                    .println(t.toInfix().equals(this.infix) ? "\tInfix passed" : "Infix didnt pass :c\n" + t.toInfix());
            System.out.println(t.toPostfix().equals(this.postfix) ? "\tPostfix passed"
                    : "Postfix didnt pass :c\n" + t.toPostfix());
            System.out.println(
                    t.toPrefix().equals(this.prefix) ? "\tPrefix passed" : "Prefix didnt pass :c\n" + t.toPrefix());

            // check if is math expression
            System.out.println(t.isMathExpression() == this.isMathExpression ? "\tMathExpr passed" : "MathExpr didnt pass");
            System.out.println("");
        }
    }

    public static void main(String[] args) {
        
        Testcase test1 = new Testcase("inf=6,5,9,7,2,4,1,8,3", "pos=5,7,9,6,1,4,2,3,8");
        test1.setIsMathExpression(false);
        test1.setNotationStrings("659724183", "579614238", "826957413");
        test1.setTreeMatrixString("      (8)   \n    /     \\ \n   (2)   (3)\n /     \\    \n(6)   (4)   \n    \\     \\ \n   (9)   (1)\n /     \\    \n(5)   (7)   \n            \n");
        test1.test();

        Testcase test2 = new Testcase("inf=7,3,4,2,1,6,5,8", "pre=4,7,3,2,6,1,5,8");
        test2.setIsMathExpression(false);
        test2.setNotationStrings("73421658", "37185624", "47326158");
        test2.setTreeMatrixString("   (4)            \n /     \\          \n(7)   (2)         \n    \\     \\       \n   (3)   (6)      \n       /     \\    \n      (1)   (5)   \n                \\ \n               (8)\n                  \n");
        test2.test();

        Testcase test3 = new Testcase("inf=3,5,9,6,1,4,8,2,7", "pos=5,6,9,3,8,2,4,7,1");
        test3.setIsMathExpression(false);
        test3.setNotationStrings("359614827", "569382471", "139567428");
        test3.setTreeMatrixString("      (1)      \n    /     \\    \n(3)         (7)\n    \\     /    \n   (9)   (4)   \n /     \\     \\ \n(5)   (6)   (2)\n          /    \n         (8)   \n               \n");
        test3.test();

        Testcase test4 = new Testcase("inf=a,*,b,+,c,/,d", "pos=a,b,*,c,d,/,+");
        test4.setIsMathExpression(false);
        test4.setNotationStrings("a*b+c/d", "ab*cd/+", "+*ab/cd");
        test4.setTreeMatrixString("         (+)         \n       /     \\       \n   (*)         (/)   \n /     \\     /     \\ \n(a)   (b)   (c)   (d)\n                     \n");
        test4.test();

        Testcase test5 = new Testcase("inf=4,*,3,+,5,/,6", "pos=4,3,*,5,6,/,+");
        test5.setIsMathExpression(true);
        test5.setNotationStrings("4*3+5/6", "43*56/+", "+*43/56");
        test5.setTreeMatrixString("         (+)         \n       /     \\       \n   (*)         (/)   \n /     \\     /     \\ \n(4)   (3)   (5)   (6)\n                     \n");
        test5.test();


        Testcase test6 = new Testcase("inf=a,*,3,+,5,/,6", "pos=a,3,*,5,6,/,+");
        test6.setIsMathExpression(false);
        test6.setNotationStrings("a*3+5/6", "a3*56/+", "+*a3/56");
        test6.setTreeMatrixString("         (+)         \n       /     \\       \n   (*)         (/)   \n /     \\     /     \\ \n(a)   (3)   (5)   (6)\n                     \n");
        test6.test();
    }
}
