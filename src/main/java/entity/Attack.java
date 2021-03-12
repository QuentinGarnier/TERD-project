package entity;

import graphics.Tools;

public class Attack {

    public static void attack(AbstractEntity entity1, AbstractEntity entity2){
        if (entity2 == null || !entity1.withinReach(entity2, entity1.getRange()) || entity2.getState() == EntityState.INVULNERABLE)

        switch (entity1.entityType){
            case HERO_ARCHER:
                if (entity1.getState() == EntityState.PARALYSED)
                if (Math.random() > (entity1.getState() == EntityState.POISONED ? 0.35 : 0.25)) {
                    entity2.takeDamage(entity1.getAttack());
                } System.out.println(Tools.TextEffects.red("Missed target"));
            case HERO_WARRIOR:
                    entity2.takeDamage(entity1.getAttack());
                    ((Player) entity1).modifyHunger(-1);
            case HERO_WITCHER:
                    entity2.setState(EntityState.BURNT);
                    entity1.modifyHP(1);

            case MONSTER_GOBLIN:
            case MONSTER_ORC:
            case MONSTER_SPIDER:
            case MONSTER_WIZARD:

            default: break;
        }
    }
}
