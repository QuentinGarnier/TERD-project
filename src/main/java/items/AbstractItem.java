package items;

import graphics.elements.Position;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public abstract class AbstractItem {
    private static int idCounter = 0;
    public final ItemType type;
    private final int idPosRoom;
    private final int id;


    private final String name;
    private final int price;
    protected String effectLine;
    public final boolean immediateUse;
    private Position position;

    AbstractItem(int i, String n, ItemType t, Position position, boolean immediateUse) {
        this.idPosRoom = i;
        this.name = n;
        this.type = t;
        this.immediateUse = immediateUse;
        this.position = position;
        this.id = idCounter++;
        this.price = 0; // -> prix à ajouter dans la lecture du fichier etc (pour l'achat/vente)
        //this.effectLine = e; //à ajouter quand la dernière ligne sera parse (actuellement les 'undefined')
    }

    public static AbstractItem generateRandomItem(int id, Position p) {
        if(new Random().nextInt(2) == 0) return new ItemCoin(id, "Coin " + id, p);
        if(new Random().nextInt(2) == 0) return new ItemTrap(id, "Trap " + id, p);
        else {
            ItemType[] itemTypes = ItemType.values();
            int rndElt = new Random().nextInt(itemTypes.length);
            AbstractItem res;
            ItemType itemType = itemTypes[rndElt];
            switch (itemType) {
                case COIN -> res = new ItemCoin(id, "Coin " + id, p);
                case CONSUMABLE -> res = new ItemConsumable(id, "Consumable " + id, p);
                case EQUIP -> res = new ItemEquip(id, "Equip " + id, p);
                case FOOD -> res = new ItemFood(id, "Food " + id, p);
                case TRAP -> res = new ItemTrap(id, "Trap" + id, p);
                default -> res = null;
            }
            return res;
        }
    }

    public int getIdPosRoom() {
        return this.idPosRoom;
    }

    public String getName() {
        return this.name;
    }

    public int getPrice() {
        return this.price;
    }

    public Position getPosition() {
        return position;
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
                            case "0": item = new ItemFood(Integer.parseInt(info[0]), info[2], null); break;
                            case "1": item = new ItemConsumable(Integer.parseInt(info[0]), info[2], null); break;
                            case "2": item = new ItemEquip(Integer.parseInt(info[0]), info[2], null); break;
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

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof AbstractItem){
            return ((AbstractItem) o).id == id;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "[#" + (idPosRoom < 100 ? (idPosRoom < 10 ? "00" : "0") : "") + idPosRoom + "] " + name;
    }
}
