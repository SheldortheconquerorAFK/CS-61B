import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.introcs.StdStats;

import java.awt.Color;

public class SeamCarver {
    Picture p;
    double[][] energyMatrix;
    boolean isOriginReset;

    public SeamCarver(Picture picture) {
        p = picture;
        energyMatrix = new double[height()][width()];
        isOriginReset = false;
        for (int y = 0; y < height(); y++) {
            for (int x = 0; x < width(); x++) {
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
            }
        }
    }

    public Picture picture() {
        return p;
    }

    public int width() {
        if (isOriginReset) {
            return p.height();
        } else {
            return p.width();
        }
    }

    public int height() {
        if (isOriginReset) {
            return p.width();
        } else {
            return p.height();
        }
    }

    public double energy(int x, int y) {
        if (isOriginReset) {
            int oldY = p.height() - 1 - x;
            int oldX = y;
            return energyMatrix[oldY][oldX];
        } else {
            return energyMatrix[y][x];
        }
    }

    public int[] findHorizontalSeam() {
        return null;
    }

    public int[] findVerticalSeam() {
        double[][] pathEnergyRecord = new double[height()][width()];
        int[][] nextRowPixelFrom = new int[height()][width()];
        for (int row = 0; row < height(); row++) {
            for (int col = 0; col < width(); col++) {
                if (row == 0) {
                    pathEnergyRecord[row][col] = energy(col, row);
                    nextRowPixelFrom[row][col] = col;
                } else {
                    if (col == 0) {
                        pathEnergyRecord[row][col] = energy(col, row) + StdStats.min(new double[]{pathEnergyRecord[row - 1][col], pathEnergyRecord[row - 1][col + 1]});
                        if (StdStats.min(new double[]{pathEnergyRecord[row - 1][col], pathEnergyRecord[row - 1][col + 1]}) == pathEnergyRecord[row - 1][col]) {
                            nextRowPixelFrom[row][col] = col;
                        } else {
                            nextRowPixelFrom[row][col] = col + 1;
                        }
                    } else if (col == width() - 1) {
                        pathEnergyRecord[row][col] = energy(col, row) + StdStats.min(new double[]{pathEnergyRecord[row - 1][col - 1], pathEnergyRecord[row - 1][col]});
                        if (StdStats.min(new double[]{pathEnergyRecord[row - 1][col - 1], pathEnergyRecord[row - 1][col]}) == pathEnergyRecord[row - 1][col]) {
                            nextRowPixelFrom[row][col] = col - 1;
                        } else {
                            nextRowPixelFrom[row][col] = col;
                        }
                    } else {
                        pathEnergyRecord[row][col] = energy(col, row) + StdStats.min(new double[]{pathEnergyRecord[row - 1][col - 1], pathEnergyRecord[row - 1][col], pathEnergyRecord[row - 1][col + 1]});
                        if (StdStats.min(new double[]{pathEnergyRecord[row - 1][col], pathEnergyRecord[row - 1][col + 1]}) == pathEnergyRecord[row - 1][col]) {
                            nextRowPixelFrom[row][col] = col - 1;
                        } else if (StdStats.min(new double[]{pathEnergyRecord[row - 1][col], pathEnergyRecord[row - 1][col + 1]}) == pathEnergyRecord[row][col]){
                            nextRowPixelFrom[row][col] = col;
                        } else {
                            nextRowPixelFrom[row][col] = col + 1;
                        }
                    }
                }
            }
        }
        double minPathEnergy = StdStats.min(pathEnergyRecord[height() - 1]);
        int[] seam = new int[height()];
        for (int i = 0; i < width(); i++) {
            if (pathEnergyRecord[height() - 1][i] == minPathEnergy) {
                seam[height() - 1] = i;
                for (int j = height() - 1; j > 0; j--) {
                    seam[j - 1] = nextRowPixelFrom[j][seam[j]];
                }
            }
        }
        return seam;
    }

    public void removeHorizontalSeam(int[] seam) {
        SeamRemover.removeHorizontalSeam(p, seam);
    }

    public void removeVerticalSeam(int[] seam) {
        SeamRemover.removeVerticalSeam(p, seam);
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