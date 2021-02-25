package graphics.elements.cells;

public abstract class AbstractCellElement {
    private CellElementType content;

    AbstractCellElement(CellElementType type) {
        content = type;
    }

    public CellElementType getType() {
        return content;
    }

    public char getSymbol() {
        return content.getSymbol();
    }

    public boolean isAccessible() {
        return content.isAccessible();
    }

    @Override
    public String toString() {
        return content.toString();
    }
}
