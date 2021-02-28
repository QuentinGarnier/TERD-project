package graphics.elements;

import graphics.elements.cells.*;
import graphics.map.WorldMap;

import java.util.List;
import java.util.Random;

public class Room {
    public static final int MIN_WIDTH = 5;
    public static final int MAX_WIDTH = 7;
    public static final int MIN_HEIGHT = 5;
    public static final int MAX_HEIGHT = 7;
    private final Position topLeft;
    private final Position bottomRight;
    public final int id;
    private int lowestRoomNeighbor;
    private final Random gen;

    public Room(List<Room> roomList, WorldMap w) {
        this.id = roomList.size();
        this.lowestRoomNeighbor = this.id;
        this.gen = new Random();
        this.topLeft = findTopLeft();
        this.bottomRight = findBottomRight();
        if (!checkCollision(w)){
            updateLab(w);
            roomList.add(this);
        }
    }

    private Position findTopLeft() {
        return new Position(Math.abs(gen.nextInt()) % WorldMap.MAX_X, Math.abs(gen.nextInt()) % WorldMap.MAX_Y);
    }

    private Position findBottomRight() {
        return new Position(
                topLeft.getX() + MIN_WIDTH + gen.nextInt(MAX_WIDTH - MIN_WIDTH),
                topLeft.getY() + MIN_HEIGHT + gen.nextInt(MAX_HEIGHT - MIN_HEIGHT));
    }

    public boolean checkCollision(WorldMap w) {
        if (!topLeft.insideWorld() || !bottomRight.insideWorld()) return true;
        for (int x = topLeft.getX(); x <= bottomRight.getX(); x++) {
            for (int y = topLeft.getY(); y <= bottomRight.getY(); y++) {
                if (w.getCell(x, y).getCurrentContent() != CellElementType.OUTSIDE_ROOM) return true;
            }
        }
        return false;
    }

    public void updateLab(WorldMap w) {
        for (int x = topLeft.getX(); x <= bottomRight.getX(); x++) {
            for (int y = topLeft.getY(); y <= bottomRight.getY(); y++) {
                if ((x == topLeft.getX() && (y == bottomRight.getY() || y == topLeft.getY())) ||
                        (x == bottomRight.getX() && (y == bottomRight.getY() || y == topLeft.getY())))
                    w.setCell(x, y, new Cell(CellElementType.CORNER, id));
                else if (x == topLeft.getX() || x == bottomRight.getX())
                    w.setCell(x, y, new Cell(CellElementType.VERTICAL_WALL, id));
                else if (y == topLeft.getY() || y == bottomRight.getY())
                    w.setCell(x, y, new Cell(CellElementType.HORIZONTAL_WALL, id));
                else w.setCell(x, y, new Cell(CellElementType.EMPTY, id)); // ==> empty cell <-> in room
            }
        }
    }

    void putRandomEltInRoom(WorldMap w) {

    }

    public void setLowestRoomNeighbor(int lowestRoomNeighbor) {
        this.lowestRoomNeighbor = lowestRoomNeighbor;
    }

    public Position getTopLeft() {
        return topLeft;
    }

    public Position getBottomRight() {
        return bottomRight;
    }

    public int getWidth() {
        return (bottomRight.getX() - topLeft.getX());
    }

    public int getHeight() {
        return (bottomRight.getY() - topLeft.getY());
    }

    public int getLowestRoomNeighbor() {
        return lowestRoomNeighbor;
    }

    public int getId() { return id; }

    public static boolean isRoom(Cell c){
        CellElementType ct = c.getCurrentContent();
        return ct == CellElementType.HORIZONTAL_WALL ||
                ct == CellElementType.VERTICAL_WALL ||
                ct == CellElementType.CORNER ||
                ct == CellElementType.EMPTY;
    }

    public boolean isPositionInsideRoom(Position p){
        return p.getX() >= topLeft.getX() && p.getX() <= bottomRight.getX() &&
                p.getY() >= topLeft.getY() && p.getY() <= bottomRight.getY();
    }
}
