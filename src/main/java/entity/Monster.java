package entity;

import graphics.elements.ErrorPositionOutOfBound;
import graphics.elements.Position;

import java.util.Random;

public class Monster extends AbstractEntity{

    public Monster(Position p, int id, EntityType et) throws ErrorPositionOutOfBound {
        super(p, id, et);
    }

    public static Monster generateRandomMonster(Position pos, int id) throws ErrorPositionOutOfBound {
        EntityType[] monsterTypes = EntityType.monsters();
        int rndElt = new Random().nextInt(monsterTypes.length);
        EntityType monsterType = monsterTypes[rndElt];
        return new Monster(pos, id, monsterType);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Monster){
            Monster m = (Monster) obj;
            return m.getPosition().equals(getPosition());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
