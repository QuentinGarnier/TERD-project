package entity;

import graphics.Tools;
import graphics.elements.ErrorPositionOutOfBound;
import graphics.elements.Move;
import graphics.elements.Position;
import graphics.elements.cells.Cell;
import graphics.elements.cells.CellElementType;
import graphics.map.WorldMap;
import graphics.window.GamePanel;
import graphics.window.GameWindow;

import javax.swing.*;
import java.awt.*;

public abstract class AbstractEntity extends JPanel {
    private Position position;
    public final EntityType entityType;
    private final Strategy strategy;
    private int HP, HPMax;
    private int attack, attackMax;
    private int range;
    private EntityState state;
    private int remainingTime;
    private final int id;
    private final int size;
    private final ImageIcon barIcon;
    private final JLabel barLabel;

    public AbstractEntity(Position position, int id, EntityType entityType) throws ErrorPositionOutOfBound {
        super();
        checkPosition(position);
        this.position = position;
        this.entityType = entityType;
        this.strategy = new Strategy(this);
        this.HP = entityType.isHeroType()? entityType.HPByType : entityType.HPByType + WorldMap.stageNum + Player.getInstancePlayer().getLvl();
        this.HPMax = entityType.isHeroType()? entityType.HPByType : entityType.HPByType + WorldMap.stageNum + Player.getInstancePlayer().getLvl();
        this.attack = entityType.isHeroType()? entityType.attackByType : entityType.attackByType + WorldMap.stageNum + Player.getInstancePlayer().getLvl();
        this.attackMax = entityType.isHeroType()? entityType.attackByType : entityType.attackByType + WorldMap.stageNum + Player.getInstancePlayer().getLvl();
        this.range = entityType.rangeByType;
        this.state = EntityState.NEUTRAL;
        this.remainingTime = EntityState.NEUTRAL.getDuration();
        this.id = id;

        // Graphics
        setLayout(new BorderLayout());
        this.size = GamePanel.size;
        this.barLabel = new JLabel();
        this.barIcon = new ImageIcon("data/images/interfaces/" + "bar_red.png");

        barLabel.setHorizontalAlignment(SwingConstants.LEFT);
        setup();
    }

    private JLabel image() {
        ImageIcon entityImg = new ImageIcon(entityType.cellElementType.getIcon().getImage());
        Image img = entityImg.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
        entityImg.setImage(img);
        JLabel entityP = new JLabel(entityImg);
        entityP.setSize(size, size);
        return entityP;
    }

    private JLabel bar() {
        updateBar();
        barLabel.setIcon(barIcon);
        return barLabel;
    }

    private void updateBar() {
        int length = (int) (size * (HP / (float) HPMax));
        Image image = barIcon.getImage().getScaledInstance(
                length == 0 ? 1 : length, 3, Image.SCALE_SMOOTH);
        barIcon.setImage(image);
    }

    private void setup() {
        setOpaque(false);
        if(!entityType.isHeroType() && !entityType.isMerchantType()) add(bar(), BorderLayout.NORTH);
        add(image());
        setSize(size, size);
        setLocation();
    }

    // Graphics
    public void setLocation() {
        super.setLocation(position.getX() * size, position.getY() * size);
    }


    public void checkPosition(Position p) throws ErrorPositionOutOfBound {
        if (!p.insideWorld()) throw new ErrorPositionOutOfBound(p);
    }

    public boolean moveEntity(Position p) throws ErrorPositionOutOfBound {
        CellElementType ct = entityType.cellElementType;
        WorldMap worldMap = WorldMap.getInstanceWorld();
        worldMap.getCell(position).entityLeft();
        boolean moved = position.nextPosition(p);
        setLocation();
        worldMap.getCell(position).setEntity(this);
        Cell currentCell = worldMap.getCell(position);
        if (this instanceof Player && currentCell.getBaseContent().equals(CellElementType.CORRIDOR))
            for (Position pos : position.calcRangePosition(2, false)) worldMap.getCell(pos).removeFog();
        if (ct.isHero() && currentCell.getItem() != null) {
            if(currentCell.getItem().immediateUse) {
                currentCell.getItem().setPosition(null);
                currentCell.getItem().use();
            }
            else if(Player.addItem()) GameWindow.addToLogs("You have found: " + currentCell.getItem() + "!", Tools.WindowText.golden);
            currentCell.getItem().setPosition(null);
            currentCell.heroPickItem();
            //GameWindow.refreshInventory();
        }
        return moved;
    }


    public boolean moveEntity(Move m) throws ErrorPositionOutOfBound {
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
        setLocation();
    }

    public void setPosition(Position p) {
        position.setPosition(p.getX(), p.getY());
    }

    public int getHP() {
        return HP;
    }

    public int getHPMax() {
        return HPMax;
    }

    public void setHPMax(int x) {
        HPMax = x;
        updateBar();
    }

    public void modifyHP(int health) {
        HP = Math.max(Math.min(HP + health, HPMax), 0);
        if(HP == 0) {
            removeEntity();
            if (this instanceof Monster) {
                GameWindow.addToLogs(this + " die!", Tools.WindowText.red);
                Player.getInstancePlayer().earnXP(entityType.experienceByType);
            }
        }
        //GameWindow.refreshInventory();
        updateBar();
    }
    public void fullHeal() {
        HP = HPMax;
    }

    public int getAttack() { return attack; }

    public void setAttack(int att) {
        attack = Math.max(att, 0);
    }

    public int getAttackMax() {
        return attackMax;
    }

    public void setAttackMax(int attMax) {
        attackMax = Math.max(attMax, 0);
    }

    public int getRange() {
        return range;
    }

    public void setRange(int r) {
        range = Math.max(r, 0);
    }

    public EntityState getState() {
        return state;
    }

    public void setState(EntityState state) {
        this.state = state;
        updateRemainingTime();
    }

    public void updateState(EntityState state) {
        if (state == null) return;
        setState(state);
        EntityState.immediateEffects(this);
        //GameWindow.refreshInventory();
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
        setLocation(-size, -size);
        worldMap.getCell(position).entityLeft();
    }

    public void takeDamage(int damage) {
        modifyHP(-damage);
    }

    public boolean withinReach(AbstractEntity entity, int range) {
        return Position.distance(this.getPosition(), entity.getPosition()) <= range;
    }

    public void applyStrategy() {
        if (HP > 0) strategy.applyStrategy();
    }

    public void goTo(Position p) {
        WorldMap worldMap = WorldMap.getInstanceWorld();
        worldMap.getCell(position).entityLeft();
        this.position = p;
        setLocation();
        worldMap.getCell(position).setEntity(this);
    }

    public boolean isHero() {
        return this instanceof Player;
    }

    public boolean isMonster() {
        return !isHero();
    }

    @Override
    public String toString() {
        return switch (entityType) {
            case HERO_ARCHER, HERO_MAGE, HERO_WARRIOR -> "HERO";
            default -> entityType.toString();
        };
    }
}
