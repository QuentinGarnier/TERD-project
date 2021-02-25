package entity;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PlayerTest {
    @Test
    public void testAddItem() {
        Player.addItem(2);
        Player.addItem(0);
        Player.addItem(501);
        System.out.println(Player.getInventory());

        assertEquals(3, Player.getInventory().size());
    }
}
