package items.Collectables;

import entity.Player;
import graphics.Language;
import graphics.Tools;
import graphics.elements.Position;
import graphics.window.GameWindow;
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
        Player.getInstancePlayer().modifyHP(this.hpGain);
        GameWindow.addToLogs("+" + hpGain + " " + Language.hp(), Tools.WindowText.green);
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
        return "[ +" + hpGain + " " +  Language.hp() + " ] - [ +" + hungerGain + " " + Language.logFood() + " ]";
    }

    @Override
    public int getPrice() { return 5;}
}
