package entity;

import graphics.elements.cells.CellElementType;

public enum EntityType {
    GOBLIN(CellElementType.GOBLIN),
    SPIDER(CellElementType.SPIDER),
    ORC(CellElementType.ORC),
    WIZARD(CellElementType.WIZARD),
    HERO(CellElementType.HERO);

    CellElementType cellElementType;
    EntityType(CellElementType ct){
        cellElementType = ct;
    }
}
