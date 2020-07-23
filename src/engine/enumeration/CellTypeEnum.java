package engine.enumeration;

/**
 * Enum that contains the two types of cells.
 * GREEN - 1
 * RED - 0
 */
public enum CellTypeEnum {
    GREEN, RED;

    /**
     * Method responsible for the transformation of user input to enum.
     * Example - user inputs cell with value of 1 - it gets the type GREED.
     *
     * @param value user input varying between 0/1.
     * @return type of the cell in enum.
     */
    public static CellTypeEnum getType(byte value) {
        if (value == 1) {
            return GREEN;
        } else {
            return RED;
        }
    }
}
