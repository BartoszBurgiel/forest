public class TreeTest {
    public static void main(String[] args) {

        // define the postfix and infix representations
        Representation postfix = new Representation("pos=5,7,9,6,1,4,2,3,8");
        Representation infix = new Representation("inf=6,5,9,7,2,4,1,8,3");

        // create a tree
        Tree t = new Tree(infix, postfix);
        String want = "      (8)   \n    /     \\ \n   (2)   (3)\n /     \\    \n(6)   (4)   \n    \\     \\ \n   (9)   (1)\n /     \\    \n(5)   (7)   \n            \n";

        System.out.println(want.equals(t.toString()) ? "passed" : "didnt pass :c\n"+t.toString());

        // define the postfix and infix representations
        postfix = new Representation("pre=4,7,3,2,6,1,5,8");
        infix = new Representation("inf=7,3,4,2,1,6,5,8");

        // create a tree
        t = new Tree(infix, postfix);
        want = "   (4)            \n /     \\          \n(7)   (2)         \n    \\     \\       \n   (3)   (6)      \n       /     \\    \n      (1)   (5)   \n                \\ \n               (8)\n                  \n";
        System.out.println(want.equals(t.toString()) ? "passed" : "didnt pass :c\n"+t.toString());

        // define the postfix and infix representations
        postfix = new Representation("pre=9,6,3,5,1,8,4,2,7");
        infix = new Representation("inf=6,3,9,1,5,4,2,8,7");

        // create a tree
        t = new Tree(infix, postfix);
        want = "      (9)            \n    /     \\          \n(6)         (5)      \n    \\     /     \\    \n   (3)   (1)   (8)   \n             /     \\ \n            (4)   (7)\n                \\    \n               (2)   \n                     \n";
        System.out.println(want.equals(t.toString()) ? "passed" : "didnt pass :c\n"+t.toString());

        // define the postfix and infix representations
        postfix = new Representation("pos=5,6,9,3,8,2,4,7,1");
        infix = new Representation("inf=3,5,9,6,1,4,8,2,7");

        // create a tree
        t = new Tree(infix, postfix);
        want = "      (1)      \n    /     \\    \n(3)         (7)\n    \\     /    \n   (9)   (4)   \n /     \\     \\ \n(5)   (6)   (2)\n          /    \n         (8)   \n               \n";
        System.out.println(want.equals(t.toString()) ? "passed" : "didnt pass :c\n"+t.toString());

        // define the postfix and infix representations
        postfix = new Representation("pos=a,b,*,c,d,/,+");
        infix = new Representation("inf=a,*,b,+,c,/,d");

        // create a tree
        t = new Tree(infix, postfix);
        want = "         (+)         \n       /     \\       \n   (*)         (/)   \n /     \\     /     \\ \n(a)   (b)   (c)   (d)\n                     \n";
        System.out.println(want.equals(t.toString()) ? "passed" : "didnt pass :c\n"+t.toString());


    }
}
