package World;

import java.util.Arrays;

public class World {
    private static final World instanceWorld = new World();
    public static World getInstanceWorld() {
        return instanceWorld;
    }

    public static final int MAX_X = 70; // to be verified
    public static final int MAX_Y = 20; // to be verified
    private final Cell lab[][];

    private World(){
        lab = new Cell[MAX_X][MAX_Y];
        for (int y = 0; y < MAX_Y; y++){
            for (int x = 0; x < MAX_X; x++){ // TODO
                if(x == 0 || x == (MAX_X - 1) || y == 0 || y == (MAX_Y - 1)){
                    lab[x][y] = new Cell(ElementsEnum.WALL); // TODO
                }
                else{
                    lab[x][y] = new Cell(ElementsEnum.EMPTY); // TODO
                }

            }
        }
    }

    public boolean validCell(int x, int y){
        return x >= 0 && x < MAX_X && y >= 0 && y < MAX_Y;
    }

    public Cell getCell(int x, int y) {
        return lab[x][y];
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int j = 0; j < MAX_Y; j++){
            for(int i = 0; i < MAX_X; i++){
                sb.append(lab[i][j].toString());
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }
}
