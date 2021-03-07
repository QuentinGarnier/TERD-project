package items;

import entity.Player;

import java.util.Random;

public class ItemCoin extends AbstractItem{
    private final int value;
    public ItemCoin(int id, String name) {
        super(id, name, ItemType.COIN, true);
        value = new Random().nextInt(10) + 1;
    }

    @Override
    public boolean use() {
        Player.getInstancePlayer().incrementMoney(value);
        return true;
    }

    @Override
    void parseEffectLine() {

    }
}
