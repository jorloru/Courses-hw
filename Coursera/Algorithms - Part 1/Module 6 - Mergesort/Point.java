/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Comparator;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param x the <em>x</em>-coordinate of the point
     * @param y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        /* YOUR CODE HERE */

        // Check null input
        if (that == null) {
            throw new java.lang.NullPointerException();
        }

        // Edge cases
        if (this.compareTo(that) == 0) {
            return Double.NEGATIVE_INFINITY;
        }
        else if (this.x == that.x) {
            return Double.POSITIVE_INFINITY;
        }
        else if (this.y == that.y) {
            return 0.0;
        }

        // General case
        return (double) (that.y - this.y) / (that.x - this.x);
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     * point (x0 = x1 and y0 = y1);
     * a negative integer if this point is less than the argument
     * point; and a positive integer if this point is greater than the
     * argument point
     */
    public int compareTo(Point that) {
        /* YOUR CODE HERE */

        // Check null input
        if (that == null) {
            throw new java.lang.NullPointerException();
        }

        if (this.y > that.y) {
            return 1;
        }
        else if (this.y < that.y) {
            return -1;
        }
        else {
            return Integer.compare(this.x, that.x);
        }
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        /* YOUR CODE HERE */
        return new SlopeOrder();
    }

    private class SlopeOrder implements Comparator<Point> {

        public int compare(Point p1, Point p2) {

            return Double.compare(slopeTo(p1), slopeTo(p2));
        }
    }


    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        /* YOUR CODE HERE */

        // Set StdDraw
        StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);

        int n = Integer.parseInt(StdIn.readString());

        Point[] p = new Point[n];

        for (int i = 0; i < n; i++) {

            p[i] = new Point(Integer.parseInt(StdIn.readString()),
                             Integer.parseInt(StdIn.readString()));
            p[i].draw();
        }

        // Test slope calculation

        StdOut.println("Slope calculation from first to second point:");
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {

                StdOut.println("\t" + p[i] + "\t" + p[j] + "\t" + p[i].slopeTo(p[j]));
            }
        }
        StdOut.println();

        // Test sorting by natural order

        Point[] pOrdered = p.clone();
        Arrays.sort(pOrdered);

        StdOut.println("Points ordered by natural order:");
        for (int i = 0; i < n; i++) {
            StdOut.println("\t" + pOrdered[i]);
        }
        StdOut.println();

        // Test sorting by slope order to point with least natural order

        Arrays.sort(pOrdered, p[0].slopeOrder());

        StdOut.println("Points ordered by slope order to " + p[0] + ":");
        for (int i = 0; i < n; i++) {
            StdOut.println("\t" + pOrdered[i] + "\t(Slope " + p[0].slopeTo(pOrdered[i]) + ")");
        }
        StdOut.println();
    }
}
