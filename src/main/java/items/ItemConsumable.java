package items;

import graphics.elements.Position;


// POTION, TELEPORTATION -> extends ITEMCUNSUMMABLE --> @
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
}
