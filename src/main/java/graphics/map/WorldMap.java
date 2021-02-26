package graphics.map;

import entity.Player;
import graphics.elements.Corridor;
import graphics.elements.Position;
import graphics.elements.cells.Cell;
import graphics.elements.Room;
import graphics.elements.cells.CellElementType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WorldMap {
    private static final WorldMap instanceWorld = new WorldMap();

    public static final int MAX_X = 50; // to be verified
    public static final int MAX_Y = 20; // to be verified
    private static final int maxRandomRoom = 100;
    private final Cell[][] lab;
    private final List<Room> rooms;
    private final List<Corridor> corridors;


    private WorldMap() {
        lab = new Cell[MAX_X][MAX_Y];
        rooms = new ArrayList<>();
        corridors = new ArrayList<>();
        initializeLab();
        createRooms();
        placePlayer();
        createCorridors();
    }

    private void initializeLab() {
        for (int x = 0; x < MAX_X; x++) {
            for (int y = 0; y < MAX_Y; y++) {
                lab[x][y] = new Cell(CellElementType.OUTSIDE_ROOM, -1);
            }
        }
    }

    private void createRooms() {
        int id = 0;
        for (int i = 0; i < maxRandomRoom; i++) {
            Room r = new Room(id);
            if (!r.checkCollision(lab)) {
                r.updateLab(lab);
                rooms.add(r);
                id++;
            }
        }
    }

    private void createCorridors() {
        int id = 0;
        for (Room r : rooms) {
            Corridor c = new Corridor(id, this, r, rooms, corridors);
            if (c.isValid()) id++;
        }
        rooms.forEach(x -> System.out.print(x.getLowestRoomNeighbor() + " "));
        System.out.println();
    }

    public static WorldMap getInstanceWorld() {
        return instanceWorld;
    }

    public Cell getCell(int x, int y) {
        return lab[x][y];
    }

    public Cell getCell(Position p) {
        return getCell(p.getX(), p.getY());
    }

    public void setCell(Position p, Cell c){
        lab[p.getX()][p.getY()] = c;
    }

    private void placePlayer() {
        Random rnd = new Random();
        int iRoom = rnd.nextInt(rooms.size());
        Room room = this.rooms.get(iRoom);
        room.setHeroIsHere(true);

        int x = room.getTopLeft().getX() + rnd.nextInt(room.getWidth() - 1) + 1;
        int y = room.getTopLeft().getY() + rnd.nextInt(room.getHeight() - 1) + 1;

        Player.getInstancePlayer().setPosition(x, y);
        lab[x][y] = new Cell(CellElementType.HERO, -1);
    }
    public void setPlayerPlace(int x, int y){
        lab[x][y] = new Cell(CellElementType.HERO, -1);
    }
    public void toEmptyACell(int x, int y){
        lab[x][y] = new Cell(CellElementType.HERO, -1);
    }
    public void repaint(){ System.out.println(this); }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int j = 0; j < MAX_Y; j++) {
            for(int i = 0; i < MAX_X; i++) sb.append(lab[i][j] != null? lab[i][j].toString(): ' ');
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }
}