package World;

public class World {
    public static final int MAX_X = 100; // to be verified
    public static final int MAX_Y = 100; // to be verified
    private final Cell lab[][];
    World(){
        lab = new Cell[MAX_X][MAX_Y];
        for (int x = 0; x < MAX_X; x++){
            for (int y = 0; y < MAX_Y; y++){
                lab[x][y] = new Cell();
            }
        }
    }
    public Cell getCell(int x, int y) {
        return lab[x][y];
    }
}
