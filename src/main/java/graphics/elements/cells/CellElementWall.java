package graphics.elements.cells;

public class CellElementWall extends CellElement {

    public CellElementWall(boolean isVertical) {
        super(isVertical? CellElementType.VERTICAL_WALL: CellElementType.HORIZONTAL_WALL);
    }
}
