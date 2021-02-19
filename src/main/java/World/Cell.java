package World;

public class Cell {
    private boolean isBlocked = false;
    // in the cell there can be either
    // 1. MONSTER, OBSTACLE (WALL, TREE or whatever), CELL not inside the lab... --> in all of these cases BLOCKED == TRUE
    // 2. ITEM or NOTHING --> BLOCKED == FALSE
    // 3. by default isBlocked == false
    public boolean isBlocked() {
        return isBlocked;
    }
}
