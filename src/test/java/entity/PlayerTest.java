package entity;

import org.junit.Test;

public class PlayerTest {
    @Test
    public void testProgramme() {
        Player.addItem(2);
        Player.addItem(0);
        System.out.println(Player.getInventory());
    }
}
