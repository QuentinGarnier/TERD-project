package items;

import graphics.elements.Position;

import java.util.Objects;
import java.util.Random;

public abstract class AbstractItem {
    private static int idCounter = 0;
    public final ItemType type;
    private final int idPosRoom;
    private final int id;

    private final int price;
    public final boolean immediateUse;
    private Position position;

    AbstractItem(int i, ItemType t, Position position, boolean immediateUse) {
        this.idPosRoom = i;
        this.type = t;
        this.immediateUse = immediateUse;
        this.position = position;
        this.id = idCounter++;
        this.price = 0; // -> prix à ajouter dans la lecture du fichier etc (pour l'achat/vente)
        //this.effectLine = e; //à ajouter quand la dernière ligne sera parse (actuellement les 'undefined')
    }

    public static AbstractItem generateRandomItem(int id, Position p) {
        if(new Random().nextInt(2) == 0) return new ItemCoin(id, p);
        else {
            ItemType[] itemTypes = ItemType.values();
            int rndElt = new Random().nextInt(itemTypes.length);
            AbstractItem res;
            ItemType itemType = itemTypes[rndElt];
            switch (itemType) {
                case COIN -> res = new ItemCoin(id, p);
                case CONSUMABLE -> res = new ItemConsumable(id, p);
                case EQUIP -> res = new ItemEquip(id, p);
                case FOOD -> res = new ItemFood(id, p);
                case TRAP -> res = new ItemTrap(id, p);
                default -> res = null;
            }
            return res;
        }
    }

    public int getIdPosRoom() {
        return this.idPosRoom;
    }

    public int getPrice() {
        return this.price;
    }

    public Position getPosition() {
        return position;
    }

    public abstract boolean use();  //return true if not consumed (equip), else return false (food, consumable)

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
        return "[#" + (id < 100 ? (id < 10 ? "00" : "0") : "") + id + "] ";
    }
}
