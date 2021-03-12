package entity;

import graphics.Tools;
import graphics.elements.cells.CellElementType;

import java.util.Arrays;

public enum EntityType {

    HERO_ARCHER(CellElementType.HERO, 50, 15, 5),
    HERO_WARRIOR(CellElementType.HERO, 100, 30, 1),
    HERO_MAGE(CellElementType.HERO, 75, 10, 3),

    MONSTER_GOBLIN(CellElementType.GOBLIN, 40, 10, 1),
    MONSTER_ORC(CellElementType.ORC, 75, 20, 1),
    MONSTER_SPIDER(CellElementType.SPIDER, 35, 15, 1),
    MONSTER_WIZARD(CellElementType.WIZARD, 15, 15, 3);

    final CellElementType cellElementType;
    final int HPByType;
    final int attackByType;
    final int rangeByType;

    EntityType(CellElementType ct, int hp, int attack,int range){
        cellElementType = ct;
        HPByType = hp;
        attackByType = attack;
        rangeByType = range;
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
            case HERO_MAGE: return Tools.TextEffects.encircled(Tools.TextEffects.blue(" " + toString().split("_")[1] + " "));

            case MONSTER_GOBLIN:
            case MONSTER_ORC:
            case MONSTER_SPIDER:
            case MONSTER_WIZARD: return Tools.TextEffects.encircled(toString());

            default: return "";
        }
    }
}
