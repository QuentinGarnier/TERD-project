package graphics.elements.cells;

public class Cell {

    /**
     * In the cell there can be either:
     * 1. MONSTER, OBSTACLE (WALL, TREE or whatever), CELL not inside the lab... -> in all of these cases NOT_ACCESSIBLE
     * 2. ITEM or EMPTY --> ACCESSIBLE
     */
    public final int id;
    private final CellElementType content;
    private boolean heroIsHere;

    public Cell(CellElementType e, int id) {
        content = e;
        this.id = id;
        this.heroIsHere = false;
    }

    public void setHeroIsHere(boolean heroIsHere) {
        this.heroIsHere = heroIsHere;
    }

    public boolean isHeroIsHere() {
        return heroIsHere;
    }

    public CellElementType getContent() {
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
