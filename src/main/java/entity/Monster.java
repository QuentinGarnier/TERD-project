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


    public void attackPlayer() {
        Player.getInstancePlayer().takeDamage(this.getAttack());
    }

    public void moveMonster(){
        //here move the monster by applying the monster strategy's algorithm)
    }

    public void gameMonster(){
        if (withinReach(Player.getInstancePlayer(), 1)) { //in the long term probably something like race.getRange() instead of '1' with a new monster field
            attackPlayer();
        } else {
            moveMonster();
        }
    }

    /* Deleted (but check my proposition in AbstractEntity before if you agree)
    public boolean withinReach(Player p){
        Position pos = p.getPos();
        if ((pos.getX() == this.getPos().getX() + 1) || (pos.getX() == this.getPos().getX() - 1) || (pos.getY() == this.getPos().getY() + 1) || (pos.getY() == this.getPos().getY() - 1)){
            return true;
        }
        else
            return false;
    }*/
}
