package entity;

import graphics.ColorStr;

public class Attack {
    public static boolean attack(AbstractEntity entity1, AbstractEntity entity2){

        if (!entity1.withinReach(entity2, entity1.getRange()) || entity2.getState() == EntityState.INVULNERABLE) return false;

        switch (entity1.entityType){
            case HERO_ARCHER:
                if (entity1.getState() == EntityState.PARALYSED) return false;
                if (Math.random() > (entity1.getState() == EntityState.POISONED ? 0.35 : 0.25)) {
                    entity2.takeDamage(entity1.getAttack());
                    return true;
                } System.out.println(ColorStr.red("Missed target")); return false;
            case HERO_WARRIOR:
                    entity2.takeDamage(entity1.getAttack());
                    ((Player) entity1).modifyHunger(-1);
                    return true;
            case HERO_WITCHER:
                    entity2.setState(EntityState.BURNT);
                    entity1.modifyHP(1);
                    return true;

            case MONSTER_GOBLIN:
            case MONSTER_ORC:
            case MONSTER_SPIDER:
            case MONSTER_WIZARD:

            default: return false;
        }
    }
}
