package engine.service;

import engine.enumeration.CellTypeEnum;
import model.Cell;
import model.GenerationZero;
import model.ObservedCellInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class (Service) responsible for validating user input coming from UserInputService.
 *
 * @author - Viktor Kurtev
 */
public final class ValidatorService {

    /**
     * Constant for the minimum height of a grid.
     */
    private final int MIN_HEIGHT = 0;

    /**
     * Constant for the minimum width of a grid.
     */
    private final int MIN_WIDTH = 0;

    /**
     * Constant for the maximum width of a grid.
     */
    private final int MAX_WIDTH = 1000;

    /**
     * Constant regex pattern responsible for the validation of grid size input.
     */
    private final String gridSizeInputPattern = "\\s*([0-9]{1,4})\\s*,\\s*([0-9]{1,4})\\s*";

    /**
     * Constant regex pattern responsible for the validation of each individual grid value.
     */
    private final String gridValuesInputPattern = "([0-1])";

    /**
     * Constant regex pattern responsible for the validation of observed cell info.
     */
    private final String observedCellInfoInputPattern = "\\s*([0-9]{1,4})\\s*,\\s*([0-9]{1,4})\\s*,\\s*([0-9]*)\\s*";

    /**
     * Method responsible for the validation of the size of a grid, provided by the user input.
     *
     * @param gridSizeInput raw String representation of the grid size.
     * @return Optional of an array, containing the validated height - x, width - y or an empty Optional if the validation has failed.
     */
    public Optional<int[]> validateInputSize(String gridSizeInput) {
        Pattern pattern = Pattern.compile(gridSizeInputPattern);
        Matcher matcher = pattern.matcher(gridSizeInput);
        if (matcher.matches()) {
            int height = Integer.parseInt(matcher.group(1));
            int width = Integer.parseInt(matcher.group(2));
            if (validateGridSize(height, width)) {
                int[] validatedGridSize = new int[]{height, width};
                return Optional.of(validatedGridSize);
            }
        }
        return Optional.empty();
    }

    /**
     * Method responsible for the validation of the grid values, provided by the user input.
     *
     * @param gridValuesInput raw String representation of the grid values.
     * @return Optional of a List of Cells, containing the validated Cell values for a row of a grid or an empty Optional if the validation has failed.
     */
    public Optional<List<Cell>> validateGridValuesInput(String gridValuesInput) {
        Pattern pattern = Pattern.compile(gridValuesInputPattern);
        Matcher matcher = pattern.matcher(gridValuesInput);
        List<Cell> validatedGridRowValues = new ArrayList<>();
        byte matchedGridRowValue;
        while (matcher.find()) { // matcher.find() iterates through the provided String : Example input String - "031" for pattern ([0-1]) -> matcher.find() -> "0" ->  matcher.find() -> "" ->  matcher.find() -> "1".
            matchedGridRowValue = Byte.parseByte(matcher.group(0)); // Each group consists of a single character matched by the regex pattern for the input String, which then transforms into a byte.
            validatedGridRowValues.add(new Cell(CellTypeEnum.getType(matchedGridRowValue)));
        }
        if (validatedGridRowValues.size() != GenerationZero.getInstance().getGridWidth()) {
            return Optional.empty();
        }
        return Optional.of(validatedGridRowValues);
    }

    /**
     * Method responsible for the validation of the Observed Cell Info, provided by the user input.
     *
     * @param observedCellInfoInput raw String representation of the Observed Cell Info.
     * @return Optional of ObservedCellInfo object, containing the validated Observed Cell Info, or an empty Optional if the validation has failed.
     */
    public Optional<ObservedCellInfo> validateObservedCellInfoInput(String observedCellInfoInput) {
        Pattern pattern = Pattern.compile(observedCellInfoInputPattern);
        Matcher matcher = pattern.matcher(observedCellInfoInput);
        if (matcher.matches()) {
            int x = Integer.parseInt(matcher.group(1));
            int y = Integer.parseInt(matcher.group(2));
            long n = Long.parseLong(matcher.group(3));
            if (validateCoordinates(x, y)) {
                ObservedCellInfo validatedObservedCellInfo = new ObservedCellInfo(x, y, n);
                return Optional.of(validatedObservedCellInfo);
            }
        }
        return Optional.empty();
    }

    /**
     * Method responsible for the validation of height and width bounds of a grid.
     * MIN_HEIGHT (0) < height <= width < MAX_WIDTH (1000)
     *
     * @param height of a grid, provided by the user to be validated.
     * @param width  of a grid, provided by the user to be validated.
     * @return True if the height and width are in bounds or False if they aren't.
     */
    private boolean validateGridSize(int height, int width) {
        return height > MIN_HEIGHT && width < MAX_WIDTH && height <= width;
    }

    /**
     * Method responsible for the validation of coordinates, provided by the user input.
     *
     * @param x is height coordinate of a grid.
     * @param y is width coordinate of a grid.
     * @return True if the coordinates are in bounds of the size of a grid or False if they aren't.
     */
    private boolean validateCoordinates(int x, int y) {
        int maxGridX = GenerationZero.getInstance().getGridHeight() - 1;
        int maxGridY = GenerationZero.getInstance().getGridWidth() - 1;
        return x >= MIN_HEIGHT && x <= maxGridX && y >= MIN_WIDTH && y <= maxGridY;
    }

}
