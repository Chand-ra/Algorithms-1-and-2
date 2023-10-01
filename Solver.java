/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class Solver {
    private boolean isSolvable;
    private Node min;
    private int movesCount;

    private class Node {
        Board board;
        int manhattan;
        int moves;
        int priority;
        Node previous;

        public Node(Board given, Node previous) {
            this.board = given;
            this.manhattan = given.manhattan();
            this.previous = previous;
        }
    }

    private class ManhattanPriority implements Comparator<Node> {
        public int compare(Node o1, Node o2) {
            return Integer.compare(o1.priority, o2.priority);
        }
    }

    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();
        Node original = new Node(initial, null);
        Node twin = new Node(initial.twin(), null);
        ManhattanPriority manhattanPriority = new ManhattanPriority();
        MinPQ<Node> originalPQ = new MinPQ<>(manhattanPriority);
        MinPQ<Node> twinPQ = new MinPQ<>(manhattanPriority);

        while (true) {
            if (original.board.isGoal()) {
                movesCount = original.moves;
                min = original;
                isSolvable = true;
                return;
            }
            for (Board neighbour : original.board.neighbors()) {
                if (original.previous == null) {
                    Node node = new Node(neighbour, original);
                    node.moves = 1;
                    node.priority = node.manhattan + node.moves;
                    originalPQ.insert(node);
                }
                else if (!original.previous.board.equals(neighbour)) {
                    Node node = new Node(neighbour, original);
                    node.moves = original.moves + 1;
                    node.priority = node.manhattan + node.moves;
                    originalPQ.insert(node);
                }
            }
            original = originalPQ.delMin();

            if (twin.board.isGoal()) {
                isSolvable = false;
                return;
            }
            for (Board neighbour : twin.board.neighbors()) {
                if (twin.previous == null) {
                    Node node = new Node(neighbour, twin);
                    node.moves = 0;
                    node.priority = node.manhattan + node.moves;
                    twinPQ.insert(node);
                }
                else if (!twin.previous.board.equals(neighbour)) {
                    Node node = new Node(neighbour, twin);
                    node.moves = twin.moves + 1;
                    node.priority = node.manhattan + node.moves;
                    twinPQ.insert(node);
                }
            }
            twin = twinPQ.delMin();
        }

    }

    public boolean isSolvable() {
        return isSolvable;
    }

    public int moves() {
        if (!isSolvable()) return -1;
        return movesCount;
    }

    public Iterable<Board> solution() {
        if (!isSolvable) return null;
        Stack<Board> boardStack = new Stack<Board>();
        while (min.previous != null) {
            boardStack.push(min.board);
            min = min.previous;
        }
        boardStack.push(min.board);
        return boardStack;
    }

    public static void main(String[] args) {
        for (String filename : args) {

            // read in the board specified in the filename
            In in = new In(filename);
            int n = in.readInt();
            int[][] tiles = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    tiles[i][j] = in.readInt();
                }
            }

            // solve the slider puzzle
            Board initial = new Board(tiles);
            Solver solver = new Solver(initial);
            StdOut.println(filename + ": " + solver.moves());
            for (Board move : solver.solution()) {
                System.out.println(move);
            }
        }
    }
}
