package entity;

import graphics.window.GameWindow;

import java.awt.*;

public class Attack {

    public static void attack(AbstractEntity entity1, AbstractEntity entity2) {
        Color red = new Color(140,30,30);

        if (entity2 == null || !entity1.withinReach(entity2, entity1.getRange()) || entity2.getState() == EntityState.INVULNERABLE) {
            GameWindow.addToLogs("Nothing happens.", Color.LIGHT_GRAY);
            return;
        }
        if (entity1.entityType != EntityType.HERO_ARCHER) {
            entity2.takeDamage(entity1.getAttack());
            GameWindow.addToLogs(entity1 + " deals " + entity1.getAttack() + " damage to " + entity2 + "." + (entity2.getHP() == 0? " " + entity2 + " die!" : ""), red);
        }
        switch (entity1.entityType) {
            case HERO_ARCHER:
                if (Math.random() < (entity1.getState() == EntityState.POISONED ? 0.25 : 0.15)) {
                    GameWindow.addToLogs("Missed target.", red);
                    return;
                } else {
                    entity2.takeDamage(entity1.getAttack());
                    GameWindow.addToLogs(entity1 + " deals " + entity1.getAttack() + " damage to " + entity2 + "." + (entity2.getHP() == 0? " " + entity2 + " die!" : ""), red);
                }

            case HERO_WARRIOR:
                ((Player) entity1).modifyHunger(-1);
                if (entity2.entityType == EntityType.MONSTER_ORC) entity2.updateState(EntityState.ENRAGED);
                return;

            case HERO_MAGE:
                entity1.modifyHP(2);
                if (entity1.getState() != EntityState.PARALYSED) entity2.updateState(EntityState.BURNT);
                return;

            case MONSTER_GOBLIN: break;
            case MONSTER_ORC: if(entity2.getState() != EntityState.PARALYSED) entity2.updateState(EntityState.PARALYSED); break;
            case MONSTER_SPIDER: if(entity2.getState() != EntityState.POISONED) entity2.updateState(EntityState.POISONED); break;
            case MONSTER_WIZARD: if (Math.random() > 0.80) if(entity2.getState() != EntityState.FROZEN) entity2.updateState(EntityState.FROZEN); break;
        }
    }

}
