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

    public void setPosition(int x, int y) {
        position.setPosition(x,y);
    }

    public void setPosition(Position p) {
        position.setPosition(p.getX(), p.getY());
    }

    public int getHP() {
        return HP;
    }

    public int getAttack() { return attack; }
}
