package entity;

import graphics.elements.cells.CellElementType;

import java.util.Arrays;

public enum EntityType {

    HERO_ARCHER(CellElementType.HERO_A, 100, 20, 5, 0, 0),
    HERO_WARRIOR(CellElementType.HERO_W, 200, 35, 1, 0,0),
    HERO_MAGE(CellElementType.HERO_M, 140, 15, 3, 0,0),

    MONSTER_GOBLIN(CellElementType.GOBLIN, 40, 8, 1, 25, 4),
    MONSTER_ORC(CellElementType.ORC, 54, 20, 1, 70, 4),
    MONSTER_SPIDER(CellElementType.SPIDER, 38, 5, 1, 35, 5),
    MONSTER_WIZARD(CellElementType.WIZARD, 25, 10, 3, 40, 6),
    MONSTER_BOSS(CellElementType.BOSS, 200, 10, 4, 500, 15),

    ALLY_MERCHANT(CellElementType.MERCHANT, 100, 0, 1, 0, 0);

    public final CellElementType cellElementType;
    public final int HPByType;
    public final int attackByType;
    public final int rangeByType;
    public final int experienceByType;
    public final int attackZone;

    EntityType(CellElementType ct, int hp, int attack, int range, int xp, int attackZone) {
        cellElementType = ct;
        HPByType = hp;
        attackByType = attack;
        rangeByType = range;
        experienceByType = xp;
        this.attackZone = attackZone;
    }

    public CellElementType getCellElementType(){
        return cellElementType;
    }

    public static EntityType[] monsters() {
        return Arrays.stream(EntityType.values()).filter(e -> e.cellElementType.isMonster()).toArray(EntityType[]::new);
    }

    public boolean isHeroType() {
        return cellElementType.isHero();
    }

    public boolean isMerchantType() {return cellElementType.isMerchant();}

    @Override
    public String toString() {
        return this.name().split("_")[1];
    }
}
