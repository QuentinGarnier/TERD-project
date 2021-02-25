package entity;

import graphics.elements.Move;
import graphics.elements.Position;
import graphics.map.WorldMap;
import items.AbstractItem;

import java.util.ArrayList;
import java.util.Objects;
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
        char key;
        boolean gameState = true;
        WorldMap wmap = WorldMap.getInstanceWorld();
        while (gameState){
            key = sc.nextLine().charAt(0);
            Position pos = instancePlayer.getPos();
            Position oldPos = new Position(pos.getX(), pos.getY());
            switch (key){
                case 'z': pos.nextPosition(wmap, Move.UP.getMove());
                    System.out.println("Hero has moved to UP");//just for check
                    break;
                case 'q': pos.nextPosition(wmap, Move.LEFT.getMove());
                    System.out.println("Hero has moved to LEFT");
                    break;
                case 's': pos.nextPosition(wmap, Move.DOWN.getMove());
                    System.out.println("Hero has moved to BOTTOM");
                    break;
                case 'd': pos.nextPosition(wmap, Move.RIGHT.getMove());
                    System.out.println("Hero has moved to RIGHT");
                    break;
                case 'p': gameState = false;
                    System.out.println("Leaved game");
                    break;
                default: break;
            }

            if (key == 'z' || key == 'q' || key == 's' || key == 'd'){
                wmap.toEmptyACell(oldPos.getX(), oldPos.getY());
                instancePlayer.setPosition(pos);
                wmap.setPlayerPlace(pos.getX(), pos.getY());
                wmap.repaint();
            }

        }
        sc.close();
    }
    public static void showCommands(){
        System.out.println("To move : z (top), q (left), s (bottom), d (right)" + System.lineSeparator()
                         + "To leave : p");
    }
}
