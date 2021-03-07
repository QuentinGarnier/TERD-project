package graphics.elements.cells;

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
    private int itemId;
    private CellElementType itemContent;

    // entity
    private int entityId;
    private CellElementType entityContent;

    public Cell(CellElementType e, int id) {
        this.baseContent = e;
        this.baseId = id;
        this.itemId = -1;
        this.entityId = -1;
    }

    public void entityLeft(){
        setEntity(null, -1);
    }

    public void heroPickItem(){
        setItem(null, -1);
    }

    public CellElementType getBaseContent() {
        return baseContent;
    }

    public void setItem(CellElementType ct, int id) {
        this.itemId = id;
        this.itemContent = ct;
    }

    public void setEntity(CellElementType ct, int id) {
        this.entityId = id;
        this.entityContent = ct;
    }

    public int getCurrentId() {
        return baseId;
    }

    public int getBaseId() {
        return baseId;
    }

    public int getItemId() {
        return itemId;
    }

    public CellElementType getItemContent() {
        return itemContent;
    }

    public int getEntityId() {
        return entityId;
    }

    public CellElementType getEntityContent() {
        return entityContent;
    }

    public CellElementType getMainContentType() {
        if (entityContent != null) return entityContent;
        if (itemContent != null) return itemContent;
        return baseContent;
    }

    public boolean isAccessible() {
        return getMainContentType().isAccessible();
    }

    @Override
    public String toString() {
        return this.getMainContentType().toString();
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
