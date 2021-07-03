import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final int n;
    private final WeightedQuickUnionUF quickFindUF;
    private int openedSitesCount;
    private boolean percolates;
    private boolean[] openedSites;
    private boolean[] openedTopSites;
    private boolean[] openedButtomSites;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();

        this.n = n;
        this.quickFindUF = new WeightedQuickUnionUF(n * n);
        this.openedSites = new boolean[n * n];
        this.openedTopSites = new boolean[n];
        this.openedButtomSites = new boolean[n];
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row <= 0 || col <= 0 || row > this.n || col > this.n) throw new IllegalArgumentException();

        if (isOpen(row, col)) return;

        openedSitesCount++;
        int siteNum = getSiteNum(row, col);
        openedSites[siteNum] = true;

        if (siteNum < this.n)
            openedTopSites[siteNum] = true;

        if (isButtomSite(siteNum))
            openedButtomSites[siteNum - (this.n - 1) * n] = true;

        if (row - 1 > 0 && isOpen(row - 1, col)) {
            int neighbour = getSiteNum(row - 1, col);
            quickFindUF.union(siteNum, neighbour);
        }

        if (row + 1 <= this.n && isOpen(row + 1, col)) {
            int neighbour = getSiteNum(row + 1, col);
            quickFindUF.union(siteNum, neighbour);
        }

        if (col - 1 > 0 && isOpen(row, col - 1)) {
            int neighbour = getSiteNum(row, col - 1);
            quickFindUF.union(siteNum, neighbour);
        }

        if (col + 1 <= this.n && isOpen(row, col + 1)) {
            int neighbour = getSiteNum(row, col + 1);
            quickFindUF.union(siteNum, neighbour);
        }

        if (isFull(row, col) && connectedToButtomSite(siteNum))
            this.percolates = true;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row <= 0 || col <= 0 || row > this.n || col > this.n) throw new IllegalArgumentException();

        int siteNum = getSiteNum(row, col);
        return openedSites[siteNum];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row <= 0 || col <= 0 || row > this.n || col > this.n) throw new IllegalArgumentException();

        int siteNum = getSiteNum(row, col);
        if (!this.openedSites[siteNum]) return false;

        if (siteNum < n && openedTopSites[siteNum])
            return true;

        if (connectedToTopSite(siteNum)) return true;

        return false;
    }

    private boolean connectedToTopSite(int siteNum) {
        int siteNumSet = quickFindUF.find(siteNum);
        for (int c = 1; c <= this.n; c++) {
            // connected to opened top element
            int topNum = getSiteNum(1, c);
            if  (openedTopSites[topNum] && siteNumSet == quickFindUF.find(topNum)) {
                return true;
            }
        }
        return false;
    }

    private boolean connectedToButtomSite(int siteNum) {
        int siteNumSet = quickFindUF.find(siteNum);
        for (int c = 1; c <= this.n; c++) {
            int buttomNum = getSiteNum(this.n, c);
            if  (openedButtomSites[buttomNum - (this.n - 1) * n] && siteNumSet == quickFindUF.find(buttomNum)) {
                return true;
            }
        }
        return false;
    }

    private boolean isButtomSite(int siteNum) {
        return siteNum >= (this.n - 1) * n;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.openedSitesCount;
    }

    // does the system percolate?
    public boolean percolates() {
        return this.percolates;
    }

    private int getSiteNum(int row, int col) {
        return (row - 1) * this.n + (col - 1);
    }

    // test client (optional)
    public static void main(String[] args) {
        // UncommentedEmptyMethodBody
    }
}