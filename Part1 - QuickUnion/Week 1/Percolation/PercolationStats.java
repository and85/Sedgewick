import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE_COEF = 1.96;

    private final double[] trialResults;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException();

        trialResults = new double[trials];

        for (int t = 0; t < trials; t++) {
            Percolation percolation = new Percolation(n);

            while (true) {

                openRandomSite(n, percolation);

                if (percolation.percolates())
                    break;
            }

            trialResults[t] = (double) percolation.numberOfOpenSites() / (n * n);
        }
    }

    private void openRandomSite(int n, Percolation percolation) {
        int r = StdRandom.uniform(n) + 1;
        int c = StdRandom.uniform(n) + 1;

        percolation.open(r, c);
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(trialResults);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(trialResults);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - CONFIDENCE_COEF * stddev() / Math.sqrt(trialResults.length);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + CONFIDENCE_COEF * stddev() / Math.sqrt(trialResults.length);
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);

        PercolationStats stats = new PercolationStats(n, t);
        StdOut.printf("mean                    = %f%n", stats.mean());
        StdOut.printf("stddev                  = %f%n", stats.stddev());
        StdOut.printf("95%% confidence interval = [%f, %f]%n", stats.confidenceLo(), stats.confidenceHi());
    }

}