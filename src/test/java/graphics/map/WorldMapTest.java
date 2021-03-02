package graphics.map;

import graphics.elements.Room;
import graphics.elements.cells.Cell;
import graphics.elements.cells.CellElementType;
import org.junit.Test;

import static org.junit.Assert.*;

public class WorldMapTest {
    WorldMap w = WorldMap.getInstanceWorld();
    @Test
    public void testProgrammeStrongConnected() {
        for (int i = 0; i < 100; i++) {
            System.out.println();
            for (Room r : w.getRooms()) {
                assertEquals(r.getLowestRoomNeighbor(), 0);
            }
            w.generateWorld();
        }
        //System.out.println(WorldMap.getInstanceWorld().toString());
    }
    @Test
    public void testSecurityCell(){
        // test that lab do not modify
        for (int i = 0; i < WorldMap.MAX_X; i++){
            for (int j = 0; j < WorldMap.MAX_Y; j++){
                Cell c = w.getCell(i, j);
                c =  new Cell(CellElementType.OUTSIDE_ROOM, -1);
            }
        }
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
}
