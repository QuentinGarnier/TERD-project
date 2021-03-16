package entity;

import graphics.Tools;
import graphics.elements.cells.CellElementType;

import javax.swing.*;
import java.util.Arrays;

public enum EntityType {

    HERO_ARCHER(CellElementType.HERO_A, 60, 25, 5),
    HERO_WARRIOR(CellElementType.HERO_W, 100, 30, 1),
    HERO_MAGE(CellElementType.HERO_M, 75, 15, 3),

    MONSTER_GOBLIN(CellElementType.GOBLIN, 35, 8, 1),
    MONSTER_ORC(CellElementType.ORC, 55, 20, 1),
    MONSTER_SPIDER(CellElementType.SPIDER, 34, 5, 1),
    MONSTER_WIZARD(CellElementType.WIZARD, 20, 12, 3);

    final CellElementType cellElementType;
    final int HPByType;
    final int attackByType;
    final int rangeByType;

    EntityType(CellElementType ct, int hp, int attack, int range) {
        cellElementType = ct;
        HPByType = hp;
        attackByType = attack;
        rangeByType = range;
    }

    public CellElementType getCellElementType(){
        return cellElementType;
    }

    public static EntityType[] monsters() {
        return Arrays.stream(EntityType.values()).filter(e -> !e.cellElementType.isHero()).toArray(EntityType[]::new);
    }

    @Override
    public String toString() {
        return super.toString().split("_")[1];
    }

    public String toString2() {
        return switch (this) {
            case HERO_ARCHER -> Tools.TextEffects.encircled(Tools.TextEffects.green(" " + toString() + " "));
            case HERO_WARRIOR -> Tools.TextEffects.encircled(Tools.TextEffects.red(" " + toString() + " "));
            case HERO_MAGE -> Tools.TextEffects.encircled(Tools.TextEffects.blue(" " + toString() + " "));

            case MONSTER_GOBLIN -> "";
            case MONSTER_ORC -> "";
            case MONSTER_SPIDER -> "";
            case MONSTER_WIZARD -> Tools.TextEffects.encircled(toString());

            default -> "";
        };
    }
}
