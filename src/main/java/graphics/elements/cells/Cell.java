package graphics.elements.cells;

public class Cell {

    /**
     * In the cell there can be either:
     * 1. MONSTER, OBSTACLE (WALL, TREE or whatever), CELL not inside the lab... -> in all of these cases NOT_ACCESSIBLE
     * 2. ITEM or EMPTY --> ACCESSIBLE
     */
    public final int id;
    private AbstractCellElement content;

    public Cell(AbstractCellElement e, int id) {
        content = e;
        this.id = id;
    }

    public Cell() {
        id = -1;
        content = new CellElementEmpty();
    }


    public AbstractCellElement getContent() {
        return content;
    }

    public boolean isAccessible() {
        return content.isAccessible();
    }

    @Override
    public String toString() {
        return content.toString();
    }
}
