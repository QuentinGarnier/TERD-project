package entity;

import graphics.elements.Position;
import items.AbstractItem;

import java.awt.im.InputContext;
import java.util.ArrayList;
import java.util.Locale;


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

    public int getMoney() { return instancePlayer.money; }

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


    public static String getKeyboard(){
        InputContext context = InputContext.getInstance();
        Locale country = context.getLocale();
        return country.toString();
    }

    public static void showCommands(){
        char top = 'w', left = 'a';
        if (getKeyboard().equals("fr_FR")){ top = 'z'; left = 'q'; }
        System.out.printf("To move : %c (top), %c (left), s (bottom), d (right)%sTo leave : p%s", top, left, System.lineSeparator(), System.lineSeparator());
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

    public void incrementMoney(){money++;}


}
