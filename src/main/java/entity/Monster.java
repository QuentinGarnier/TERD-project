package entity;

import graphics.elements.ErrorPositionOutOfBound;
import graphics.elements.Position;

import java.util.Random;

public class Monster extends AbstractEntity{

    public Monster(Position p, int hp, int attack, int range, int id, EntityType et) throws ErrorPositionOutOfBound {
        super(p, hp, attack, range, id, et);
    }

    public static Monster generateRandomMonster(Position pos, int id) throws ErrorPositionOutOfBound {
        EntityType[] monsterTypes = EntityType.monsters();
        int rndElt = new Random().nextInt(monsterTypes.length);
        Monster res;
        EntityType monsterType = monsterTypes[rndElt];
        switch (monsterType){
            case MONSTER_GOBLIN -> res = new Monster(pos, 20, 5, 1, id, monsterType);
            case MONSTER_ORC -> res =  new Monster(pos, 50, 8, 1, id, monsterType);
            case MONSTER_SPIDER -> res =  new Monster(pos, 10, 2, 1, id, monsterType);
            case MONSTER_WIZARD -> res =  new Monster(pos, 15, 10, 3, id, monsterType);
            default -> {
                return generateRandomMonster(pos, id);
            }
        }
        return res;
    }
}
