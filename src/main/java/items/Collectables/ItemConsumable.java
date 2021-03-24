package items.Collectables;

import entity.Player;
import graphics.elements.Position;
import items.ItemType;


// POTION, TELEPORTATION -> extends ITEM CONSUMABLE --> @
public class ItemConsumable extends AbstractCollectableItems {
    private final ConsumableTypes ct;

    public ItemConsumable(int id, Position p) {
        super(id, ItemType.CONSUMABLE, p, false);
        ct = ConsumableTypes.createConsumableTypes();
    }

    @Override
    public boolean usePrivate() {
        ct.applyEffect();
        Player.removeItem(this);
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
