package World;

import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;

public class PositionTest {

    @Test
    public void testLucky() {
        Position p0 = new Position(0, 0);
        Position p1 = new Position(0, 0);
        assertEquals(p1, p0);
    }

}