/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private final int n;
    private final int[][] tiles;
    private final int hammingValue, manhattanValue;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {

        this.n = tiles[0].length;
        this.tiles = deepCopyIntMatrix(tiles);

        int hammingCounter = 0;
        int manhattanCounter = 0;

        int size = n * n;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] != 0) {
                    if (tiles[i][j] != (i * n + j + 1) % size) {
                        hammingCounter++;
                    }
                    manhattanCounter += Math.abs((tiles[i][j] + size - 1) % size / n - i);
                    manhattanCounter += Math.abs((tiles[i][j] + size - 1) % size % n - j);
                }
            }
        }

        hammingValue = hammingCounter;
        manhattanValue = manhattanCounter;
    }

    private static int[][] deepCopyIntMatrix(int[][] matrix) {
        int[][] result = new int[matrix.length][];
        for (int r = 0; r < matrix.length; r++) {
            result[r] = matrix[r].clone();
        }
        return result;
    }

    // string representation of this board
    public String toString() {

        StringBuilder s = new StringBuilder();

        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(tiles[i][j]);
                if (j == n - 1) {
                    s.append("\n");
                }
                else {
                    s.append(" ");
                }
            }
        }

        return s.toString();
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        return hammingValue;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        return manhattanValue;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {

        // Check null
        if (y == null) {
            return false;
        }

        // Check reference
        if (y == this) {
            return true;
        }

        // Check type
        if (y.getClass() != this.getClass()) {
            return false;
        }

        Board other = (Board) y;

        // Check dimension
        if (n != other.dimension()) {
            return false;
        }

        // General case
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.tiles[i][j] != other.tiles[i][j])
                    return false;
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {

        int[][] newTiles;
        List<Board> neighborBoards = new ArrayList<Board>();

        int iEmpty = -1;
        int jEmpty = -1;

        outerloop:
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) {
                    iEmpty = i;
                    jEmpty = j;
                    break outerloop;
                }
            }
        }

        if (iEmpty > 0) {
            newTiles = deepCopyIntMatrix(tiles);

            newTiles[iEmpty][jEmpty] = tiles[iEmpty - 1][jEmpty];
            newTiles[iEmpty - 1][jEmpty] = 0;

            neighborBoards.add(new Board(newTiles));
        }
        if (iEmpty < n - 1) {
            newTiles = deepCopyIntMatrix(tiles);

            newTiles[iEmpty][jEmpty] = tiles[iEmpty + 1][jEmpty];
            newTiles[iEmpty + 1][jEmpty] = 0;

            neighborBoards.add(new Board(newTiles));
        }
        if (jEmpty > 0) {
            newTiles = deepCopyIntMatrix(tiles);

            newTiles[iEmpty][jEmpty] = tiles[iEmpty][jEmpty - 1];
            newTiles[iEmpty][jEmpty - 1] = 0;

            neighborBoards.add(new Board(newTiles));
        }
        if (jEmpty < n - 1) {
            newTiles = deepCopyIntMatrix(tiles);

            newTiles[iEmpty][jEmpty] = tiles[iEmpty][jEmpty + 1];
            newTiles[iEmpty][jEmpty + 1] = 0;

            neighborBoards.add(new Board(newTiles));
        }

        return neighborBoards;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {

        int i1, j1, i2, j2;
        int[][] swappedtiles = deepCopyIntMatrix(tiles);
        int swap;

        i1 = 0;
        j1 = 0;
        i2 = n - 1;
        j2 = n - 1;

        if (tiles[i1][j1] == 0) {
            j1 = 1;
        }
        if (tiles[i2][j2] == 0) {
            j2 = n - 2;
        }

        swap = tiles[i1][j1];
        swappedtiles[i1][j1] = tiles[i2][j2];
        swappedtiles[i2][j2] = swap;

        return new Board(swappedtiles);
    }

    // unit testing (not graded)
    public static void main(String[] args) {

        int[][] array = new int[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                array[i][j] = i * 3 + j;
            }
        }

        Board board = new Board(array);

        StdOut.println(board);

        StdOut.println("Hamming: " + board.hamming());
        StdOut.println("Manhattan: " + board.manhattan());

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                array[i][j] = (i * 3 + j + 1) % 9;
            }
        }

        Board board1 = new Board(array);

        StdOut.println(board1);

        StdOut.println("Hamming: " + board1.hamming());
        StdOut.println("Manhattan: " + board1.manhattan());

        for (Board board2 : board1.neighbors()) {
            StdOut.println(board2);
        }
    }

}
