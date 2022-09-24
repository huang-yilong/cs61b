package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private WeightedQuickUnionUF uf;

    private WeightedQuickUnionUF ufWithoutBottom;

    private int N;

    private int openNum = 0;

    private int ufBottom;

    private int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    private int[][] grid;

    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        this.N = N;
        uf = new WeightedQuickUnionUF(N * N + 2);
        ufWithoutBottom = new WeightedQuickUnionUF(N * N + 1);
        ufBottom = N * N + 1;
        grid = new int[N][N];
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!isOpen(row, col)) {
            grid[row][col] = 1;
            unionAround(row, col);
            openNum += 1;
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);
        return grid[row][col] == 1;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row, col);
        return ufWithoutBottom.connected(0, ufIndex(row, col));
    }

    // number of open sites
    public int numberOfOpenSites() {
        return openNum;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.connected(0, ufBottom);
    }

    private void validate(int row, int col) {
        if (row < 0 || row >= N || col < 0 || col >= N) {
            throw new IndexOutOfBoundsException();
        }
    }

    private int ufIndex(int row, int col) {
        return row * N + col + 1;
    }

    private void unionAround(int row, int col) {
        for (int[] dir : directions) {
            int x = row + dir[0];
            int y = col + dir[1];
            if (0 <= x && x < N && 0 <= y && y < N && isOpen(x, y)) {
                uf.union(ufIndex(x, y), ufIndex(row, col));
                ufWithoutBottom.union(ufIndex(row, col), ufIndex(x, y));
            }
        }

        if (row == 0) {
            uf.union(ufIndex(row, col), 0);
            ufWithoutBottom.union(ufIndex(row, col), 0);
        }

        if (row == N - 1) {
            uf.union(ufIndex(row, col), ufBottom);
        }
    }

    // use for unit testing (not required, but keep this here for the autograder)
    public static void main(String[] args) {

    }
}
