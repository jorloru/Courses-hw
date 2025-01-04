/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class PointSET {

    private SET<Point2D> set;

    // construct an empty set of points
    public PointSET() {
        set = new SET<Point2D>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return set.isEmpty();
    }

    // number of points in the set
    public int size() {
        return set.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {

        if (p == null) {
            throw new IllegalArgumentException();
        }

        set.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {

        if (p == null) {
            throw new IllegalArgumentException();
        }

        return set.contains(p);
    }

    // draw all points to standard draw
    public void draw() {

        // StdDraw.setPenRadius(0.01);
        // StdDraw.setPenColor(StdDraw.BLACK);

        for (Point2D p : set) {
            p.draw();
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {

        if (rect == null) {
            throw new IllegalArgumentException();
        }

        SET<Point2D> subset = new SET<Point2D>();

        for (Point2D p : set) {
            if (rect.contains(p)) {
                subset.add(p);
            }
        }

        return subset;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {

        if (p == null) {
            throw new IllegalArgumentException();
        }

        if (set.size() == 0) {
            return null;
        }

        double bestdistance = Double.POSITIVE_INFINITY;
        Point2D bestpoint = null;

        for (Point2D point : set) {
            if (p.distanceSquaredTo(point) < bestdistance) {
                bestdistance = p.distanceSquaredTo(point);
                bestpoint = point;
            }
        }

        return bestpoint;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {

        PointSET set = new PointSET();
        Point2D dummypoint;

        while (!StdIn.isEmpty()) {
            set.insert(new Point2D(StdIn.readDouble(), StdIn.readDouble()));
        }

        StdOut.println("All points inserted, there are " + set.size() + " points.");

        /* StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(StdDraw.GREEN);

        RectHV rect = new RectHV(0.0, 0.0, 1.0, 0.5);
        rect.draw();

        StdDraw.setPenColor(StdDraw.BLACK);

        for (Point2D p : set.range(rect)) {
            p.draw();
        }*/

        set.draw();

        StdDraw.setPenRadius(0.02);
        StdDraw.setPenColor(StdDraw.RED);

        dummypoint = new Point2D(0.2, 0.2);
        dummypoint.draw();

        StdDraw.setPenColor(StdDraw.GREEN);

        set.nearest(dummypoint).draw();
    }
}
