package graphics.elements.cells;

public abstract class CellElement {
    private CellElementType content;

    CellElement(CellElementType type) {
        this.content = type;
    }

    public CellElementType getType() {
        return this.content;
    }

    public char getSymbol() {
        return this.content.getSymbol();
    }

    public boolean isAccessible() {
        return this.content.isAccessible();
    }

    @Override
    public String toString() {
        return this.content.toString();
    }
}
