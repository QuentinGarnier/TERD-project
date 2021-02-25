package graphics.elements.cells;

public class CellElementWall extends AbstractCellElement {

    public CellElementWall(boolean isVertical) {
        super(isVertical? CellElementType.VERTICAL_WALL: CellElementType.HORIZONTAL_WALL);
    }
}
