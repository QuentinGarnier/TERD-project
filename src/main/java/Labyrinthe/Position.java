package Labyrinthe;

public class Position {
    private int x;
    private int y;

    Position(int x, int y){
        this.x = x;
        this.y = y;
    }
    public final void nextPosition(Lab l, Position p){
        int newX = p.x + x;
        int newY = p.y + y;
        if (!l.getCell(newX, newY).isBlocked()){
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
