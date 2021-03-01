package entity;

/**
 * Define the specific strategy according the chosen specialty
 */

public interface PlayerStrategy {
    boolean attack(Monster monster);
    void applyStateEffect();
}
