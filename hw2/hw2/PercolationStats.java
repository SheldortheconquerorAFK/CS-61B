package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    private double[] result;
    private int T;

    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("N and T must be bigger than 0.");
        }
        pf = new PercolationFactory();
        result = new double[T];
        this.T = T;

        for (int i = 0; i < T; i++) {
            Percolation p = pf.make(N);
            while (!p.percolates()) {
                int row = StdRandom.uniform(N);
                int col = StdRandom.uniform(N);
                p.open(row, col);
            }
            result[i] = p.numberOfOpenSites();
        }
    }

    public double mean() {
        return StdStats.mean(result);
    }

    public double stddev() {
        return StdStats.stddev(result);
    }

    public double confidenceLow() {
        return mean() - 1.96 * stddev() / Math.pow(T, 0.5);
    }

    public double confidenceHigh() {
        return mean() + 1.96 * stddev() / Math.pow(T, 0.5);
    }
}