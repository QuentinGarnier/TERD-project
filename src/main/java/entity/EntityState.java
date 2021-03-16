package entity;

import graphics.Tools;
import graphics.window.GameWindow;

import java.awt.*;

/**
 * List all different types of states
 *      NEUTRAL -> white, Entity is affected by nothing
 *
 *      FROZEN -> blue, Player can't do anything (no moving, no attack) during 2 turns
 *      BUNT -> red, Player take  2 damages/turn during 8 turns
 *      POISONED -> purple, Player take 1 damages/turn and his moving can be random (he sees cloudy) during 8 turns
 *      PARALYSED -> yellow, Player can't attack during 3 turns
 *
 *      INVULNERABLE (can't take damage during 4 turns)
 *      ENRAGED (make +10 damage more during 5 turns)
 *      HEALED (regain +3 hp/turns during 6 turns)
 *
 *      -1 == null
 */

public enum EntityState {

    NEUTRAL("", -1),

    FROZEN(Tools.TextEffects.blue("Frozen"), 2),
    BURNT(Tools.TextEffects.red("Burnt"), 8),
    POISONED(Tools.TextEffects.magenta("Poisoned"), 8),
    PARALYSED(Tools.TextEffects.yellow("Paralysed"), 3),

    INVULNERABLE(Tools.TextEffects.cyan("Invulnerable"), 3),
    ENRAGED(Tools.TextEffects.red("Enraged"), 5),
    HEALED(Tools.TextEffects.green("Healed"), 6);

    private final String text;
    private final int duration;

    EntityState(String text, int duration){
        this.text = text;
        this.duration = duration;
    }

    public String getText() { return text; }

    public int getDuration() { return duration; }

    public static void immediateEffects(AbstractEntity entity){
        String text = "you don't burn monsters";
        switch (entity.getState()){
            case PARALYSED:
                if (entity.entityType == EntityType.HERO_WARRIOR) {entity.setAttack((int) (entity.getAttackMax() * 0.80)); text = "-20% Attack";}
                if (entity.entityType == EntityType.HERO_ARCHER) {entity.setRange(5 - 2); text = "-2 range";}
                if (entity.isHero()) GameWindow.addToLogs("HERO is paralized: " + text, new Color(210,170,60));
                else GameWindow.addToLogs("MONSTER is paralized : " + text, new Color(210,170,60));
                break;
            case ENRAGED:
                entity.setAttack(entity.getAttackMax() + 10);
                GameWindow.addToLogs("Rage makes " + entity.toString() + " stronger (+10 attack)", new Color(140,30,30));
                break;
            case FROZEN: GameWindow.addToLogs(entity.toString() + " is frozen", new Color(80,140,180)); break;
            case POISONED: GameWindow.addToLogs(entity.toString() + " is poisonned", new Color(100,60,120)); break;
            case BURNT: GameWindow.addToLogs(entity.toString() + " is burning", new Color(140,30,30)); break;
            case HEALED: GameWindow.addToLogs(entity.toString() + " is healed", new Color(80, 140, 50)); break;
            case INVULNERABLE:  GameWindow.addToLogs(entity.toString() + " can't take damage", new Color(80,140,180)); break;
            default: break;
        }
        if (entity.entityType == EntityType.HERO_WARRIOR && (entity.getState() == PARALYSED || entity.getState() == ENRAGED)) return;
        if (entity.entityType == EntityType.HERO_ARCHER && entity.getState() != PARALYSED) entity.setRange(5);
        if (entity.getState() != ENRAGED) entity.setAttack(entity.getAttackMax());
    }

    public static void turnEffects(AbstractEntity entity){
        applyState(entity);
        decrementRemainingTime(entity);
    }

    private static void applyState(AbstractEntity entity) {
        switch (entity.getState()){
            case BURNT:
                entity.takeDamage(2);
                if (entity.isHero()) GameWindow.addToLogs("The burn inflicted you 2 damages", new Color(140,30,30));
                break;
            case POISONED:
                entity.takeDamage(1);
                if (entity instanceof Player){
                    ((Player) entity).modifyHunger(-1);
                    GameWindow.addToLogs("You are suffering from poisoning (-1 HP, -1 Hunger)", new Color(100,60,120));
                } break;
            case HEALED:
                entity.modifyHP(3);
                if (entity.isHero()) GameWindow.addToLogs("Cared for (+3 HP)", new Color(80, 140, 50));
                break;
            default: break;
        }
    }

    private static void decrementRemainingTime(AbstractEntity entity){
        entity.decrementRemainingTime();
    }


}
