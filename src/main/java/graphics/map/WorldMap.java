package graphics.map;

import entity.Player;
import entity.Merchant;
import graphics.ChooseAttackCell;
import graphics.Tools;
import graphics.elements.*;
import graphics.elements.cells.Cell;
import graphics.elements.cells.CellElementType;
import entity.WhatHeroDoes;
import items.AbstractItem;

import java.util.*;

public class WorldMap {
    private static final WorldMap instanceWorld = new WorldMap();

    public static final int MAX_X = 70; // to be verified
    public static final int MAX_Y = 20; // to be verified
    private static final int maxRandomRoom = 100;
    private final Cell[][] lab;
    private final List<Room> rooms;
    private final List<Corridor> corridors;
    private final List<AbstractItem> items;


    private WorldMap() throws ErrorPositionOutOfBound {
        lab = new Cell[MAX_X][MAX_Y];
        rooms = new ArrayList<>();
        corridors = new ArrayList<>();
        items = new ArrayList<>();
        generateWorld();
    }

    public void generateWorld() throws ErrorPositionOutOfBound {
        rooms.clear();
        corridors.clear();
        items.clear();
        items.add(AbstractItem.end);
        initializeLab();
        createRooms();
        createCorridors(true);
        placePlayer();
        placeEnd();
        placeMerchant();
    }

    public void placePlayer() {
        Random rnd = new Random();
        int iRoom = rnd.nextInt(rooms.size());
        Room room = rooms.get(iRoom);

        int x = room.getTopLeft().getX() + rnd.nextInt(room.getWidth() - 1) + 1;
        int y = room.getTopLeft().getY() + rnd.nextInt(room.getHeight() - 1) + 1;
        getCell(Player.getInstancePlayer().getPosition()).entityLeft();
        if (getCell(x, y).isAccessible() && getCell(x, y).getItem() == null) {
            Player.getInstancePlayer().setPosition(x, y);
            getCell(x, y).setEntity(Player.getInstancePlayer());
            Player.getInstancePlayer().getWhatHeroDoes().setP(new Position(x, y));
            room.setVisited();
        }
        else placePlayer();
    }

    private void placeEnd(){
        Random rnd = new Random();
        int iRoom = rnd.nextInt(rooms.size());
        Room room = rooms.get(iRoom);
        int x = room.getTopLeft().getX() + rnd.nextInt(room.getWidth() - 1) + 1;
        int y = room.getTopLeft().getY() + rnd.nextInt(room.getHeight() - 1) + 1;
        Position pos = new Position(x, y);
        if (getCell(x, y).isAccessible() && getCell(x, y).getItem() == null) {
            Position[] neighbors = pos.getNeighbor(true);
            for (Position p : neighbors)
                if (lab[p.getX()][p.getY()].isDoor(lab)) {
                    placeEnd();
                    return;
                }
            AbstractItem.end.setPosition(new Position(x, y));
            getCell(x, y).setItem(AbstractItem.end);
            AbstractItem.end.setLocation();
        }
        else placeEnd();
    }

    public void placeMerchant() {
        Merchant merchant = Merchant.getInstanceMerchant();
        Random rnd = new Random();
        int safeRoomId = rnd.nextInt(rooms.size());
        Room room = rooms.get(safeRoomId);

        int x = room.getTopLeft().getX() + rnd.nextInt(room.getWidth() - 3) + 2;
        int y = room.getTopLeft().getY() + rnd.nextInt(room.getHeight() - 3) + 2;
        if (getCell(x, y).isAccessible() && getCell(x, y).getItem() == null) {
            merchant.setPosition(x, y);
            getCell(x, y).setEntity(merchant);
            while(room.getMonsters().size() != 0){
                getCell(room.getMonsters().get(0).getPosition()).entityLeft();
                room.removeEntity(room.getMonsters().get(0));
            }
            merchant.setSafeRoomId(safeRoomId);
            room.setVisited();
        }
        else placeMerchant();
    }

    private void initializeLab() {
        for (int x = 0; x < MAX_X; x++) {
            for (int y = 0; y < MAX_Y; y++) {
                lab[x][y] = new Cell(CellElementType.OUTSIDE_ROOM, 0, new Position(x, y));
            }
        }
    }

    private void createRooms() throws ErrorPositionOutOfBound {
        for (int i = 0; i < maxRandomRoom; i++) {
            new Room(rooms, lab, items);
        }
        if (rooms.size() == 0) createRooms();
    }

    public List<Room> getRooms() {
        return Collections.unmodifiableList(rooms);
    }
    public List<Corridor> getCorridors(){
        return Collections.unmodifiableList(corridors);
    }

    private void createCorridors(boolean firstTime) {
        for (Room r : rooms) {
            new Corridor(lab, r, rooms, corridors, firstTime);
        }
        for (Room room : rooms) {
            if (room.getLowestRoomNeighbor() != 0) {
                createCorridors(false);
                break;
            }
        }
    }

    public static WorldMap getInstanceWorld() {
        return instanceWorld;
    }

    public Cell getCell(int x, int y) {
        return lab[x][y];
    }

    public Cell getCell(Position p) {
        return getCell(p.getX(), p.getY());
    }

    public Corridor getCurrentCorridor(int x, int y){
        Cell c = getCell(x, y);
        return c.getBaseContent().equals(CellElementType.CORRIDOR) ?
                corridors.get(c.getBaseId()) : null;
    }

    public Room getCurrentRoom(int x, int y){
        Cell c = getCell(x, y);
        CellElementType ct = c.getBaseContent();
        return ct.isWall() || ct.equals(CellElementType.EMPTY)? rooms.get(c.getBaseId()) : null;
    }

    public List<AbstractItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public void repaint() {
        System.out.println(this);
        showATH();
    }

    private int padding(String str) {
        String s1 = str.replaceAll("\\033\\[..m", "").replace(Tools.TerminalText.DEFAULT, "");
        return (MAX_X - s1.length())/2;
    }

    public void showATH() {
        String row0 = Player.getInstancePlayer().getEntityType().toString() + " " + Player.getInstancePlayer().getState().getText();
        int padRow0 = padding(row0);
        String row1 = "  >>> Level : " + Tools.TerminalText.green("" + Player.getInstancePlayer().getLvl()) + " " + "| Hunger : " + Tools.TerminalText.magenta(Player.getInstancePlayer().getHunger() + " (" + Player.getInstancePlayer().getHungerState() + ")") + " <<<";
        int padRow1 = padding(row1);
        String row2 = "Money : " + Tools.TerminalText.yellow(Player.getInstancePlayer().getMoney() + " ●") + " " + "| HP : " + Tools.TerminalText.red(Player.getInstancePlayer().getHP() + "/" + Player.getInstancePlayer().getHPMax() + " ♥") + " " +
                "| Attack : " + Tools.TerminalText.blue(Player.getInstancePlayer().getAttack() + " ⚔");
        int padRow2 = padding(row2);
        //System.out.println(padRow1 + " " + padRow2);
        String ATH = " ".repeat(padRow0) + row0 + System.lineSeparator() +
                "-".repeat(MAX_X) + System.lineSeparator() +
                " ".repeat(padRow1) + row1 + System.lineSeparator() +
                "-".repeat(MAX_X) + System.lineSeparator() +
                "|" + " ".repeat(padRow2 - 1) + row2 + " ".repeat(padRow2 - 1) + "|"+ System.lineSeparator() +
                "-".repeat(MAX_X) + System.lineSeparator();
        System.out.println(ATH);
    }

    private static void applyCommand(Move m){
        Player player = Player.getInstancePlayer();
        WhatHeroDoes choice = player.getWhatHeroDoes();
        Position pos = player.getWhatHeroDoes().getP();
        switch (choice){
            case MOVING -> {
                player.makeAction(false, m, null);
                gamePlayer();
            }
            case CHOOSING_ATTACK -> {
                Position p = ChooseAttackCell.selectCase(pos, m);
                player.getWhatHeroDoes().setP(p);
                gamePlayer();
            }
            case ATTACKING -> {
                player.makeAction(true, null, player.getWhatHeroDoes().getP());
                ChooseAttackCell.unselectCase(pos);
                player.setWhatHeroDoes(WhatHeroDoes.MOVING);
                player.getWhatHeroDoes().setP(player.getPosition());
                gamePlayer();
            }
        }
    }

    public static void gamePlayer() {

        Scanner sc = new Scanner(System.in);
        String buffer;
        char key;
        WorldMap.getInstanceWorld().repaint();

        buffer = sc.nextLine();
        if(buffer.length() == 1) {
            key = buffer.charAt(0);

            if(Tools.getKeyboard().equals("fr_FR")) key = Tools.universalCharOf(key);
            Player player = Player.getInstancePlayer();
            WhatHeroDoes choice = player.getWhatHeroDoes();
            switch (key) {
                case 'w' -> applyCommand(Move.UP);
                case 'a' -> applyCommand(Move.LEFT);
                case 's' -> applyCommand(Move.DOWN);
                case 'd' -> applyCommand(Move.RIGHT);
                case 'q' -> {
                    Position oldPos = player.getWhatHeroDoes().getP();
                    switch (choice) {
                        case MOVING -> player.setWhatHeroDoes(WhatHeroDoes.CHOOSING_ATTACK);
                        case CHOOSING_ATTACK -> player.setWhatHeroDoes(WhatHeroDoes.ATTACKING);
                    }
                    player.getWhatHeroDoes().setP(oldPos);
                    applyCommand(null);
                    break;
                }
                case 'p' -> System.out.println("You left the game.");
                default -> gamePlayer();
            }

            WorldMap.getInstanceWorld().repaint();
        }
        sc.close();
    }

    @Override
    public String toString() {
        Cell hero = getCell(Player.getInstancePlayer().getPosition());
        StringBuilder sb = new StringBuilder();
        for(int j = 0; j < MAX_Y; j++) {
            for(int i = 0; i < MAX_X; i++) {
                sb.append(getCell(i, j).equals(hero) ? Tools.TerminalText.yellow(lab[i][j].toString()) : lab[i][j].toString());
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }
}