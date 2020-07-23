package model;

/**
 * Class responsible for holding the generation state in a grid.
 * Each new object is referred to by "Next/New Generation".
 *
 * @author - Viktor Kurtev
 */
public final class Generation {

    /**
     * Variable containing the generation grid.
     */
    private final Cell[][] grid;

    public Generation(Cell[][] grid) {
        this.grid = grid;
    }

    public Cell[][] getGrid() {
        return grid;
    }
}
