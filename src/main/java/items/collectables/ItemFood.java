package items.collectables;

import entity.Player;
import graphics.Language;
import graphics.Tools;
import graphics.elements.Position;
import graphics.window.GameWindow;
import items.ItemType;

import java.util.Random;

public class ItemFood extends AbstractCollectableItem {
    private final int hungerGain;
    private final int hpGain;

    public ItemFood(int id, Position p) {
        super(id, ItemType.FOOD, p,false);
        // TODO put correct hp and hunger -> maybe a class 'FoodTypes' can be useful for the diversity
        Random gen = new Random();
        this.hungerGain = 10 + gen.nextInt(11);
        this.hpGain = 1 + gen.nextInt(10);
    }

    @Override
    public boolean usePrivate() {
        Player.getInstancePlayer().modifyHP(this.hpGain);
        GameWindow.addToLogs("+" + hpGain + " " + Language.hp() + ".", Tools.WindowText.green);
        Player.getInstancePlayer().modifyHunger(this.hungerGain);
        Player.removeItem(this);
        return true;
    }

    @Override
    public String toString() {
        return Language.logFood();
    }

    @Override
    public String getEffect() {
        return "[+" + hpGain + " " +  Language.hp() + " | +" + hungerGain + " " + Language.hunger() + "]";
    }

    @Override
    public String getDescription() {
        return getEffect();
    }

    @Override
    public int getPrice() { return 5;}
}
