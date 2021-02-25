package entity;

import graphics.elements.Move;
import graphics.elements.Position;
import graphics.map.WorldMap;
import items.AbstractItem;

import java.awt.im.InputContext;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class Player extends AbstractEntity {
    private static final Player instancePlayer = new Player(new Position(0, 0), 100, 10);

    private ArrayList<AbstractItem> inventory;
    private int money;

    private Player(Position position, int HP, int attack) {
        super(position, HP, attack);
        this.inventory = new ArrayList<>();
        this.money = 0;
    }

    public static Player getInstancePlayer() {
        return instancePlayer;
    }

    public static ArrayList<AbstractItem> getInventory() {
        return instancePlayer.inventory;
    }

    public static AbstractItem getItemByID(int id) {
        for(AbstractItem i : instancePlayer.inventory) if(i.getID() == id) return i;
        return null;
    }

    /**
     * Function when you gain an item:
     * @param id is the ID of the item in the items.data file (data/items/items.data)
     */
    public static void addItem(int id) {
        AbstractItem item = AbstractItem.getItemByID(id);
        if(item != null) instancePlayer.inventory.add(item);
    }

    public static void gamePlayer(){
        Scanner sc = new Scanner(System.in);
        String buffer;
        char key;
        boolean gameState = true;
        WorldMap wmap = WorldMap.getInstanceWorld();

        while (gameState){
            buffer = sc.nextLine();
            if (buffer.length() == 1) {
                key = buffer.charAt(0);
                Position pos = instancePlayer.getPos();
                Position oldPos = new Position(pos.getX(), pos.getY());

                if(getKeyboard().equals("fr_FR")){ key = Player.charConverterToUniversal(key); }

                switch (key) {
                    case 'w': pos.nextPosition(wmap, Move.UP.getMove()); break;
                    case 'a': pos.nextPosition(wmap, Move.LEFT.getMove()); break;
                    case 's': pos.nextPosition(wmap, Move.DOWN.getMove()); break;
                    case 'd': pos.nextPosition(wmap, Move.RIGHT.getMove()); break;
                    case 'p':
                        gameState = false;
                        System.out.println("Leaved game");
                        break;
                    default:
                        break;
                }

                if (key == 'w' || key == 'a' || key == 's' || key == 'd') {
                    wmap.toEmptyACell(oldPos.getX(), oldPos.getY());//subject to change (currently the hero can only walk on EMPTY cells, so that's what he leaves)
                    instancePlayer.setPosition(pos);
                    wmap.setPlayerPlace(pos.getX(), pos.getY());
                    wmap.repaint();
                }
            }
        }
        sc.close();
    }

    public static String getKeyboard(){
        InputContext context = InputContext.getInstance();
        Locale country = context.getLocale();
        return country.toString();
    }

    public static void showCommands(){
        char top = 'w', left = 'a';
        if (getKeyboard().equals("fr_FR")){ top = 'z'; left = 'q'; }
        System.out.printf("To move : %c (top), %c (left), s (bottom), d (right)%sTo leave : p", top, left, System.lineSeparator());
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


}
