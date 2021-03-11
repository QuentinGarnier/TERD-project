package entity;

import graphics.Tools;
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

    public String toString2() {
        switch (this){

            case HERO_ARCHER: return Tools.TextEffects.encircled(Tools.TextEffects.green(" " + toString().split("_")[1] + " "));
            case HERO_WARRIOR: return Tools.TextEffects.encircled(Tools.TextEffects.red(" " + toString().split("_")[1] + " "));
            case HERO_WITCHER: return Tools.TextEffects.encircled(Tools.TextEffects.blue(" " + toString().split("_")[1] + " "));

            case MONSTER_GOBLIN:
            case MONSTER_ORC:
            case MONSTER_SPIDER:
            case MONSTER_WIZARD: return Tools.TextEffects.encircled(toString());

            default: return "";
        }
    }
}
