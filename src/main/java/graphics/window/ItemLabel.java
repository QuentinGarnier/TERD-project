package graphics.window;

import graphics.elements.Position;
import graphics.elements.cells.Cell;
import graphics.map.WorldMap;
import items.AbstractItem;

import javax.swing.*;
import java.util.Objects;

public class ItemLabel extends JLabel {
    private final AbstractItem item;
    public static final int size = GamePanel.size;
    ItemLabel(AbstractItem item){
        super();
        this.item = item;

        Position p = item.getPosition();
        Cell cell = WorldMap.getInstanceWorld().getCell(p);
        setIcon(cell.getMainContentType().getIcon());
        setSize(size, size);
        setLocation(p.getX() * size, p.getY() * size);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof AbstractItem){
            return ((AbstractItem) o).equals(item);
        } else if (o instanceof ItemLabel){
            return getLocation().equals(((ItemLabel) o).getLocation());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(item);
    }
}
