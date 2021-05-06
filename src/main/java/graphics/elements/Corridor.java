package graphics.elements;

import graphics.elements.cells.Cell;
import graphics.elements.cells.CellElementType;
import graphics.map.WorldMap;

import java.util.*;

public class Corridor {
    private boolean hasBeenVisited;
    private final List<Position> positionList;
    private final List<Position> doorList;
    public final int id;
    public Room startRoom;
    private final Random gen;
    private final Cell[][] lab;
    private final List<Room> rooms;

    public Corridor(Cell[][] lab, Room r, List<Room> rooms, List<Corridor> corridors, boolean firstTime) {
        positionList = new ArrayList<>();
        doorList = new ArrayList<>();
        gen = new Random();
        this.id = corridors.size();
        this.lab = lab;
        this.startRoom = r;
        this.rooms = rooms;
        if ((r.id == 0 && firstTime) || r.getLowestRoomNeighbor() != 0) createCorridor(r, rooms, corridors);
        hasBeenVisited = false;
    }

    private void createCorridor(Room r, List<Room> rooms, List<Corridor> corridors) {
        Position start = openDoor(r);
        List<Position> Q = new ArrayList<>();
        Position[][] P = new Position[WorldMap.MAX_X][WorldMap.MAX_Y];
        Q.add(start);
        while (Q.size() != 0) {
            Position z = eject(Q);
            for (Position x : Objects.requireNonNull(z).getNeighbor(true)) {
                if (x != null && P[x.getX()][x.getY()] == null){
                    P[x.getX()][x.getY()] = z;
                    Cell cell = lab[x.getX()][x.getY()];
                    CellElementType ct = cell.getBaseContent();
                    if ((ct == CellElementType.HORIZONTAL_WALL ||
                            ct == CellElementType.VERTICAL_WALL) &&
                            rooms.get(cell.getBaseId()).getLowestRoomNeighbor() != startRoom.getLowestRoomNeighbor()) {
                        updateRooms(rooms, rooms.get(cell.getBaseId()));
                        createPath(P, start, x, true, corridors);
                        return;
                    }
                    else if (ct == CellElementType.CORRIDOR &&
                            corridors.get(cell.getBaseId()).startRoom.getLowestRoomNeighbor() != startRoom.getLowestRoomNeighbor()) {
                        updateCorridors(rooms, corridors.get(cell.getBaseId()));
                        createPath(P, start, x, false, corridors);
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

    private void createPath(Position[][] P, Position start, Position end, boolean endIsRoom, List<Corridor> corridors) {
        int iStart = lab[start.getX()][start.getY()].getBaseId();
        int iEnd = lab[end.getX()][end.getY()].getBaseId();
        int cellId = endIsRoom ? id : iEnd;
        doorList.add(start);
        doorList.add(end);
        Position current = P[end.getX()][end.getY()];
        while (!current.equals(start)){
            positionList.add(current);
            lab[current.getX()][current.getY()] = new Cell(CellElementType.CORRIDOR, cellId, current, lab);
            current = P[current.getX()][current.getY()];
        }
        lab[start.getX()][start.getY()] = new Cell(CellElementType.EMPTY, iStart, start, lab);
        if(start.getY() - 1 >= 0) {
            Position pos = new Position(start.getX(), start.getY() - 1);
            if(lab[start.getX()][start.getY() - 1].getBaseContent() == CellElementType.VERTICAL_WALL)
                lab[start.getX()][start.getY() - 1] = new Cell(CellElementType.CORNER_BOT, lab[start.getX()][start.getY() - 1].getBaseId(), pos, lab);
            else if(lab[start.getX()][start.getY() - 1].getBaseContent() == CellElementType.CORNER_TOP)
                lab[start.getX()][start.getY() - 1] = new Cell(CellElementType.CORNER_BOT, lab[start.getX()][start.getY() - 1].getBaseId(), pos, lab);
        }
        if (endIsRoom) {
            lab[end.getX()][end.getY()] = new Cell(CellElementType.EMPTY, iEnd, end, lab);
            Position pos = new Position(end.getX(), end.getY() - 1);
            if(end.getY() - 1 >= 0) {
                if(lab[end.getX()][end.getY() - 1].getBaseContent() == CellElementType.VERTICAL_WALL)
                    lab[end.getX()][end.getY() - 1] = new Cell(CellElementType.CORNER_BOT, lab[end.getX()][end.getY() - 1].getBaseId(), pos, lab);
                else if(lab[end.getX()][end.getY() - 1].getBaseContent() == CellElementType.CORNER_TOP)
                    lab[end.getX()][end.getY() - 1] = new Cell(CellElementType.CORNER_BOT, lab[end.getX()][end.getY() - 1].getBaseId(), pos, lab);
            }
            corridors.add(this);
            addDoorToRoom(end);
        } else {
            lab[end.getX()][end.getY()] = new Cell(CellElementType.CORRIDOR, iEnd, end, lab);
            corridors.get(iEnd).positionList.addAll(positionList);
            corridors.get(iEnd).doorList.add(start);
            corridors.get(iEnd).doorList.add(end);
        }
        addDoorToRoom(start);
    }

    private void addDoorToRoom(Position pos){
        rooms.get(lab[pos.getX()][pos.getY()].getBaseId()).addDoor(pos);
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

    public void setVisited(){
        if (!hasBeenVisited) {
            hasBeenVisited = true;
            positionList.forEach(p-> lab[p.getX()][p.getY()].removeFog());
            doorList.forEach(p-> lab[p.getX()][p.getY()].removeFog());
        }
    }
}

