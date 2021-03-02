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
    public final void nextPosition(Position p) {
        WorldMap worldMap = WorldMap.getInstanceWorld();
        int newX = p.getX() + this.x;
        int newY = p.y + y;
        if (insideWorld(newX, newY) && worldMap.getCell(newX, newY).getBaseContent().isAccessible()) {
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
        return res.insideWorld() ? res : null;
    }

    public static boolean insideWorld(int x, int y){
        return x > 0 && x < WorldMap.MAX_X &&
                y > 0 && y < WorldMap.MAX_Y;
    }

    public boolean insideWorld() {
        return insideWorld(x, y);
    }

    public static int calculateRange(Position p1, Position p2){
        return Math.max(Math.abs(p2.x- p1.x), Math.abs(p2.y - p1.y));
    }

    public double distance(Position p){
        return distance(this, p);
    }

    Position[] getNeighbor(){
        Position[] ps = new Position[4];
        ps[0] = Position.sumPos(this, Move.UP);
        ps[1] = Position.sumPos(this, Move.LEFT);
        ps[2] = Position.sumPos(this,Move.DOWN);
        ps[3] = Position.sumPos(this, Move.RIGHT);
        return ps;
    }

    public static double distance(Position p1, Position p2){
        return Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
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
