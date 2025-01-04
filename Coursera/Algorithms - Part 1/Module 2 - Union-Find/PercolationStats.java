/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private double[] thresholds;

    // Perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {

        // Check validity of input arguments
        if ((n <= 0) || (trials <= 0)) {
            throw new IllegalArgumentException("Input arguments must be positive");
        }

        // Initialize variables
        thresholds = new double[trials];
        Percolation percol;
        int row, col;

        for (int i = 0; i < trials; i++) {
            percol = new Percolation(n);
            while (!percol.percolates()) {

                // Generate a row, col pair at random
                row = StdRandom.uniformInt(n) + 1;
                col = StdRandom.uniformInt(n) + 1;

                // Repeat until row, col is a closed site; open it if it is
                if (percol.isOpen(row, col)) {
                    continue;
                }
                else {
                    percol.open(row, col);
                }
            }
            thresholds[i] = (double) percol.numberOfOpenSites() / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(thresholds);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(thresholds);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(thresholds.length);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(thresholds.length);
    }

    // test client (see below)
    public static void main(String[] args) {
        PercolationStats stats = new PercolationStats(Integer.parseInt(args[0]),
                                                      Integer.parseInt(args[1]));
        StdOut.println("mean                    = " + stats.mean());
        StdOut.println("stddev                  = " + stats.stddev());
        StdOut.println(
                "95% confidence interval = [" + stats.confidenceLo() + ", " + stats.confidenceHi()
                        + "]");
    }
}
