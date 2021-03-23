package items;

import entity.Player;
import graphics.elements.Position;
import graphics.window.GameWindow;

import java.awt.*;
import java.util.Random;

public class ItemCoin extends AbstractItem{
    private final int value;
    public ItemCoin(int id, Position p) {
        super(id, ItemType.COIN, p, true);
        value = new Random().nextInt(10) + 1;
    }

    @Override
    public boolean usePrivate() {
        Player.getInstancePlayer().incrementMoney(value);
        GameWindow.addToLogs("You have found: " + value + " coin" + (value>1? "s": "") + "!", new Color(210,170,60));
        return true;
    }

    @Override
    public String getEffect() {
        return null;
    }
}
