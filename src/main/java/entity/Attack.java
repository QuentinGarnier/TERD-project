package entity;

import graphics.Tools;

public class Attack {

    public static void attack(AbstractEntity entity1, AbstractEntity entity2) {
        if (entity2 == null || !entity1.withinReach(entity2, entity1.getRange()) || entity2.getState() == EntityState.INVULNERABLE) return;

        switch (entity1.entityType){
            case HERO_ARCHER:
                if (Math.random() < (entity1.getState() == EntityState.POISONED ? 0.35 : 0.25)) {System.out.println(Tools.TextEffects.red("Missed target")); return;}

            case HERO_WARRIOR:
                ((Player) entity1).modifyHunger(-1);
                entity2.takeDamage(entity1.getAttack());
                if (entity2.entityType == EntityType.MONSTER_ORC) entity2.setState(EntityState.ENRAGED); return;

            case HERO_MAGE:
                entity2.takeDamage(entity1.getAttack());
                if (entity1.getState() != EntityState.PARALYSED) entity2.setState(EntityState.BURNT);
                entity1.modifyHP(2); return;

            case MONSTER_GOBLIN: break;
            case MONSTER_ORC: entity2.setState(EntityState.PARALYSED); break;
            case MONSTER_SPIDER: entity2.setState(EntityState.POISONED); break;
            case MONSTER_WIZARD: if (Math.random() > 0.80) entity2.setState(EntityState.FROZEN); break;
            default: break;
        }

        entity2.takeDamage(entity1.getAttack());
    }
}
