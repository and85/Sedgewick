//Implementation requirement.  To implement the A* algorithm, you must use the MinPQ data type for the priority queue.

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;


public class Solver {

    private Board initial;

    //private Board searchNode;
    //private Board prevSearchNode;

    MinPQ<Board> pq;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        this.initial = initial;
        //searchNode = this.initial;

        this.pq = new MinPQ<Board>(new ManhattanComparator());
        //this.pq = new MinPQ<Board>(new HammingComparator());
        this.pq.insert(initial);
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        int ir1 = -1, ic1 = -1, ir2 = -1, ic2 = -1, incorectCounter = 0;
        int correctNum = 1;

        for (int r = 0; r < this.initial.dimension(); r++) {
            for (int c = 0; c < this.initial.dimension(); c++) {

                //if (this.initial.board[r][c] == 0) {
                //    correctNum++;
                //    continue;
                //}

                if (this.initial.board[r][c] != correctNum) {
                    if (ir1 == -1) {
                        ir1 = r;
                        ic1 = c;
                    }
                    else {
                        ir2 = r;
                        ic2 = c;
                    }

                    incorectCounter++;
                    if (incorectCounter > 2) return true;
                }

                correctNum++;
            }
        }

        Board clone = new Board(this.initial.board);
        int tmp = clone.board[ir1][ic1];
        clone.board[ir1][ic1] = clone.board[ir2][ic2];
        clone.board[ir2][ic2] = tmp;

        return !clone.isGoal();
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return 0;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return new SolutionIterable(this.pq);
    }

    public class SolutionIterable implements Iterable<Board> {
        private MinPQ<Board> pq;
        private Board searchNode;
        private Board prevNode;
        HashSet<Board> visitedNodes = new HashSet<Board>();

        public SolutionIterable(MinPQ<Board> pq) {
            this.pq = pq;
        }

        @Override
        public Iterator<Board> iterator() {

            return new Iterator<Board> () {

                @Override
                public boolean hasNext() {
                    return prevNode == null || !searchNode.isGoal();
                    //return !searchNode.isGoal();
                }

                @Override
                public Board next() {
                    prevNode = searchNode;
                    // todo: when pq is empty, it means there is no solution!
                    searchNode = pq.delMin();
                    visitedNodes.add(searchNode);

                    for (Board n: searchNode.neighbors()) {
                        //if (!n.equals(prevNode)) {
                        if (!visitedNodes.contains(n)) {
                            StdOut.println("Neighbor Manhattan: " + n.manhattan());
                            pq.insert(n);
                        }
                    }

                    return searchNode;
                }

                @Override
                public void remove() {
                    throw new UnsupportedOperationException("no changes allowed");
                }
            };
        }
    }

    public class ManhattanComparator implements Comparator<Board> {
        @Override
        public int compare(Board first, Board second) {
            return Integer.compare(first.manhattan(), second.manhattan());
        }
    }

    public class HammingComparator implements Comparator<Board> {
        @Override
        public int compare(Board first, Board second) {
            return Integer.compare(first.hamming(), second.hamming());
        }
    }

    // test client (see below)
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

            int counter = 0;
            for (Board board : solver.solution()) {
                StdOut.println(board);
                StdOut.println("SearchNode Manhattan: " + board.manhattan());
                counter++;
            }
        }
    }
}