package entity;

import graphics.Tools;
import graphics.elements.ErrorPositionOutOfBound;
import graphics.elements.Move;
import graphics.elements.Position;
import graphics.elements.Room;
import graphics.elements.cells.Cell;
import graphics.elements.cells.CellElementType;
import graphics.map.WorldMap;
import items.AbstractItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Player extends AbstractEntity {
    private static Player instancePlayer;

    static {
        try {
            instancePlayer = new Player();
        } catch (ErrorPositionOutOfBound errorPositionOutOfBound) {
            errorPositionOutOfBound.printStackTrace();
        }
    }

    private int level;
    private int hunger;
    private List<AbstractItem> inventory;
    private int money;

    private Player() throws ErrorPositionOutOfBound {
        super(new Position(0, 0), -1, EntityType.HERO_WARRIOR);
        level = 1;
        hunger = 100; //default: full bar
        inventory = new ArrayList<>();
        money = 0;
        setState(EntityState.ENRAGED);
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
        if (c.getItem() != null){
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

    public EntityState getState() { return super.getState(); }

    public boolean spendMoney(int cost) {
        if (cost > money){
            System.out.println(Tools.TextEffects.red("Not enough money"));
            return false;
        }
        money -= cost;
        return true;
    }

    public static AbstractItem getItemByID(int id) {
        for(AbstractItem i : instancePlayer.inventory) if(i.getIdPosRoom() == id) return i;
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

    public void incrementMoney(int x){
        money += x;
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
        else return "Starving";                //25 to 0 (0 = you die)
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
        for(int i=0; i<inventory.size(); i++) if(inventory.get(i).getIdPosRoom() == itemID) {
            if(!inventory.get(i).use()) inventory.remove(i); //use the item then remove it if it returns false (see use() functions)
            break;
        }
    }

    public boolean canMove() {
        return Player.getInstancePlayer().getState() != EntityState.FROZEN;
    }

    private void moveMonsters(){
        WorldMap worldMap = WorldMap.getInstanceWorld();
        Cell cell = worldMap.getCell(getPosition());
        if (cell.getBaseContent().equals(CellElementType.EMPTY)) {
            worldMap.getRooms().get(cell.getBaseId()).getMonsters().forEach(e -> {
                e.applyStrategy();
            });
        }
    }

    public boolean makeAction(boolean isAttacking, Move m, Position p){
        if (m == null && p == null) return false;
        return isAttacking ? attackHero(p) : moveHero(m);
    }

    private boolean moveHero(Move move) {
        if(canMove()) {
            if (moveEntity(move)) {
                moveMonsters();
                EntityState.applyStateTurnEffects(Player.getInstancePlayer());
                return true;
            }
            return false;
        }
        moveMonsters();
        EntityState.applyStateTurnEffects(Player.getInstancePlayer());
        return true;
    }

    private boolean attackHero(Position position){
        WorldMap worldMap = WorldMap.getInstanceWorld();
        Cell cell = worldMap.getCell(position);
        if (cell.getEntity() != null){
            Monster m = (Monster) cell.getEntity();
            Attack.attack(this, m);
            EntityState.applyStateTurnEffects(Player.getInstancePlayer());
            return true;
        }
        return false;
    }
}
