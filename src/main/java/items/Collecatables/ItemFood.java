package items.Collecatables;

import entity.Player;
import graphics.elements.Position;
import items.Collecatables.AbstractCollectableItems;
import items.ItemType;

import java.util.Random;

public class ItemFood extends AbstractCollectableItems {
    private final int hungerGain;
    private final int hpGain;

    public ItemFood(int id, Position p) {
        super(id, ItemType.FOOD, p,false);
        // TODO put correct hp and hunger -> maybe a class 'FoodTypes' can be useful for the diversity
        Random gen = new Random();
        this.hungerGain = 1 + gen.nextInt(10);
        this.hpGain = 1 + gen.nextInt(10);
    }

    @Override
    public boolean usePrivate() {
        Player.getInstancePlayer().modifyHunger(this.hungerGain);
        Player.getInstancePlayer().modifyHP(this.hpGain);
        Player.removeItem(this);
        return false;
    }

    @Override
    public String toString() {
        return "Food";
    }

    @Override
    public String getEffect() {
        return (hpGain == 0 ? "" : hpGain + " hp") + (hungerGain == 0 ? "" : hungerGain + " hunger");
    }
}
