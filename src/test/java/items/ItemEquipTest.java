package items;

import entity.Player;
import graphics.elements.Position;
import items.Collecatables.ItemEquip;
import org.junit.Test;

import static org.junit.Assert.*;

public class ItemEquipTest {
    @Test
    public void useTest() {
        Player player = Player.getInstancePlayer();
        ItemEquip[] itemEquips = new ItemEquip[5];
        for (int i = 0; i < 5; i++){
            itemEquips[i] = new ItemEquip(i, new Position(i, i));
            assertFalse(itemEquips[i].isEquipped());
        }

        itemEquips[0].use();
        assertTrue(itemEquips[0].isEquipped());
        assertTrue(player.getAttackItem() != null || player.getDefenceItem() != null);
        /*-----------------------------------------*/
        itemEquips[0].use();
        assertFalse(itemEquips[0].isEquipped());
        assertNull(player.getAttackItem());
        assertNull(player.getDefenceItem());
        /*------------------------------------------*/
        itemEquips[0].use();
        assertTrue(itemEquips[0].isEquipped());
        itemEquips[1].use();
        assertTrue(itemEquips[1].isEquipped());
        assertTrue((player.getAttackItem() != null && player.getDefenceItem() != null) ||
                (player.getAttackItem() != null || player.getDefenceItem() != null));
    }
}
