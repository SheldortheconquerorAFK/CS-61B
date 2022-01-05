import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.introcs.StdStats;

import java.awt.Color;

public class SeamCarver {
    Picture p;
    double[][] energyMatrix;

    public SeamCarver(Picture picture) {
        p = picture;
        energyMatrix = new double[height()][width()];
        for (int y = 0; y < height(); y++) {
            for (int x = 0; x < width(); x++) {
                energyMatrix[y][x] = energy(x, y);
            }
        }
    }

    public Picture picture() {
        return p;
    }

    public int width() {
        return p.width();
    }

    public int height() {
        return p.height();
    }

    public double energy(int x, int y) {
        validateColumnIndex(x);
        validateRowIndex(y);

        int xMinusOne = x - 1;
        int xPlusOne = x + 1;
        int yMinusOne = y - 1;
        int yPlusOne = y + 1;
        if (x == 0) {
            xMinusOne = width() - 1;
        }
        if (x == width() - 1) {
            xPlusOne = 0;
        }
        if (y == 0) {
            yMinusOne = height() - 1;
        }
        if (y == height() - 1) {
            yPlusOne = 0;
        }
        Color left = p.get(xMinusOne, y);
        Color right = p.get(xPlusOne, y);
        Color up = p.get(x, yMinusOne);
        Color down = p.get(x, yPlusOne);

        double xGradient = (right.getRed() - left.getRed()) * (right.getRed() - left.getRed())
                + (right.getGreen() - left.getGreen()) * (right.getGreen() - left.getGreen())
                + (right.getBlue() - left.getBlue()) * (right.getBlue() - left.getBlue());

        double yGradient = (down.getRed() - up.getRed()) * (down.getRed() - up.getRed())
                + (down.getGreen() - up.getGreen()) * (down.getGreen() - up.getGreen())
                + (down.getBlue() - up.getBlue()) * (down.getBlue() - up.getBlue());

        return xGradient + yGradient;
    }



    public int[] findHorizontalSeam() {
        return null;
    }

    public int[] findVerticalSeam() {
        double[] seamEnergy = new double[width()];
        for (int x = 0; x < width(); x++) {
            int[] seamForX = findVerticalPath(x, 0);
            double[] energy = new double[height()];
            for (int y = 0; y < height(); y++) {
                energy[y] = energyMatrix[y][seamForX[y]];
            }
            double totalEnergy = 0;
        }
        return new int[1];
    }

    private int[] findVerticalPath(int x, int y) {
        validateColumnIndex(x);
        validateRowIndex(y);

        int[] path = new int[height() - y];
        path[0] = x;
        int lastX = x;
        for (int row = 1; row < height() - y; row++) {
            if (lastX == 0) {
                if (energyMatrix[y + row][x] <= energyMatrix[y + row][x + 1]) {
                    path[row] = x;
                    lastX = x;
                } else {
                    path[row] = x + 1;
                    lastX = x + 1;
                }
            } else if (lastX == width() - 1) {
                if (energyMatrix[y + row][x] <= energyMatrix[y + row][x - 1]) {
                    path[row] = x;
                    lastX = x;
                } else {
                    path[row] = x - 1;
                    lastX = x - 1;
                }
            } else {
                if (StdStats.min(new double[]{energyMatrix[y + row][x - 1], energyMatrix[y + row][x], energyMatrix[y + row][x + 1]}) == energyMatrix[y + row][x - 1]) {
                    path[row] = x - 1;
                    lastX = x - 1;
                } else if (StdStats.min(new double[]{energy(x - 1, y + row), energy(x, y + row), energy(x + 1, y + row)}) == energyMatrix[y + row][x]) {
                    path[row] = x;
                    lastX = x;
                } else {
                    path[row] = x + 1;
                    lastX = x + 1;
                }
            }
        }
        return path;
    }

    public void removeHorizontalSeam(int[] seam) {

    }

    public void removeVerticalSeam(int[] seam) {

    }

    private void validateRowIndex(int y) {
        if (y < 0 || y > height() - 1) {
            throw new IndexOutOfBoundsException("Invalid row index provided.");
        }
    }

    private void validateColumnIndex(int x) {
        if (x < 0 || x > width() - 1) {
            throw new IndexOutOfBoundsException("Invalid column index provided.");
        }
    }
}
