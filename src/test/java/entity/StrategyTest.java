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
        for (int kk = 0; kk < 20; kk++) {
            List<Monster> monsters = w.getCurrentRoom(hero.getPosition()).getMonsters();
            monsters.forEach(m -> {
                Position oldPos = m.getPosition();
                boolean isClose;
                for (int i = 0; i < 50; i++) {
                    isClose = m.withinReach(hero, m.getRange());
                    System.out.println(m.entityType + " " + m.getPosition());
                    m.applyStrategy();
                    System.out.println(m.getPosition());
                    if (!isClose) {
                        List<Position> p = Tools.findPath(
                                Tools.BFS(
                                        oldPos,
                                        w.getCurrentRoom(hero.getPosition()),
                                        null,
                                        null),
                                oldPos,
                                hero.getPosition(),
                                w.getCurrentRoom(hero.getPosition()), null, null);
                        if (p.size() > 2) {
                            if (w.getCell(p.get(p.size() - 2)).isAccessible())
                                assertNotEquals(m.getPosition(), p.get(p.size() - 2));
                        }
                        oldPos = m.getPosition();
                    } else if (!m.entityType.equals(EntityType.MONSTER_WIZARD)){
                        assertTrue(hero.getHP() < hero.getHPMax());
                        hero.fullHeal();
                    }
                }
            });
            w.generateWorld();
        }
    }
}
