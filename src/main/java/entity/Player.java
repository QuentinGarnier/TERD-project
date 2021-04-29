package entity;

import graphics.Language;
import graphics.Tools;
import graphics.elements.ErrorPositionOutOfBound;
import graphics.elements.Move;
import graphics.elements.Position;
import graphics.elements.cells.Cell;
import graphics.elements.cells.CellElementType;
import graphics.map.WorldMap;
import graphics.window.GameWindow;
import items.collectables.AbstractCollectableItem;
import items.AbstractItem;
import items.collectables.ItemEquip;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Player extends AbstractEntity {
    private static Player instancePlayer = new Player();

    public final static int MAX_INVENTORY_SIZE = 20;
    private int hungerTurnCounter;
    private int level;
    private int experiencePoints;
    private int hunger;
    private final List<AbstractCollectableItem> inventory;
    private ItemEquip attackItem;
    private ItemEquip defenseItem;
    private int money;
    private WhatHeroDoes whatHeroDoes;
    private Position attackPosition;

    private Player(Position p, EntityType speciality) throws ErrorPositionOutOfBound {
        super(p, -1, speciality);
        level = 1;
        experiencePoints = 0;
        hunger = 100;  //100 is the max value for the Hunger Bar
        hungerTurnCounter = 0;
        inventory = new ArrayList<>();
        money = 0;
        whatHeroDoes = WhatHeroDoes.MOVING;
    }

    private Player(EntityType speciality) throws ErrorPositionOutOfBound {
        this(new Position(0, 0), speciality);
    }

    private Player() throws ErrorPositionOutOfBound {
        this(EntityType.HERO_WARRIOR);
    }

    public void restorePlayer() {
        inventory.clear();
        experiencePoints = 0;
        level = 1;
        hunger = 100;
        money = 0;
        attackItem = null;
        defenseItem = null;
        whatHeroDoes = WhatHeroDoes.MOVING;
        setHPMax(entityType.HPByType);
        modifyHP(entityType.HPByType);
        setAttack(entityType.attackByType);
        setAttackMax(entityType.attackByType);
        modifyHunger(100, false);
        updateState(EntityState.NEUTRAL);
        Merchant.getInstanceMerchant().generateMarket();
        Merchant.SellPanel.sellPanel.clearSell();
        WorldMap.stageNum = 0;
        Monster.boss.modifyHP(Monster.boss.getHPMax());
        Monster.boss.updateState(EntityState.NEUTRAL);
    }

    public WhatHeroDoes getWhatHeroDoes() {
        return whatHeroDoes;
    }

    public void setWhatHeroDoes(WhatHeroDoes whatHeroDoes) {
        this.whatHeroDoes = whatHeroDoes;
    }

    public static Player getInstancePlayer() {
        return instancePlayer;
    }

    public static List<AbstractCollectableItem> getInventory() {
        return Collections.unmodifiableList(instancePlayer.inventory);
    }

    public void earnXP(int xp) {
        int rate = (100 + (level - 1) * 50);
        experiencePoints += xp;
        GameWindow.addToLogs("+" + xp + " XP.", Tools.WindowText.green);
        while (experiencePoints/rate >= 1){
            experiencePoints -= rate;
            levelUp();
        }
    }

    public int getLvl() {
        return level;
    }

    public int getXP() {
        return experiencePoints;
    }

    public int getMaxXP() {
        return (100 + (level - 1) * 50);
    }

    public void levelUp() {
        level ++;
        GameWindow.addToLogs(Language.logLevelUp(), Tools.WindowText.green);
        setAttackMax((int) Math.round((getAttackMax() * 1.08 )));
        setAttack(getAttackMax() + (getState() == EntityState.ENRAGED ? 10 : 0));
        setHPMax((int) Math.round((getHPMax() *  1.07)));
        fullHeal();
        if (!EntityState.isBeneficial(getState())) updateState(EntityState.NEUTRAL);
    }

    public int getMoney() {
        return money;
    }

    public EntityState getState() {
        return super.getState();
    }

    public void modifyMoney(int x) {
        money = Math.max(money + x, 0);
        Merchant.BuyPanel.buyPanel.updateMarket();
    }

    public boolean enoughMoney(int cost) {
        return money >= cost;
    }

    public boolean throwItem(AbstractCollectableItem ai) {
        Cell currentCell = WorldMap.getInstanceWorld().getCell(getPosition());
        if (currentCell.getItem() != null) {
            GameWindow.addToLogs(Language.logCantDropItem(), Color.RED);
            return false;
        }
        else {
            if (ai instanceof ItemEquip && ((ItemEquip) ai).isEquipped())
                ai.use();
            currentCell.setItem(ai);
            ai.setPosition(getPosition());
            inventory.remove(ai);
            Merchant.SellPanel.sellPanel.removeSellInventory(ai);
            GameWindow.addToLogs("Vous avez jeté " + ai + ".", Tools.WindowText.golden);
            GameWindow.refreshInventory(false);
        }
        return true;
    }

    public static boolean addItem() {
        if (instancePlayer.inventory.size() < MAX_INVENTORY_SIZE) {
            Cell c = WorldMap.getInstanceWorld().getCell(Player.getInstancePlayer().getPosition());
            AbstractItem item = c.getItem();
            if (item instanceof AbstractCollectableItem){
                item.setPosition(null);
                instancePlayer.inventory.add((AbstractCollectableItem) item);
                c.heroPickItem();
                Merchant.SellPanel.sellPanel.addSellInventory((AbstractCollectableItem) item);
            }
            return true;
        } else {
            GameWindow.addToLogs(Language.logInventoryFull(), Color.RED);
            return false;
        }
    }

    public static void addItem(AbstractCollectableItem ai) {
        instancePlayer.inventory.add(ai);
    }

    public static void removeItem(AbstractCollectableItem ai) {
        instancePlayer.inventory.remove(ai);
        if (ai instanceof ItemEquip && ((ItemEquip) ai).isEquipped()) ai.use();
        Merchant.SellPanel.sellPanel.removeSellInventory(ai);
    }

    public int getHunger() {
        return hunger;
    }

    public String getHungerState() {
        if(hunger > 75) return "Sated";        //100 to 76
        else if(hunger > 50) return "Peckish"; //75 to 51
        else if(hunger > 30) return "Hungry";  //50 to 31
        else return "Starving";                //30 to 0 (0 = you die)
    }

    //Hunger is capped at 100.
    public void modifyHunger(int x, boolean toDisplayMsg) {
        hunger = Math.max(Math.min(hunger + x, 100), 0);
        if (toDisplayMsg && (x >= 0)) GameWindow.addToLogs(Language.logModifyHunger(x), Tools.WindowText.purple);
        if (hunger == 0) {
            modifyHP(-getHPMax());
            Tools.gameEnd(Tools.Victory_Death.DEATH_BY_HUNGER);
        }
    }

    public static void chooseSpeciality(int sp) {
        int x = instancePlayer.getPosition().getX();
        int y = instancePlayer.getPosition().getY();
        Position p = new Position(x, y);
        instancePlayer = switch(sp) {
            case 1 -> new Player(p, EntityType.HERO_ARCHER);
            case 2 -> new Player(p, EntityType.HERO_MAGE);
            default -> new Player(p, EntityType.HERO_WARRIOR);
        };
    }

    @Override
    public void setAttack(int att) {
        super.setAttack(att);
    }

    @Override
    public void decrementRemainingTime() {
        super.decrementRemainingTime();
    }

    private void moveMonsters() {
        WorldMap worldMap = WorldMap.getInstanceWorld();
        Cell cell = worldMap.getCell(getPosition());
        if (cell.getBaseContent().equals(CellElementType.EMPTY)) {
            worldMap.getRooms().get(cell.getBaseId()).getMonsters().forEach(e -> {
                if (getHP() != 0) {
                    e.applyStrategy();
                    EntityState.turnEffects(e);
                }
            });
        }
        if (getHP() == 0 && getHunger() > 0) Tools.gameEnd(Tools.Victory_Death.DEATH_BY_HP);
    }

    private void moveMerchant() {
        WorldMap worldMap = WorldMap.getInstanceWorld();
        Cell cell = worldMap.getCell(getPosition());
        if (cell.getBaseId() == Merchant.getInstanceMerchant().getSafeRoomId() && (!cell.getBaseContent().equals(CellElementType.CORRIDOR))) Merchant.getInstanceMerchant().applyStrategy();
    }

    public void makeAction(boolean isAttacking, Move m, Position p) throws ErrorPositionOutOfBound {
        boolean b;
        if (getState().equals(EntityState.FROZEN)) {
            GameWindow.addToLogs(Language.logYouAreFrozen(), Color.CYAN);
            b = true;
        } else {
            if ((m == null && p == null) || getHP() == 0) return;
            b = isAttacking ? attack(p) : move(m);
        }
        if(b) {
            hungerTurnCounter++;
            if(hungerTurnCounter >= 5) {
                modifyHunger(-1, false);
                hungerTurnCounter = 0;
            }
            moveMonsters();
            moveMerchant();
            EntityState.turnEffects(this);
        }
    }

    private boolean move(Move move) throws ErrorPositionOutOfBound {
        WorldMap worldMap = WorldMap.getInstanceWorld();
        Cell oldCell = worldMap.getCell(getPosition());
        if (moveEntity(move)) {
            Cell currentCell = worldMap.getCell(getPosition());
            if (currentCell.getBaseContent().equals(CellElementType.END)) return false;
            if (!currentCell.equals(oldCell)){
                if (currentCell.getBaseContent().equals(CellElementType.CORRIDOR)){
                    worldMap.getCorridors().get(currentCell.getBaseId()).setVisited();
                } else {
                    worldMap.getRooms().get(currentCell.getBaseId()).setVisited();
                }
            }
            return true;
        }
        return false;
    }

    private boolean attack(Position position) {
        WorldMap worldMap = WorldMap.getInstanceWorld();
        Cell cell = worldMap.getCell(position);
        if (cell.getEntity() instanceof Merchant) {
            Merchant.getInstanceMerchant().openMarket();
            return false;
        }
        if (cell.getEntity() instanceof Monster && Position.distance(getPosition(), position) <= getRange()) {
            Monster m = (Monster) cell.getEntity();
            Attack.attack(this, m);
            if (attackItem != null && m.getHP() != 0 && entityType != EntityType.HERO_ARCHER) attackItem.applyEffect(m);
            return true;
        }
        return false;
    }

    public void setAttackItem(ItemEquip attackItem) {
        this.attackItem = attackItem;
    }

    public void setDefenceItem(ItemEquip defenceItem) {
        this.defenseItem = defenceItem;
    }

    public ItemEquip getAttackItem() {
        return attackItem;
    }

    public ItemEquip getDefenceItem() {
        return defenseItem;
    }

    public int getDefense() {
        return defenseItem == null ? 0 : defenseItem.getEffectInt();
    }

    @Override
    public int getAttack() { return super.getAttack() + (attackItem == null ? 0 : attackItem.getEquipmentType().getEffectInt()); }

    public void restoreAttackPos(){
        attackPosition = getPosition();
    }

    public Position getAttackPosition() {
        return attackPosition;
    }

    public void setAttackPosition(Position p){
        this.attackPosition = p;
    }

    @Override
    protected void setLocation() {
        restoreAttackPos();
        super.setLocation();
    }

    public enum WhatHeroDoes {
        MOVING, CHOOSING_ATTACK, ATTACKING;
    }
}
