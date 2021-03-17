package entity;

import graphics.Tools;
import graphics.elements.cells.CellElementType;

import java.util.Arrays;

public enum EntityType {

    HERO_ARCHER(CellElementType.HERO_A, 60, 25, 5, 0),
    HERO_WARRIOR(CellElementType.HERO_W, 100, 30, 1, 0),
    HERO_MAGE(CellElementType.HERO_M, 75, 15, 3, 0),

    MONSTER_GOBLIN(CellElementType.GOBLIN, 35, 8, 1, 135),
    MONSTER_ORC(CellElementType.ORC, 55, 20, 1, 80),
    MONSTER_SPIDER(CellElementType.SPIDER, 34, 5, 1, 40),
    MONSTER_WIZARD(CellElementType.WIZARD, 20, 12, 3, 45);

    public final CellElementType cellElementType;
    public final int HPByType;
    public final int attackByType;
    public final int rangeByType;
    public final int experienceByType;

    EntityType(CellElementType ct, int hp, int attack, int range, int xp) {
        cellElementType = ct;
        HPByType = hp;
        attackByType = attack;
        rangeByType = range;
        experienceByType = xp;
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
            case HERO_ARCHER -> Tools.TerminalText.encircled(Tools.TerminalText.green(" " + toString() + " "));
            case HERO_WARRIOR -> Tools.TerminalText.encircled(Tools.TerminalText.red(" " + toString() + " "));
            case HERO_MAGE -> Tools.TerminalText.encircled(Tools.TerminalText.blue(" " + toString() + " "));

            case MONSTER_GOBLIN -> "";
            case MONSTER_ORC -> "";
            case MONSTER_SPIDER -> "";
            case MONSTER_WIZARD -> Tools.TerminalText.encircled(toString());

            default -> "";
        };
    }
}
