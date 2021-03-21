package items;

import entity.Player;
import graphics.elements.Position;

public class ItemFood extends AbstractItem {
    private final int hungerGain;
    private final int hpGain;

    public ItemFood(int id, Position p) {
        super(id, ItemType.FOOD, p,false);
        // TODO put correct hp and hunger -> maybe a class 'FoodTypes' can be useful for the diversity
        this.hungerGain = 0;
        this.hpGain = 0;
    }

    @Override
    public boolean usePrivate() {
        Player.getInstancePlayer().modifyHunger(this.hungerGain);
        Player.getInstancePlayer().modifyHP(this.hpGain);
        return false;
    }

    @Override
    public String toString() {
        return "Food";
    }
}
