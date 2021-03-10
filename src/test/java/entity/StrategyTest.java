package entity;

import graphics.elements.ErrorPositionOutOfBound;
import graphics.elements.Position;
import graphics.map.WorldMap;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class StrategyTest {
    @Test
    public void StrategyTest() throws ErrorPositionOutOfBound {
        WorldMap w = WorldMap.getInstanceWorld();
        Player hero = Player.getInstancePlayer();
        Position validPos = w.getRooms().get(0).getTopLeft();
        validPos.setPosition(validPos.getX() + 1, validPos.getY() + 1);
        Monster m = new Monster(validPos, 100, 10, -2, EntityType.MONSTER_GOBLIN);
        Position oldPos = m.getPosition();
        int oldHeroHp = hero.getHP();
        System.out.println("HERO POSITION = " + hero.getPosition());
        boolean isClose = false;
        for (int i = 0; i < 100; i++) {
            m.applyStrategy();
           if (!m.withinReach(hero, 1)) {
                assertTrue(oldPos != m.getPosition());
                oldPos = m.getPosition();
            } else {
               if (!isClose) isClose = true;
               else {
                   if (hero.getHP() == 0) break;
                   assertTrue(oldHeroHp != hero.getHP());
                   oldHeroHp = hero.getHP();
               }
           }
           System.out.println("MONSTER POS = " + m.getPosition() + " | Hero HP = " + hero.getHP());
        }
        System.out.println("MONSTER POS = " + m.getPosition() + " | Hero HP = " + hero.getHP());
    }
}
