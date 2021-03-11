package entity;

import graphics.Tools;

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
 *      ENRAGED (make +5 damage more during 5 turns)
 *      HEALED (regain +2 hp/turns during 6 turns)
 *
 *      -1 == null
 */

public enum EntityState {

    NEUTRAL("", -1),

    FROZEN(Tools.TextEffects.blue("Frozen"), 2),
    BURNT(Tools.TextEffects.red("Burnt"), 8),
    POISONED(Tools.TextEffects.magenta("Poisoned"), 8),
    PARALYSED(Tools.TextEffects.yellow("Paralysed"), 3),

    INVULNERABLE("Invulnerable", 4),
    ENRAGED("Enraged", 5),
    HEALED("Healed", 6);

    private final String text;
    private final int duration;

    EntityState(String text, int duration){
        this.text = text;
        this.duration = duration;
    }

    public String getText() { return text; }

    public int getDuration() { return duration; }

    public static void applyStateEffects(AbstractEntity entity){
        applyState(entity);
        decrementRemainingTime(entity);
    }

    private static void applyState(AbstractEntity entity) {
        switch (entity.getState()){
            case FROZEN:
                System.out.println(Tools.TextEffects.blue("The freezes immobilizes you"));
                break;
            case BURNT:
                entity.takeDamage(2);
                System.out.println(Tools.TextEffects.red("The burn inflicted 2 damages"));
                break;
            case POISONED:
                entity.takeDamage(1);
                if (entity instanceof Player){
                    ((Player) entity).modifyHunger(-1);
                    System.out.println(Tools.TextEffects.magenta("You are suffering from poisoning (-1 HP, -1 Hunger (+ Archer : decreasing accuracy))"));
                } break;
            case PARALYSED:
                System.out.println(Tools.TextEffects.yellow("You are paralized" ));
            default: break;
        }
    }

    private static void decrementRemainingTime(AbstractEntity entity){
        entity.decrementRemainingTime();
    }


}
