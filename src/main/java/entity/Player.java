package entity;

import graphics.Tools;
import graphics.elements.ErrorPositionOutOfBound;
import graphics.elements.Move;
import graphics.elements.Position;
import graphics.elements.Room;
import graphics.elements.cells.Cell;
import graphics.elements.cells.CellElementType;
import graphics.map.WorldMap;
import graphics.window.GameWindow;
import items.AbstractItem;

import java.awt.*;
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
    private WhatHeroDoes whatHeroDoes;

    private Player() throws ErrorPositionOutOfBound {
        super(new Position(0, 0), -1, EntityType.HERO_WARRIOR);
        level = 1;
        hunger = 100; //default: full bar
        inventory = new ArrayList<>();
        money = 0;
        whatHeroDoes = WhatHeroDoes.MOVING;
    }

    public WhatHeroDoes getWhatHeroDoes() {
        return whatHeroDoes;
    }

    public void setWhatHeroDoes(WhatHeroDoes whatHeroDoes) {
        this.whatHeroDoes = whatHeroDoes;
    }

    public static Player getInstancePlayer() {
        return instancePlayer;
    }

    public static List<AbstractItem> getInventory() {
        return Collections.unmodifiableList(instancePlayer.inventory);
    }

    public void pickElement(Position p) {
        WorldMap worldMap = WorldMap.getInstanceWorld();
        Cell c = worldMap.getCell(p);
        if(c.getItem() != null) {
            Room r = worldMap.getRooms().get(c.getBaseId());
            inventory.add(r.getItems().get(c.getItemId()));
        }
        else System.out.println("Nothing to pick");
    }

    public int getMoney() {
        return money;
    }

    public int getLvl() {
        return level;
    }

    public EntityState getState() {
        return super.getState();
    }

    public boolean spendMoney(int cost) {
        if (cost > money) {
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

    public void incrementMoney(int x) {
        money += x;
    }

    public void incrementMoney() {
        money++;
    }

    public void levelUp() {
        level ++;
        //Add stats upgrade here (before the heal)
        fullHeal();
        GameWindow.refreshInventory();
    }

    public int getHunger() {
        return hunger;
    }

    public String getHungerState() {
        if(hunger > 75) return "Sated";        //100 to 76
        else if(hunger > 50) return "Peckish"; //75 to 51
        else if(hunger > 30) return "Hungry";  //50 to 31
        else return "Starving";                //30 to 0 (0 = you die)
    }

    //Hunger is capped at 100.
    public void modifyHunger(int x) {
        hunger = Math.max(Math.min(hunger + x, 100), 0);
        GameWindow.addToLogs("You " + (x >= 0 ? "gain " + x : "lose " + (-x)) + " Hunger point" + (x > 1 || x < -1 ? "s" : "") + ". You are " + getHungerState() + ".", new Color(100,60,120));
        GameWindow.refreshInventory();
    }

    @Override
    public void setAttack(int att) {
        super.setAttack(att);
        GameWindow.refreshInventory();
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

    private void moveMonsters() {
        WorldMap worldMap = WorldMap.getInstanceWorld();
        Cell cell = worldMap.getCell(getPosition());
        if (cell.getBaseContent().equals(CellElementType.EMPTY)) {
            worldMap.getRooms().get(cell.getBaseId()).getMonsters().forEach(e -> {
                e.applyStrategy();
            });
        }
    }

    public boolean makeAction(boolean isAttacking, Move m, Position p) {
        if(m == null && p == null) return false;
        return isAttacking ? attack(p) : move(m);
    }

    private boolean move(Move move) {
        if(canMove()) {
            if (moveEntity(move)) {
                whatHeroDoes.setP(getPosition());
                moveMonsters();
                EntityState.turnEffects(this);
                return true;
            }
            return false;
        }
        moveMonsters();
        EntityState.turnEffects(this);
        return true;
    }

    private boolean attack(Position position) {
        WorldMap worldMap = WorldMap.getInstanceWorld();
        Cell cell = worldMap.getCell(position);
        if (cell.getEntity() instanceof Monster && Position.calculateRange(getPosition(), position) <= getRange()) {
            Monster m = (Monster) cell.getEntity();
            Attack.attack(this, m);
            EntityState.turnEffects(this);
            moveMonsters();
            return true;
        }
        return false;
    }
}
