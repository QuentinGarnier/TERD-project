package entity;

import graphics.Language;
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

    FROZEN(Tools.TerminalText.blue("Frozen"), 2),
    BURNT(Tools.TerminalText.red("Burnt"), 8),
    POISONED(Tools.TerminalText.magenta("Poisoned"), 8),
    PARALYSED(Tools.TerminalText.yellow("Paralysed"), 3),

    INVULNERABLE(Tools.TerminalText.cyan("Invulnerable"), 4),
    ENRAGED(Tools.TerminalText.red("Enraged"), 5),
    HEALED(Tools.TerminalText.green("Healed"), 6);

    private final String text;
    private final int duration;

    EntityState(String text, int duration) {
        this.text = text;
        this.duration = duration;
    }

    public String getText() {
        return text;
    }

    public int getDuration() {
        return duration;
    }

    public static boolean isBeneficial(EntityState state){
        return state == INVULNERABLE || state == EntityState.HEALED || state == ENRAGED;
    }

    public static void immediateEffects(AbstractEntity entity) {
        switch (entity.getState()) {
            case PARALYSED:
                if (entity.entityType == EntityType.HERO_WARRIOR) entity.setAttack((int) (entity.getAttackMax() * 0.80));
                if (entity.entityType == EntityType.HERO_ARCHER) entity.setRange(5 - 2);
                if (entity.isMonster()) entity.setAttack((int) (entity.getAttackMax() * 0.60));
                GameWindow.addToLogs(Language.logEffect(entity), Tools.WindowText.golden);
                break;
            case ENRAGED:
                entity.setAttack(entity.getAttackMax() + 10);
                GameWindow.addToLogs(Language.logEffect(entity), Tools.WindowText.red);
                break;
            case FROZEN: GameWindow.addToLogs(Language.logEffect(entity), Tools.WindowText.cyan); break;
            case POISONED: GameWindow.addToLogs(Language.logEffect(entity), Tools.WindowText.purple); break;
            case BURNT: GameWindow.addToLogs(Language.logEffect(entity), Tools.WindowText.orange); break;
            case HEALED: GameWindow.addToLogs(Language.logEffect(entity), Tools.WindowText.green); break;
            case INVULNERABLE:  GameWindow.addToLogs(Language.logEffect(entity), Tools.WindowText.golden); break;
            default: break;
        }

        if (entity.entityType == EntityType.HERO_WARRIOR && (entity.getState() == PARALYSED || entity.getState() == ENRAGED)) return;
        if (entity.entityType == EntityType.HERO_ARCHER && entity.getState() != PARALYSED) entity.setRange(5);
        if (entity.getState() != ENRAGED && entity.getState() != PARALYSED) entity.setAttack(entity.getAttackMax());
    }

    public static void turnEffects(AbstractEntity entity) {
        applyState(entity);
        decrementRemainingTime(entity);
    }

    private static void applyState(AbstractEntity entity) {
        if (entity.getHP() == 0) return;
        int amount;
        switch (entity.getState()) {
            case BURNT:
                amount = 2;
                entity.takeDamage(amount);
                if (entity.isHero()) GameWindow.addToLogs(Language.logBurnEffect(amount), Tools.WindowText.orange);
                break;
            case POISONED:
                amount = 1;
                entity.takeDamage(amount);
                if (entity instanceof Player){
                    ((Player) entity).modifyHunger(-1, false);
                    GameWindow.addToLogs(Language.logPoisonEffect(amount), Tools.WindowText.purple);
                    if (((Player) entity).getHunger() == 0) { GameWindow.addToLogs(Language.logHeroDeath(true), Color.RED); return; }
                } break;
            case HEALED:
                amount = 10;
                entity.modifyHP(amount);
                if (entity.isHero()) GameWindow.addToLogs(Language.logHealEffect(amount), Tools.WindowText.green);
                break;
            default: break;
        }
        if (entity.getHP() == 0 && entity.isHero()) GameWindow.addToLogs(Language.logHeroDeath(false), Color.RED);
    }

    private static void decrementRemainingTime(AbstractEntity entity) {
        entity.decrementRemainingTime();
    }


}
