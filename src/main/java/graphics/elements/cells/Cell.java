package graphics.elements.cells;

import entity.AbstractEntity;
import graphics.Tools;
import items.AbstractItem;

import java.util.Objects;

public class Cell {

    /**
     * In the cell there can be either:
     * 1. MONSTER, OBSTACLE (WALL, TREE or whatever), CELL not inside the lab... -> in all of these cases NOT_ACCESSIBLE
     * 2. ITEM or EMPTY --> ACCESSIBLE
     */

    // room | corridor | outside room
    private final int baseId;
    private final CellElementType baseContent;

    // item
    private AbstractItem item;

    // entity
    private AbstractEntity entity;

    private boolean isAimed;

    public Cell(CellElementType e, int id) {
        this.baseContent = e;
        this.baseId = id;
    }

    public void entityLeft(){
        setEntity(null);
    }

    public void heroPickItem() {
        setItem(null);
    }

    public CellElementType getBaseContent() {
        return baseContent;
    }

    public void setItem(AbstractItem it) {
        this.item = it;
    }

    public void setEntity(AbstractEntity entity) {
        this.entity = entity;
    }

    public int getBaseId() {
        return baseId;
    }

    public int getItemId() {
        return item.getIdPosRoom();
    }

    public AbstractItem getItem() {
        return item;
    }

    public AbstractEntity getEntity(){
        return entity;
    }

    public CellElementType getMainContentType() {
        if (entity != null) return entity.getEntityType().getCellElementType();
        if (item != null) return item.type.ct;
        return baseContent;
    }

    public boolean isAimed(){
        return isAimed;
    }

    public void setAimed(boolean b){
        isAimed = b;
    }

    public boolean isAccessible() {
        return getMainContentType().isAccessible();
    }

    @Override
    public String toString() {
        return isAimed? Tools.TerminalText.encircled(getMainContentType().toString()) : getMainContentType().toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Cell){
            Cell cell = (Cell) o;
            if (cell.baseId == baseId) {
                return switch (baseContent) {
                    case CORRIDOR -> cell.baseContent == CellElementType.CORRIDOR;
                    case HORIZONTAL_WALL, VERTICAL_WALL, CORNER, EMPTY -> cell.baseContent == CellElementType.EMPTY;
                    default -> false;
                };
            }
            //return cell.getBaseId() == baseId && cell.getBaseContent().equals(baseContent);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(baseId, baseContent);
    }
}
