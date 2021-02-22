package graphics.elements;

import graphics.map.WorldMap;

public class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public final void nextPosition(WorldMap wmap, Position p) {
        int newX = p.getX() + this.x;
        int newY = p.y + y;
        if (wmap.getCell(newX, newY).getContent().isAccessible()) {
            x = newX; y = newY;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
