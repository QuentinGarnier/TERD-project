package items;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public abstract class AbstractItem {
    public final ItemType type;
    private final int id;
    private final String name;
    private final int price;
    protected String effectLine;
    public final boolean immediateUse;

    AbstractItem(int i, String n, ItemType t, boolean immediateUse) {
        this.id = i;
        this.name = n;
        this.type = t;
        this.immediateUse = immediateUse;

        this.price = 0; // -> prix à ajouter dans la lecture du fichier etc (pour l'achat/vente)
        //this.effectLine = e; //à ajouter quand la dernière ligne sera parse (actuellement les 'undefined')
        if (immediateUse) use();
    }

    public static AbstractItem generateRandomItem(int id){
        ItemType[] itemTypes = ItemType.values();
        int rndElt = new Random().nextInt(itemTypes.length);
        AbstractItem res;
        ItemType itemType = itemTypes[rndElt];
        switch (itemType){
            case COIN -> res = new ItemCoin(id, "Coin " + id);
            case CONSUMABLE -> res = new ItemConsumable(id, "Consumable " + id);
            case EQUIP -> res = new ItemEquip(id, "Equip " + id);
            case FOOD -> res = new ItemFood(id, "Food " + id);
            default -> res = null;
        }
        return res;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int getPrice() {
        return this.price;
    }

    public abstract boolean use();  //return true if not consumed (equip), else return false (food, consumable)
    abstract void parseEffectLine();  //each Item class parse differently the line (depends off the effect)

    public static AbstractItem getItemByID(int searchID) {
        AbstractItem item = null;
        try {
            Scanner scanner = new Scanner(new File("data/items/items.data"));
            String line;
            while(scanner.hasNextLine()) {
                line = scanner.nextLine();
                if(line.length() > 0) if(line.charAt(0) != '#') {
                    String[] info = line.split(";"); //info[0] = id; info[1] = type; info[2] = name; info[3] = effect (to do)
                    if(Integer.parseInt(info[0]) == searchID) {
                        switch (info[1]) {
                            case "0": item = new ItemFood(Integer.parseInt(info[0]), info[2]); break;
                            case "1": item = new ItemConsumable(Integer.parseInt(info[0]), info[2]); break;
                            case "2": item = new ItemEquip(Integer.parseInt(info[0]), info[2]); break;
                        }
                    }
                }
            }
            scanner.close();
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public String toString() {
        return "[#" + (id < 100 ? (id < 10 ? "00" : "0") : "") + id + "] " + name;
    }
}
