package items;

import graphics.elements.cells.CellElementType;

/**
 * List all different types of items you can have.
 */

public enum ItemType {
    COIN(CellElementType.COIN),
    FOOD(CellElementType.BURGER),  //food to restore hunger
    CONSUMABLE(CellElementType.ITEM),  //potions, scrolls...
    EQUIP(CellElementType.ITEM),  //weapons and armors
    TRAP(CellElementType.TRAP);     //traps

    public final CellElementType cellElementType;

    ItemType(CellElementType ct){
        this.cellElementType = ct;
    }
}
