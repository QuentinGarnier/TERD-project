package entity;

import graphics.elements.ErrorPositionOutOfBound;
import graphics.elements.Move;
import graphics.elements.Position;
import graphics.elements.cells.Cell;
import graphics.elements.cells.CellElementType;
import graphics.map.WorldMap;

public abstract class AbstractEntity {
    private Position position;
    public final EntityType entityType;
    private final Strategy strategy;
    private int HP, HPMax;
    private int attack;
    private int range;
    private EntityState state;
    private int remainingTime;
    private final int id;

    public AbstractEntity(Position position, int id, EntityType entityType) throws ErrorPositionOutOfBound {
        checkPosition(position);
        this.position = position;
        this.entityType = entityType;
        this.strategy = new Strategy(this);
        this.HP = entityType.HPByType;
        this.HPMax = entityType.HPByType;
        this.attack = entityType.attackByType;
        this.range = entityType.rangeByType;
        this.state = EntityState.NEUTRAL;
        this.remainingTime = EntityState.NEUTRAL.getDuration();
        this.id = id;
    }

    public void checkPosition(Position p) throws ErrorPositionOutOfBound {
        if (!p.insideWorld()) throw new ErrorPositionOutOfBound(p);
    }

    public boolean moveEntity(Position p) {
        CellElementType ct = entityType.cellElementType;
        WorldMap worldMap = WorldMap.getInstanceWorld();
        worldMap.getCell(position).entityLeft();
        boolean moved = position.nextPosition(p);
        worldMap.getCell(position).setEntity(this);
        Cell currentCell = worldMap.getCell(position);
        if (ct == CellElementType.HERO && currentCell.getItem() != null) {
            //System.out.println("You have earned " + ColorStr.yellow("+1 coin") + "!" + System.lineSeparator());
            if(currentCell.getItem().immediateUse) currentCell.getItem().use();
            else Player.addItem(currentCell.getItemId()); //ne marche probablement pas comme voulu vu que les ID sont désormais dépendant des salles

            currentCell.getItem().setPosition(null);
            currentCell.heroPickItem();
        }
        return moved;
    }

    public boolean moveEntity(Move m){
        return moveEntity(m.getMove());
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

    public int getHPMax() { return HPMax; }

    public int getHP() { return HP; }

    public int getAttack() { return attack; }

    public int getRange() { return range; }

    public EntityState getState(){ return state;}

    public void setState(EntityState state){
        this.state = state;
        updateRemainingTime();
    }

    public void updateRemainingTime(){ remainingTime = getState().getDuration(); }

    public void decrementRemainingTime(){
        if (getState() != EntityState.NEUTRAL ) remainingTime--;
        if (remainingTime == 0) setState(EntityState.NEUTRAL);
    }

    public EntityType getEntityType() { return entityType; }

    public void modifyAttack(int att) {
        attack = att;
        if(attack < 0) attack = 0;
    }

    /*
     * Modify the value of the current HP.
     * @param x number of HP added or substituted (can be positive or negative).
     */


    public void modifyHP(int x) {
        if(HP + x > HPMax) HP = HPMax;
        else if(HP + x < 0) HP = 0;
        else HP += x;
    }

    public void takeDamage(int damage){ modifyHP(-damage); }

    public void toHeal(int health){ modifyHP(health); }

    public void setHPMax(int x) {
        HPMax = x;
    }

    public void fullHeal() { HP = HPMax; }

    public boolean withinReach(AbstractEntity entity, int range){
        return Position.calculateRange(this.getPosition(), entity.getPosition()) <= range;
    }

    public void applyStrategy(){
        strategy.applyStrategy();
    }

    public void goTo(Position p){
        WorldMap worldMap = WorldMap.getInstanceWorld();
        worldMap.getCell(position).entityLeft();
        this.position = p;
        worldMap.getCell(position).setEntity(this);
    }
}
