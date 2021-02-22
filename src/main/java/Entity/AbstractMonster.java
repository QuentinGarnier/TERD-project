package entity;

import graphics.elements.Position;

public abstract class AbstractMonster extends AbstractEntity {
    private int speed;//an example of a monster's characteristic

    public AbstractMonster(Position position, int HP, int attack, int speed) {
        super(position, HP, attack);
        this.speed = speed;
    }
}
