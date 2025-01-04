import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

/******************************************************************************
 *  Compilation:  javac RandomWord.java
 *  Execution:    java RandomWord
 *
 *  Prints "Hello, World". By tradition, this is everyone's first program.
 *
 *  % java HelloWorld
 *  Hello, World
 *
 *  These 17 lines of text are comments. They are not part of the program;
 *  they serve to remind us about its properties. The first two lines tell
 *  us what to type to compile and test the program. The next line describes
 *  the purpose of the program. The next few lines give a sample execution
 *  of the program and the resulting output. We will always include such
 *  lines in our programs and encourage you to do the same.
 *
 ******************************************************************************/

public class RandomWord {

    public static void main(String[] args) {

        String champion = "";
        int counter = 0;

        while (!StdIn.isEmpty()) {

            String word = StdIn.readString();
            counter += 1;

            if (champion.equals("")) {
                champion = word;
            }
            else {
                if (StdRandom.bernoulli(1.0 / counter)) {
                    champion = word;
                }
            }
        }

        StdOut.println(champion);
    }
}
