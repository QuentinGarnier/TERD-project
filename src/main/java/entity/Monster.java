package entity;

import graphics.elements.Position;

import java.util.Random;

public class Monster extends AbstractEntity {
    public static final Monster boss = new Monster(new Position(10, 10), 0, EntityType.MONSTER_BOSS);

    public Monster(Position p, int id, EntityType et)  {
        super(p, id, et);
    }

    public static Monster generateRandomMonster(Position pos, int id)  {
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
}
