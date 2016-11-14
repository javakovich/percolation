import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Created by slazakovich on 9/5/2016.
 */
public class Percolation {

    private int[] opened;
    private int n;
    private int virtualTopIndex;
    private int virtualBottomIndex;
    private WeightedQuickUnionUF weightedQuickUnionUF;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n should be positive value");
        }
        this.n = n;
        weightedQuickUnionUF = new WeightedQuickUnionUF(n * n + 2);
        opened = new int[n * n + 2];
        virtualTopIndex = opened.length - 2;
        virtualBottomIndex = opened.length - 1;
        opened[virtualTopIndex] = 1;
        opened[virtualBottomIndex] = 1;
    }

    public void open(int i, int j) {
        validate(i, j);
        int current = xyTo1D(i, j);
        opened[current] = 1;

        if (j != 1) {
            int left = xyTo1D(i, j - 1);
            if (opened[left] == 1) {
                weightedQuickUnionUF.union(current, left);
            }
        }
        if (j != n) {
            int right = xyTo1D(i, j + 1);
            if (opened[right] == 1) {
                weightedQuickUnionUF.union(current, right);
            }
        }

        int top = xyTo1D(i - 1, j);
        if (opened[top] == 1) {
            weightedQuickUnionUF.union(current, top);
        }

        int bottom = xyTo1D(i + 1, j);
        if (opened[bottom] == 1) {
            weightedQuickUnionUF.union(current, bottom);
        }
    }

    public boolean isOpen(int i, int j) {
        validate(i, j);
        return opened[xyTo1D(i, j)] == 1;
    }

    public boolean isFull(int i, int j) {
        validate(i, j);
        return weightedQuickUnionUF.connected(xyTo1D(i, j), virtualTopIndex);
    }

    public boolean percolates() {
        return weightedQuickUnionUF.connected(virtualTopIndex, virtualBottomIndex);
    }

    private int xyTo1D(int x, int y) {
        int index = (x - 1) * n + (y - 1);
        if (x == 0) {
            index = virtualTopIndex;
        } else if (x == n + 1) {
            index = virtualBottomIndex;
        }
        return index;
    }

    private void validate(int x, int y) {
        if (x < 1 || x > n || y < 1 || y > n) {
            throw new IndexOutOfBoundsException("x and y should be between 1 and n inclusive");
        }

    }

    public static void main(String[] args) {
    } // test client (optional)
}
