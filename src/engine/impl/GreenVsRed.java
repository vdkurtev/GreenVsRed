package engine.impl;

import engine.IGreenVsRed;
import engine.enumeration.CellTypeEnum;
import engine.service.UserInputService;
import engine.service.ValidatorService;
import exception.InvalidUserInputException;
import model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Class responsible for holding the core logic for the "GreenVsRed" game, also called "Game of Life".
 *
 * @author - Viktor Kurtev
 */
public class GreenVsRed implements IGreenVsRed {

    private final String INVALID_GRID_VALUE_INPUT_EXCEPTION_MESSAGE = "Invalid grid value input!\n";
    private final String INVALID_CELL_VALUE_INPUT_EXCEPTION_MESSAGE = "Please input: 0 for Red cell / 1 for Green cell!";
    private final String INVALID_CELL_NUMBER_INPUT_EXCEPTION_MESSAGE = "Please input correct number of cells for the row!";
    private final String INVALID_OBSERVED_CELL_INFO_INPUT_EXCEPTION_MESSAGE = "Please input x1,y1,N - Where x1 - height coordinate, y1 - width coordinate, N - number of generations to be created!";
    private final String INVALID_GRID_SIZE_INPUT_EXCEPTION_MESSAGE = "The provided grid size isn't valid, it should be -> 0 < x <= y < 1000";

    /**
     * Variable used to access validation methods for user input.
     */
    private final ValidatorService validatorService;

    /**
     * Variable containing the info for a given cell that will be observed throughout the generations.
     */
    private ObservedCellInfo observedCellInfo;

    /**
     * Variable containing the generations created throughout the workflow of the game.
     */
    private List<Generation> generationsHistory;

    public GreenVsRed() {
        this.validatorService = new ValidatorService();
    }

    /**
     * Method responsible for the execution of the game program.
     */
    @Override
    public void execute() {
        System.out.println("Execution successful!");
        System.out.println("GreenVsRed");
        System.out.println("Starting setup ...");
        setup();
        System.out.println("Starting game ...");
        start();
    }

    /**
     * Method responsible for the setup of the game before starting the core logic.
     */
    @Override
    public void setup() {
        try {
            // Set Generation Zero grid size
            initGenerationZero();
            // Fill grid values by rows
            fillGenerationZeroGridByRow();
            // Set Observed cell
            setObservedCell();
            // Init Generations history
            initGenerationHistory();
            System.out.println("Setup complete!");
        } catch (IOException e) {
            System.out.println("Setup failed!");
            System.out.println(e.getMessage());
        } finally {
            UserInputService.closeBFReader();
        }
    }

    /**
     * Method responsible for the initialization of Generation Zero grid.
     * Accepts user input for the grid and validates it.
     *
     * @throws InvalidUserInputException thrown with INVALID_GRID_SIZE_INPUT_EXCEPTION_MESSAGE if the user inputs wrong grid bounds.
     */
    @Override
    public void initGenerationZero() throws InvalidUserInputException {
        System.out.println("Input grid size in order - height,width ( 0 < height <= width < 1000 ) :");
        String gridSizeInput = UserInputService.getUserInput();
        Optional<int[]> validatedGridSize =
                Optional.ofNullable(
                        validatorService.validateInputSize(gridSizeInput)
                                .orElseThrow(() -> new InvalidUserInputException(INVALID_GRID_SIZE_INPUT_EXCEPTION_MESSAGE)));
        validatedGridSize.ifPresent(size -> GenerationZero.getInstance().initGrid(size[0], size[1])); // size[0] holds height, size[1] holds width
    }

    /**
     * Method responsible for the assignment of cells to the Generation Zero grid.
     * Iterates through user input for every row of the grid.
     *
     * @throws InvalidUserInputException thrown with INVALID_GRID_VALUE_INPUT_EXCEPTION_MESSAGE + INVALID_CELL_NUMBER_INPUT_EXCEPTION_MESSAGE when the user inputs wrong number of cell values
     *                                   or with INVALID_GRID_VALUE_INPUT_EXCEPTION_MESSAGE + INVALID_CELL_VALUE_INPUT_EXCEPTION_MESSAGE when wrong cell values are inputted.
     */
    @Override
    public void fillGenerationZeroGridByRow() throws InvalidUserInputException {
        String gridValueInput;
        int height = GenerationZero.getInstance().getGridHeight();
        int width = GenerationZero.getInstance().getGridWidth();
        Cell[][] grid = GenerationZero.getInstance().getGrid();
        System.out.println("Input grid rows with values of 0 - Red or 1 - Green and length equal to grid width :");
        for (int i = 0; i < height; i++) {
            gridValueInput = UserInputService.getUserInput();
            if (gridValueInput.length() == width) {
                grid[i] = validatorService.validateGridValuesInput(gridValueInput)
                        .orElseThrow(() -> new InvalidUserInputException(INVALID_GRID_VALUE_INPUT_EXCEPTION_MESSAGE + INVALID_CELL_VALUE_INPUT_EXCEPTION_MESSAGE))
                        .toArray(new Cell[0]); // toArray() and toArray(new Cell[0]) are the same thing.
            } else {
                throw new InvalidUserInputException(INVALID_GRID_VALUE_INPUT_EXCEPTION_MESSAGE + INVALID_CELL_NUMBER_INPUT_EXCEPTION_MESSAGE);
            }
        }
        setNewGenerationCellsNeighbours(grid);
    }

    /**
     * Method responsible for the assignment of user input to the Observed Cell object.
     *
     * @throws InvalidUserInputException thrown with INVALID_OBSERVED_CELL_INFO_INPUT_EXCEPTION_MESSAGE when the user inputs out of bounds coordinates.
     */
    @Override
    public void setObservedCell() throws InvalidUserInputException {
        System.out.println("Input observed cell info in order - coordinate X, coordinate Y, number of generations N :");
        String observedCellInfoInput = UserInputService.getUserInput();
        observedCellInfo = validatorService.validateObservedCellInfoInput(observedCellInfoInput).orElseThrow(() -> new InvalidUserInputException(INVALID_OBSERVED_CELL_INFO_INPUT_EXCEPTION_MESSAGE));
    }

    /**
     * Method responsible for the initialization of Generation history.
     * Adds Generation Zero to the history.
     */
    @Override
    public void initGenerationHistory() {
        generationsHistory = new ArrayList<>();
        generationsHistory.add(new Generation(GenerationZero.getInstance().getGrid()));
    }

    /**
     * Method responsible for starting the core game logic.
     * Iterates N times through creating new Generations, new Cells then adding them to the history.
     * At the end prints the result.
     */
    @Override
    public void start() {
        System.out.println("Game has started!");
        int height = GenerationZero.getInstance().getGridHeight();
        int width = GenerationZero.getInstance().getGridWidth();
        for (int iterationsCount = 0; iterationsCount < observedCellInfo.getN(); iterationsCount++) {
            Cell[][] newGenerationGrid = new Cell[height][width];
            createNewCells(newGenerationGrid, height, width);
            setNewGenerationCellsNeighbours(newGenerationGrid);
            generationsHistory.add(new Generation(newGenerationGrid));
        }
        printResult();
        System.out.println("Exiting!");
    }

    /**
     * Method responsible for the execution of rules onto a given cell from the old generation grid to form the new generation grid.
     *
     * @param totalGreenNeighbours Long number of green neighbours around the Cell that we want to apply the rules for.
     * @param cellType             current type of the Cell that we are going to use to determine the outcome.
     * @return GREEN type for cells with 3 or 6 neighbours, cellType (Type doesn't change) for cells with 2 neighbours and RED for everyone else.
     */
    @Override
    public CellTypeEnum executeNextGenerationRules(long totalGreenNeighbours, CellTypeEnum cellType) {
        if (totalGreenNeighbours == 3 || totalGreenNeighbours == 6) {
            return CellTypeEnum.GREEN;
        } else if (totalGreenNeighbours == 2) {
            return cellType;
        }
        return CellTypeEnum.RED;
    }

    /**
     * Method responsible for the creation of cells for the new Generation.
     * Accesses the last Generation's grid from the Generation history,
     * calculates the number of surrounding non null cells,
     * calculates the number of green neighbours for the current cell
     * and then creates the cell into the new Generation grid by getting its type after the execution of rules for new Generation cells.
     *
     * @param newGenerationGrid will hold the new Generation of cells.
     * @param height            of the new grid.
     * @param width             of the new grid.
     */
    private void createNewCells(Cell[][] newGenerationGrid, int height, int width) {
        Cell lastGenCell;
        List<Cell> surroundingNonNullCells;
        long totalGreenNeighbours;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                lastGenCell = generationsHistory.get(generationsHistory.size() - 1).getGrid()[i][j];
                surroundingNonNullCells = getSurroundingNonNullCells(lastGenCell);
                totalGreenNeighbours = getSurroundingGreenCellsCount(surroundingNonNullCells);
                newGenerationGrid[i][j] = new Cell(executeNextGenerationRules(totalGreenNeighbours, lastGenCell.getType()));
            }
        }
    }

    /**
     * Method responsible for setting the links between cells and their neighbours.
     *
     * @param generationGrid current Generation grid from which the given cells will be accessed.
     */
    private void setNewGenerationCellsNeighbours(Cell[][] generationGrid) {
        for (int i = 0; i < GenerationZero.getInstance().getGridHeight(); i++) {
            for (int j = 0; j < GenerationZero.getInstance().getGridWidth(); j++) {
                if (isCellValid(i - 1, j)) {
                    generationGrid[i][j].getNeighbourCells().setUp(generationGrid[i - 1][j]);
                }
                if (isCellValid(i + 1, j)) {
                    generationGrid[i][j].getNeighbourCells().setDown(generationGrid[i + 1][j]);
                }
                if (isCellValid(i, j - 1)) {
                    generationGrid[i][j].getNeighbourCells().setLeft(generationGrid[i][j - 1]);
                }
                if (isCellValid(i, j + 1)) {
                    generationGrid[i][j].getNeighbourCells().setRight(generationGrid[i][j + 1]);
                }
            }
        }
    }

    /**
     * Method responsible for the validation of cell coordinates.
     *
     * @param x height coordinate.
     * @param y width coordinate.
     * @return True if the cell is in bounds of the grid ( Using the Generation Zero bounds cause they are the same for every Generation ) or False if the cell is out of bounds for the grid.
     */
    private boolean isCellValid(int x, int y) {
        return (x >= 0 && x < GenerationZero.getInstance().getGridHeight()) && (y >= 0 && y < GenerationZero.getInstance().getGridWidth());
    }

    /**
     * Method responsible for getting the cells surrounding a given cell which aren't null.
     * Uses Ternary operator to assure that methods aren't called on any null objects which would result in a NullPointerException.
     *
     * @param cell given cell for which we want to find the non null surrounding cells.
     * @return list of cell's surrounding the given cell.
     */
    private List<Cell> getSurroundingNonNullCells(Cell cell) {
        NeighbourCells neighbourCells = cell.getNeighbourCells();
        Cell up = neighbourCells.getUp();
        Cell down = neighbourCells.getDown();
        Cell left = neighbourCells.getLeft();
        Cell right = neighbourCells.getRight();

        return new ArrayList<Cell>() {{
            add(up);
            add(up != null ? up.getNeighbourCells().getLeft() : null); // Usage of ternary operator to check if the given cell is null - returns its left neighbour cell reference or null if the given cell is null.
            add(up != null ? up.getNeighbourCells().getRight() : null);
            add(down);
            add(down != null ? down.getNeighbourCells().getLeft() : null);
            add(down != null ? down.getNeighbourCells().getRight() : null);
            add(left);
            add(right);
        }}.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList()); // filter(Objects::nonNull) returns only objects which aren't null and then uses .collect(Collectors.toList()) to add up the resulting non null cells in a list and return it.
    }

    /**
     * Method responsible for counting the number of green cells surrounding the given cell.
     *
     * @param surroundingNonNullCells list of the surrounding non null cells which will be stream and then filtered for the cells which type is green.
     * @return number of surrounding green cells which is available after the iteration and filtration of the list.
     */
    private long getSurroundingGreenCellsCount(List<Cell> surroundingNonNullCells) {
        return surroundingNonNullCells.stream()
                .filter(nonNullCell -> nonNullCell.getType() == CellTypeEnum.GREEN)
                .count();
    }

    /**
     * Method responsible for counting the number of times that the observed cell was green throughout the generations.
     *
     * @return long number representing the number of times the observed cell was green throughout the generations.
     */
    private long getResult() {
        int observedCellX = observedCellInfo.getX();
        int observedCellY = observedCellInfo.getY();
        return generationsHistory.stream()
                .filter(generation -> generation.getGrid()[observedCellX][observedCellY].getType().equals(CellTypeEnum.GREEN))
                .count();
    }

    /**
     * Method responsible for the printing of the final result for the observed cell.
     */
    private void printResult() {
        System.out.println("The observed cell was green for " + getResult() + " generations!");
    }

}
