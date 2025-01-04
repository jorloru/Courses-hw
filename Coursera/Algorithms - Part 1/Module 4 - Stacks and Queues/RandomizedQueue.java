/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] queue;
    private int size;

    // construct an empty randomized queue
    public RandomizedQueue() {
        this.queue = (Item[]) new Object[1];
        this.size = 0;
    }

    private RandomizedQueue(Item[] queue, int size) {
        this.queue = queue;
        this.size = size;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    private void resize() {
        Item[] newqueue = (Item[]) new Object[2 * queue.length];
        for (int i = 0; i < size; i++) {
            newqueue[i] = queue[i];
        }
        queue = newqueue;
    }

    // add the item
    public void enqueue(Item item) {

        // Check input
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null.");
        }

        // Check if resize is needed
        if (size == queue.length) {
            resize();
        }

        // Add item to queue
        queue[size] = item;

        // Update size
        size++;
    }

    private void swap(int i, int j) {
        Item tmp = queue[i];
        queue[i] = queue[j];
        queue[j] = tmp;
    }

    // remove and return a random item
    public Item dequeue() {

        // Check if empty
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("Cannot retrieve item from empty queue.");
        }

        // Randomize output
        swap(StdRandom.uniformInt(size), size - 1);

        // Retrieve item
        Item item = queue[size - 1];
        queue[size - 1] = null; // Avoid loitering

        // Update size
        size--;

        // Return item
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {

        // Check if empty
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("Cannot retrieve item from empty queue.");
        }

        return queue[StdRandom.uniformInt(size)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {

        private RandomizedQueue<Item> rqueue;

        private RandomizedQueueIterator() {
            rqueue = new RandomizedQueue<Item>(queue.clone(), size);
        }

        public boolean hasNext() {
            return !rqueue.isEmpty();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {

            // Check if empty
            if (!hasNext()) {
                throw new java.util.NoSuchElementException(
                        "Cannot retrieve item from empty queue.");
            }

            return rqueue.dequeue();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {

        RandomizedQueue<Integer> rqueue = new RandomizedQueue<Integer>();

        StdOut.println("Current size is " + rqueue.size());
        StdOut.println("Queue is empty? " + rqueue.isEmpty());

        for (int i = 0; i < 10; i++) {
            rqueue.enqueue(i);
        }
        StdOut.println("Added numbers 0 to 9");

        StdOut.println("Current size is " + rqueue.size());
        StdOut.println("Queue is empty? " + rqueue.isEmpty());

        StdOut.println("Dequeueing all entries");
        for (int i = 0; i < 10; i++) {
            StdOut.println(rqueue.dequeue());
        }

        StdOut.println("Current size is " + rqueue.size());
        StdOut.println("Queue is empty? " + rqueue.isEmpty());

        StdOut.println("Checking iterator independence");

        for (int i = 0; i < 10; i++)
            rqueue.enqueue(i);
        for (int a : rqueue) {
            for (int b : rqueue)
                StdOut.print(a + "-" + b + " ");
            StdOut.println();
        }

        StdOut.println("Attempting Iterator.remove() operation");
        try {
            Iterator<Integer> iter = rqueue.iterator();
            iter.remove();
        }
        catch (UnsupportedOperationException e) {
            StdOut.println(e);
        }
    }

}
