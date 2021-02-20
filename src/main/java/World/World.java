package World;

public class World {
    private static final World instanceWorld = new World();
    public static World getInstanceWorld() {
        return instanceWorld;
    }

    public static final int MAX_X = 70; // to be verified
    public static final int MAX_Y = 20; // to be verified
    private final Cell[][] lab;

    private World(){
        lab = new Cell[MAX_X][MAX_Y];
        for (int y = 0; y < MAX_Y; y++){
            // TODO to be verified
            for (int x = 0; x < MAX_X; x++){
                if((x == 0 && (y == 0 || y == (MAX_Y - 1))) ||
                        (x == (MAX_X - 1) && (y == 0 || y == (MAX_Y - 1)))){
                    lab[x][y] = new Cell(ElementsEnum.CORNER);
                } else if (x == 0 || x == MAX_X - 1){
                    lab[x][y] = new Cell(ElementsEnum.VERTICAL_WALL);
                } else if (y == 0 || y == MAX_Y - 1){
                    lab[x][y] = new Cell(ElementsEnum.HORIZONTAL_WALL);
                } else {
                    lab[x][y] = new Cell(ElementsEnum.EMPTY);
                }
            }
        }
    }

    /* TODO is it useful ? a cell is valid if it accessible
        see isAccessible method in Cell class
    public boolean validCell(int x, int y){
        return x >= 0 && x < MAX_X && y >= 0 && y < MAX_Y;
    }
    */

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
