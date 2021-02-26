package graphics.elements;

import graphics.map.WorldMap;

import java.util.Objects;

public class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public final void nextPosition(WorldMap wmap, Position p) {
        int newX = p.getX() + this.x;
        int newY = p.y + y;
        if (wmap.getCell(newX, newY).getContent().isAccessible()) {
            x = newX; y = newY;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Position sumPos(Position p, Move m) {
        Position res = new Position(p.x + m.getMove().x, p.y + m.getMove().y);
        if (res.x < 0 || res.x >= WorldMap.MAX_X ||
                res.y < 0 || res.y >= WorldMap.MAX_Y) return null;
        return res;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Position){
            Position position = (Position) o;
            return position.getX() == x && position.getY() == y;
        } return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
