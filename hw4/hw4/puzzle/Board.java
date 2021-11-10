package hw4.puzzle;

import edu.princeton.cs.algs4.Queue;

import java.util.Arrays;

public class Board implements WorldState {
    int[][] tiles;
    int N;

    public Board(int[][] tiles) {
        this.tiles = tiles;
        this.N = tiles.length;
    }

    public int tileAt(int i, int j) {
        if (i >= N || j >= N || i < 0 || j < 0) {
            throw new IndexOutOfBoundsException("Wrong i or j given.");
        }
        return tiles[i][j];
    }

    public int size() {
        return N;
    }

    /**
     * To calculate all neighbor status of the 8-puzzle board.
     * Copied and pasted from http://joshh.ug/neighbors.html.
     * @return All neighbors stored in an queue.
     */
    @Override
    public Iterable<WorldState> neighbors() {
        Queue<WorldState> neighbors = new Queue<>();
        int hug = size();
        int bug = -1;
        int zug = -1;
        for (int rug = 0; rug < hug; rug++) {
            for (int tug = 0; tug < hug; tug++) {
                if (tileAt(rug, tug) == 0) {
                    bug = rug;
                    zug = tug;
                }
            }
        }
        int[][] ili1li1 = new int[hug][hug];
        for (int pug = 0; pug < hug; pug++) {
            for (int yug = 0; yug < hug; yug++) {
                ili1li1[pug][yug] = tileAt(pug, yug);
            }
        }
        for (int l11il = 0; l11il < hug; l11il++) {
            for (int lil1il1 = 0; lil1il1 < hug; lil1il1++) {
                if (Math.abs(-bug + l11il) + Math.abs(lil1il1 - zug) - 1 == 0) {
                    ili1li1[bug][zug] = ili1li1[l11il][lil1il1];
                    ili1li1[l11il][lil1il1] = 0;
                    Board neighbor = new Board(ili1li1);
                    neighbors.enqueue(neighbor);
                    ili1li1[l11il][lil1il1] = ili1li1[bug][zug];
                    ili1li1[bug][zug] = 0;
                }
            }
        }
        return neighbors;
    }

    public int hamming() {
        int wrongPos = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tiles[i][j] == 0) continue;
                if (iIndex(tiles[i][j]) != i || jIndex(tiles[i][j]) != j) {
                    wrongPos++;
                }
            }
        }
        return wrongPos;
    }

    private int iIndex(int number) {
        return number / N;
    }

    private int jIndex(int number) {
        return number - iIndex(number) * N - 1;
    }

    public int manhattan() {
        int totalMove = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tiles[i][j] == 0) continue;
                totalMove += Math.abs(i - iIndex(tiles[i][j])) + Math.abs(j - jIndex(tiles[i][j]));
            }
        }
        return totalMove;
    }

    @Override
    public int estimatedDistanceToGoal() {
        return manhattan();
    }

    @Override
    public boolean equals(Object y) {
        if (this == y) {
            return true;
        }
        if (y == null || getClass() != y.getClass()) {
            return false;
        }
        Board b = (Board) y;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tiles[i][j] != b.tiles[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }


    /** Returns the string representation of the board. 
      * Uncomment this method. */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

    public static void main(String[] args) {
        int[][] tiles = new int[2][2];
        tiles[0][0] = 0;
        tiles[0][1] = 1;
        tiles[1][0] = 2;
        tiles[1][1] = 3;

        Board b = new Board(tiles);
        System.out.println(b);
    }
}
