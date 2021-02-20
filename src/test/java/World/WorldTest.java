package World;

import org.junit.Test;

public class WorldTest {
    @Test
    public void testProgramme() {
        World w = World.getInstanceWorld();
        System.out.println(w.toString());
    }
}
