package graphics.elements.cells;

import graphics.Tools;

import javax.swing.*;

public enum CellElementType {
    EMPTY('.', true, "map/grounds/grass"), // work also for doors
    HORIZONTAL_WALL('—', false, "map/walls/stone_horizontal"),
    VERTICAL_WALL('|', false, "map/walls/stone_vertical"),
    CORNER_BOT('┼', false, "map/walls/stone_corner_bot"),
    CORNER_TOP('┼', false, "map/walls/stone_corner_top"),
    CORRIDOR('#', true, "map/grounds/wood"),
    OUTSIDE_ROOM(' ', false, "map/grounds/water"),

    STONE('&',false, "map/miscellaneous/stone"),
    TREE('T',false, "map/miscellaneous/tree"),
    PALM_TREE('T', false, "map/miscellaneous/palm_tree"),
    HOLE('u', false, "map/miscellaneous/hole"),
    BOX('■', false, "map/miscellaneous/box"),

    COIN('●', true, "map/miscellaneous/coin"),
    ITEM('%', true, "items/consumable/bag"),
    BURGER('B', true, "items/food/big_burger"),
    END('E', true, "map/miscellaneous/end"),
    TRAP('x', true, "map/miscellaneous/trap"),

    HERO_W('@', false, "entities/hero/hero_01"),
    HERO_A('@', false, "entities/hero/hero_02"),
    HERO_M('@', false, "entities/hero/hero_03"),
    GOBLIN('G', false, "entities/monsters/goblin"),
    SPIDER('S', false, "entities/monsters/spider"),
    WIZARD('W', false, "entities/monsters/wizard"),
    ORC('O', false, "entities/monsters/orc"),
    BOSS('D', false, "entities/monsters/dragon"),
    MERCHANT('M', false, "entities/merchant/merchant");

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

    public boolean isMonster() {
        return switch (this) {
            case GOBLIN, ORC, SPIDER, WIZARD -> true;
            default -> false;
        };
    }

    public boolean isMerchant() {
        return this == MERCHANT;
    }

    public boolean isWall() {
         return this.equals(CellElementType.VERTICAL_WALL)
                || this.equals(CellElementType.HORIZONTAL_WALL) || this.equals(CellElementType.CORNER_BOT)
                || this.equals(CellElementType.CORNER_TOP);
    }

    public boolean isObstacle() {
        return this.equals(CellElementType.TREE) || this.equals(CellElementType.STONE)
                || this.equals(CellElementType.HOLE) || this.equals(CellElementType.BOX) || this.equals(CellElementType.PALM_TREE);
    }

    @Override
    public String toString() {
        return switch (this) {
            case HERO_W, HERO_A, HERO_M -> Tools.TerminalText.cyan("" + symbol);
            case ITEM -> Tools.TerminalText.green("" + symbol);
            case COIN -> Tools.TerminalText.yellow("" + symbol);
            case TRAP -> Tools.TerminalText.red("" + symbol);
            case END -> Tools.TerminalText.blue("" + symbol);
            default -> "" + symbol;
        };
    }
}
