package entity;

import graphics.Tools;
import graphics.window.GameWindow;

import java.awt.*;

public class Attack {

    public static void attack(AbstractEntity entity1, AbstractEntity entity2) {
        Color red = new Color(140,30,30);

        if (entity2 == null || !entity1.withinReach(entity2, entity1.getRange()) || entity2.getState() == EntityState.INVULNERABLE) {
            GameWindow.addToLogs("Nothing happens.", Color.LIGHT_GRAY);
            return;
        }

        switch (entity1.entityType) {
            case HERO_ARCHER:
                if (Math.random() < (entity1.getState() == EntityState.POISONED ? 0.35 : 0.25)) {
                    System.out.println(Tools.TextEffects.red("Missed target"));
                    GameWindow.addToLogs("Missed target.", new Color(80, 160, 50));
                    return;
                }

            case HERO_WARRIOR:
                ((Player) entity1).modifyHunger(-1);
                entity2.takeDamage(entity1.getAttack());
                if (entity2.entityType == EntityType.MONSTER_ORC) entity2.setState(EntityState.ENRAGED);
                GameWindow.addToLogs(entity1 + " deals " + entity1.getAttack() + " damage to " + entity2 + "." + (entity2.getHP() == 0? " " + entity2 + " die!":""), red);
                return;

            case HERO_MAGE:
                entity2.takeDamage(entity1.getAttack());
                if (entity1.getState() != EntityState.PARALYSED) entity2.setState(EntityState.BURNT);
                entity1.modifyHP(2);
                GameWindow.addToLogs(entity1 + " deals " + entity1.getAttack() + " damage to " + entity2 + ". " + (entity2.getHP() == 0? entity2 + " die!" : "Enemy is burning to the ground! [-2 HP per turn]"), red);
                return;

            case MONSTER_GOBLIN: break;
            case MONSTER_ORC: if(entity2.getState() != EntityState.PARALYSED) entity2.setState(EntityState.PARALYSED); break;
            case MONSTER_SPIDER: if(entity2.getState() != EntityState.POISONED) entity2.setState(EntityState.POISONED); break;
            case MONSTER_WIZARD: if (Math.random() > 0.80) if(entity2.getState() != EntityState.FROZEN) entity2.setState(EntityState.FROZEN); break;
            default: break;
        }

        entity2.takeDamage(entity1.getAttack());
        GameWindow.addToLogs(entity1 + " deals " + entity1.getAttack() + " damage to " + entity2 + "." + (entity2.getHP() == 0? " " + entity2 + " die!":""), red);
    }

}
