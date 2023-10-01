/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class BoggleSolver {
    private final Trie dict;
    private Integer[][] adjacent;

    public BoggleSolver(String[] dictionary) {
        if (dictionary == null) throw new IllegalArgumentException();
        dict = new Trie();
        for (String word : dictionary) {
            dict.add(word);
        }
    }

    private void createAdjacencyList(BoggleBoard board) {
        int boardSize = board.cols() * board.rows();
        adjacent = new Integer[boardSize][8];
        for (int i = 0; i < adjacent.length; i++) {
            int row = indexToRow(board, i);
            int rowCount = board.rows();
            int col = indexToCol(board, i);
            int colCount = board.cols();
            int count = 0;
            if (row != 0) {
                adjacent[i][count++] = posToIndex(board, row - 1, col);
            }
            if (row != rowCount - 1) {
                adjacent[i][count++] = posToIndex(board, row + 1, col);
            }
            if (col != 0) {
                adjacent[i][count++] = posToIndex(board, row, col - 1);
            }
            if (col != colCount - 1) {
                adjacent[i][count++] = posToIndex(board, row, col + 1);
            }
            if (col != 0 && row != 0) {
                adjacent[i][count++] = posToIndex(board, row - 1, col - 1);
            }
            if (col != colCount - 1 && row != 0) {
                adjacent[i][count++] = posToIndex(board, row - 1, col + 1);
            }
            if (col != 0 && row != rowCount - 1) {
                adjacent[i][count++] = posToIndex(board, row + 1, col - 1);
            }
            if (col != colCount - 1 && row != rowCount - 1) {
                adjacent[i][count] = posToIndex(board, row + 1, col + 1);
            }
        }
    }

    private int indexToRow(BoggleBoard board, int index) {
        return index / board.cols();
    }

    private int indexToCol(BoggleBoard board, int index) {
        return index - board.cols() * indexToRow(board, index);
    }

    private int posToIndex(BoggleBoard board, int row, int col) {
        return board.cols() * row + col;
    }

    public Iterable<String> getAllValidWords(BoggleBoard board) {
        createAdjacencyList(board);
        SET<String> wordSet = new SET<>();
        for (int i = 0; i < board.cols() * board.rows(); i++) {
            Iterable<String> words = depthFirstSearch(board, i);
            for (String word : words) {
                wordSet.add(word);
            }
        }
        return wordSet;
    }

    private Iterable<String> depthFirstSearch(BoggleBoard board, int s) {
        boolean[] marked = new boolean[board.cols() * board.rows()];
        Bag<String> words = new Bag<>();
        dfs(words, board, s, marked, new StringBuilder());
        return words;
    }

    private void dfs(Bag<String> words, BoggleBoard board, int s, boolean[] marked,
                     StringBuilder word) {
        marked[s] = true;
        if (!dict.prefixQuery(word.toString())) {
            return;
        }
        char letter = board.getLetter(indexToRow(board, s), indexToCol(board, s));
        if (letter == 'Q') word.append("QU");
        else word.append(letter);
        String string = word.toString();
        if (word.length() >= 3 && dict.contains(string)) {
            words.add(string);
        }
        for (Integer w : adjacent[s]) {
            if (w == null) continue;
            if (!marked[w])
                dfs(words, board, w, Arrays.copyOf(marked, marked.length), new StringBuilder(word));
        }
    }

    public int scoreOf(String word) {
        if (word == null) throw new IllegalArgumentException();
        if (!dict.contains(word)) return 0;
        int length = word.length();
        if (length == 3 || length == 4) return 1;
        if (length == 5) return 2;
        if (length == 6) return 3;
        if (length == 7) return 5;
        if (length >= 8) return 11;
        return 0;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(args[1]);
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
    }
}
