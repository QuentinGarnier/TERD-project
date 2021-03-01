package entity;

import graphics.elements.Position;
import graphics.elements.cells.CellElementType;

public abstract class AbstractMonster extends AbstractEntity {
    private int speed;//an example of a monster's characteristic

    public AbstractMonster(Position position, int HP, int attack, int speed, CellElementType ct, int id) {
        super(position, HP, attack, ct, id);
        this.speed = speed;
    }
}
