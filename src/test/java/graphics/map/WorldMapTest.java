package graphics.map;

import graphics.elements.Room;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WorldMapTest {
    @Test
    public void testProgramme() {
        WorldMap w = WorldMap.getInstanceWorld();
        for (int i = 0; i < 100; i++) {
            System.out.println();
            for (Room r : w.getRooms()) {
                assertEquals(r.getLowestRoomNeighbor(), 0);
            }
            w.generateWorld();
        }

        //System.out.println(WorldMap.getInstanceWorld().toString());

    }
}
