package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    WeightedQuickUnionUF grid;
    int length;

    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("N must be an integer bigger than 0.");
        }
        WeightedQuickUnionUF grid = new WeightedQuickUnionUF((int) Math.pow(N, 2));
        length = N;
    }

    public void open(int row, int col) {

    }

    private int calcArrayIndex(int row, int col) {
        if (row > length - 1 || col > length - 1) {
            throw new IndexOutOfBoundsException("The row or column number is out of length of the grid.");
        }
        return length * row + col;
    }
}