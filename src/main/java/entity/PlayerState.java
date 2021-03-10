package entity;

import graphics.ColorStr;

/**
 * List all different types of states
 *      NEUTRAL -> white, Player is affected by nothing
 *
 *      FROZEN -> blue, Player can't do anything (no moving, no attack) during 2 turns
 *      BUNT -> red, Player take  2 damages/turn during 10 turns
 *      POISONED -> purple, Player take 1 damages/turn and his moving can be random (he sees cloudy) (need to fix a probability) during 10 turns
 *      PARALYSED -> yellow, Player can't attack during 3 turns
 *
 *      INVULNERABLE (can't take damage during 4 turns)
 *      ENRAGED (make +5 damage more during 5 turns)
 *      HEALED (regain +2 hp/sec during 6 turns)
 */

public enum PlayerState {

    NEUTRAL("", -1),

    FROZEN(ColorStr.blueBG("Frozen"), 2),
    BURNT(ColorStr.redBG("Burnt"),10),
    POISONED(ColorStr.magentaBG("Poisoned"), 10),
    PARALYSED(ColorStr.yellowBG("Paralysed"), 3),

    INVULNERABLE("Invulnerable", 4),
    ENRAGED("Enraged", 5),
    HEALED("Healed", 6);

    private final String text;
    private final int duration;

    PlayerState(String text, int duration){
        this.text = text;
        this.duration = duration;
    }

    public int getDuration() { return duration; }

    public String getText() { return text; }
}
