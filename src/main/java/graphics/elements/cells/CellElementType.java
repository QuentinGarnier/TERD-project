package graphics.elements.cells;

public enum CellElementType {
    VERTICAL_WALL('|', false),
    HORIZONTAL_WALL('—', false),
    CORNER('┼', false), // TODO there are 4 types of corners?
    MONSTER('O', false),
    OUTSIDE_ROOM('?', false),
    TREE('&',false),
    HERO('@', false),
    ITEM('%', true),
    EMPTY(' ', true);

    private final char symbol;
    private final boolean isAccessible;

    CellElementType(char c, boolean isAccessible) {
        this.symbol = c;
        this.isAccessible = isAccessible;
    }

    public char getSymbol() {
        return symbol;
    }

    public boolean isAccessible() {
        return isAccessible;
    }

    @Override
    public String toString() {
        return String.valueOf(symbol);
    }
}
