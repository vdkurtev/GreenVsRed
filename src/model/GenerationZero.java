package model;

/**
 * Class responsible to hold a single instance for the so called "Generation Zero".
 * Used to begin the "GreenVsRed" game.
 * Holds the first generation of cells in a grid.
 * Implements Singleton Design Pattern.
 *
 * @author - Viktor Kurtev
 */
public final class GenerationZero {

    /**
     * Static GenerationZero variable that will be instanced one time only.
     */
    private static GenerationZero generationZero = null;

    /**
     * Variable that contains all the cells.
     */
    private Cell[][] grid;

    private GenerationZero() {
    }

    /**
     * Static method responsible for getting the only instance of GenerationZero.
     *
     * @return new GenerationZero object at first call of the method or an already existing GenerationZero object after the first call.
     */
    public static GenerationZero getInstance() {
        if (generationZero == null) {
            generationZero = new GenerationZero();
        }
        return generationZero;
    }

    /**
     * Method responsible for the assignment of boundaries to the grid.
     *
     * @param height - vertical boundary for the grid.
     * @param width  - horizontal boundary for the grid.
     */
    public void initGrid(int height, int width) {
        this.grid = new Cell[height][width];
    }

    public Cell[][] getGrid() {
        return grid;
    }

    /**
     * Method responsible for the acquisition of the height of the grid.
     *
     * @return height of the grid.
     */
    public int getGridHeight() {
        return grid.length;
    }

    /**
     * Method responsible for the acquisition of the width of the grid.
     *
     * @return width of the grid.
     */
    public int getGridWidth() {
        return grid[0].length;
    }

}
