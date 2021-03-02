package entity;

import graphics.elements.Position;
import graphics.elements.cells.CellElementType;

public class Monster extends AbstractEntity{
    private boolean isRoom;
    private int id;

    //private MonsterStrategy race;

    private Monster() {
        super(new Position(0, 0), 100, 10, CellElementType.MONSTER, -2);
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
