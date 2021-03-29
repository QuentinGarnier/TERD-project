package items.Collectables;

import entity.Player;
import graphics.Language;
import graphics.elements.Position;
import items.ItemType;


// POTION, TELEPORTATION -> extends ITEM CONSUMABLE --> @
public class ItemConsumable extends AbstractCollectableItems {
    public final ConsumableTypes ct;

    public ItemConsumable(int id, Position p) {
        super(id, ItemType.CONSUMABLE, p, false);
        ct = ConsumableTypes.createConsumableTypes();
    }

    @Override
    public boolean usePrivate() {
        ct.applyEffect();
        Player.removeItem(this);
        return true;
    }

    @Override
    public String getEffect() {
        return ct.getEffect();
    }

    @Override
    public String toString() {
        return Language.translate(ct);
    }

    @Override
    public int getPrice() { return ct.getPrice(); }
}
