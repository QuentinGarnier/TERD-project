package graphics.map;

import entity.Player;
import graphics.ColorStr;
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
        createCorridors();
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

    private void createCorridors() {
        for (Room r : rooms) {
            new Corridor(lab, r, rooms, corridors);
        }
        for (Room room : rooms) {
            if (room.getLowestRoomNeighbor() != 0) {
                createCorridors();
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
        Player.getInstancePlayer().setPosition(x, y);
        getCell(x, y).setEntity(CellElementType.HERO, 0);
    }

    public void repaint() {
        System.out.println(this);
        showATH();
    }

    private int padding(String str) {
        String s1 = str.replaceAll("\\033\\[3.m", "").replace(ColorStr.DEFAULT, "");
        return (MAX_X - s1.length())/2 - 1;
    }

    public void showATH() {
        String row0 = Player.getInstancePlayer().getSpecialty().toString() + " " + Player.getInstancePlayer().getState().getText();
        int padRow0 = padding(row0);
        String row1 = "  >>> Level : " + ColorStr.green("" + Player.getInstancePlayer().getLvl()) + " " + "| Hunger : " + ColorStr.magenta(Player.getInstancePlayer().getHunger() + " (" + Player.getInstancePlayer().getHungerState() + ")") + " <<<";
        int padRow1 = padding(row1);
        String row2 = "Money : " + ColorStr.yellow(Player.getInstancePlayer().getMoney() + " ●") + " " + "| HP : " + ColorStr.red(Player.getInstancePlayer().getSpecialty().getHP() + "/" + Player.getInstancePlayer().getSpecialty().getHPmax() + " ♥") + " " +
                "| Attack : " + ColorStr.blue(Player.getInstancePlayer().getSpecialty().getAttack() + " ⚔");
        int padRow2 = padding(row2);
        //System.out.println(padRow1 + " " + padRow2);
        String ATH = " ".repeat(padRow0) + row0 + System.lineSeparator() +
                "-".repeat(MAX_X) + System.lineSeparator() +
                " ".repeat(padRow1 + 1) + row1 + System.lineSeparator() +
                "-".repeat(MAX_X) + System.lineSeparator() +
                "|" + " ".repeat(padRow2) + row2 + " ".repeat(padRow2) + "|"+ System.lineSeparator() +
                "-".repeat(MAX_X) + System.lineSeparator();
        System.out.println(ATH);
    }

    public static char charConverterToUniversal(char c) {
        return switch (c) {
            case 'z' -> 'w';
            case 'q' -> 'a';
            case 'a' -> 'q';
            case 'w' -> '_';
            default -> c;
        };
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

                if(Player.getKeyboard().equals("fr_FR")) key = charConverterToUniversal(key);

                instancePlayer.applyStateEffect();

                switch (key) {
                    case 'w': if (instancePlayer.canMove()) instancePlayer.moveEntity(Move.UP); break;
                    case 'a': if (instancePlayer.canMove()) instancePlayer.moveEntity(Move.LEFT); break;
                    case 's': if (instancePlayer.canMove()) instancePlayer.moveEntity(Move.DOWN); break;
                    case 'd': if (instancePlayer.canMove()) instancePlayer.moveEntity(Move.RIGHT); break;
                    case 'q': instancePlayer.attack(null); break;
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
                sb.append(getCell(i, j).equals(hero) ? ColorStr.yellow(lab[i][j].toString()) : lab[i][j].toString());
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }
}