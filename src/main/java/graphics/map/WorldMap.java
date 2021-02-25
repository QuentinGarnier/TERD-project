package graphics.map;

import entity.Player;
import graphics.elements.cells.Cell;
import graphics.elements.cells.CellElementEntity;
import graphics.elements.cells.CellElementOutsideRoom;
import graphics.elements.Room;
import graphics.elements.cells.CellElementType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WorldMap {
    private static final WorldMap instanceWorld = new WorldMap();

    public static final int MAX_X = 50; // to be verified
    public static final int MAX_Y = 20; // to be verified
    private final int maxRandomRoom = 100;
    private Cell[][] lab;
    private List<Room> rooms;


    private WorldMap() {
        lab = new Cell[MAX_X][MAX_Y];
        rooms = new ArrayList<Room>();
        initializeLab();
        createRooms();
        placePlayer();
    }

    private void initializeLab() {
        for (int x = 0; x < MAX_X; x++) {
            for (int y = 0; y < MAX_Y; y++) {
                lab[x][y] = new Cell(new CellElementOutsideRoom());
            }
        }
    }

    private void createRooms() {
        for (int i = 0; i < maxRandomRoom; i++) {
            Room r = new Room();
            if (!r.checkCollision(lab)) {
                r.updateLab(lab);
                rooms.add(r);
            }
        }
    }

    public static WorldMap getInstanceWorld() {
        return instanceWorld;
    }

    public Cell getCell(int x, int y) {
        return instanceWorld.lab[x][y];
    }

    private void placePlayer() {
        Random rnd = new Random();
        int iRoom = rnd.nextInt(rooms.size());
        Room room = this.rooms.get(iRoom);
        room.setHeroIsHere(true);

        int x = room.getTopLeft().getX() + rnd.nextInt(room.getWidth() - 1) + 1;
        int y = room.getTopLeft().getY() + rnd.nextInt(room.getHeight() - 1) + 1;

        Player.getInstancePlayer().setPosition(x, y);
        lab[x][y] = new Cell(new CellElementEntity(CellElementType.HERO, Player.getInstancePlayer()));
    }

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
