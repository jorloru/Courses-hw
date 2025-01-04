/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {

    private Node first, last;
    private int size;

    // construct an empty deque
    public Deque() {
        this.first = null;
        this.last = null;
        this.size = 0;
    }

    private class Node {
        Item item;
        Node previous;
        Node next;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {

        // Check input
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null.");
        }

        // Store previous first node
        Node oldfirst = first;

        // Set up new first node
        first = new Node();
        first.item = item;
        first.next = oldfirst;
        first.previous = null;

        // Update other nodes accordingly
        if (size == 0) {
            last = first;
        }
        else {
            oldfirst.previous = first;
        }
        if (size == 1) {
            last = oldfirst;
        }

        // Update size
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {

        // Check input
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null.");
        }

        // Store previous last node
        Node oldlast = last;

        // Set up new last node
        last = new Node();
        last.item = item;
        last.next = null;
        last.previous = oldlast;

        // Update other nodes accordingly
        if (size == 0) {
            first = last;
        }
        else {
            oldlast.next = last;
        }
        if (size == 1) {
            first = oldlast;
        }

        // Update size
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {

        // Check if empty
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("Cannot retrieve item from empty deque.");
        }

        // Retrieve item
        Item item = first.item;
        first.item = null; // Avoid loitering

        // Modify the affected links
        if (size == 1) {
            first = null;
            last = null;
        }
        else {
            first = first.next;
            first.previous = null;
        }

        // Update size
        size--;

        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {

        // Check if empty
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("Cannot retrieve item from empty deque.");
        }

        // Retrieve item
        Item item = last.item;
        last.item = null; // Avoid loitering

        // Modify the affected links
        if (size == 1) {
            first = null;
            last = null;
        }
        else {
            last = last.previous;
            last.next = null;
        }

        // Update size
        size--;

        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {

        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {

            // Check if empty
            if (!hasNext()) {
                throw new java.util.NoSuchElementException(
                        "Cannot retrieve item from empty deque.");
            }

            Item item = current.item;
            current = current.next;

            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<Integer>();
        deque.addFirst(2);
        deque.addLast(3);
        deque.addFirst(1);
        StdOut.println("Current size is " + deque.size());
        StdOut.println("Deque is empty? " + deque.isEmpty());
        StdOut.println(deque.removeFirst());
        StdOut.println(deque.removeFirst());
        StdOut.println(deque.removeLast());
        StdOut.println("Current size is " + deque.size());
        StdOut.println("Deque is empty? " + deque.isEmpty());

        deque.addFirst(3);
        deque.addFirst(2);
        deque.addFirst(1);

        for (int a : deque) {
            for (int b : deque)
                StdOut.print(a + "-" + b + " ");
            StdOut.println();
        }

        try {
            Iterator<Integer> iter = deque.iterator();
            iter.remove();
        }
        catch (UnsupportedOperationException e) {
            StdOut.println(e);
        }

        // Unit test 12


    }

}
