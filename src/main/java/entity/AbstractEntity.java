package entity;

import graphics.elements.Position;

public abstract class AbstractEntity{
    private final Position position;
    private int HP, HPmax;
    private int attack;

    public AbstractEntity(Position position, int hp, int attack) {
        this.position = position;
        this.HPmax = hp;
        this.HP = hp;
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

    public int getMaxHP() {
        return HPmax;
    }

    public int getHP() {
        return HP;
    }

    public int getAttack() {
        return attack;
    }

    /**
     * Modify the value of the current HP.
     * @param x number of HP added or substituted (can be positive or negative).
     */
    public void modifyHP(int x) {
        if(this.HP + x > this.HPmax) this.HP = this.HPmax;
        else if(this.HP + x < 0) this.HP = 0;
        else this.HP += x;
    }

    public void setHPmax(int x) {
        this.HPmax = x;
    }

    public void fullHeal() {
        this.HP = this.HPmax;
    }
}
