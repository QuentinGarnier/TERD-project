package graphics.elements.cells;

import graphics.ColorStr;

public enum CellElementType {
    VERTICAL_WALL('|', false),
    HORIZONTAL_WALL('—', false),
    CORNER('┼', false), // TODO there are 4 types of corners?
    MONSTER('O', false),
    OUTSIDE_ROOM(' ', false),
    CORRIDOR('#', true),
    TREE('&',false),
    HERO('@', false),
    ITEM('%', true),
    EMPTY('.', true); // work also for doors

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
        switch (this) {
            case HERO: return ColorStr.cyan("" + symbol);
            case MONSTER: return ColorStr.red("" + symbol);
            case ITEM: return ColorStr.green("" + symbol);
            default: return "" + symbol;
        }
    }
}
