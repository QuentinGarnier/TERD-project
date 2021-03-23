package entity;

import graphics.Tools;
import graphics.window.GameWindow;

import java.awt.*;

public class Attack {

    public static void attack(AbstractEntity entity1, AbstractEntity entity2) {
        if (entity2 == null) return;

        if (entity2 instanceof Trader){ GameWindow.addToLogs("Don't attack the merchant!", Color.WHITE); return; }

        if (entity2.getState() == EntityState.INVULNERABLE) {
            GameWindow.addToLogs("Nothing happens.", Color.LIGHT_GRAY);
            return;
        }
        if (entity1.entityType != EntityType.HERO_ARCHER) {
            GameWindow.addToLogs(entity1 + " deals " + entity1.getAttack() + " damage to " + entity2 + ".", Tools.WindowText.red);
            entity2.takeDamage(entity1.getAttack());
        }
        switch (entity1.entityType) {
            case HERO_ARCHER:
                double arrow = Math.random();
                double target = Math.random();
                if (target < (entity1.getState() == EntityState.POISONED ? 0.15 : 0.10)){
                    GameWindow.addToLogs("Missed target.", Tools.WindowText.red);
                    return;
                }
                else if (target >= 0.80) {
                    GameWindow.addToLogs(entity1 + " critically hit " + entity2 + ". " + "[" + (int) (entity1.getAttack()*1.5) + " damages]", Tools.WindowText.red);
                    entity2.takeDamage((int) (entity1.getAttack()*1.5));
                } else {
                    GameWindow.addToLogs(entity1 + " deals " + entity1.getAttack() + " damage to " + entity2 + ".", Tools.WindowText.red);
                    entity2.takeDamage(entity1.getAttack());
                }
                if (arrow < 0.20 && entity2.getHP() != 0) {entity2.updateState(EntityState.POISONED); return;}
                if (arrow >= 0.80 && entity2.getHP() != 0) entity2.updateState(EntityState.PARALYSED);
                System.out.println(arrow + " ---- " + target);
                return;

            case HERO_WARRIOR:
                ((Player) entity1).modifyHunger(-1);
                if (entity2.entityType == EntityType.MONSTER_ORC && entity2.getHP() != 0) entity2.updateState(EntityState.ENRAGED);
                return;

            case HERO_MAGE:
                entity1.modifyHP(2);
                if (entity1.getState() != EntityState.PARALYSED && entity2.getHP() != 0) entity2.updateState(EntityState.BURNT);
                return;

            case MONSTER_GOBLIN: break;
            case MONSTER_ORC: if(entity2.getState() != EntityState.PARALYSED) entity2.updateState(EntityState.PARALYSED); break;
            case MONSTER_SPIDER: if(entity2.getState() != EntityState.POISONED) entity2.updateState(EntityState.POISONED); break;
            case MONSTER_WIZARD: if (Math.random() > 0.80) if(entity2.getState() != EntityState.FROZEN) entity2.updateState(EntityState.FROZEN); break;
        }
    }

}
