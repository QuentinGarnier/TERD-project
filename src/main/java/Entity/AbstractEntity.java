package entity;

import graphics.elements.Position;

public abstract class AbstractEntity implements Entity {
    private final Position position;
    private int HP;
    private int attack;

    public AbstractEntity(Position position, int HP, int attack) {
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
        return this.HP;
    }

    @Override
    public int getAttack() {
        return this.attack;
    }
}
