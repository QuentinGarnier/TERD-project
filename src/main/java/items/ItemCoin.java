package items;

import entity.Player;
import graphics.Language;
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
        Player.getInstancePlayer().modifyMoney(value);
        GameWindow.addToLogs(Language.logGainMoney(value), new Color(210,170,60));
        return true;
    }
}
