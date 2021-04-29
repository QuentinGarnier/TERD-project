package items;

import entity.EntityState;
import entity.Player;
import graphics.elements.Position;
import graphics.window.GameWindow;
import items.collectables.ItemEquip;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ItemEquipTest {
    @Test
    public void useTest() {
        GameWindow.setSettings(GameWindow.language(), false, GameWindow.Difficulty.ENDLESS);
        Player player = Player.getInstancePlayer();
        List<AbstractItem> itemList = new ArrayList<>();
        for (int i = 0; i < 2000; i++) {
            itemList.add(AbstractItem.generateRandomItem(i, new Position(i, i)));
        }
        itemList.forEach(item -> {
            if (item instanceof ItemCoin) {
                int money = player.getMoney();
                item.use();
                assertTrue(money < player.getMoney());
            } else if (item instanceof ItemTrap) {
                Position p = new Position(player.getPosition().getX(), player.getPosition().getY());
                int hp = player.getHP();
                item.use();
                ItemTrap it = (ItemTrap) item;
                switch (it.effect) {
                    case 0 -> assertEquals(player.getState(), EntityState.BURNT);
                    case 1 -> assertEquals(player.getState(), EntityState.FROZEN);
                    case 2 -> assertEquals(player.getState(), EntityState.POISONED);
                    case 3 -> assertNotEquals(player.getPosition(), p);
                    case 4 -> assertEquals(player.getHP(), hp - 15);
                }
            } else if (item instanceof ItemEquip) {
                ItemEquip ie = (ItemEquip) item;
                ie.use();
                assertTrue(ie.isEquipped() || !player.entityType.equals(ie.getEquipmentType().entityType));
            }
            player.fullHeal();
        });
    }
}
