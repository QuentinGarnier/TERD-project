package graphics.elements;

import entity.EntityType;
import entity.Monster;
import graphics.elements.cells.Cell;
import graphics.elements.cells.CellElementType;
import graphics.map.WorldMap;
import items.AbstractItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Room {
    public static final int MIN_WIDTH = 5;
    public static final int MAX_WIDTH = 7;
    public static final int MIN_HEIGHT = 5;
    public static final int MAX_HEIGHT = 7;
    private final List<AbstractItem> items;
    private final List<Monster> monsters;
    private final Position topLeft;
    private final Position bottomRight;
    public final int id;
    private int lowestRoomNeighbor;
    private final Random gen;

    public Room(List<Room> roomList, Cell[][] lab) throws ErrorPositionOutOfBound {
        this.id = roomList.size();
        this.items = new ArrayList<>();
        this.monsters = new ArrayList<>();
        this.lowestRoomNeighbor = this.id;
        this.gen = new Random();
        this.topLeft = findTopLeft();
        this.bottomRight = findBottomRight();
        if (!checkCollision(lab)){
            updateLab(lab);
            roomList.add(this);
            putRandomEltInRoom(lab);
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

    public boolean checkCollision(Cell[][] lab) {
        if (!topLeft.insideWorld() || !bottomRight.insideWorld()) return true;
        for (int x = topLeft.getX(); x <= bottomRight.getX(); x++) {
            for (int y = topLeft.getY(); y <= bottomRight.getY(); y++) {
                if (lab[x][y].getBaseContent() != CellElementType.OUTSIDE_ROOM) return true;
            }
        }
        return false;
    }

    public void updateLab(Cell[][] lab) {
        for (int x = topLeft.getX(); x <= bottomRight.getX(); x++) {
            for (int y = topLeft.getY(); y <= bottomRight.getY(); y++) {
                if ((x == topLeft.getX() && (y == bottomRight.getY() || y == topLeft.getY())) ||
                        (x == bottomRight.getX() && (y == bottomRight.getY() || y == topLeft.getY())))
                    lab[x][y] = new Cell(CellElementType.CORNER, id);
                else if (x == topLeft.getX() || x == bottomRight.getX())
                   lab[x][y] = new Cell(CellElementType.VERTICAL_WALL, id);
                else if (y == topLeft.getY() || y == bottomRight.getY())
                    lab[x][y] = new Cell(CellElementType.HORIZONTAL_WALL, id);
                else lab[x][y] = new Cell(CellElementType.EMPTY, id); // ==> empty cell <-> in room
            }
        }
    }

    private void putRandomEltInRoom(Cell[][] lab) throws ErrorPositionOutOfBound {
        int nbOfElt = gen.nextInt(getArea() * 30 / 100);
        nbOfElt = 1; // to erase
        while (nbOfElt > 0) {
            //Position pos = getRandomPosInRoom(lab);
            Position pos = new Position(getTopLeft().getX() + 1 , getTopLeft().getY() + 1); // to erase
            Monster m = new Monster(pos, 10,15,monsters.size(), EntityType.GOBLIN);
            lab[pos.getX()][pos.getY()].setEntity(m.entityType.getCellElementType(), m.getId());
            monsters.add(m);
            nbOfElt--;
        }
    }

    private Position getRandomPosInRoom(Cell[][] lab){
        int x = gen.nextInt(getWidth() - 2);
        int y = gen.nextInt(getHeight() - 2);
        if (lab[x][y].getMainContentType().equals(lab[x][y].getBaseContent()))
            return new Position(x, y);
        else return getRandomPosInRoom(lab);
    }

    public List<AbstractItem> getItems() {
        return Collections.unmodifiableList(items);
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

    public int getArea(){
        return getHeight() * getWidth();
    }

    public int getLowestRoomNeighbor() {
        return lowestRoomNeighbor;
    }

    public int getId() { return id; }

    public List<Monster> getMonsters(){
        return Collections.unmodifiableList(monsters);
    }

    public static boolean isRoom(Cell c){
        CellElementType ct = c.getBaseContent();
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
