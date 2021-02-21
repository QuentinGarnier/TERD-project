package World;

import java.util.ArrayList;
import java.util.List;

public class World {
    private static final World instanceWorld = new World();
    public static World getInstanceWorld() {
        return instanceWorld;
    }

    public static final int MAX_X = 50; // to be verified
    public static final int MAX_Y = 20; // to be verified
    private final int maxRandomRoom = 100;
    private final Cell[][] lab;
    private final List<Room> rooms;


    private World(){
        lab = new Cell[MAX_X][MAX_Y];
        rooms = new ArrayList<Room>();
        initializeLab();
        createRooms();
    }

    void initializeLab(){
        for (int x = 0; x < MAX_X; x++){
            for (int y = 0; y < MAX_Y; y++){
                lab[x][y] = new Cell(ElementsEnum.OUTSIDE_ROOM);
            }
        }
    }

    void createRooms(){
        for (int i = 0; i < maxRandomRoom; i++){
            Room r = new Room();
            if (!r.checkCollision(lab)){
                r.updateLab(lab);
                rooms.add(r);
            }
        }
    }

    public Cell getCell(int x, int y) {
        return lab[x][y];
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int j = 0; j < MAX_Y; j++){
            for(int i = 0; i < MAX_X; i++){
                if (lab[i][j] != null) sb.append(lab[i][j].toString());
                else sb.append(' ');
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }
}
