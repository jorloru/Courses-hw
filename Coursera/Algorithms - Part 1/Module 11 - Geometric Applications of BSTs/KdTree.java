/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

public class KdTree {

    // private static final boolean HORIZONTAL = true;
    private static final boolean VERTICAL = false;

    private static final boolean RIGHT = true;
    private static final boolean LEFT = false;

    private Node root;
    private int size;

    // construct an empty set of points
    public KdTree() {
        root = null;
        size = 0;
    }

    private class Node {

        private Point2D point;
        private boolean direction;
        private Node childLeft;
        private Node childRight;

        public Node(Point2D point, boolean direction) {

            this.point = point;
            this.direction = direction;

            childLeft = null;
            childRight = null;
        }

        public Node getChild(boolean branch) {
            if (branch == LEFT) {
                return this.childLeft;
            }
            else {
                return this.childRight;
            }
        }

        public void setChild(Node child, boolean branch) {
            if (branch == LEFT) {
                this.childLeft = child;
            }
            else {
                this.childRight = child;
            }
        }
    }

    // is the set empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {

        if (p == null) {
            throw new IllegalArgumentException();
        }

        if (root == null) {
            root = new Node(p, VERTICAL);
            size++;
            return;
        }

        Node currentNode = root;
        Node lastNode = root; // Initialization not necessary, stupid IDE

        boolean branch = LEFT;

        while (currentNode != null) {

            lastNode = currentNode;

            if (currentNode.direction == VERTICAL) {
                if (p.x() < currentNode.point.x()) {
                    currentNode = currentNode.childLeft;
                    branch = LEFT;
                }
                else if (p.x() > currentNode.point.x()) {
                    currentNode = currentNode.childRight;
                    branch = RIGHT;
                }
                else {
                    if (p.y() != currentNode.point.y()) {
                        currentNode = currentNode.childRight;
                        branch = RIGHT;
                    }
                    else {
                        return;
                    }
                }
            }
            else {
                if (p.y() < currentNode.point.y()) {
                    currentNode = currentNode.childLeft;
                    branch = LEFT;
                }
                else if (p.y() > currentNode.point.y()) {
                    currentNode = currentNode.childRight;
                    branch = RIGHT;
                }
                else {
                    if (p.x() != currentNode.point.x()) {
                        currentNode = currentNode.childRight;
                        branch = RIGHT;
                    }
                    else {
                        return;
                    }
                }
            }
        }
        lastNode.setChild(new Node(p, !lastNode.direction), branch);
        size++;
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {

        if (p == null) {
            throw new IllegalArgumentException();
        }

        Node currentNode = root;

        while (currentNode != null) {

            if (currentNode.direction == VERTICAL) {
                if (p.x() < currentNode.point.x()) {
                    currentNode = currentNode.childLeft;
                }
                else if (p.x() > currentNode.point.x()) {
                    currentNode = currentNode.childRight;
                }
                else {
                    if (p.y() != currentNode.point.y()) {
                        currentNode = currentNode.childRight;
                    }
                    else {
                        return true;
                    }
                }
            }
            else {
                if (p.y() < currentNode.point.y()) {
                    currentNode = currentNode.childLeft;
                }
                else if (p.y() > currentNode.point.y()) {
                    currentNode = currentNode.childRight;
                }
                else {
                    if (p.x() != currentNode.point.x()) {
                        currentNode = currentNode.childRight;
                    }
                    else {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // draw all points to standard draw
    public void draw() {

        StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(StdDraw.BLACK);

        String binary;
        Node currentNode;

        if (isEmpty()) {
            return;
        }

        root.point.draw();

        int level = 1;
        int drawnPoints = 1;
        int currentLevel;
        char nextChar;

        while (drawnPoints < size) {

            forloop:
            for (int i = 0; i < Math.pow(2, level); i++) {

                binary = Integer.toBinaryString(i);

                currentNode = root;
                currentLevel = 0;

                while (currentLevel < level) {

                    if (binary.length() < (currentLevel + 1)) {
                        nextChar = '0'; // This is to avoid extending the string
                    }
                    else {
                        nextChar = binary.charAt(binary.length() - currentLevel - 1);
                    }

                    if (nextChar == '0') {
                        currentNode = currentNode.childLeft;
                    }
                    else {
                        currentNode = currentNode.childRight;
                    }

                    if (currentNode == null) {
                        continue forloop;
                    }

                    currentLevel++;
                }
                currentNode.point.draw();
                drawnPoints++;
            }
            level++;
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {

        if (rect == null) {
            throw new IllegalArgumentException();
        }

        ArrayList<Point2D> contained = new ArrayList<Point2D>();
        Queue<Node> nodesToSearch = new Queue<Node>();

        Node currentNode;

        nodesToSearch.enqueue(root);

        // Search for a point contained in rect or whose segment intersects rect
        while (!nodesToSearch.isEmpty()) {

            currentNode = nodesToSearch.dequeue();

            if (rect.contains(currentNode.point)) {
                contained.add(currentNode.point);
                if (currentNode.childLeft != null) {
                    nodesToSearch.enqueue(currentNode.childLeft);
                }
                if (currentNode.childRight != null) {
                    nodesToSearch.enqueue(currentNode.childRight);
                }
            }
            else {
                if (currentNode.direction == VERTICAL) {
                    if (rect.xmax() <= currentNode.point.x()) {
                        if (currentNode.childLeft != null) {
                            nodesToSearch.enqueue(currentNode.childLeft);
                        }
                    }
                    else if (rect.xmin() >= currentNode.point.x()) {
                        if (currentNode.childRight != null) {
                            nodesToSearch.enqueue(currentNode.childRight);
                        }
                    }
                    else {
                        if (currentNode.childLeft != null) {
                            nodesToSearch.enqueue(currentNode.childLeft);
                        }
                        if (currentNode.childRight != null) {
                            nodesToSearch.enqueue(currentNode.childRight);
                        }
                    }
                }
                else {
                    if (rect.ymax() <= currentNode.point.y()) {
                        if (currentNode.childLeft != null) {
                            nodesToSearch.enqueue(currentNode.childLeft);
                        }
                    }
                    else if (rect.ymin() >= currentNode.point.y()) {
                        if (currentNode.childRight != null) {
                            nodesToSearch.enqueue(currentNode.childRight);
                        }
                    }
                    else {
                        if (currentNode.childLeft != null) {
                            nodesToSearch.enqueue(currentNode.childLeft);
                        }
                        if (currentNode.childRight != null) {
                            nodesToSearch.enqueue(currentNode.childRight);
                        }
                    }
                }
            }
        }
        return contained;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {

        if (p == null) {
            throw new IllegalArgumentException();
        }

        Node bestNode = root; // This should be null, stupid IDE
        double bestDistance = Double.POSITIVE_INFINITY;
        double bestDistanceSquared = Double.POSITIVE_INFINITY; // Stupid auto-grader

        Stack<Node> nodesToSearchAgain = new Stack<Node>();

        Node currentNode = root;

        boolean branch;
        boolean firstTimeSearch = true;

        while (true) {

            if (firstTimeSearch) {

                // Check if node is nearest up to now
                if (p.distanceSquaredTo(currentNode.point) < bestDistanceSquared) {
                    bestNode = currentNode;
                    bestDistanceSquared = p.distanceSquaredTo(currentNode.point);
                    bestDistance = Math.sqrt(bestDistanceSquared);
                }

                // Get direction of next best step
                if (currentNode.direction == VERTICAL) {
                    branch = p.x() > currentNode.point.x();
                }
                else {
                    branch = p.y() > currentNode.point.y();
                }

                // Store to check worst step later
                if (currentNode.getChild(!branch) != null) {
                    nodesToSearchAgain.push(currentNode);
                }

                // Move to next step. If null, retrieve a 'check later' node
                if (currentNode.getChild(branch) != null) {
                    currentNode = currentNode.getChild(branch);
                }
                else {
                    if (!nodesToSearchAgain.isEmpty()) {
                        currentNode = nodesToSearchAgain.pop();
                        firstTimeSearch = false;
                    }
                    else {
                        break;
                    }
                }
            }
            else {

                if (currentNode.direction == VERTICAL) {

                    branch = p.x() > currentNode.point.x();

                    if (bestDistance >= Math.abs(p.x() - currentNode.point.x())) {
                        if (currentNode.getChild(!branch) != null) {
                            currentNode = currentNode.getChild(!branch);
                            firstTimeSearch = true;
                        }
                    }
                    else {
                        if (!nodesToSearchAgain.isEmpty()) {
                            currentNode = nodesToSearchAgain.pop();
                        }
                        else {
                            break;
                        }
                    }
                }
                else {

                    branch = p.y() > currentNode.point.y();

                    if (bestDistance >= Math.abs(p.y() - currentNode.point.y())) {
                        if (currentNode.getChild(!branch) != null) {
                            currentNode = currentNode.getChild(!branch);
                            firstTimeSearch = true;
                        }
                    }
                    else {
                        if (!nodesToSearchAgain.isEmpty()) {
                            currentNode = nodesToSearchAgain.pop();
                        }
                        else {
                            break;
                        }
                    }
                }
            }
        }
        return bestNode.point;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {

        KdTree kdtree = new KdTree();
        Point2D dummypoint;

        while (!StdIn.isEmpty()) {
            dummypoint = new Point2D(StdIn.readDouble(), StdIn.readDouble());
            kdtree.insert(dummypoint);
            /* if (!kdtree.contains(dummypoint)) {
                StdOut.println("Hmmm, fishy.");
            }*/
        }

        StdOut.println("All points inserted, there are " + kdtree.size() + " points.");

        /* StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(StdDraw.GREEN);

        RectHV rect = new RectHV(0.0, 0.0, 1.0, 1.0);
        rect.draw();

        StdDraw.setPenColor(StdDraw.BLACK);

        for (Point2D p : kdtree.range(rect)) {
            p.draw();
        }*/

        Point2D query = new Point2D(0.431, 0.939);
        Point2D answer = kdtree.nearest(query);

        StdOut.println("The nearest point is " + answer);

        kdtree.draw();

        StdDraw.setPenRadius(0.02);
        StdDraw.setPenColor(StdDraw.RED);
        query.draw();

        StdDraw.setPenColor(StdDraw.GREEN);
        answer.draw();
    }
}
