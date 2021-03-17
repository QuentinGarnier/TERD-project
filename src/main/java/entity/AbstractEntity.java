package entity;

import graphics.Tools;
import graphics.elements.ErrorPositionOutOfBound;
import graphics.elements.Move;
import graphics.elements.Position;
import graphics.elements.cells.Cell;
import graphics.elements.cells.CellElementType;
import graphics.map.WorldMap;
import graphics.window.GameWindow;

import java.awt.*;

public abstract class AbstractEntity {
    private Position position;
    public final EntityType entityType;
    private final Strategy strategy;
    private int HP, HPMax;
    private int attack, attackMax;
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
        this.attackMax = entityType.attackByType;
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
        if (ct.isHero() && currentCell.getItem() != null) {
            if(currentCell.getItem().immediateUse) currentCell.getItem().use();
            else {
                Player.addItem(currentCell.getItemId());
                GameWindow.addToLogs("You have found: " + currentCell.getItem() + "!", Tools.WindowText.golden);
            }
            currentCell.getItem().setPosition(null);
            currentCell.heroPickItem();
            GameWindow.refreshInventory();
        }
        return moved;
    }

    public boolean moveEntity(Move m) {
        return moveEntity(m.getMove());
    }

    public int getId() {
        return id;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(int x, int y) {
        position.setPosition(x, y);
    }

    public void setPosition(Position p) {
        position.setPosition(p.getX(), p.getY());
    }

    public int getHP() { return HP; }

    public int getHPMax() { return HPMax; }

    public void setHPMax(int x) {
        HPMax = x;
    }

    public void modifyHP(int health) {
        HP = Math.max(Math.min(HP + health, HPMax), 0);
        if (HP == 0) removeEntity();
        GameWindow.refreshInventory();
    }
    public void fullHeal() {
        HP = HPMax;
    }

    public int getAttack() { return attack; }

    public void setAttack(int att) {
        attack = Math.max(att, 0);
    }

    public int getAttackMax() { return attackMax; }

    public void setAttackMax(int attMax) { attackMax = Math.max(attMax, 0); }

    public int getRange() { return range; }

    public void setRange(int r) { range = Math.max(r, 0); }

    public EntityState getState() { return state; }

    public void setState(EntityState state){
        this.state = state;
        updateRemainingTime();
    }

    public void updateState(EntityState state) {
        setState(state);
        EntityState.immediateEffects(this);
        GameWindow.refreshInventory();
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public void updateRemainingTime() {
        remainingTime = getState().getDuration();
    }

    public void decrementRemainingTime() {
        if (getState() != EntityState.NEUTRAL ) remainingTime--;
        if (remainingTime == 0) updateState(EntityState.NEUTRAL);
    }

    public EntityType getEntityType() {
        return entityType;
    }

    private void removeEntity() {
        WorldMap worldMap = WorldMap.getInstanceWorld();
        Cell cell = worldMap.getCell(position);
        //if (this instanceof Monster)worldMap.getRooms().get(cell.getBaseId()).removeEntity((Monster)this);
        worldMap.getCell(position).entityLeft();
    }

    public void takeDamage(int damage) {
        modifyHP(-damage);
    }

    public boolean withinReach(AbstractEntity entity, int range) {
        return Position.calculateRange(this.getPosition(), entity.getPosition()) <= range;
    }

    public void applyStrategy() {
        if (HP > 0) strategy.applyStrategy();
    }

    public void goTo(Position p) {
        WorldMap worldMap = WorldMap.getInstanceWorld();
        worldMap.getCell(position).entityLeft();
        this.position = p;
        worldMap.getCell(position).setEntity(this);
    }

    public boolean isHero(){ return this == Player.getInstancePlayer();}

    public boolean isMonster(){return !isHero();}

    @Override
    public String toString() {
        return switch (entityType) {
            case HERO_ARCHER, HERO_MAGE, HERO_WARRIOR -> "HERO";
            default -> entityType.toString();
        };
    }
}
