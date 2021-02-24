package entity;

import graphics.elements.Position;

public abstract class AbstractEntity{
    private final Position position;
    private int HP;
    private int attack;

    public AbstractEntity(Position position, int HP, int attack) {
        this.position = position;
        this.HP = HP;
        this.attack = attack;
    }

    public Position getPos() {
        return position;
    }

    public int getHP() {
        return this.HP;
    }

    public int getAttack() { return this.attack; }
}
