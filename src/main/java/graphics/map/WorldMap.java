package graphics.map;

import entity.Player;
import graphics.ColorStr;
import graphics.elements.Corridor;
import graphics.elements.Move;
import graphics.elements.Position;
import graphics.elements.cells.Cell;
import graphics.elements.Room;
import graphics.elements.cells.CellElementType;

import java.util.*;

public class WorldMap {
    private static final WorldMap instanceWorld = new WorldMap();

    public static final int MAX_X = 50; // to be verified
    public static final int MAX_Y = 20; // to be verified
    private static final int maxRandomRoom = 100;
    private static final int maxRandomCoinByWorld = 50; // test
    private final Cell[][] lab;
    private final List<Room> rooms;
    private final List<Corridor> corridors;


    private WorldMap() {
        lab = new Cell[MAX_X][MAX_Y];
        rooms = new ArrayList<>();
        corridors = new ArrayList<>();
        generateWorld();
    }

    public void generateWorld(){
        rooms.clear();
        corridors.clear();
        initializeLab();
        createRooms();
        createCorridors();
        placePlayer();
        generateMoney();//just to test
    }

    private void initializeLab() {
        for (int x = 0; x < MAX_X; x++) {
            for (int y = 0; y < MAX_Y; y++) {
                lab[x][y] = new Cell(CellElementType.OUTSIDE_ROOM, 0);
            }
        }
    }

    private void createRooms() {
        for (int i = 0; i < maxRandomRoom; i++) {
            new Room(rooms, this);
        }
        if (rooms.size() == 0) createRooms();
    }

    public List<Room> getRooms() {
        return Collections.unmodifiableList(rooms);
    }

    private void createCorridors() {
        for (Room r : rooms) {
            new Corridor(this, r, rooms, corridors);
        }
        // rooms.forEach(x -> System.out.print(x.getLowestRoomNeighbor() + " "));
        // System.out.println();
        for (int i = 0; i < rooms.size(); i++){
            if (rooms.get(i).getLowestRoomNeighbor() != 0){
                createCorridors();
                break;
            }
        }
        //System.out.println(corridors.size());
    }

    private void generateMoney() {
        Random gen = new Random();
        int randomX, randomY;
        for (int i = 0; i < maxRandomCoinByWorld; i++){
            randomX = gen.nextInt(MAX_X);
            randomY = gen.nextInt(MAX_Y);
            if (lab[randomX][randomY].getCurrentContent() == CellElementType.EMPTY){
                lab[randomX][randomY] = new Cell(CellElementType.COIN, 0);
            }
        }
    }

    public static WorldMap getInstanceWorld() { return instanceWorld; }

    public Cell getCell(int x, int y) { return lab[x][y]; }

    public Cell getCell(Position p) { return getCell(p.getX(), p.getY()); }

    public void setCell(int x, int y, Cell c){ lab[x][y] = c; }

    public void setCell(Position p, Cell c){ setCell(p.getX(), p.getY(), c); }

    public void setHeroIsHere(boolean isRoom, int id){
        for (int i = 0; i < MAX_X; i++){
            for (int j = 0; j < MAX_Y; j++){
                Cell c = lab[i][j];
                c.setHeroIsHere(isRoom ?
                        Room.isRoom(c) && c.getCurrentId() == id :
                        c.getCurrentContent() == CellElementType.CORRIDOR && c.getCurrentId() == id);
            }
        }
    }

    private void placePlayer() {
        Random rnd = new Random();
        int iRoom = rnd.nextInt(rooms.size());
        Room room = rooms.get(iRoom);
        setHeroIsHere(true, iRoom);

        int x = room.getTopLeft().getX() + rnd.nextInt(room.getWidth() - 1) + 1;
        int y = room.getTopLeft().getY() + rnd.nextInt(room.getHeight() - 1) + 1;

        Player.getInstancePlayer().setPosition(x, y);
        lab[x][y] = new Cell(CellElementType.HERO, 0);
    }
    public void setPlayerPlace(Position p, Cell heroCell) {
        setCell(p, heroCell);
    }
    public void toEmptyACell(Position p) {
        setCell(p, new Cell(CellElementType.HERO, 0));
    }

    public void repaint() {
        System.out.println(this);
        showATH();
    }

    public void showATH(){
        StringBuilder ATH = new StringBuilder();
        ATH.append("-".repeat(48)).append(System.lineSeparator());
        ATH.append("| Money : ").append(ColorStr.yellow(Player.getInstancePlayer().getMoney() + " ●")).append(" ");
        ATH.append("| HP : ").append(ColorStr.red(Player.getInstancePlayer().getHP() + "/100 ♥")).append(" ");
        ATH.append("| Attack : ").append(ColorStr.blue(Player.getInstancePlayer().getAttack() + " ⚔")).append(" |").append(System.lineSeparator());
        ATH.append("-".repeat(48)).append(System.lineSeparator());
        System.out.println(ATH.toString());
    }

    public static char charConverterToUniversal(char c){
        switch (c){
            case 'z': return 'w';
            case 'q': return 'a';

            case 'a':       //possibly to be defined
            case 'w': return '_';
            default: return c;
        }
    }

    public static void gamePlayer() {
        Scanner sc = new Scanner(System.in);
        String buffer;
        char key;
        boolean gameState = true;
        Player instancePlayer = Player.getInstancePlayer();
        Cell oldCell = new Cell(CellElementType.EMPTY, 0);
        Cell heroCell = instanceWorld.getCell(instancePlayer.getPos());

        while (gameState){
            buffer = sc.nextLine();
            if (buffer.length() == 1) {
                key = buffer.charAt(0);
                Position pos = instancePlayer.getPos();
                Position oldPos = new Position(pos.getX(), pos.getY());

                if(Player.getKeyboard().equals("fr_FR")){ key = charConverterToUniversal(key); }

                switch (key) {
                    case 'w': pos.nextPosition(instanceWorld, Move.UP.getMove()); break;
                    case 'a': pos.nextPosition(instanceWorld, Move.LEFT.getMove()); break;
                    case 's': pos.nextPosition(instanceWorld, Move.DOWN.getMove()); break;
                    case 'd': pos.nextPosition(instanceWorld, Move.RIGHT.getMove()); break;
                    case 'p':
                        gameState = false;
                        System.out.println("Left game");
                        break;
                    default: break;
                }

                if (key == 'w' || key == 'a' || key == 's' || key == 'd') {
                    instancePlayer.setPosition(pos);
                    instanceWorld.setCell(oldPos, oldCell);
                    oldCell = instanceWorld.getCell(pos);
                    instanceWorld.setPlayerPlace(pos, heroCell);


                    if (oldCell.getCurrentContent() == CellElementType.COIN){
                        instancePlayer.incrementMoney();
                        System.out.println("You have earned " + ColorStr.yellow("+1 coin") + System.lineSeparator());
                        oldCell = new Cell(CellElementType.EMPTY, 0);
                    }

                    instanceWorld.repaint();
                }
            }
        }
        sc.close();
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int j = 0; j < MAX_Y; j++) {
            for(int i = 0; i < MAX_X; i++) {
                sb.append(lab[i][j].isHeroIsHere() ? ColorStr.yellow(lab[i][j].toString()) : lab[i][j].toString());
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }
}