package entity;

import graphics.map.WorldMap;
import org.junit.Test;

public class PlayerTest {
    WorldMap wmap = WorldMap.getInstanceWorld();

    @Test
    public void testAddItem() {
        wmap.repaint();
        /*Player.addItem(2);
        Player.addItem(0);
        Player.addItem(501);*/
        System.out.println(Player.getInventory());

        //assertEquals(3, Player.getInventory().size());
    }
}
