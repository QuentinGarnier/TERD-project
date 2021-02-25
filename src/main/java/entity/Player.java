package entity;

import graphics.ColorStr;
import graphics.elements.Position;
import items.AbstractItem;
import items.ItemConsumable;
import items.ItemEquip;
import items.ItemFood;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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
}
