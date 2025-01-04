/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class BruteCollinearPoints {

    private LineSegment[] lineSegments;
    private int size;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {

        // Check null input
        if (points == null) {
            throw new IllegalArgumentException();
        }
        else {
            for (Point p : points) {
                if (p == null) {
                    throw new IllegalArgumentException();
                }
            }
        }

        // Check no duplicate points
        for (int i = 0; i < points.length - 1; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException();
                }
            }
        }

        lineSegments = new LineSegment[1];
        size = 0;

        Point[] subset = new Point[4];

        double slope;

        for (int i = 0; i < points.length - 3; i++) {
            for (int j = i + 1; j < points.length - 2; j++) {
                slope = points[i].slopeTo(points[j]);
                for (int k = j + 1; k < points.length - 1; k++) {
                    if (points[i].slopeTo(points[k]) == slope) {
                        for (int n = k + 1; n < points.length; n++) {
                            if (points[i].slopeTo(points[n]) == slope) {

                                subset[0] = points[i];
                                subset[1] = points[j];
                                subset[2] = points[k];
                                subset[3] = points[n];

                                Arrays.sort(subset);

                                addSegment(subset[0], subset[3]);
                            }
                        }
                    }
                }
            }
        }
        shrinkToSize();
    }

    private void addSegment(Point p1, Point p2) {
        LineSegment lineSegment = new LineSegment(p1, p2);
        if (lineSegments.length == size) {
            doubleSize();
        }
        lineSegments[size] = lineSegment;
        size++;
    }

    private void doubleSize() {
        LineSegment[] newArray = new LineSegment[2 * lineSegments.length];
        System.arraycopy(lineSegments, 0, newArray, 0, lineSegments.length);
        lineSegments = newArray;
    }

    private void shrinkToSize() {
        if (size < lineSegments.length) {
            LineSegment[] newArray = new LineSegment[size];
            System.arraycopy(lineSegments, 0, newArray, 0, size);
            lineSegments = newArray;
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return size;
    }

    // the line segments
    public LineSegment[] segments() {
        return lineSegments.clone();
    }

    // test client
    public static void main(String[] args) {

        Point[] points = new Point[10];

        points[0] = new Point(0, 3);
        points[1] = new Point(1, 2);
        points[2] = new Point(2, 1);
        points[3] = new Point(3, 0);
        points[4] = new Point(0, 0);
        points[5] = new Point(1, 1);
        points[6] = new Point(2, 2);
        points[7] = new Point(3, 3);
        points[8] = new Point(0, 1);
        points[9] = new Point(0, 2);

        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        LineSegment[] segments = collinear.segments();

        for (LineSegment segment : segments) {
            StdOut.println(segment);
        }
    }
}
