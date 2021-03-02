package entity;

import graphics.ColorStr;
import graphics.elements.Position;
import graphics.elements.Room;
import graphics.elements.cells.Cell;
import graphics.elements.cells.CellElementType;
import graphics.map.WorldMap;
import items.AbstractItem;

import java.awt.im.InputContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;


public class Player extends AbstractEntity {
    private static final Player instancePlayer = new Player();

    private int level;
    private int hunger; //max: 100
    private List<AbstractItem> inventory;
    private int money;
    private PlayerState state;
    private AbstractPlayerStrategy specialty;

    private boolean isRoom;
    private int id;

    private Player() {
        super(new Position(0, 0), 100, 10, CellElementType.HERO, -1);
        level = 1;
        hunger = 100; //default: full bar
        inventory = new ArrayList<>();
        money = 0;
        state = PlayerState.POISONED;
        specialty = new ArcherStrategy(); //default: warrior
    }

    public static Player getInstancePlayer() {
        return instancePlayer;
    }

    public static List<AbstractItem> getInventory() {
        return Collections.unmodifiableList(instancePlayer.inventory);
    }

    public void pickElement(Position p){
        WorldMap worldMap = WorldMap.getInstanceWorld();
        Cell c = worldMap.getCell(p);
        if (c.getItemContent() != null){
            Room r = worldMap.getRooms().get(c.getBaseId());
            inventory.add(r.getItems().get(c.getItemId()));
        } else {
            System.out.println("Nothing to pick");
        }
    }

    public int getMoney() {
        return money;
    }

    public int getLvl() {
        return level;
    }

    public PlayerState getState() { return state; }

    public AbstractPlayerStrategy getSpecialty() { return specialty; }

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
        char top = 'w', left = 'a', attack = 'q';
        if (getKeyboard().equals("fr_FR")){ top = 'z'; left = 'q'; attack = 'a';}
        System.out.printf(System.lineSeparator() + "To move : %c (top), %c (left), s (bottom), d (right)%sTo attack (not effective): %c%sTo leave : p%s", top, left, System.lineSeparator(), attack, System.lineSeparator(), System.lineSeparator());
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

    public void attack(Monster monster){
        specialty.attack(monster);
    }

    public void applyStateEffect(){
        specialty.applyStateEffect();
    }
}
