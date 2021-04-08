package entity;

import graphics.Tools;
import graphics.elements.ErrorPositionOutOfBound;
import graphics.elements.Position;
import graphics.map.WorldMap;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class StrategyTest {
    @Test
    public void StrategyTest() throws ErrorPositionOutOfBound {
        WorldMap w = WorldMap.getInstanceWorld();
        Player hero = Player.getInstancePlayer();
        List<Monster> monsters = w.getCurrentRoom(hero.getPosition()).getMonsters();
        monsters.forEach(m -> {
            Position oldPos = m.getPosition();
            int oldHeroHp = hero.getHP();
            System.out.println("HERO POSITION = " + hero.getPosition());
            boolean isClose = false;
            for (int i = 0; i < 100; i++) {
                m.applyStrategy();
                if (!m.withinReach(hero, m.getRange())) {
                    List<Position> p = Tools.findPath(
                            Tools.BFS(
                                    oldPos,
                                    w.getCurrentRoom(hero.getPosition()),
                                    null,
                                    null),
                            oldPos,
                            hero.getPosition(),
                            w.getCurrentRoom(hero.getPosition()), null, null);
                    if (p.size() > 2){
                        if (w.getCell(p.get(p.size() - 2)).isAccessible()) assertNotEquals(oldPos, m.getPosition());
                    }
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
        });
    }
}
