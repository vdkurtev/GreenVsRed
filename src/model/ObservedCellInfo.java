package model;

/**
 * Class responsible for storing information for a desired cell that's going to be observed throughout the generations.
 *
 * @author - Viktor Kurtev
 */
public final class ObservedCellInfo {
    /**
     * Variable containing the height coordinate of the cell.
     */
    private final int x;
    /**
     * Variable containing the width coordinate of the cell.
     */
    private final int y;
    /**
     * Variable containing the number of generations throughout which it will be observed.
     */
    private final long n;

    public ObservedCellInfo(int x, int y, long n) {
        this.x = x;
        this.y = y;
        this.n = n;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public long getN() {
        return n;
    }

}
