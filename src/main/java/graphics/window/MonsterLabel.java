package graphics.window;

import entity.Monster;
import graphics.elements.Position;
import graphics.elements.cells.Cell;
import graphics.map.WorldMap;

import javax.swing.*;
import java.util.Objects;

public class MonsterLabel extends JLabel {
    private final Monster m;
    public static final int size = GamePanel.size;
    MonsterLabel(Monster m){
        super();
        this.m = m;
        Position p = m.getPosition();
        Cell cell = WorldMap.getInstanceWorld().getCell(m.getPosition());
        setIcon(cell.getMainContentType().getIcon());
        setSize(size, size);
        setLocation(p.getX() * size, p.getY() * size);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Monster){
            return ((Monster) o).equals(m);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(m);
    }
}
