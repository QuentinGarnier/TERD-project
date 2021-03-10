package entity;

import graphics.elements.cells.CellElementType;

import java.util.Arrays;

public enum EntityType {

    HERO_ARCHER(CellElementType.HERO),
    HERO_WARRIOR(CellElementType.HERO),
    HERO_WITCHER(CellElementType.HERO),

    MONSTER_GOBLIN(CellElementType.GOBLIN),
    MONSTER_ORC(CellElementType.ORC),
    MONSTER_SPIDER(CellElementType.SPIDER),
    MONSTER_WIZARD(CellElementType.WIZARD);

    CellElementType cellElementType;

    EntityType(CellElementType ct){
        cellElementType = ct;
    }

    public CellElementType getCellElementType(){
        return cellElementType;
    }

    public static EntityType[] monsters(){
        return Arrays.stream(EntityType.values()).filter(e -> e.cellElementType != CellElementType.HERO).toArray(EntityType[]::new);
    }
}
