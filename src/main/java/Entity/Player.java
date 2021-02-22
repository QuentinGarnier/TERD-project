package entity;

import graphics.elements.Position;
import items.Item;
import items.ItemConsumable;
import items.ItemEquip;
import items.ItemFood;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Player extends AbstractEntity {
    private static final Player instancePlayer = new Player(new Position(0, 0), 100, 10);

    private ArrayList<Item> inventory;
    private int money;

    private Player(Position position, int HP, int attack) {
        super(position, HP, attack);
        this.inventory = new ArrayList<>();
        this.money = 0;
    }

    public static Player getInstancePlayer() {
        return instancePlayer;
    }

    public static ArrayList<Item> getInventory() {
        return instancePlayer.inventory;
    }

    public static Item getItemByID(int id) {
        for(Item i : instancePlayer.inventory) if(i.getID() == id) return i;
        return null;
    }

    /**
     * Function when you gain an item:
     * @param id is the ID of the item in the items.data file (data/items/items.data)
     */
    public static void addItem(int id) {
        try {
            Scanner scanner = new Scanner(new File("data/items/items.data"));
            String line;
            while(scanner.hasNextLine()) {
                line = scanner.nextLine();
                if(line.length() > 0) if(line.charAt(0) != '#') {
                    String[] info = line.split(";"); //info[0] = id; info[1] = type; info[2] = name; info[3] = effect (to do)
                    if(Integer.parseInt(info[0]) == id) {
                        switch (info[1]) {
                            case "0": instancePlayer.inventory.add(new ItemFood(Integer.parseInt(info[0]), info[2])); break;
                            case "1": instancePlayer.inventory.add(new ItemConsumable(Integer.parseInt(info[0]), info[2])); break;
                            case "2": instancePlayer.inventory.add(new ItemEquip(Integer.parseInt(info[0]), info[2])); break;
                        }
                    }
                }
            }
            scanner.close();
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
