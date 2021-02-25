package graphics.elements.cells;

public class Cell {

    /**
     * In the cell there can be either:
     * 1. MONSTER, OBSTACLE (WALL, TREE or whatever), CELL not inside the lab... -> in all of these cases NOT_ACCESSIBLE
     * 2. ITEM or EMPTY --> ACCESSIBLE
     */
    private AbstractCellElement content;

    public Cell(AbstractCellElement e) {
        this.content = e;
    }

    public Cell() {
        this.content = new CellElementEmpty();
    }


    public AbstractCellElement getContent() {
        return this.content;
    }

    @Override
    public String toString() {
        return this.content.toString();
    }
}
