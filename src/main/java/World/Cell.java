package World;

public class Cell {
    // in the cell there can be either
    // 1. MONSTER, OBSTACLE (WALL, TREE or whatever), CELL not inside the lab... --> in all of these cases BLOCKED == TRUE
    // 2. ITEM or NOTHING --> BLOCKED == FALSE
    // 3. by default isBlocked == false
    private ElementsEnum cellContent;
    Cell(ElementsEnum e){
        this.cellContent = e;
    }

    public boolean isBlocked() {
        return !(cellContent == ElementsEnum.ITEM || cellContent == ElementsEnum.EMPTY);
    }

    @Override
    public String toString() {
        return cellContent.toString();
    }
}
