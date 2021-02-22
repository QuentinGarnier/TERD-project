package graphics.elements;

import graphics.map.WorldMap;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PositionTest {
    @Test
    public void testProgramme() {
        WorldMap w = WorldMap.getInstanceWorld();
        Position p0 = new Position(1, 1);

        p0.nextPosition(w, Move.UP.getMove());
        assertEquals(1, p0.getX());
        assertEquals(2, p0.getY());

        p0.nextPosition(w, Move.LEFT.getMove());
        // can't go LEFT since (0, 0) is not accessible
        assertEquals(1, p0.getX());
        assertEquals(2, p0.getY());

        p0.nextPosition(w, Move.RIGHT.getMove());
        assertEquals(2, p0.getX());
        assertEquals(2, p0.getY());
    }
}