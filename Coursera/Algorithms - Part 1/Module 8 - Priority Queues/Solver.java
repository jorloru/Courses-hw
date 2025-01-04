/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.List;

public class Solver {

    private SearchNode solutionNode;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {

        // Check if null
        if (initial == null) {
            throw new IllegalArgumentException("Initial game state cannot be null.");
        }

        // Check if already solution
        if (initial.isGoal()) {
            solutionNode = new SearchNode(initial, 0, null);
            return;
        }

        Board alt = initial.twin();

        MinPQ<SearchNode> pq = new MinPQ<SearchNode>();
        MinPQ<SearchNode> pqAlt = new MinPQ<SearchNode>();

        pq.insert(new SearchNode(initial, 0, null));
        pqAlt.insert(new SearchNode(alt, 0, null));

        SearchNode minimum = pq.delMin();
        SearchNode minimumAlt = pqAlt.delMin();

        while (!(minimum.board.isGoal() || minimumAlt.board.isGoal())) {

            for (SearchNode neighbor : minimum.getNeighbors()) {
                if (!neighbor.equalBoards(minimum.previous)) {
                    pq.insert(neighbor);
                }
            }
            minimum = pq.delMin();

            for (SearchNode neighborAlt : minimumAlt.getNeighbors()) {
                if (!neighborAlt.equalBoards(minimumAlt.previous)) {
                    pqAlt.insert(neighborAlt);
                }
            }
            minimumAlt = pqAlt.delMin();
        }

        if (minimum.board.isGoal()) {
            solutionNode = minimum;
        }
        else {
            solutionNode = null;
        }
    }

    private class SearchNode implements Comparable<SearchNode> {

        Board board;
        int moves, priority;
        SearchNode previous;

        public SearchNode(Board board, int moves, SearchNode previous) {
            this.board = board;
            this.moves = moves;
            this.previous = previous;
            this.priority = board.manhattan() + moves;
        }

        public Iterable<SearchNode> getNeighbors() {

            List<SearchNode> neighbors = new ArrayList<SearchNode>();

            for (Board neighbor : board.neighbors()) {
                neighbors.add(new SearchNode(neighbor, moves + 1, this));
            }

            return neighbors;
        }

        public int compareTo(SearchNode other) {
            return Integer.compare(this.priority, other.priority);
        }

        public boolean equalBoards(SearchNode other) {

            if (other == null) {
                return false;
            }
            return this.board.equals(other.board);
        }
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return !(solutionNode == null);
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable()) {
            return -1;
        }
        return solutionNode.moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {

        if (!isSolvable()) {
            return null;
        }

        Stack<Board> solution = new Stack<Board>();


        SearchNode currentNode = solutionNode;
        solution.push(currentNode.board);
        for (int i = 0; i < solutionNode.moves; i++) {
            currentNode = currentNode.previous;
            solution.push(currentNode.board);
        }

        return solution;
    }

    // test client
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }

    }

}
