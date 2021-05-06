package graphics.elements;

import entity.Player;
import graphics.map.WorldMap;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class PositionTest {

    @Test
    public void testNextPosition() {
        WorldMap w = WorldMap.getInstanceWorld();
        Position p0 = Player.getInstancePlayer().getPosition();
        Position p1 = new Position(p0.getX(), p0.getY());
        Random rnd = new Random();

        for(int i=0; i<100; i++) {
            int gen = rnd.nextInt(4);
            p1.setPosition(p0.getX(), p0.getY());

            switch (gen) {
                case 0 -> {
                    p0.nextPosition(Move.UP.getMove());
                    if (p1.getY() - 1 >= 0)
                        assertTrue(w.getCell(p1.getX(), p1.getY() - 1).isAccessible() != p0.equals(p1));
                    else assertEquals(p0, p1);
                }
                case 1 -> {
                    p0.nextPosition(Move.LEFT.getMove());
                    if (p1.getX() - 1 >= 0)
                        assertTrue(w.getCell(p1.getX() - 1, p1.getY()).isAccessible() != p0.equals(p1));
                    else assertEquals(p0, p1);
                }
                case 2 -> {
                    p0.nextPosition(Move.RIGHT.getMove());
                    if (p1.getX() + 1 < WorldMap.MAX_X)
                        assertTrue(w.getCell(p1.getX() + 1, p1.getY()).isAccessible() != p0.equals(p1));
                    else assertEquals(p0, p1);
                }
                case 3 -> {
                    p0.nextPosition(Move.DOWN.getMove());
                    if (p1.getY() + 1 < WorldMap.MAX_Y)
                        assertTrue(w.getCell(p1.getX(), p1.getY() + 1).isAccessible() != p0.equals(p1));
                    else assertEquals(p0, p1);
                }
            }
        }
    }

}