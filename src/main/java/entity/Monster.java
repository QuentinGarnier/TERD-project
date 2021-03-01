package entity;

import graphics.elements.Position;
import graphics.elements.cells.CellElementType;

public class Monster extends AbstractEntity{

    private static final Monster instanceMonster = new Monster();
    private int level;
    private boolean isRoom;
    private int id;

    private Monster() {
        super(new Position(0, 0), 100, 10, CellElementType.MONSTER, -2);
        level = 1;
    }

    public static Monster getInstanceMonster() {
        return instanceMonster;
    }

    public int getLvl() {
        return level;
    }

    public void levelUp() {
        level ++;
    }

    public void attackPlayer(Player p) {
        if (withinReach(p) == true){
            p.modifyHP(-this.getAttack());
        }
    }

    public boolean withinReach(Player p){
        Position pos = p.getPos();
        if ((pos.getX() == this.getPos().getX() + 1) || (pos.getX() == this.getPos().getX() - 1) || (pos.getY() == this.getPos().getY() + 1) || (pos.getY() == this.getPos().getY() + 1)){
            return true;
        }
        else
            return false;
    }


}
