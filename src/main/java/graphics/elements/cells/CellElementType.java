package graphics.elements.cells;

import graphics.Tools;

import javax.swing.*;

public enum CellElementType {
    BURGER('B', true, "items/consumable/big_burger"),
    VERTICAL_WALL('|', false, "map/walls/stone_vertical"),
    HORIZONTAL_WALL('—', false, "map/walls/stone_horizontal"),
    CORNER('┼', false, "map/walls/stone_corner"), // TODO there are 4 types of corners?
    MONSTER('O', false, ""),
    OUTSIDE_ROOM(' ', false, "map/grounds/water"),
    CORRIDOR('#', true, "map/grounds/wood"),
    TREE('&',false, "map/miscellaneous/tree"),
    STONE('&',false, "map/miscellaneous/stone"),
    HERO_W('@', false, "entities/hero/hero_01"),
    HERO_A('@', false, "entities/hero/hero_02"),
    HERO_M('@', false, "entities/hero/hero_03"),
    MERCHANT('M', false, ""),
    ITEM('%', true, "map/miscellaneous/item"),
    COIN('●', true, "map/miscellaneous/coin"),
    EMPTY('.', true, "map/grounds/stone"), // work also for doors
    GOBLIN('G', false, "entities/monsters/goblin"),
    SPIDER('S', false, "entities/monsters/spider"),
    ORC('O', false, "entities/monsters/orc"),
    WIZARD('W', false, "entities/monsters/wizard"),
    TRAP('x', true, "");

    private final char symbol;
    private final boolean isAccessible;
    private ImageIcon icon;

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

    public void setIcon(ImageIcon i) {
        this.icon = i;
    }

    public boolean isHero() {
        return switch (this) {
            case HERO_W, HERO_A, HERO_M -> true;
            default -> false;
        };
    }

    @Override
    public String toString() {
        return switch (this) {
            case HERO_W, HERO_A, HERO_M -> Tools.TerminalText.cyan("" + symbol);
            case MONSTER -> Tools.TerminalText.red("" + symbol);
            case ITEM -> Tools.TerminalText.green("" + symbol);
            case COIN -> Tools.TerminalText.yellow("" + symbol);
            default -> "" + symbol;
        };
    }
}
