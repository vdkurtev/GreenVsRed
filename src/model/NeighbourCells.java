package model;

/**
 * Class responsible for storing the direct neighbours of a cell in a grid.
 *
 * @author - Viktor Kurtev
 */
public final class NeighbourCells {

    /**
     * Variable containing reference the neighbour cell above.
     */
    private Cell up;

    /**
     * Variable containing reference the neighbour cell below.
     */
    private Cell down;

    /**
     * Variable containing reference the neighbour cell on the left.
     */
    private Cell left;

    /**
     * Variable containing reference the neighbour cell on the right.
     */
    private Cell right;

    /**
     * Method responsible for assigning reference to the variable up after validation.
     *
     * @param up contains the reference of an object of type Cell.
     */
    public void setUp(Cell up) {
        this.up = isCellValid(up) ? up : null;
    }

    /**
     * Method responsible for assigning reference to the variable up after validation.
     *
     * @param down contains the reference of an object of type Cell.
     */
    public void setDown(Cell down) {
        this.down = isCellValid(down) ? down : null;
    }

    /**
     * Method responsible for assigning reference to the variable up after validation.
     *
     * @param left contains the reference of an object of type Cell.
     */
    public void setLeft(Cell left) {
        this.left = isCellValid(left) ? left : null;
    }

    /**
     * Method responsible for assigning reference to the variable up after validation.
     *
     * @param right contains the reference of an object of type Cell.
     */
    public void setRight(Cell right) {
        this.right = isCellValid(right) ? right : null;
    }

    /**
     * Method responsible for the validation of a neighbour cell.
     *
     * @param cell contains reference to object in which a neighbour cell will be stored.
     * @return True if the neighbour exists, False if the neighbour doesn't exist.
     */
    private boolean isCellValid(Cell cell) {
        return cell != null;
    }

    public Cell getUp() {
        return up;
    }

    public Cell getDown() {
        return down;
    }

    public Cell getLeft() {
        return left;
    }

    public Cell getRight() {
        return right;
    }


}
