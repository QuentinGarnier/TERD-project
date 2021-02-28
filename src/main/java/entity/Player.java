package entity;

import graphics.ColorStr;
import graphics.elements.Position;
import items.AbstractItem;

import java.awt.im.InputContext;
import java.util.ArrayList;
import java.util.Locale;


public class Player extends AbstractEntity {
    private static final Player instancePlayer = new Player();

    private int level;
    private int hunger; //max: 100
    private ArrayList<AbstractItem> inventory;
    private int money;

    private boolean isRoom;
    private int id;

    private Player() {
        super(new Position(0, 0), 100, 10);
        level = 1;
        hunger = 100; //default: full bar
        inventory = new ArrayList<>();
        money = 0;
    }

    public static Player getInstancePlayer() {
        return instancePlayer;
    }

    public static ArrayList<AbstractItem> getInventory() {
        return instancePlayer.inventory;
    }

    public int getMoney() {
        return money;
    }

    public int getLvl() {
        return level;
    }

    public boolean spendMoney(int cost) {
        if (cost > money){
            System.out.println(ColorStr.red("Not enough money"));
            return false;
        }
        money -= cost;
        return true;
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
        if (item != null) instancePlayer.inventory.add(item);
    }


    public static String getKeyboard() {
        InputContext context = InputContext.getInstance();
        Locale country = context.getLocale();
        return country.toString();
    }

    public static void showCommands() {
        char top = 'w', left = 'a';
        if (getKeyboard().equals("fr_FR")){ top = 'z'; left = 'q'; }
        System.out.printf(System.lineSeparator() + "To move : %c (top), %c (left), s (bottom), d (right)%sTo leave : p%s", top, left, System.lineSeparator(), System.lineSeparator());
    }

    public void incrementMoney() {
        money++;
    }

    public void levelUp() {
        level ++;
    }

    public int getHunger() {
        return hunger;
    }

    public String getHungerState() {
        if(hunger > 75) return "Sated";        //100 to 76
        else if(hunger > 50) return "Peckish"; //75 to 51
        else if(hunger > 30) return "Hungry";  //50 to 26
        else return "Starving";                     //25 to 0 (0 = you die)
    }

    //Hunger is capped at 100.
    public void modifyHunger(int x) {
        if(hunger + x > 100) hunger = 100;
        else if(hunger + x < 0) hunger = 0;
        else hunger += x;
    }

    /**
     * Use an item of the inventory.
     * @param itemID ID of the item (do nothing if it's not in the inventory)
     */
    public void useItem(int itemID) {
        for(int i=0; i<inventory.size(); i++) if(inventory.get(i).getID() == itemID) {
            if(!inventory.get(i).use()) inventory.remove(i); //use the item then remove it if it returns false (see use() functions)
            break;
        }
    }
}
