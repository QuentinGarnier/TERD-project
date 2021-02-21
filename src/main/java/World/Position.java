package World;

public class Position {
    private int x;
    private int y;

    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }
    public final void nextPosition(World w, Position p){
        int newX = p.x + x;
        int newY = p.y + y;
        if (w.getCell(newX, newY).getCellContent().isAccessible()){
            x = newX; y = newY;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
