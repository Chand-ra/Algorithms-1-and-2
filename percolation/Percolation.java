/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[] isOpen;
    private int openSitesCount = 0;
    private int length;
    private WeightedQuickUnionUF uf;

    public Percolation(int n) {
        length = n;
        uf = new WeightedQuickUnionUF(n * n + 2);
        if (n <= 0) {
            throw new IllegalArgumentException("Edge length cannot be non-positive.");
        }
        isOpen = new boolean[n * n + 2];
    }

    private int index(int row, int col) {
        return length * (row - 1) + col;
    }

    private void rowColError(int row, int col) {
        if (row < 1 || row > length) {
            throw new IllegalArgumentException("Row is outside of prescribed range.");
        }
        if (col < 1 || col > length) {
            throw new IllegalArgumentException("Column is outside of prescribed range.");
        }
    }

    private boolean isConnected(int index1, int index2) {
        return uf.find(index1) == uf.find(index2);
    }

    private boolean isOpenNeighbour(int row, int col) {
        if (row < 1 || row > length || col < 1 || col > length) return false;
        else return isOpen[index(row, col)];
    }

    public void open(int row, int col) {
        rowColError(row, col);
        if (isOpen[index(row, col)]) return;
        isOpen[index(row, col)] = true;

        if (isOpenNeighbour(row - 1, col)) {
            uf.union(index(row, col), index(row - 1, col));
        }
        if (isOpenNeighbour(row + 1, col)) {
            uf.union(index(row, col), index(row + 1, col));
        }
        if (isOpenNeighbour(row, col - 1)) {
            uf.union(index(row, col), index(row, col - 1));
        }
        if (isOpenNeighbour(row, col + 1)) {
            uf.union(index(row, col), index(row, col + 1));
        }
        if (row == 1) {
            uf.union(0, index(row, col));
        }
        if (row == length) {
            uf.union(length * length + 1, index(row, col));
        }
        openSitesCount++;
    }

    public boolean isOpen(int row, int col) {
        rowColError(row, col);
        return isOpen[index(row, col)];
    }

    public boolean isFull(int row, int col) {
        rowColError(row, col);
        if (index(row, col) > length) return isConnected(0, index(row, col));
        else return isOpen(row, col);
    }

    public boolean percolates() {
        return isConnected(length * length + 1, 0);
    }

    public int numberOfOpenSites() {
        return openSitesCount;
    }

    public static void main(String[] args) {
        int n = StdIn.readInt();
        Percolation percolation = new Percolation(n);
        while (!StdIn.isEmpty()) {
            int row = StdIn.readInt();
            int col = StdIn.readInt();
            percolation.open(row, col);
        }

        System.out.println(percolation.numberOfOpenSites());
        System.out.println(percolation.percolates());
    }
}
