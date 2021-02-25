package graphics.elements.cells;

import entity.AbstractEntity;

public class CellElementEntity extends AbstractCellElement {
    private AbstractEntity entity;

    public CellElementEntity(CellElementType type, AbstractEntity e) {
        super(type);
        this.entity = e;
    }

    public AbstractEntity getEntity() {
        return this.entity;
    }
}
