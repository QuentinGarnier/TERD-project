package entity;

import graphics.elements.Position;
import graphics.map.WorldMap;
import items.collectables.AbstractCollectableItem;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PlayerTest {

    @Test
    public void testAddItem() {
        WorldMap.getInstanceWorld().generateWorld();
        Player.addItem(AbstractCollectableItem.generateAbstractCollItems(0, new Position(0,0)));
        assertEquals(1, Player.getInventory().size());
        // Try catch because addItem don't add the item in the merchant inventory,
        // So when we try to do Merchant.SellPanel.sellPanel.removeSellInventory(ai); in
        // throwItem we will have ArrayIndexOutOfBoundsException
        try {
            Player.getInstancePlayer().throwItem(Player.getInventory().get(0));
        } catch (ArrayIndexOutOfBoundsException e) {
            assertEquals(0, Player.getInventory().size());
        }
    }
}
