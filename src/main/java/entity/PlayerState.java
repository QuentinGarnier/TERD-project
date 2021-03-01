package entity;

/**
 * List all different types of states you can be affected
 *      NEUTRAL -> white, Player is affected by nothing
 *      FROZEN -> blue, Player can't do anything (no moving, no attack) during 5 turns
 *      BUNT -> red, Player take  2 damages/turn during 10 turns
 *      POISONED -> purple, Player take 1 damages/turn and his moving can be random (he sees cloudy) (need to fix a probability) during 10 turns
 *      PARALYSED -> yellow, Player can't attack during 10 turns
 *
 *      We can also imagine positive states like INVULNERABLE (can't take damage during x turns), ENRAGED (make +5 damage more during x turns), HEALED (regain +2 hp/sec during x turns)
 */

public enum PlayerState {

    NEUTRAL(-1), FROZEN(5), BURNT(10), POISONED(10), PARALYSED(10);

    private final int duration;

    PlayerState(int duration){
        this.duration = duration;
    }

    public int getDuration() { return duration; }

}
