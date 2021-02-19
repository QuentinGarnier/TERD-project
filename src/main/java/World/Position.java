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
        if (w.validCell(newX, newY) && !w.getCell(newX, newY).blocked()){
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
