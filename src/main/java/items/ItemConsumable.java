package items;

import graphics.elements.Position;


// POTION, TELEPORTATION -> extends ITEMCONSUMABLE --> @
public class ItemConsumable extends AbstractItem {
    private final ConsumableTypes ct;

    public ItemConsumable(int id, Position p) {
        super(id, ItemType.CONSUMABLE, p, false);
        ct = ConsumableTypes.createConsumableTypes();
    }

    @Override
    public boolean usePrivate() {
        ct.applyEffect();
        return false;
    }

    @Override
    public String getEffect() {
        return ct.getEffect();
    }

    @Override
    public String toString() {
        return ct.toString();
    }
}
