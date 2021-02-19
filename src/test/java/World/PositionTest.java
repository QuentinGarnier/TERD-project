package World;


import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PositionTest {
    @Test
    public void testLucky() {
        World w = new World();
        Position p0 = new Position(0, 0);
        Move m = Move.UP;
        p0.nextPosition(w, m.getMove());
        assertEquals(0, p0.getX());
        assertEquals(1, p0.getY());
    }
}