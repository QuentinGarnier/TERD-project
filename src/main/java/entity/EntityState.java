package entity;

import graphics.Language;
import graphics.Tools;
import graphics.window.GameWindow;

import javax.swing.*;
import java.util.Objects;

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

    NEUTRAL("", -1, ""),

    FROZEN(Tools.TerminalText.blue("Frozen"), 2, "ice"),
    BURNT(Tools.TerminalText.red("Burnt"), 8, "flame"),
    POISONED(Tools.TerminalText.magenta("Poisoned"), 8, "poison"),
    PARALYSED(Tools.TerminalText.yellow("Paralysed"), 3, "para"),

    INVULNERABLE(Tools.TerminalText.cyan("Invulnerable"), 4, "inv"),
    ENRAGED(Tools.TerminalText.red("Enraged"), 5, "rage"),
    HEALED(Tools.TerminalText.green("Healed"), 6, "heal");

    private final String text;
    private final int duration;
    private final ImageIcon[] anim = new ImageIcon[4];

    EntityState(String text, int duration, String animation) {
        this.text = text;
        this.duration = duration;
        if(!animation.equals("")) for(int i=0; i<4; i++)
            anim[i] = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("data/images/animations/"+ animation + (i+1) +".png")));
    }

    public String getText() {
        return text;
    }

    public int getDuration() {
        return duration;
    }

    public ImageIcon[] getAnim() {
        return anim;
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
                entity.setAttack((int) (entity.getAttackMax() * 1.10));
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
                amount = 1 + (int) Math.floor(entity.getHPMax() * 0.02);
                entity.takeDamage(amount);
                if (entity.isHero()) GameWindow.addToLogs(Language.logBurnEffect(amount), Tools.WindowText.orange);
                break;
            case POISONED:
                amount = 1 + (int) Math.round(entity.getHPMax() * 0.01);
                entity.takeDamage(amount);
                if (entity instanceof Player){
                    GameWindow.addToLogs(Language.logPoisonEffect(amount), Tools.WindowText.purple);
                    ((Player) entity).modifyHunger(-1, false);
                    if (((Player) entity).getHunger() == 0)  return;
                } break;
            case HEALED:
                amount = 1 + (int) Math.round(entity.getHPMax() * 0.06);
                entity.modifyHP(amount);
                if (entity.isHero()) GameWindow.addToLogs(Language.logHealEffect(amount), Tools.WindowText.green);
                break;
            default: break;
        }
        if (entity.getHP() == 0 && entity.isHero()) Tools.gameEnd(Tools.Victory_Death.DEATH_BY_HP);
    }

    private static void decrementRemainingTime(AbstractEntity entity) {
        entity.decrementRemainingTime();
    }

    public static EntityState getRandom() {
        double rn = Math.random();
        return rn < 0.20 ? FROZEN : rn >= 0.60 ? BURNT : PARALYSED;
    }

    public boolean isNegatifState() {
        return switch (this) {
            case NEUTRAL, ENRAGED, HEALED, INVULNERABLE -> false;
            default -> true;
        };
    }
}
