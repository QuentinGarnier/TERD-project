package entity;

import graphics.elements.Position;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class AttackTest {
    List<AbstractEntity> entityList;
    Player player = Player.getInstancePlayer();

    @Before
    public void initialisation(){
        entityList = new ArrayList<>();
        for (int i = 0; i < 1000; i++){
            entityList.add(Monster.generateRandomMonster(new Position(0, 0), -1));
        }
    }

    @Test
    public void testAttack() {
        // TODO
        entityList.forEach(e -> {
            int playerPV = player.getHP();
            int monsterPV = e.getHP();
            Attack.attack(e, player);
            assertEquals(player.getHP(), playerPV - e.getAttack());
            if (!player.getState().equals(EntityState.FROZEN)) Attack.attack(player, e);
            assertTrue(
                    (e.getHP() <= Math.max(0, monsterPV - player.getAttack()) && !player.getState().equals(EntityState.FROZEN)) ||
                   (e.getHP() == monsterPV && player.getState().equals(EntityState.FROZEN)));
            player.fullHeal();
        });
    }
}
