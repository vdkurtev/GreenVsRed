package engine;

import engine.enumeration.CellTypeEnum;
import exception.InvalidUserInputException;

/**
 * Interface for the so called "GreenVsRed" mutation of "Game of Life"
 * Extends IGame for the base game methods.
 *
 * @author - Viktor Kurtev
 */
public interface IGreenVsRed extends IGame {

    /**
     * Method responsible for the initialization of the so called "Generation Zero".
     *
     * @throws InvalidUserInputException contains the INVALID_GRID_SIZE_INPUT_EXCEPTION_MESSAGE
     *                                   thrown when the height and width given by the user aren't in the scope of 0 < x <= y < 1000
     */
    void initGenerationZero() throws InvalidUserInputException;

    /**
     * Method responsible for the assignment of cell data into Generation Zero's grid (2D matrix) of Cell objects row by row
     * It's row by row cause of the way user input is treated - each row of user input represents 1 entire row of the grid
     *
     * @throws InvalidUserInputException contains two messages depending on the case :
     *                                   INVALID_GRID_VALUE_INPUT_EXCEPTION_MESSAGE + INVALID_CELL_VALUE_INPUT_EXCEPTION_MESSAGE - when grid row values aren't valid and/or the cell value isn't equal to 0 - RED, 1 - GREEN
     *                                   or
     *                                   INVALID_GRID_VALUE_INPUT_EXCEPTION_MESSAGE + INVALID_CELL_NUMBER_INPUT_EXCEPTION_MESSAGE - when grid row values aren't valid and/or the number of cells in the input aren't equal to the grid's height ( X )
     */
    void fillGenerationZeroGridByRow() throws InvalidUserInputException;

    /**
     * Method responsible for the set of the so called "Observed Cell" and its data.
     *
     * @throws InvalidUserInputException contains the INVALID_OBSERVED_CELL_INFO_INPUT_EXCEPTION_MESSAGE
     *                                   thrown when the observed cell data given by the user isn't valid - x1 - height, y - width aren't in the scope of the grid
     */
    void setObservedCell() throws InvalidUserInputException;

    /**
     * Method responsible for initialization of the so called "Generation History".
     * Initializes a List of Generation objects.
     * Adds "Generation Zero"during initialization.
     */
    void initGenerationHistory();

    /**
     * Method responsible for execution and application of the Rules that form the next generation.
     *
     * @param totalGreenNeighbours Long number of green neighbours around the Cell that we want to apply the rules for.
     * @param cellType             current type of the Cell that we are going to use to determine the outcome.
     * @return new/old type of the cell varying between GREEN/RED after application of the rules for the game.
     */
    CellTypeEnum executeNextGenerationRules(long totalGreenNeighbours, CellTypeEnum cellType);

}
