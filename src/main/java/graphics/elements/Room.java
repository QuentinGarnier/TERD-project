package graphics.elements;

import graphics.elements.cells.*;
import graphics.map.WorldMap;

import java.util.Random;

public class Room {
    public static final int MIN_WIDTH = 3;
    public static final int MAX_WIDTH = 7;
    public static final int MIN_HEIGHT = 3;
    public static final int MAX_HEIGHT = 7;
    private final Position topLeft;
    private final Position bottomRight;
    private boolean heroIsHere = false; // if true room is shown
    Random gen;

    public Room() {
        this.gen = new Random();
        this.topLeft = findTopLeft();
        this.bottomRight = findBottomRight();
    }

    private Position findTopLeft() {
        return new Position(Math.abs(gen.nextInt()) % WorldMap.MAX_X, Math.abs(gen.nextInt()) % WorldMap.MAX_Y);
    }

    private Position findBottomRight() {
        return new Position(
                topLeft.getX() + MIN_WIDTH + Math.abs(gen.nextInt()) % MAX_WIDTH,
                topLeft.getY() + MIN_HEIGHT + Math.abs(gen.nextInt()) % MAX_HEIGHT);
    }

    public boolean insideWorld() {
        return  topLeft.getX() < WorldMap.MAX_X &&
                topLeft.getY() < WorldMap.MAX_Y &&
                topLeft.getX() >= 0 &&
                topLeft.getY() >= 0 &&
                bottomRight.getX() < WorldMap.MAX_X &&
                bottomRight.getY() < WorldMap.MAX_Y &&
                bottomRight.getX() >= 0 &&
                bottomRight.getY() >= 0;
    }

    public boolean checkCollision(Cell[][] lab) {
        if (!insideWorld()) return true;
        for (int x = topLeft.getX(); x <= bottomRight.getX(); x++) {
            for (int y = topLeft.getY(); y <= bottomRight.getY(); y++) {
                if (lab[x][y].getContent().getType() != CellElementType.OUTSIDE_ROOM) return true;
            }
        }
        return false;
    }

    public void updateLab(Cell[][] lab) {
        for (int x = topLeft.getX(); x <= bottomRight.getX(); x++) {
            for (int y = topLeft.getY(); y <= bottomRight.getY(); y++) {
                if ((x == topLeft.getX() && (y == bottomRight.getY() || y == topLeft.getY())) ||
                        (x == bottomRight.getX() && (y == bottomRight.getY() || y == topLeft.getY())))
                    lab[x][y] = new Cell(new AbstractCellElementOutsideRoom());
                else if (x == topLeft.getX() || x == bottomRight.getX())
                    lab[x][y] = new Cell(new AbstractCellElementWall(true));
                else if (y == topLeft.getY() || y == bottomRight.getY())
                    lab[x][y] = new Cell(new AbstractCellElementWall(false));
                else lab[x][y] = new Cell(new AbstractCellElementEmpty());
            }
        }
    }

    void putRandomEltInRoom(Cell[][] lab){

    }
}
