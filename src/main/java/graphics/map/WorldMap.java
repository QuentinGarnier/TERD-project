package graphics.map;

import entity.Attack;
import entity.Player;
import graphics.Tools;
import graphics.elements.*;
import graphics.elements.cells.Cell;
import graphics.elements.cells.CellElementType;

import java.util.*;

public class WorldMap {
    private static WorldMap instanceWorld;

    static {
        try {
            instanceWorld = new WorldMap();
        } catch (ErrorPositionOutOfBound errorPositionOutOfBound) {
            errorPositionOutOfBound.printStackTrace();
        }
    }

    public static final int MAX_X = 70; // to be verified
    public static final int MAX_Y = 20; // to be verified
    private static final int maxRandomRoom = 100;
    private final Cell[][] lab;
    private final List<Room> rooms;
    private final List<Corridor> corridors;


    private WorldMap() throws ErrorPositionOutOfBound {
        lab = new Cell[MAX_X][MAX_Y];
        rooms = new ArrayList<>();
        corridors = new ArrayList<>();
        generateWorld();
    }

    public void generateWorld() throws ErrorPositionOutOfBound {
        rooms.clear();
        corridors.clear();
        initializeLab();
        createRooms();
        createCorridors(true);
        placePlayer();
    }

    private void initializeLab() {
        for (int x = 0; x < MAX_X; x++) {
            for (int y = 0; y < MAX_Y; y++) {
                lab[x][y] = new Cell(CellElementType.OUTSIDE_ROOM, 0);
            }
        }
    }

    private void createRooms() throws ErrorPositionOutOfBound {
        for (int i = 0; i < maxRandomRoom; i++) {
            new Room(rooms, lab);
        }
        if (rooms.size() == 0) createRooms();
    }

    public List<Room> getRooms() {
        return Collections.unmodifiableList(rooms);
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

    private void placePlayer() {
        Random rnd = new Random();
        int iRoom = rnd.nextInt(rooms.size());
        Room room = rooms.get(iRoom);

        int x = room.getTopLeft().getX() + rnd.nextInt(room.getWidth() - 1) + 1;
        int y = room.getTopLeft().getY() + rnd.nextInt(room.getHeight() - 1) + 1;

        if (!getCell(x,y).isAccessible()) placePlayer();
        else {
            Player.getInstancePlayer().setPosition(x, y);
            getCell(x, y).setEntity(Player.getInstancePlayer());
        }
    }

    public void repaint() {
        System.out.println(this);
        showATH();
    }

    private int padding(String str) {
        String s1 = str.replaceAll("\\033\\[..m", "").replace(Tools.TextEffects.DEFAULT, "");
        return (MAX_X - s1.length())/2;
    }

    public void showATH() {
        String row0 = Player.getInstancePlayer().getEntityType().toString2() + " " + Player.getInstancePlayer().getState().getText();
        int padRow0 = padding(row0);
        String row1 = "  >>> Level : " + Tools.TextEffects.green("" + Player.getInstancePlayer().getLvl()) + " " + "| Hunger : " + Tools.TextEffects.magenta(Player.getInstancePlayer().getHunger() + " (" + Player.getInstancePlayer().getHungerState() + ")") + " <<<";
        int padRow1 = padding(row1);
        String row2 = "Money : " + Tools.TextEffects.yellow(Player.getInstancePlayer().getMoney() + " ●") + " " + "| HP : " + Tools.TextEffects.red(Player.getInstancePlayer().getHP() + "/" + Player.getInstancePlayer().getHPMax() + " ♥") + " " +
                "| Attack : " + Tools.TextEffects.blue(Player.getInstancePlayer().getAttack() + " ⚔");
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
        boolean b = player.makeAction(false, m, null);
    }

    public static void gamePlayer() {

        Scanner sc = new Scanner(System.in);
        String buffer;
        char key;
        boolean gameState = true;
        Player instancePlayer = Player.getInstancePlayer();

        while(gameState) {
            buffer = sc.nextLine();
            if(buffer.length() == 1) {
                key = buffer.charAt(0);

                if(Tools.getKeyboard().equals("fr_FR")) key = Tools.universalCharOf(key);

                switch (key) {
                    case 'w': applyCommand(Move.UP); break;
                    case 'a': applyCommand(Move.LEFT); break;
                    case 's': applyCommand(Move.DOWN); break;
                    case 'd': applyCommand(Move.RIGHT); break;
                    case 'q': Attack.attack(instancePlayer, null); break;
                    case 'p':
                        gameState = false;
                        System.out.println("You left the game.");
                        break;
                    default: break;
                }

                WorldMap.getInstanceWorld().repaint();
            }
        }
        sc.close();
    }

    @Override
    public String toString() {
        Cell hero = getCell(Player.getInstancePlayer().getPosition());
        StringBuilder sb = new StringBuilder();
        for(int j = 0; j < MAX_Y; j++) {
            for(int i = 0; i < MAX_X; i++) {
                sb.append(getCell(i, j).equals(hero) ? Tools.TextEffects.yellow(lab[i][j].toString()) : lab[i][j].toString());
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }
}