package items.Collecatables;

import graphics.elements.Position;
import items.AbstractItem;
import items.ItemType;

import java.util.Objects;
import java.util.Random;

public abstract class AbstractCollectableItems extends AbstractItem {

    private final static ItemType[] salableItems = {ItemType.FOOD, ItemType.CONSUMABLE, ItemType.EQUIP};

    public AbstractCollectableItems(int i, ItemType t, Position position, boolean immediateUse) {
        super(i, t, position, immediateUse);
    }

    public static AbstractCollectableItems generateAbstractCollItems(int id, Position pos){
        Random rd = new Random();
        ItemType it = Objects.requireNonNull(salableItems)[rd.nextInt(salableItems.length)];
        switch (it) {
            case CONSUMABLE -> {
                return new ItemConsumable(id, pos);
            }
            case EQUIP -> {
                return new ItemEquip(id, pos);
            }
            case FOOD -> {
                return new ItemFood(id, pos);
            }
        }
        return null;
    }

    public abstract String getEffect();
}
