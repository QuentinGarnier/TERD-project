package entity;

import graphics.Language;
import graphics.Tools;
import graphics.elements.Position;
import graphics.map.WorldMap;
import graphics.window.GameWindow;
import items.collectables.ItemEquip;

import java.awt.*;

public class Attack {

    public static void attack(AbstractEntity entity1, AbstractEntity entity2) {
        if (entity2 == null) return;

        int oldHP = entity2.getHP();

        if (entity2.getState() == EntityState.INVULNERABLE) {
            GameWindow.addToLogs(Language.logNothingHappens(entity2), Color.LIGHT_GRAY);
            return;
        }
        if (entity1.entityType != EntityType.HERO_ARCHER) {
            int dmg = Math.max(0, entity1.getAttack() - ((entity2 instanceof Player) ? (int) ((double) Player.getInstancePlayer().getDefense()/100 * entity1.getAttack()) : 0));
            GameWindow.addToLogs(Language.logDealDamage(entity1, entity2, dmg), Tools.WindowText.red);
            entity2.takeDamage(dmg);
            if (entity2.getEntityType() == EntityType.MONSTER_BOSS) {
                if (oldHP >= entity2.getHPMax()*0.75 && entity2.getHP() < entity2.getHPMax()*0.75 || oldHP >= entity2.getHPMax()*0.25 && entity2.getHP() < entity2.getHPMax()*0.25) entity2.updateState(EntityState.INVULNERABLE);
            }

        }
        switch (entity1.entityType) {
            case HERO_ARCHER:
                double arrow = Math.random();
                double target = Math.random();
                if (target < (entity1.getState() == EntityState.POISONED ? 0.12 : 0.08)){
                    GameWindow.addToLogs(Language.logMissedTarget(), Tools.WindowText.red);
                    return;
                }
                else if (target >= 0.80) {
                    GameWindow.addToLogs(Language.logCriticalHit(entity1, entity2, (int) (entity1.getAttack()*1.5)), Tools.WindowText.red);
                    entity2.takeDamage((int) (entity1.getAttack()*1.5));
                } else {
                    GameWindow.addToLogs(Language.logDealDamage(entity1, entity2, entity1.getAttack()), Tools.WindowText.red);
                    entity2.takeDamage(entity1.getAttack());
                }

                ItemEquip ie = ((Player) entity1).getAttackItem();
                if (ie != null && ie.getEquipmentType().getMagicEffect() != null) ie.applyEffect((Monster) entity2);
                else if (arrow < 0.20) entity2.updateState(EntityState.POISONED);

                if (entity2.getEntityType() == EntityType.MONSTER_BOSS) {
                    if (oldHP >= entity2.getHPMax()*0.75 && entity2.getHP() < entity2.getHPMax()*0.75 || oldHP >= entity2.getHPMax()*0.25 && entity2.getHP() < entity2.getHPMax()*0.25) entity2.updateState(EntityState.INVULNERABLE);
                }
                return;

            case HERO_WARRIOR:
                ((Player) entity1).modifyHunger(-1, true);
                if (entity2.entityType == EntityType.MONSTER_ORC) entity2.updateState(EntityState.ENRAGED);
                break;

            case HERO_MAGE:
                entity1.modifyHP((int) (Math.random()*6) + 5);

                for (Monster m : WorldMap.getInstanceWorld().getCurrentRoom(entity2.getPosition()).getMonsters()) {
                    if (m.getHP() != 0  && Position.distance(entity2.getPosition(), m.getPosition()) == 1) {
                        GameWindow.addToLogs(Language.logAreaDamage(m), Tools.WindowText.red);
                        m.takeDamage((int) (m.getHPMax() * 0.20));
                    }
                }

                if (entity1.getState() != EntityState.PARALYSED && entity2.getState() != EntityState.INVULNERABLE) entity2.updateState(EntityState.getRandom());
                return;

            case MONSTER_GOBLIN: break;
            case MONSTER_ORC: if(entity2.getState() != EntityState.PARALYSED) entity2.updateState(EntityState.PARALYSED); break;
            case MONSTER_SPIDER: if(entity2.getState() != EntityState.POISONED) entity2.updateState(EntityState.POISONED); break;
            case MONSTER_WIZARD: if (Math.random() > 0.80 && entity2.getState() != EntityState.FROZEN) entity2.updateState(EntityState.FROZEN); break;
            case MONSTER_BOSS: if(entity2.getState() != EntityState.BURNT) entity2.updateState(EntityState.BURNT); break;

        }

        if (entity1.isMonster() && entity2.entityType == EntityType.HERO_WARRIOR && entity2.getHP() <= entity2.getHPMax()/10 && entity2.getState() != EntityState.ENRAGED) entity2.updateState(EntityState.ENRAGED);

    }

}
