package entity;

import graphics.elements.ErrorPositionOutOfBound;
import graphics.elements.Position;

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
