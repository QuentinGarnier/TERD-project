package graphics.elements.cells;

import entity.Entity;

public class CellElementEntity extends CellElement {
    private Entity entity;

    public CellElementEntity(CellElementType type, Entity e) {
        super(type);
        this.entity = e;
    }

    public Entity getEntity() {
        return this.entity;
    }
}
