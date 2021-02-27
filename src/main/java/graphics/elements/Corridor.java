package graphics.elements;

import graphics.elements.cells.Cell;
import graphics.elements.cells.CellElementType;
import graphics.map.WorldMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Corridor {
    private final List<Position> positionList;
    public final int id;
    public Room startRoom;
    private final Random gen;
    private boolean isValid;

    public Corridor(int id, WorldMap w, Room r, List<Room> rooms, List<Corridor> corridors){
        positionList = new ArrayList<>();
        isValid = false;
        gen = new Random();
        this.id = id;
        this.startRoom = r;
        if (r.id == 0 || r.getLowestRoomNeighbor() != 0){
            createCorridor(w, r, rooms, corridors);
            while (!isValid) createCorridor(w, r, rooms, corridors);
            corridors.add(this);
        }
    }

    private void createCorridor(WorldMap w, Room r, List<Room> rooms, List<Corridor> corridors){
        Position start = openDoor(r);
        List<Position> Q = new ArrayList<>();
        Position[][] P = new Position[WorldMap.MAX_X][WorldMap.MAX_Y];
        Q.add(start);
        while (Q.size() != 0){
            Position z = eject(Q);
            for (Position x : getNeighbor(w, z, r)){
                if (x != null && P[x.getX()][x.getY()] == null){
                    P[x.getX()][x.getY()] = z;
                    Cell cell = w.getCell(x);
                    CellElementType ct = cell.getContent();
                    if ((ct == CellElementType.HORIZONTAL_WALL ||
                            ct == CellElementType.VERTICAL_WALL) &&
                            rooms.get(cell.id).getLowestRoomNeighbor() != startRoom.getLowestRoomNeighbor()) {
                        updateRooms(rooms, rooms.get(cell.id));
                        createPath(w, P, start, x);
                        return;
                    } else if (ct == CellElementType.CORRIDOR &&
                            corridors.get(cell.id).startRoom.getLowestRoomNeighbor() != startRoom.getLowestRoomNeighbor()) {
                        updateCorridors(rooms, corridors.get(cell.id));
                        createPath(w, P, start, x);
                        return;
                    } else if (ct == CellElementType.OUTSIDE_ROOM){
                        Q.add(x);
                    }
                }
            }
        }
    }

    private void updateRooms(List<Room> rooms, Room endRoom){
        int start = startRoom.getLowestRoomNeighbor();
        int end = endRoom.getLowestRoomNeighbor();
        int min = Math.min(start, end);
        int max = Math.max(start, end);
        //System.out.println("Linking :" + startRoom.id + " to " + endRoom.id);
        //System.out.println("Updating : " + max + " to " + min);
        rooms.forEach(x -> {
            if (x.getLowestRoomNeighbor() == max)
                x.setLowestRoomNeighbor(min);
        });
        //rooms.forEach(x -> System.out.print(x.getLowestRoomNeighbor() + " "));
        //System.out.println();
    }


    private void updateCorridors(List<Room> rooms, Corridor c){
        //System.out.println("CORRIDOR");
        updateRooms(rooms, c.startRoom);
    }

    private void createPath(WorldMap w, Position[][] P, Position start, Position end){
        int iStart = w.getCell(start).id;
        int iEnd = w.getCell(end).id;
        Position current = P[end.getX()][end.getY()];
        while (!current.equals(start)){
            positionList.add(current);
            w.setCell(current, new Cell(CellElementType.CORRIDOR, this.id));
            current = P[current.getX()][current.getY()];
        }
        w.setCell(start, new Cell(CellElementType.EMPTY, iStart));
        w.setCell(end, new Cell(CellElementType.EMPTY, iEnd));
        isValid = true;
    }

    private Position eject(List<Position> Q){
        if (Q.size() == 0) return null;
        int rnd = gen.nextInt(Q.size());
        return Q.remove(rnd);
    }

    Position openDoor(Room r){
        int rnd = gen.nextInt(4);
        Position res;
        // 0 = horizontal top | 1 = vertical left | 2 = horizontal bottom | 3 = vertical right
        int  cord = 1 + gen.nextInt((rnd % 2 == 0 ?  r.getWidth() : r.getHeight()) - 1);
        switch (rnd){
            case 0:
                res = new Position(r.getTopLeft().getX() + cord, r.getTopLeft().getY());
                break;
            case 1:
                res = new Position(r.getTopLeft().getX(), r.getTopLeft().getY() + cord);
                break;
            case 2:
                res = new Position(r.getBottomRight().getX() - cord, r.getBottomRight().getY());
                break;
            case 3:
                // orientation of ax to bottom
                res = new Position(r.getBottomRight().getX(), r.getBottomRight().getY() - cord);
                break;
            default: res = null;
        }
        if (res.getX() == 0 || res.getX() == WorldMap.MAX_X - 1 ||
                res.getY() == 0 || res.getY() == WorldMap.MAX_Y - 1) return openDoor(r);
        return res;
    }

    Position[] getNeighbor(WorldMap w, Position p, Room r){
        Position[] ps = new Position[4];
        ps[0] = Position.sumPos(p, Move.UP);
        ps[1] = Position.sumPos(p, Move.LEFT);
        ps[2] = Position.sumPos(p, Move.DOWN);
        ps[3] = Position.sumPos(p, Move.RIGHT);
        return ps;
    }

    public boolean isValid() {
        return isValid;
    }
}

