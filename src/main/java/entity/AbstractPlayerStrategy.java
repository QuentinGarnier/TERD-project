package entity;

/**
 * Define the specific strategy according to the chosen specialty
 */

public abstract class AbstractPlayerStrategy extends AbstractEntityStrategy{

    AbstractPlayerStrategy(int att, int hp){
        super(att, hp);
    }

    public abstract void applyStateEffect();
    public abstract String toString();
}
