package graphics.elements;

import entity.Player;
import graphics.elements.cells.Cell;
import graphics.elements.cells.CellElementType;
import graphics.map.WorldMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public final boolean nextPosition(Position p) {
        WorldMap worldMap = WorldMap.getInstanceWorld();
        int newX = p.getX() + this.x;
        int newY = p.y + y;
        if (insideWorld(newX, newY) && worldMap.getCell(newX, newY).isAccessible()) {
            x = newX; y = newY;
            return true;
        }
        return false;
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
        return x >= 0 && x < WorldMap.MAX_X &&
                y >= 0 && y < WorldMap.MAX_Y;
    }

    public int posToInt(){
        return WorldMap.MAX_Y * y + x;
    }

    public boolean insideWorld() {
        return insideWorld(x, y);
    }

   /* public static int calculateRange(Position p1, Position p2){
        /*if (p1.x == p2.x) return Math.abs(p1.y - p2.y);
        if (p1.y == p2.y) return Math.abs(p1.x - p2.x);
        return Integer.MAX_VALUE;
        return Math.max(Math.abs(p2.x - p1.x), Math.abs(p2.y - p1.y));
    }*/

    public double distance(Position p){
        return distance(this, p);
    }

    public Position[] getNeighbor(boolean worldCreation){
        WorldMap worldMap = WorldMap.getInstanceWorld();
        List<Position> positionList = new ArrayList<>();
        Position pos;
        for (Move m : Move.values()) {
            pos = Position.sumPos(this, m);
            if (pos != null && (worldCreation || worldMap.getCell(pos).isAccessible())) positionList.add(pos);
        }
        return positionList.toArray(new Position[0]);
    }

    public static double distance(Position p1, Position p2){
        if (p1 == null || p2 == null) return Double.MAX_VALUE;
        return Math.round(Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2)));
    }

    public static ArrayList<Position> calcRangePosition(){
        Player player = Player.getInstancePlayer();
        int range = player.getRange();
        Position pos = player.getPosition();
        WorldMap worldMap = WorldMap.getInstanceWorld();
        ArrayList<Position> res = new ArrayList<>();
        int roomId = worldMap.getCell(pos).getBaseId();
        for (int x = 0; x < 2 * range; x++)
            for (int y = 0; y < 2 * range; y++){
                Position p = new Position(pos.getX() - range + x, pos.getY() - range + y);
                if (!p.insideWorld()) continue;
                Cell c = worldMap.getCell(p);
                if (p.insideWorld() &&
                        !(distance(p, pos) > range) &&
                        c.getBaseContent().equals(CellElementType.EMPTY) &&
                        c.getBaseId() == roomId) res.add(p);
            }
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
