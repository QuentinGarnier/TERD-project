package graphics.elements.cells;

import graphics.ColorStr;

import javax.swing.*;

public enum CellElementType {
    VERTICAL_WALL('|', false, "map/walls/stone_vertical"),
    HORIZONTAL_WALL('—', false, "map/walls/stone_horizontal"),
    CORNER('┼', false, "map/walls/stone_corner"), // TODO there are 4 types of corners?
    MONSTER('O', false, ""),
    OUTSIDE_ROOM(' ', false, "map/grounds/water"),
    CORRIDOR('#', true, "map/grounds/wood"),
    TREE('&',false, "map/miscellaneous/tree"),
    STONE('&',false, "map/miscellaneous/stone"),
    HERO('@', false, "entities/hero/hero_01"),
    ITEM('%', true, "map/miscellaneous/item"),
    COIN('●', true, "map/miscellaneous/coin"),
    EMPTY('.', true, "map/grounds/stone"), // work also for doors
    GOBLIN('G', false, "entities/monsters/goblin"),
    SPIDER('S', false, ""),
    ORC('O', false, ""),
    WIZARD('W', false, "");

    private final char symbol;
    private final boolean isAccessible;
    private final ImageIcon icon;

    CellElementType(char c, boolean isAccessible, String pathEnd) {
        this.symbol = c;
        this.isAccessible = isAccessible;
        this.icon = new ImageIcon("data/images/" + pathEnd + ".png");
    }

    public char getSymbol() {
        return symbol;
    }

    public boolean isAccessible() {
        return isAccessible;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    @Override
    public String toString() {
        switch (this) {
            case HERO: return ColorStr.cyan("" + symbol);
            case MONSTER: return ColorStr.red("" + symbol);
            case ITEM: return ColorStr.green("" + symbol);
            case COIN: return ColorStr.yellow("" + symbol);
            default: return "" + symbol;
        }
    }
}
