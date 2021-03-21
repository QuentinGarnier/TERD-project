package entity;

import graphics.Tools;
import graphics.elements.cells.CellElementType;

import java.util.Arrays;

public enum EntityType {

    HERO_ARCHER(CellElementType.HERO_A, 100, 25, 5, 0),
    HERO_WARRIOR(CellElementType.HERO_W, 200, 30, 1, 0),
    HERO_MAGE(CellElementType.HERO_M, 140, 15, 3, 0),

    MONSTER_GOBLIN(CellElementType.GOBLIN, 35, 8, 1, 25),
    MONSTER_ORC(CellElementType.ORC, 54, 20, 1, 70),
    MONSTER_SPIDER(CellElementType.SPIDER, 32, 5, 1, 35),
    MONSTER_WIZARD(CellElementType.WIZARD, 20, 10, 3, 40);

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

    public boolean isHeroType() {
        return this.cellElementType.isHero();
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
