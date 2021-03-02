package entity;

/**
 * Define the specific strategy according to the chosen specialty
 */

public interface PlayerStrategy {
    boolean attack(Monster monster);
    void applyStateEffect();
    String toString();
}
