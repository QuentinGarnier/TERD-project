package graphics.map;

import javax.swing.*;

public enum Theme {
    DUNGEON("stone", "stone", "dirt", "lava", "stone", "hole"),
    FOREST("grass", "stone", "wood", "water", "tree", "stone"),
    ISLANDS("grass", "fence", "sand", "water", "tree", "stone"),
    FINAL_BOSS("stone", "stone", "stone", "lava", "stone", "hole"),
    MERCHANT("wood", "wood", "wood", "water", "box", "box");

    private final ImageIcon ground;
    private final ImageIcon wall_horizontal, wall_vertical, corner_top, corner_bot;
    private final ImageIcon corridor;
    private final ImageIcon outside;
    private final ImageIcon obstacle1, obstacle2;

    Theme(String groundStr, String wallsStr, String corridorsStr, String outsideStr, String obstStr1, String obstStr2) {
        ground = new ImageIcon("data/images/map/grounds/" + groundStr + ".png");
        corridor = new ImageIcon("data/images/map/grounds/" + corridorsStr + ".png");
        outside = new ImageIcon("data/images/map/grounds/" + outsideStr + ".png");

        wall_horizontal = new ImageIcon("data/images/map/walls/" + wallsStr + "_horizontal.png");
        wall_vertical = new ImageIcon("data/images/map/walls/" + wallsStr + "_vertical.png");
        corner_top = new ImageIcon("data/images/map/walls/" + wallsStr + "_corner_top.png");
        corner_bot = new ImageIcon("data/images/map/walls/" + wallsStr + "_corner_bot.png");

        obstacle1 = new ImageIcon("data/images/map/miscellaneous/" + obstStr1 + ".png");
        obstacle2 = new ImageIcon("data/images/map/miscellaneous/" + obstStr2 + ".png");
    }

    public ImageIcon getGround() {
        return ground;
    }

    public ImageIcon getCorridor() {
        return corridor;
    }

    public ImageIcon getOutside() {
        return outside;
    }

    public ImageIcon getWall_horizontal() {
        return wall_horizontal;
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

    public ImageIcon getObstacle1() {
        return obstacle1;
    }

    public ImageIcon getObstacle2() {
        return obstacle2;
    }

    @Override
    public String toString() {
        return this.name();
    }
}
