package graphics.elements.cells;

import graphics.Tools;

import javax.swing.*;

public enum CellElementType {
    BURGER('B', true, "items/food/big_burger"),
    COIN('●', true, "map/miscellaneous/coin"),
    CORNER_BOT('┼', false, "map/walls/stone_corner_bot"),
    CORNER_TOP('┼', false, "map/walls/stone_corner_top"),
    CORRIDOR('#', true, "map/grounds/wood"),
    EMPTY('.', true, "map/grounds/grass"), // work also for doors
    GOBLIN('G', false, "entities/monsters/goblin"),
    HORIZONTAL_WALL('—', false, "map/walls/stone_horizontal"),
    ITEM('%', true, "items/consumable/bag"),
    MERCHANT('M', false, ""),
    HERO_A('@', false, "entities/hero/hero_02"),
    HERO_M('@', false, "entities/hero/hero_03"),
    HERO_W('@', false, "entities/hero/hero_01"),
    ORC('O', false, "entities/monsters/orc"),
    OUTSIDE_ROOM(' ', false, "map/grounds/water"),
    SPIDER('S', false, "entities/monsters/spider"),
    STONE('&',false, "map/miscellaneous/stone"),
    TRAP('x', true, ""),
    TREE('&',false, "map/miscellaneous/tree"),
    VERTICAL_WALL('|', false, "map/walls/stone_vertical"),
    WIZARD('W', false, "entities/monsters/wizard");

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
            case ITEM -> Tools.TerminalText.green("" + symbol);
            case COIN -> Tools.TerminalText.yellow("" + symbol);
            case TRAP -> Tools.TerminalText.red("" + symbol);
            default -> "" + symbol;
        };
    }
}
