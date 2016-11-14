import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * Created by slazakovich on 9/7/2016.
 */
public class PercolationStats {

    private double[] results;
    private double totalCount;
    private double trials;
    private double mean;
    private double stddev;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Number of trials and n should be positive values");
        }
        results = new double[trials];
        totalCount = n * n;
        this.trials = trials;
        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            double openedCount = 0;
            while (!percolation.percolates()) {
                int x;
                int y;
                do {
                    x = StdRandom.uniform(n) + 1;
                    y = StdRandom.uniform(n) + 1;
                } while (percolation.isOpen(x, y));
                percolation.open(x, y);
                openedCount++;
            }
            results[i] = openedCount / totalCount;
        }
        mean = StdStats.mean(results);
        stddev = StdStats.stddev(results);
        stddev = stddev * stddev;

    }

    public double mean() {
        return mean;
    }


    public double stddev() {
        return stddev;
    }


    public double confidenceLo() {
        return (mean - 1.96 * Math.sqrt(stddev) / Math.sqrt(trials));
    }


    public double confidenceHi() {
        return (mean + 1.96 * Math.sqrt(stddev) / Math.sqrt(trials));
    }

    public static void main(String[] args) {
        PercolationStats ps = new PercolationStats(200, 100);
        StdOut.printf("mean = %f\n", ps.mean());
        StdOut.printf("stddev = %f\n", ps.stddev());
        StdOut.printf("95%% confidence interval = %f, %f\n", ps.confidenceLo(), ps.confidenceHi());


    }

}
