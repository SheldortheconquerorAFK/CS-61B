import edu.princeton.cs.algs4.Picture;

import java.awt.Color;

public class SeamCarver {
    Picture p;
    double[][] energyMatrix;

    public SeamCarver(Picture picture) {
        p = picture;
        energyMatrix = new double[height()][width()];
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
        energyMatrix[y][x] = xGradient + yGradient;

        return xGradient + yGradient;
    }



    public int[] findHorizontalSeam() {
        return null;
    }

    public int[] findVerticalSeam() {
        return null;
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
