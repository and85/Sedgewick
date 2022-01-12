/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.ArrayList;

public class Board {

    public int[][] board;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        //this.board = tiles;
        this.board = new int[tiles.length][];
        for (int r = 0; r < tiles.length; r++) {
            board[r] = new int[tiles.length];
            for (int c = 0; c < tiles.length; c++) {
                board[r][c] = tiles[r][c];
            }
        }
    }

    // string representation of this board
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(this.board.length + "\n");

        for (int r = 0; r < this.board.length; r++) {
            for (int c = 0; c < this.board[r].length; c++) {
                result.append(String.format("%2d ", this.board[r][c]));
            }

            result.append("\n");
        }



        return result.toString();
    }

    // board dimension n
    public int dimension() {
        return this.board.length;
    }

    // number of tiles out of place
    public int hamming() {
        int result = 0;

        int correctNumber = 1;
        for (int r = 0; r < this.board.length; r++)
            for (int c = 0; c < this.board[r].length; c++) {
                if (this.board[r][c] != 0 && this.board[r][c] != correctNumber)
                    result++;
                correctNumber++;
            }

        return result;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int result = 0;

        for (int r = 0; r < this.board.length; r++)
            for (int c = 0; c < this.board[r].length; c++) {
                if (this.board[r][c] == 0) continue;

                result += Math.abs(getCorrectRow(this.board[r][c]) - r) +
                        Math.abs(getCorrectColumn(this.board[r][c]) - c);
            }

        return result;
    }

    private int getCorrectRow(int tile) {
        return (tile - 1) / this.board.length;
    }

    private int getCorrectColumn(int tile) {
        return (tile - 1) % this.board.length;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;

        if (!(y instanceof Board))
            return false;

        Board that = (Board)y;
        if (this.board.length != that.board.length) return false;

        for (int r = 0; r < this.board.length; r++)
        for (int c = 0; c < this.board[r].length; c++) {
            if (this.board[r].length != that.board[r].length) return false;

            if (this.board[r][c] != that.board[r][c]) return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = 17;
        for (int r = 0; r < this.board.length; r++)
            for (int c = 0; c < this.board[r].length; c++) {
                result = 31 * result + board[r][c];
            }

        return result;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        ArrayList<Board> neighbors = new ArrayList<>();

        int zeroRow = -1, zeroColumn = -1;
        for (int r = 0; r < this.board.length; r++) {
            for (int c = 0; c < this.board[r].length; c++) {
                if (this.board[r][c] == 0) {
                    zeroRow = r;
                    zeroColumn = c;
                    break;
                }
            }

            if (zeroRow != -1) break;
        }

        if (zeroRow - 1 >= 0) {
            Board clone = new Board(this.board);
            clone.board[zeroRow][zeroColumn] = clone.board[zeroRow - 1][zeroColumn];
            clone.board[zeroRow - 1][zeroColumn] = 0;

            neighbors.add(clone);
        }

        if (zeroRow + 1 < this.board.length) {
            Board clone = new Board(this.board);
            clone.board[zeroRow][zeroColumn] = clone.board[zeroRow + 1][zeroColumn];
            clone.board[zeroRow + 1][zeroColumn] = 0;

            neighbors.add(clone);
        }

        if (zeroColumn - 1 >= 0) {
            Board clone = new Board(this.board);

            clone.board[zeroRow][zeroColumn] = clone.board[zeroRow][zeroColumn - 1];
            clone.board[zeroRow][zeroColumn - 1] = 0;

            neighbors.add(clone);
        }

        if (zeroColumn + 1 < this.board.length) {
            Board clone = new Board(this.board);

            clone.board[zeroRow][zeroColumn] = clone.board[zeroRow][zeroColumn + 1];
            clone.board[zeroRow][zeroColumn + 1] = 0;

            neighbors.add(clone);
        }

        return neighbors;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        Board twin = new Board(this.board);

        for (int r = 0; r < this.board.length; r++)
            for (int c = 0; c < this.board[r].length - 1; c++) {
                if (twin.board[r][c] != 0 && twin.board[r][c + 1] != 0) {
                    int tmp = twin.board[r][c];
                    twin.board[r][c] = twin.board[r][c + 1];
                    twin.board[r][c + 1] = tmp;

                    return twin;
                }
            }

        return null;
    }

    // unit testing (not graded)
    public static void main(String[] args) {

    }

}
