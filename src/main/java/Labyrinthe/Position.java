package Labyrinthe;

public class Position {
    private final int x;
    private final int y;

    Position(int x, int y){
        this.x = x;
        this.y = y;
    }
    public final Position nextPosition(Lab l, Move m){
        //todo
        return this; // TODO
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
