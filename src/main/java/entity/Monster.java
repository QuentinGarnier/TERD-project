package entity;

import graphics.elements.ErrorPositionOutOfBound;
import graphics.elements.Position;

import java.util.Random;

public class Monster extends AbstractEntity{
    private boolean isRoom;
    private int id;

    //private MonsterStrategy race;

    public Monster(Position p, int hp, int attack, int id, EntityType et) throws ErrorPositionOutOfBound {
        super(p, hp, attack, id, et);
    }

    /*
    public void attackPlayer() {
        Player.getInstancePlayer().takeDamage(this.getAttack());
    }*/

    public static Monster generateRandomMonster(Position pos, int id) throws ErrorPositionOutOfBound {
        EntityType[] entityTypes = EntityType.values();
        int rndElt = new Random().nextInt(entityTypes.length);
        Monster res;
        EntityType itemType = entityTypes[rndElt];
        switch (itemType){
            case GOBLIN -> res = new Monster(pos, 20, 5, id, itemType);
            case ORC -> res =  new Monster(pos, 50, 8, id, itemType);
            case SPIDER -> res =  new Monster(pos, 10, 2, id, itemType);
            case WIZARD -> res =  new Monster(pos, 15, 10, id, itemType);
            default -> {
                return generateRandomMonster(pos, id);
            }
        }
        return res;
    }

    public void moveMonster(){
        //here move the monster by applying the monster strategy's algorithm)
    }

    public void gameMonster(){
        if (withinReach(Player.getInstancePlayer(), 1)) { //in the long term probably something like race.getRange() instead of '1' with a new monster field
            //attackPlayer();
        } else {
            moveMonster();
        }
    }

}
