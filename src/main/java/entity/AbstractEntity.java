package entity;

import graphics.ColorStr;
import graphics.elements.ErrorPositionOutOfBound;
import graphics.elements.Move;
import graphics.elements.Position;
import graphics.elements.cells.Cell;
import graphics.elements.cells.CellElementType;
import graphics.map.WorldMap;

public abstract class AbstractEntity {
    private Position position;
    private final Strategy strategy;
    public final EntityType entityType;
    private int HP, HPmax;
    private int attack;
    private final int id;

    public AbstractEntity(Position position, int hp, int attack, int id, EntityType entityType) throws ErrorPositionOutOfBound {
        checkPosition(position);
        this.position = position;
        this.HPmax = hp;
        this.HP = hp;
        this.attack = attack;
        this.id = id;
        this.entityType = entityType;
        this.strategy = new Strategy(this);
    }

    public void checkPosition(Position p) throws ErrorPositionOutOfBound {
        if (!p.insideWorld()) throw new ErrorPositionOutOfBound(p);
    }

    public void moveEntity(Position p){
        CellElementType ct = entityType.cellElementType;
        WorldMap worldMap = WorldMap.getInstanceWorld();
        worldMap.getCell(position).entityLeft();
        position.nextPosition(p);
        worldMap.getCell(position).setEntity(ct, id);
        Cell currentCell = worldMap.getCell(position);
        if (ct == CellElementType.HERO && currentCell.getItemContent() == CellElementType.COIN){
            System.out.println("You have earned " + ColorStr.yellow("+1 coin") + "!" + System.lineSeparator());
            Player.getInstancePlayer().incrementMoney();
            currentCell.heroPickItem();
        }
    }

    public void moveEntity(Move m){
        moveEntity(m.getMove());
    }

    public Position getPosition() {
        return position;
    }

    public int getId() {
        return id;
    }

    public void setPosition(int x, int y) {
        position.setPosition(x, y);
    }

    public void setPosition(Position p) {
        position.setPosition(p.getX(), p.getY());
    }

    public int getMaxHP() { return HPmax; }

    public int getHP() { return HP; }

    public int getAttack() { return attack; }


    public void modifyAttack(int att) {
        attack = att;
        if(attack < 0) attack = 0;
    }

    /*
     * Modify the value of the current HP.
     * @param x number of HP added or substituted (can be positive or negative).
     */


    public void modifyHP(int x) {
        if(HP + x > HPmax) HP = HPmax;
        else if(HP + x < 0) HP = 0;
        else HP += x;
    }

    public void takeDamage(int damage){ modifyHP(-damage); }

    public void toHeal(int health){ modifyHP(health); }

    public void setHPmax(int x) {
        HPmax = x;
    }

    public void fullHeal() { HP = HPmax; }

    public boolean withinReach(AbstractEntity entity, int range){
        return Position.calculateRange(this.getPosition(), entity.getPosition()) <= range;
    }

    public void applyStrategy(){
        strategy.applyStrategy();
    }

    public void goTo(Position p){
        this.position = p;
    }
}
