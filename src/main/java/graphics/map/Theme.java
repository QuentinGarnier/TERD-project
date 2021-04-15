package graphics.map;

import graphics.Language;
import graphics.elements.cells.CellElementType;

import javax.swing.*;
import java.util.Objects;

public enum Theme {
    DUNGEON("stone", "stone", "stone", "lava", CellElementType.STONE, CellElementType.HOLE),
    FOREST("grass", "stone", "dirt", "water", CellElementType.TREE, CellElementType.STONE),
    ISLANDS("grass", "fence", "sand", "water", CellElementType.PALM_TREE, CellElementType.STONE),
    FINAL_BOSS("brick", "brick", "stone", "lava", CellElementType.STONE, CellElementType.HOLE),
    MERCHANT("wood", "wood", "wood", "water", CellElementType.BOX, CellElementType.BOX);

    /**
     * ===== Obstacles for themes =====
     *
     * DUNGEON -> stone, hole
     * FOREST -> tree, stone
     * ISLANDS -> tree, stone
     * FINAL_BOSS -> stone, hole
     * MERCHANT -> box
     */

    public final ImageIcon ground;
    public final ImageIcon wall_horizontal, wall_vertical, corner_top, corner_bot;
    public final ImageIcon corridor;
    public final ImageIcon outside;
    public final CellElementType obstacle1, obstacle2;

    Theme(String groundStr, String wallsStr, String corridorsStr, String outsideStr, CellElementType obstacle1Str, CellElementType obstacle2Str) {
        ground = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("data/images/map/grounds/" + groundStr + ".png")));
        corridor = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("data/images/map/grounds/" + corridorsStr + ".png")));
        outside = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("data/images/map/grounds/" + outsideStr + ".png")));

        wall_horizontal = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("data/images/map/walls/" + wallsStr + "_horizontal.png")));
        wall_vertical = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("data/images/map/walls/" + wallsStr + "_vertical.png")));
        corner_top = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("data/images/map/walls/" + wallsStr + "_corner_top.png")));
        corner_bot = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("data/images/map/walls/" + wallsStr + "_corner_bot.png")));

        obstacle1 = obstacle1Str;
        obstacle2 = obstacle2Str;
    }

    public ImageIcon getWall_vertical() {
        return wall_vertical;
    }

    public ImageIcon getCorner_bot() {
        return corner_bot;
    }

    public ImageIcon getCorner_top() {
        return corner_top;
    }

    public ImageIcon themeFor(CellElementType type) {
        return switch (type) {
            case EMPTY -> ground;
            case CORRIDOR -> corridor;
            case OUTSIDE_ROOM -> outside;
            case HORIZONTAL_WALL -> wall_horizontal;
            case VERTICAL_WALL -> wall_vertical;
            case CORNER_BOT -> corner_bot;
            case CORNER_TOP -> corner_top;
            default -> new ImageIcon();
        };
    }

    @Override
    public String toString() {
        return Language.translate(this);
    }
}
