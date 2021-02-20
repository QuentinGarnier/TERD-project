package World;

public class Cell {
    // in the cell there can be either
    // 1. MONSTER, OBSTACLE (WALL, TREE or whatever), CELL not inside the lab... --> in all of these cases NOT_ACCESSIBLE
    // 2. ITEM or EMPTY --> ACCESSIBLE
    private ElementsEnum cellContent;
    Cell(ElementsEnum e){
        this.cellContent = e;
    }

    public boolean isAccessible() {
        return cellContent.isAccessible();
    }

    @Override
    public String toString() {
        return cellContent.toString();
    }
}
