/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {

        RandomizedQueue<String> rqueue = new RandomizedQueue<String>();

        while (!StdIn.isEmpty()) {
            rqueue.enqueue(StdIn.readString());
        }

        for (int i = 0; i < Integer.parseInt(args[0]); i++) {
            StdOut.println(rqueue.dequeue());
        }
    }
}
