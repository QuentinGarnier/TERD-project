package graphics.map;

import entity.AbstractEntity;
import entity.Player;
import graphics.elements.Position;
import graphics.elements.Room;
import graphics.elements.cells.CellElementType;
import items.AbstractItem;
import org.junit.Test;

import static org.junit.Assert.*;

public class WorldMapTest {
    WorldMap w = WorldMap.getInstanceWorld();

    @Test
    public void testProgrammeStrongConnected() {
        w.generateWorld();
        Position endPos;
        for (int i = 0; i < 10; i++) {
            endPos = AbstractItem.end.getPosition();
            for (Room r : w.getRooms()) {
                assertEquals(r.getLowestRoomNeighbor(), 0);
                // TEST END is ACCESSIBLE
                assertTrue((endPos.getNeighbor(true)).stream().
                        anyMatch(position -> w.getCell(position).getObstacle() == null));
            }
            w.generateWorld();
        }
    }
    @Test
    public void testSecurityCell(){
        // test that lab do not modify
        for (int i = 0; i < 9; i++) w.generateWorld();
        boolean allAtZero = true;
        for (int i = 0; i < WorldMap.MAX_X; i++){
            for (int j = 0; j < WorldMap.MAX_Y; j++){
                if (w.getCell(i, j).getBaseContent() != CellElementType.OUTSIDE_ROOM){
                    allAtZero = false;
                    break;
                }
            }
        }
        assertFalse(allAtZero);
    }

    @Test
    public void onlyOneHero()  {
        for (int i = 0; i < 100; i++) {
            int oneHero = 0;
            w.generateWorld();
            for (int x = 0; x < WorldMap.MAX_X; x++)
                for (int y = 0; y < WorldMap.MAX_Y; y++) {
                    AbstractEntity entity = w.getCell(x, y).getEntity();
                    if (entity instanceof Player) oneHero++;
                    assertTrue(oneHero < 2);
                }
        }
    }
}
