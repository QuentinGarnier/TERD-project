package Entity;

import World.Position;

public abstract class AbstractEntity implements Entity{
    private final Position position;
    private int HP;
    private int attack;

    public AbstractEntity(Position position, int HP, int attack){
        this.position = position;
        this.HP = HP;
        this.attack = attack;
    }

    @Override
    public Position getPos() {
        return position;
    }

    @Override
    public int getHP() {
        return 0;
    }

    @Override
    public int getAttack() {
        return 0;
    }
}
