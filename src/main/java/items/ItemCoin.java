package items;

import entity.Player;
import graphics.elements.Position;
import graphics.window.GameWindow;

import java.awt.*;
import java.util.Random;

public class ItemCoin extends AbstractItem{
    private final int value;
    public ItemCoin(int id, String name, Position p) {
        super(id, name, ItemType.COIN, p, true);
        value = new Random().nextInt(10) + 1;
    }

    @Override
    public boolean use() {
        Player.getInstancePlayer().incrementMoney(value);
        GameWindow.addToLogs("You have found: " + value + " coins!", new Color(210,170,60));
        return true;
    }

    @Override
    void parseEffectLine() {

    }
}
