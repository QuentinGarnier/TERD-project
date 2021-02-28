package graphics.elements.cells;

public class Cell {

    /**
     * In the cell there can be either:
     * 1. MONSTER, OBSTACLE (WALL, TREE or whatever), CELL not inside the lab... -> in all of these cases NOT_ACCESSIBLE
     * 2. ITEM or EMPTY --> ACCESSIBLE
     */
    private int currentId;
    private CellElementType currentContent;
    private int memoryId;
    private CellElementType memoryContent;
    private boolean heroIsHere;

    public Cell(CellElementType e, int id) {
        currentContent = e;
        this.currentId = id;
        this.heroIsHere = false;
    }

    public void setHeroIsHere(boolean heroIsHere) {
        this.heroIsHere = heroIsHere;
    }

    public boolean isHeroIsHere() {
        return heroIsHere;
    }

    public CellElementType getCurrentContent() {
        return currentContent;
    }

    public void setCurrentContent(CellElementType currentContent, int currentId) {
        this.currentId = currentId;
        this.currentContent = currentContent;
    }

    public void setMemory(int memoryId, CellElementType memoryContent) {
        this.memoryContent = memoryContent;
        this.memoryId = memoryId;
    }

    public int getCurrentId() {
        return currentId;
    }

    public boolean isAccessible() {
        return currentContent.isAccessible();
    }

    @Override
    public String toString() {
        return currentContent.toString();
    }
}
