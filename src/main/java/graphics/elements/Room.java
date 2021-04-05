package graphics.elements;

import entity.Merchant;
import entity.Monster;
import graphics.Tools;
import graphics.elements.cells.Cell;
import graphics.elements.cells.CellElementType;
import graphics.map.Theme;
import graphics.map.WorldMap;
import items.AbstractItem;

import java.util.*;

public class Room {
    public static final int MIN_WIDTH = 7;
    public static final int MAX_WIDTH = 12;
    public static final int MIN_HEIGHT = 7;
    public static final int MAX_HEIGHT = 12;
    private static final double MAX_ITEMS = 0.4;
    private static final double MAX_MONSTERS = 0.2;
    private boolean hasBeenVisited;
    private final List<AbstractItem> globalItems;
    private final List<Monster> monsters;
    private final List<AbstractItem> currentRoomItems;
    private final Position topLeft;
    private final Position bottomRight;
    public final int id;
    private int lowestRoomNeighbor;
    private final Random gen;
    private final Cell[][] lab;
    private final List<Position> doors;
    private final Theme theme;

    public Room(List<Room> roomList, Cell[][] lab, List<AbstractItem> items, Theme theme) throws ErrorPositionOutOfBound {
        this.id = roomList.size();
        this.globalItems = items;
        this.monsters = new ArrayList<>();
        this.doors = new ArrayList<>();
        this.currentRoomItems = new ArrayList<>();
        this.lowestRoomNeighbor = this.id;
        this.gen = new Random();
        this.topLeft = findTopLeft();
        this.bottomRight = findBottomRight();
        this.hasBeenVisited = false;
        this.theme = theme;
        this.lab = lab;
        if (!checkCollision()) {
            updateLab();
            roomList.add(this);
            putRandomEltInRoom();
        }
    }

    public Room(List<Room> roomList, Cell[][] lab, Theme theme) throws ErrorPositionOutOfBound {
        this.id = 0;
        this.topLeft = new Position(5,5);
        this.bottomRight = new Position(25,25);
        this.lab = lab;
        this.globalItems = null;
        this.currentRoomItems = null;
        this.gen = new Random();
        this.monsters = new ArrayList<>();
        this.doors = new ArrayList<>();
        this.monsters.add(Monster.boss);
        this.theme = theme;
        updateLab();
        roomList.add(this);
    }

    private Position findTopLeft() {
        return new Position(Math.abs(gen.nextInt()) % WorldMap.MAX_X, Math.abs(gen.nextInt()) % WorldMap.MAX_Y);
    }

    private Position findBottomRight() {
        return new Position(
                topLeft.getX() + MIN_WIDTH + gen.nextInt(MAX_WIDTH - MIN_WIDTH),
                topLeft.getY() + MIN_HEIGHT + gen.nextInt(MAX_HEIGHT - MIN_HEIGHT));
    }

    private boolean checkCollision() {
        if (!topLeft.insideWorld() || !bottomRight.insideWorld()) return true;
        for (int x = topLeft.getX(); x <= bottomRight.getX(); x++) {
            for (int y = topLeft.getY(); y <= bottomRight.getY(); y++) {
                if (lab[x][y].getBaseContent() != CellElementType.OUTSIDE_ROOM) return true;
            }
        }
        return false;
    }

    private void updateLab() {
        for (int x = topLeft.getX(); x <= bottomRight.getX(); x++) {
            for (int y = topLeft.getY(); y <= bottomRight.getY(); y++) {
                Position pos = new Position(x, y);
                if ((x == topLeft.getX() || x == bottomRight.getX()) && y == topLeft.getY())
                    lab[x][y] = new Cell((CellElementType.CORNER_TOP), id, pos, lab);
                else if ((x == bottomRight.getX() || x == topLeft.getX()) && y == bottomRight.getY())
                    lab[x][y] = new Cell((CellElementType.CORNER_BOT), id, pos, lab);
                else if (x == topLeft.getX() || x == bottomRight.getX())
                   lab[x][y] = new Cell(CellElementType.VERTICAL_WALL, id, pos, lab);
                else if (y == topLeft.getY() || y == bottomRight.getY())
                    lab[x][y] = new Cell(CellElementType.HORIZONTAL_WALL, id, pos, lab);
                else lab[x][y] = new Cell(CellElementType.EMPTY, id, pos, lab); // ==> empty cell <-> in room
            }
        }
    }

    private void putRandomEltInRoom() throws ErrorPositionOutOfBound {
        putItems();
        putMonsters();
    }

    private void putItems(){
        int nbOfElt = /*gen.nextInt(*/(int) Math.round(getArea() * MAX_ITEMS)/*)*/;
        while (nbOfElt > 0) {
            Position pos = getRandomPosInRoom();
            Cell currentC = lab[pos.getX()][pos.getY()];
            if (gen.nextInt(2) == 0) {
                AbstractItem m = AbstractItem.generateRandomItem(globalItems.size(), pos);
                currentC.setItem(m);
                globalItems.add(m);
                currentRoomItems.add(m);
            } else {
                currentC.setObstacle(theme.obstacle2);
            }
            nbOfElt--;
        }
    }

    public void addDoor(Position door){
        doors.add(door);
    }

    public boolean putObstacles() {
        boolean isMerchantRoom = id == Merchant.getInstanceMerchant().getSafeRoomId();
        if (!isMerchantRoom) {
            for (int x = topLeft.getX() + 1; x < bottomRight.getX(); x++)
                for (int y = topLeft.getY() + 1; y < bottomRight.getY(); y++)
                    if (lab[x][y].getMainContentType().equals(lab[x][y].getBaseContent()))
                        lab[x][y].setObstacle(theme.obstacle1);
        }
        Position start = doors.get(0);
        boolean[][] booleans = new boolean[getWidth() + 1][getHeight() + 1];
        for (boolean[] aBoolean : booleans) Arrays.fill(aBoolean, false);
        List<List<Position>> bfs = Tools.BFS(start, this, lab, null);
        doors.forEach(door -> Tools.findPath(bfs, start, door, this, lab, booleans));
        monsters.forEach(monster -> Tools.BFS(monster.getPosition(), this, lab, booleans));
        if (!isMerchantRoom) currentRoomItems.forEach(item -> Tools.BFS(item.getPosition(), this, lab, booleans));
        else Tools.BFS(Merchant.getInstanceMerchant().getPosition(), this, lab, booleans);
        return true;
    }

    private void putMonsters() throws ErrorPositionOutOfBound {
        int nbOfElt = gen.nextInt((int) Math.round(getArea() * MAX_MONSTERS));
        while (nbOfElt > 0) {
            Position pos = getRandomPosInRoom();
            Cell currentC = lab[pos.getX()][pos.getY()];
            Monster m = Monster.generateRandomMonster(pos, monsters.size());
            currentC.setEntity(m);
            monsters.add(m);
            nbOfElt--;
        }
    }

    private Position getRandomPosInRoom() {
        int x = gen.nextInt(getWidth() - 2) + topLeft.getX() + 1;
        int y = gen.nextInt(getHeight() - 2) + topLeft.getY() + 1;
        if (lab[x][y].getMainContentType().equals(lab[x][y].getBaseContent()) && lab[x][y].getObstacle() == null)
            return new Position(x, y);
        else return getRandomPosInRoom();
    }

    public List<AbstractItem> getGlobalItems() {
        return Collections.unmodifiableList(globalItems);
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
        return (getHeight() - 2) * (getWidth() - 2);
    }

    public int getLowestRoomNeighbor() {
        return lowestRoomNeighbor;
    }

    public int getId() {
        return id;
    }

    public List<Monster> getMonsters() {
        return Collections.unmodifiableList(monsters);
    }

    public List<AbstractItem> getItems() { return Collections.unmodifiableList(currentRoomItems);}

    public static boolean isRoom(Cell c) {
        CellElementType ct = c.getBaseContent();
        return ct == CellElementType.HORIZONTAL_WALL ||
                ct == CellElementType.VERTICAL_WALL ||
                ct == CellElementType.CORNER_BOT ||
                ct == CellElementType.CORNER_TOP ||
                ct == CellElementType.EMPTY;
    }

    public boolean isPositionInsideRoom(Position p) {
        return p.getX() >= topLeft.getX() && p.getX() <= bottomRight.getX() &&
                p.getY() >= topLeft.getY() && p.getY() <= bottomRight.getY();
    }

    public void removeEntity(Monster m) {
        monsters.remove(m);
    }

    public boolean isHasBeenVisited() {
        return hasBeenVisited;
    }

    public void setVisited(){
        if (!hasBeenVisited) {
            hasBeenVisited = true;
            for (int x = 0; x < getWidth() + 1; x++) for (int y = 0; y < getHeight() + 1; y++){
                lab[topLeft.getX() + x][topLeft.getY() + y].removeFog();
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int j = topLeft.getY(); j <= bottomRight.getY(); j++) {
            for(int i = topLeft.getX(); i <= bottomRight.getX(); i++) {
                //sb.append(lab[i][j].getObstacle() == null ? (lab[i][j].getMainContentType().equals(lab[i][j].getBaseContent()) ? lab[i][j].getBaseContent().toString() : ".") : lab[i][j].getObstacle().toString());
                sb.append(lab[i][j].toString());
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }
}
