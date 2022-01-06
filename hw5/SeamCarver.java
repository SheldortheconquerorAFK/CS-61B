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
            return energyMatrix[oldY][y];
        } else {
            return energyMatrix[y][x];
        }
    }

    public int[] findHorizontalSeam() {
        p.setOriginLowerLeft();
        isOriginReset = true;
        int[] hs = findVerticalSeam();
        p.setOriginUpperLeft();
        isOriginReset = false;
        return hs;
    }

    public int[] findVerticalSeam() {
        double[] seamEnergy = new double[width()];
        for (int x = 0; x < width(); x++) {
            int[] pathForX = findVerticalPath(x, 0);
            double[] energyArrayForOnePath = new double[height()];
            for (int y = 0; y < height(); y++) {
                energyArrayForOnePath[y] = energy(pathForX[y], y);
            }
            double totalEnergy = 0;
            for (double d : energyArrayForOnePath) {
                totalEnergy += d;
            }
            seamEnergy[x] = totalEnergy;
        }
        double minPathEnergy = Double.MAX_VALUE;
        int minX = 0;
        for (int i = 0; i < width(); i++) {
            if (seamEnergy[i] < minPathEnergy) {
                minPathEnergy = seamEnergy[i];
                minX = i;
            }
        }
        return findVerticalPath(minX, 0);
    }

    private int[] findVerticalPath(int x, int y) {
        validateColumnIndex(x);
        validateRowIndex(y);

        int[] path = new int[height() - y];
        if (isOriginReset) {
            path[0] = p.height() - 1 - x;
        } else {
            path[0] = x;
        }
        int lastX = x;
        for (int row = 1; row < height() - y; row++) {
            if (lastX == 0) {
                if (energy(lastX, y + row) <= energy(lastX + 1, y + row)) {
                    if (isOriginReset) {
                        path[row] = p.height() - 1 - lastX;
                    } else {
                        path[row] = lastX;
                    }
                } else {
                    if (isOriginReset) {
                        path[row] = p.height() - 1 - lastX - 1;
                    } else {
                        path[row] = lastX + 1;
                    }
                    lastX += 1;
                }
            } else if (lastX == width() - 1) {
                if (energy(lastX, y + row) <= energy(lastX - 1, y + row)) {
                    path[row] = lastX;
                } else {
                    if (isOriginReset) {
                        path[row] = p.height() - 1 - lastX + 1;
                    } else {
                        path[row] = lastX - 1;
                    }
                    lastX -= 1;
                }
            } else {
                if (StdStats.min(new double[]{energy(lastX - 1, y + row), energy(lastX, y + row), energy(lastX + 1, y + row)}) == energy(lastX - 1, y + row)) {
                    if (isOriginReset) {
                        path[row] = p.height() - 1 - lastX + 1;
                    } else {
                        path[row] = lastX - 1;
                    }
                    lastX -= 1;
                } else if (StdStats.min(new double[]{energy(lastX - 1, y + row), energy(lastX, y + row), energy(lastX + 1, y + row)}) == energy(lastX, y + row)) {
                    if (isOriginReset) {
                        path[row] = p.height() - 1 - lastX;
                    } else {
                        path[row] = lastX;
                    }
                } else {
                    if (isOriginReset) {
                        path[row] = p.height() - 1 - lastX - 1;
                    } else {
                        path[row] = lastX + 1;
                    }
                    lastX += 1;
                }
            }
        }
        return path;
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
