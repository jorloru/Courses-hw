/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean[][] grid;
    private WeightedQuickUnionUF unionfind;
    private int openSiteCount;
    private int size;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {

        // check that n > 0
        if (n <= 0) {
            throw new IllegalArgumentException("n must be positive");
        }

        // initialize variables
        size = n;
        grid = new boolean[n][n];
        unionfind = new WeightedQuickUnionUF(n * n + 2);
        openSiteCount = 0;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {

        // check if row and col are between 1 and n
        checkBounds(row, col);

        // get index of this position in UnionFind
        int index = rowcol2UFindex(row, col);

        // check if site is already open
        if (isOpen(row, col)) {
            return;
        }

        // open site
        grid[row - 1][col - 1] = true;
        openSiteCount += 1;

        // if neighbours are open, unite them
        if ((row > 1) && (isOpen(row - 1, col))) {
            unionfind.union(index, rowcol2UFindex(row - 1, col));
        }
        if ((col > 1) && (isOpen(row, col - 1))) {
            unionfind.union(index, rowcol2UFindex(row, col - 1));
        }
        if ((row < size) && (isOpen(row + 1, col))) {
            unionfind.union(index, rowcol2UFindex(row + 1, col));
        }
        if ((col < size) && (isOpen(row, col + 1))) {
            unionfind.union(index, rowcol2UFindex(row, col + 1));
        }

        // if site is top or bottom, unite it with the corresponding virtual site
        if (row == 1) {
            unionfind.union(index, size * size);
        }
        else if (row == size) {
            unionfind.union(index, size * size + 1);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {

        // check if row and col are between 1 and n
        checkBounds(row, col);

        // return state of site
        return grid[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {

        // check if row and col are between 1 and n
        checkBounds(row, col);

        // get index of this position in UnionFind
        int index = rowcol2UFindex(row, col);

        // compare canonical element of (row,col) with virtual top
        return (unionfind.find(index) == unionfind.find(size * size));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSiteCount;
    }

    // does the system percolate?
    public boolean percolates() {
        // compare canonical element of virtual top with virtual bottom
        return (unionfind.find(size * size) == unionfind.find(size * size + 1));
    }

    private void checkBounds(int row, int col) {
        if ((row < 1) || (row > size) || (col < 1) || (col > size)) {
            throw new IllegalArgumentException("Index not in bounds");
        }
    }

    private int rowcol2UFindex(int row, int col) {
        return (row - 1) * size + (col - 1);
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation percol = new Percolation(2);
        percol.open(2, 1);
        StdOut.println(percol.isFull(2, 1));
        percol.open(1, 1);
        StdOut.println(percol.isFull(2, 1));
        StdOut.println(percol.percolates());
    }
}
