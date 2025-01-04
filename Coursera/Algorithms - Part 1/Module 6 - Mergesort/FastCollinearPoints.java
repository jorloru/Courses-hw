/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class FastCollinearPoints {

    private LineSegment[] lineSegments;
    private int size;

    // Finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {

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

        double currentSlope, newSlope;
        int currentIndex;
        Point[] otherPoints;
        Point[] extremes = new Point[2];

        for (int i = 0; i < points.length - 2; i++) {

            otherPoints = Arrays.copyOfRange(points, i + 1, points.length);

            Arrays.sort(otherPoints, points[i].slopeOrder());

            // First iteration
            if (points[i].compareTo(otherPoints[0]) < 0) {
                extremes[0] = points[i];
                extremes[1] = otherPoints[0];
            }
            else {
                extremes[0] = otherPoints[0];
                extremes[1] = points[i];
            }

            currentSlope = points[i].slopeTo(otherPoints[0]);
            currentIndex = 1;

            // Other iterations
            for (int j = 1; j < otherPoints.length; j++) {

                newSlope = points[i].slopeTo(otherPoints[j]);

                if (newSlope == currentSlope) {
                    if (otherPoints[j].compareTo(extremes[0]) < 0) {
                        extremes[0] = otherPoints[j];
                    }
                    else if (otherPoints[j].compareTo(extremes[1]) > 0) {
                        extremes[1] = otherPoints[j];
                    }
                    currentIndex++;
                }
                else {

                    // If collinear points amounted to 4 or more, store segment
                    if (currentIndex >= 3) {
                        addSegment(extremes[0], extremes[1]);
                    }

                    // Reset variables
                    if (points[i].compareTo(otherPoints[j]) < 0) {
                        extremes[0] = points[i];
                        extremes[1] = otherPoints[j];
                    }
                    else {
                        extremes[0] = otherPoints[j];
                        extremes[1] = points[i];
                    }

                    currentSlope = newSlope;
                    currentIndex = 1;
                }
            }
            if (currentIndex >= 3) {
                addSegment(extremes[0], extremes[1]);
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

    // The number of line segments
    public int numberOfSegments() {
        return size;
    }

    // The line segments
    public LineSegment[] segments() {
        return lineSegments.clone();
    }

    public static void main(String[] args) {

        Point[] points = new Point[11];

        points[0] = new Point(0, 3);
        points[1] = new Point(1, 2);
        points[2] = new Point(2, 1);
        points[3] = new Point(3, 0);
        points[4] = new Point(0, 0);
        points[5] = new Point(1, 1);
        points[6] = new Point(4, 4);
        points[7] = new Point(0, 2);
        points[8] = new Point(0, 1);
        points[9] = new Point(3, 3);
        points[10] = new Point(2, 2);

        FastCollinearPoints collinear = new FastCollinearPoints(points);
        LineSegment[] segments = collinear.segments();

        for (LineSegment segment : segments) {
            StdOut.println(segment);
        }

    }
}
