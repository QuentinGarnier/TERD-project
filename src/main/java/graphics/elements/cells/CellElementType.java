package graphics.elements.cells;

import graphics.Tools;

import javax.swing.*;
import java.util.Objects;

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
    BOSS('D', false, "entities/monsters/dragonL"),
    MERCHANT('M', false, "entities/merchant/merchant");

    private static final ImageIcon bossL = new ImageIcon(Objects.requireNonNull(
            CellElementType.class.getClassLoader().getResource("data/images/entities/monsters/dragonL.png")));
    private static final ImageIcon bossR = new ImageIcon(Objects.requireNonNull(
            CellElementType.class.getClassLoader().getResource("data/images/entities/monsters/dragonR.png")));

    private final char symbol;
    private final boolean isAccessible;
    private final ImageIcon icon;

    CellElementType(char c, boolean isAccessible, String pathEnd) {
        this.symbol = c;
        this.isAccessible = isAccessible;
        this.icon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("data/images/" + pathEnd + ".png")));
    }

    public boolean isAccessible() {
        return isAccessible;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public static ImageIcon getBossIcon(boolean left){
        return left ? bossL : bossR;
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
