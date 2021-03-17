package graphics.elements;

import graphics.elements.cells.Cell;
import graphics.elements.cells.CellElementType;
import graphics.map.WorldMap;

import java.util.*;

public class Corridor {
    private final List<Position> positionList;
    public final int id;
    public Room startRoom;
    private final Random gen;

    public Corridor(Cell[][] lab, Room r, List<Room> rooms, List<Corridor> corridors, boolean firstTime) {
        positionList = new ArrayList<>();
        gen = new Random();
        this.id = corridors.size();
        this.startRoom = r;
        if ((r.id == 0 && firstTime) || r.getLowestRoomNeighbor() != 0) createCorridor(lab, r, rooms, corridors);
    }

    private void createCorridor(Cell[][] w, Room r, List<Room> rooms, List<Corridor> corridors) {
        Position start = openDoor(r);
        List<Position> Q = new ArrayList<>();
        Position[][] P = new Position[WorldMap.MAX_X][WorldMap.MAX_Y];
        Q.add(start);
        while (Q.size() != 0) {
            Position z = eject(Q);
            for (Position x : Objects.requireNonNull(z).getNeighbor(true)) {
                if (x != null && P[x.getX()][x.getY()] == null){
                    P[x.getX()][x.getY()] = z;
                    Cell cell = w[x.getX()][x.getY()];
                    CellElementType ct = cell.getBaseContent();
                    if ((ct == CellElementType.HORIZONTAL_WALL ||
                            ct == CellElementType.VERTICAL_WALL) &&
                            rooms.get(cell.getBaseId()).getLowestRoomNeighbor() != startRoom.getLowestRoomNeighbor()) {
                        updateRooms(rooms, rooms.get(cell.getBaseId()));
                        createPath(w, P, start, x, true, corridors);
                        return;
                    }
                    else if (ct == CellElementType.CORRIDOR &&
                            corridors.get(cell.getBaseId()).startRoom.getLowestRoomNeighbor() != startRoom.getLowestRoomNeighbor()) {
                        updateCorridors(rooms, corridors.get(cell.getBaseId()));
                        createPath(w, P, start, x, false, corridors);
                        return;
                    }
                    else if (ct == CellElementType.OUTSIDE_ROOM) Q.add(x);
                }
            }
        }
    }

    private void updateRooms(List<Room> rooms, Room endRoom) {
        int start = startRoom.getLowestRoomNeighbor();
        int end = endRoom.getLowestRoomNeighbor();
        int min = Math.min(start, end);
        int max = Math.max(start, end);
        rooms.forEach(x -> {
            if (x.getLowestRoomNeighbor() == max)
                x.setLowestRoomNeighbor(min);
        });
    }


    private void updateCorridors(List<Room> rooms, Corridor c) {
        updateRooms(rooms, c.startRoom);
    }

    private void createPath(Cell[][] w, Position[][] P, Position start, Position end, boolean endIsRoom, List<Corridor> corridors) {
        int iStart = w[start.getX()][start.getY()].getBaseId();
        int iEnd = w[end.getX()][end.getY()].getBaseId();
        int cellId = endIsRoom ? id : iEnd;
        Position current = P[end.getX()][end.getY()];
        while (!current.equals(start)){
            positionList.add(current);
            w[current.getX()][current.getY()] = new Cell(CellElementType.CORRIDOR, cellId);
            current = P[current.getX()][current.getY()];
        }
        w[start.getX()][start.getY()] = new Cell(CellElementType.EMPTY, iStart);
        if(start.getY() - 1 >= 0) {
            if(w[start.getX()][start.getY() - 1].getBaseContent() == CellElementType.VERTICAL_WALL)
                w[start.getX()][start.getY() - 1] = new Cell(CellElementType.CORNER_BOT, w[start.getX()][start.getY() - 1].getBaseId());
            else if(w[start.getX()][start.getY() - 1].getBaseContent() == CellElementType.CORNER_TOP)
                w[start.getX()][start.getY() - 1] = new Cell(CellElementType.CORNER_BOT, w[start.getX()][start.getY() - 1].getBaseId());
        }
        if (endIsRoom) {
            w[end.getX()][end.getY()] = new Cell(CellElementType.EMPTY, iEnd);
            if(end.getY() - 1 >= 0) {
                if(w[end.getX()][end.getY() - 1].getBaseContent() == CellElementType.VERTICAL_WALL)
                    w[end.getX()][end.getY() - 1] = new Cell(CellElementType.CORNER_BOT, w[end.getX()][end.getY() - 1].getBaseId());
                else if(w[end.getX()][end.getY() - 1].getBaseContent() == CellElementType.CORNER_TOP)
                    w[end.getX()][end.getY() - 1] = new Cell(CellElementType.CORNER_BOT, w[end.getX()][end.getY() - 1].getBaseId());
            }
            corridors.add(this);
        } else {
            w[end.getX()][end.getY()] = new Cell(CellElementType.CORRIDOR, iEnd);
            corridors.get(iEnd).positionList.addAll(positionList);
        }
    }

    private Position eject(List<Position> Q) {
        if (Q.size() == 0) return null;
        int rnd = gen.nextInt(Q.size());
        return Q.remove(rnd);
    }

    Position openDoor(Room r) {
        int rnd = gen.nextInt(4);
        Position res;
        // 0 = horizontal top | 1 = vertical left | 2 = horizontal bottom | 3 = vertical right
        int  cord = 1 + gen.nextInt((rnd % 2 == 0 ?  r.getWidth() : r.getHeight()) - 1);
        // orientation of ax to bottom
        res = switch (rnd) {
            case 0 -> new Position(r.getTopLeft().getX() + cord, r.getTopLeft().getY());
            case 1 -> new Position(r.getTopLeft().getX(), r.getTopLeft().getY() + cord);
            case 2 -> new Position(r.getBottomRight().getX() - cord, r.getBottomRight().getY());
            case 3 -> new Position(r.getBottomRight().getX(), r.getBottomRight().getY() - cord);
            default -> null;
        };
        if (res.getX() == 0 || res.getX() == WorldMap.MAX_X - 1 ||
                res.getY() == 0 || res.getY() == WorldMap.MAX_Y - 1) return openDoor(r);
        return res;
    }
}

