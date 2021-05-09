package entity;

import graphics.Language;
import graphics.Tools;
import graphics.elements.Move;
import graphics.elements.Position;
import graphics.elements.cells.Cell;
import graphics.elements.cells.CellElementType;
import graphics.map.WorldMap;
import graphics.window.GamePanel;
import graphics.window.GameWindow;
import items.AbstractItem;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

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
    private final JLabel image;
    private Animation animation;

    // MOVE WITH THREAD
    private final AtomicInteger counter;
    private final Timer timer;
    private final Point comparePosition;

    public AbstractEntity(Position position, int id, EntityType entityType)  {
        super();
        this.position = position;
        this.entityType = entityType;
        this.strategy = new Strategy(this);
        this.HP = entityType.isHeroType()? entityType.HPByType : entityType.HPByType /*+ WorldMap.stageNum*/ + Player.getInstancePlayer().getLvl() -2;
        this.HPMax = entityType.isHeroType()? entityType.HPByType : entityType.HPByType /*+ WorldMap.stageNum*/ + Player.getInstancePlayer().getLvl() - 2;
        this.attack = entityType.isHeroType()? entityType.attackByType : entityType.attackByType /*+ WorldMap.stageNum*/ + Player.getInstancePlayer().getLvl() - 2;
        this.attackMax = entityType.isHeroType()? entityType.attackByType : entityType.attackByType /*+ WorldMap.stageNum*/ + Player.getInstancePlayer().getLvl() - 2;
        this.range = entityType.rangeByType;
        this.state = EntityState.NEUTRAL;
        this.remainingTime = EntityState.NEUTRAL.getDuration();
        this.id = id;

        // Graphics
        setLayout(new BorderLayout());
        this.size = GamePanel.size * (entityType == EntityType.MONSTER_BOSS? 3 : 1);
        this.barLabel = new JLabel();
        this.barIcon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("data/images/interfaces/" + "bar_red.png")));
        this.image = new JLabel();

        barLabel.setHorizontalAlignment(SwingConstants.LEFT);
        setup();

        // MOVE WITH THREAD
        this.counter = new AtomicInteger();
        this.comparePosition = new Point(-1,-1);
        this.timer = new Timer(1, e -> {
            counter.getAndIncrement();
            myFun();
            setScrollBar();
            if (counter.get() == size) {
                ((Timer)e.getSource()).stop();
                counter.set(0);
            }
        });
    }

    private void myFun(){
        setLocation(getLocation().x + comparePosition.x , getLocation().y + comparePosition.y);
    }

    public void stopTimer(){
        if (timer != null) timer.stop();
    }

    private void image(Position p) {
        ImageIcon entityImg;
        if (p == null) entityImg = new ImageIcon(entityType.cellElementType.getIcon().getImage());
        else if (p.getX() < 0) entityImg = new ImageIcon(CellElementType.getBossIcon(false).getImage());
        else if (p.getX() > 0) entityImg = new ImageIcon(CellElementType.getBossIcon(true).getImage());
        else return;
        Image img = entityImg.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
        entityImg.setImage(img);
        image.setIcon(entityImg);
        image.repaint();
        image.revalidate();
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
        if (!setBossImage(null)) image(null);
        add(image);
        setSize(size, size);
        setLocation();
    }

    // Graphics
    protected void setLocation() {
        int realSize = GamePanel.size;
        int shift = entityType.equals(EntityType.MONSTER_BOSS) ? -1 : 0;
        if (position == null) super.setLocation(- size, - size);
        else {
            super.setLocation((position.getX() + shift) * realSize, (position.getY() + shift) * realSize);
            setScrollBar();
        }
        /*
        else {
            Point newLoc = new Point((position.getX() + shift) * realSize, (position.getY() + shift) * realSize);
            if ((Math.abs(newLoc.x - getLocation().x) == size && newLoc.y - getLocation().y == 0) ||
                    (Math.abs(newLoc.y - getLocation().y) == size && newLoc.x - getLocation().x == 0)) {
                comparePosition.setLocation(Integer.compare(newLoc.x, getLocation().x), Integer.compare(newLoc.y, getLocation().y));
                timer.start();
            }
            else {
                setLocation(newLoc);
                setScrollBar();
            }
        }
        */
    }

    public boolean listenerOn(){
        return this instanceof Player && counter.get() == 0;
    }

    private void setScrollBar(){
        if (this instanceof Player && Player.getInstancePlayer() != null
                && GameWindow.window != null && HP != 0)
            GameWindow.window.setScrollFrameBar();
    }

    public boolean moveEntity(Position p)  {
        CellElementType ct = entityType.cellElementType;
        WorldMap worldMap = WorldMap.getInstanceWorld();
        worldMap.getCell(position).entityLeft();
        boolean moved = position.nextPosition(p);
        setLocation();
        worldMap.getCell(position).setEntity(this);
        Cell currentCell = worldMap.getCell(position);
        if (this instanceof Player && currentCell.getBaseContent().equals(CellElementType.CORRIDOR))
            for (Position pos : position.calcRangePosition(Player.getInstancePlayer(),false, Player.getInstancePlayer().getRange())) worldMap.getCell(pos).removeFog();
        if (ct.isHero() && currentCell.getItem() != null) {
            AbstractItem ai = currentCell.getItem();
            if(ai.immediateUse) {
                ai.setPosition(null);
                ai.use();
                currentCell.heroPickItem();
            } else if(Player.addItem()) {
                GameWindow.addToLogs(Language.logGainItem(ai), Tools.WindowText.golden);
            }
        }
        return moved;
    }


    public boolean moveEntity(Move m)  {
        return moveEntity(m.getMove());
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
                GameWindow.addToLogs(Language.translate(this.entityType) + " " + Language.logDie() + "!", Tools.WindowText.red);
                Player.getInstancePlayer().earnXP(entityType.experienceByType);
                GamePanel.clearMonsterAttackRange();
            }
            if (this.entityType.equals(EntityType.MONSTER_BOSS)){
                Tools.gameEnd(Tools.Victory_Death.WIN);
            }
            if (this.entityType.isHeroType()){
                GameWindow.getGamePanel().setObjective();
            }
        }
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

    public void updateState(EntityState state) {
        if (state == null || getHP() == 0 || entityType == EntityType.MONSTER_BOSS && state.isNegatifState()) return;
        if (!state.equals(EntityState.NEUTRAL)) {
            if(animation != null) animation.end();
            animation = new Animation(this, state);
            animation.start();
        }
        else if(animation != null) animation.end();

        this.state = state;
        updateRemainingTime();
        EntityState.immediateEffects(this);
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
        if(animation != null) animation.end(); //Stop the animation thread if it's running
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
        if (HP > 0 && (HP < HPMax || (this instanceof Monster && ((Monster) this).canAttack()))) {
            strategy.applyStrategy();
        }
    }

    public void goTo(Position p) {
        WorldMap worldMap = WorldMap.getInstanceWorld();
        worldMap.getCell(position).entityLeft();
        GamePanel.clearMonsterAttackRange();
        this.position = p;
        setLocation();
        worldMap.getCell(position).setEntity(this);
    }

    public boolean setBossImage(Position p){
        if (entityType.equals(EntityType.MONSTER_BOSS)) {
            image(p != null ? p : new Position(position.getX() - Player.getInstancePlayer().getPosition().getX(), 0));
            return true;
        }
        return false;
    }

    public boolean isHero() {
        return this instanceof Player;
    }

    public boolean isMerchant() {
        return this instanceof Merchant;
    }

    public boolean isMonster() { return !isHero() && !isMerchant(); }

    @Override
    public String toString() {
        return switch (entityType) {
            case HERO_ARCHER, HERO_MAGE, HERO_WARRIOR -> Language.heroCP();
            default -> Language.translate(entityType);
        };
    }
}
