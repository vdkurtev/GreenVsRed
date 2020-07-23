package model;

import engine.enumeration.CellTypeEnum;

/**
 * Class responsible for representation of cells in a grid.
 * Contains type of the cell and reference to neighbour cells.
 * Possible refactoring would be :
 * Interface Cell being implemented by Green Cell and Red Cell classes each with their own rules to be applied. - Can achieve full decoupling and abstraction or easy expandability.
 * Abstract class Cell being extended by Green Cell and Red Cell classes each with their own rules to be applied. - Cannot achieve full decoupling and abstraction.
 *
 * @author - Viktor Kurtev
 */
public final class Cell {

    /**
     * Variable containing the type of the cell from CellTypeEnumeration - GREEN/RED.
     */
    private final CellTypeEnum type;

    /**
     * Variable containing reference to object of NeighbourCells. - Contains references to cells up/down/left/right of the particular cell.
     */
    private final NeighbourCells neighbourCells;

    public Cell(CellTypeEnum type) {
        this.type = type;
        neighbourCells = new NeighbourCells();
    }

    public CellTypeEnum getType() {
        return type;
    }

    public NeighbourCells getNeighbourCells() {
        return neighbourCells;
    }

}
