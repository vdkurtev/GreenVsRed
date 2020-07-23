import engine.enumeration.CellTypeEnum;
import engine.impl.GreenVsRed;
import org.junit.Assert;
import org.junit.Test;

public class GreenVsRedTest {
    @Test
    public void executeNextGenerationRules_ShouldReturnSameTypeWhenNeighboursAreTwo() {
        // Arrange
        final long totalGreenNeighbours = 2;
        final CellTypeEnum cellType = CellTypeEnum.GREEN;
        CellTypeEnum expectedCellType = CellTypeEnum.GREEN;
        GreenVsRed greenVsRed = new GreenVsRed();
        // Act
        CellTypeEnum resultCellType = greenVsRed.executeNextGenerationRules(totalGreenNeighbours, cellType);
        // Assert
        Assert.assertEquals(expectedCellType, resultCellType);
    }

    @Test
    public void executeNextGenerationRules_ShouldReturnGreenWhenNeighboursAreThree() {
        // Arrange
        final long totalGreenNeighbours = 3;
        final CellTypeEnum cellType = CellTypeEnum.GREEN;
        CellTypeEnum expectedCellType = CellTypeEnum.GREEN;
        GreenVsRed greenVsRed = new GreenVsRed();
        // Act
        CellTypeEnum resultCellType = greenVsRed.executeNextGenerationRules(totalGreenNeighbours, cellType);
        // Assert
        Assert.assertEquals(expectedCellType, resultCellType);
    }
    @Test
    public void executeNextGenerationRules_ShouldReturnRedWhenNeighboursAreFour() {
        // Arrange
        final long totalGreenNeighbours =4;
        final CellTypeEnum cellType = CellTypeEnum.GREEN;
        CellTypeEnum expectedCellType = CellTypeEnum.RED;
        GreenVsRed greenVsRed = new GreenVsRed();
        // Act
        CellTypeEnum resultCellType = greenVsRed.executeNextGenerationRules(totalGreenNeighbours, cellType);
        // Assert
        Assert.assertEquals(expectedCellType, resultCellType);
    }

}
