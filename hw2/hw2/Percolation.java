package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    WeightedQuickUnionUF grid;
    int length;
    boolean[] isSiteOpen;
    int sitesOpened;
    int virtualTopSiteIndex;
    int virtualBottomSiteIndex;

    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("N must be an integer bigger than 0.");
        }
        grid = new WeightedQuickUnionUF((int) Math.pow(N, 2) + 2);
        length = N;
        virtualTopSiteIndex = (int) Math.pow(N, 2);
        virtualBottomSiteIndex = (int) Math.pow(N, 2) + 1;
        sitesOpened = 0;

        isSiteOpen = new boolean[(int) Math.pow(N, 2) + 2];
        for (int i = 0; i < (int) Math.pow(N, 2) - 1; i++) {
            isSiteOpen[i] = false;
        }
        isSiteOpen[virtualTopSiteIndex] = true;
        isSiteOpen[virtualBottomSiteIndex] = true;
    }

    public void open(int row, int col) {
        if (row == 0 && !isSiteOpen[calcArrayIndex(row, col)]) {
            grid.union(calcArrayIndex(row, col), virtualTopSiteIndex);
        }
        if (row == length - 1 && !isSiteOpen[calcArrayIndex(row, col)]) {
            grid.union(calcArrayIndex(row, col), virtualBottomSiteIndex);
        }
        isSiteOpen[calcArrayIndex(row, col)] = true;
        sitesOpened++;
        connectNeighbour(row, col);
    }

    private void connectNeighbour(int row, int col) {
        if (row != 0 && isSiteOpen[calcArrayIndex(row - 1, col)]) {
            grid.union(calcArrayIndex(row, col), calcArrayIndex(row - 1, col));
        }
        if (row != length - 1 && isSiteOpen[calcArrayIndex(row + 1, col)]) {
            grid.union(calcArrayIndex(row, col), calcArrayIndex(row + 1, col));
        }
        if (col != 0 && isSiteOpen[calcArrayIndex(row, col - 1)]) {
            grid.union(calcArrayIndex(row, col), calcArrayIndex(row, col - 1));
        }
        if (col != length - 1 && isSiteOpen[calcArrayIndex(row, col + 1)]) {
            grid.union(calcArrayIndex(row, col), calcArrayIndex(row, col + 1));
        }
    }

    private int calcArrayIndex(int row, int col) {
        if (row > length - 1 || col > length - 1) {
            throw new IndexOutOfBoundsException("The row or column number is out of length of the grid.");
        }
        return length * row + col;
    }

    public boolean isOpen(int row, int col) {
        return isSiteOpen[calcArrayIndex(row, col)];
    }

    public boolean isFull(int row, int col) {
        return grid.find(calcArrayIndex(row, col)) == grid.find(virtualTopSiteIndex);
    }

    public int numberOfOpenSites() {
        return sitesOpened;
    }

    public boolean percolates() {
        for (int i = 0; i < length; i++) {
            if (isFull(length - 1, i)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {

    }
}