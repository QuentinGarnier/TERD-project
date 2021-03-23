package entity;

import graphics.Tools;
import graphics.elements.ErrorPositionOutOfBound;
import graphics.elements.Move;
import graphics.elements.Position;
import graphics.elements.Room;
import graphics.elements.cells.Cell;
import graphics.elements.cells.CellElementType;
import graphics.map.WorldMap;
import graphics.window.GameWindow;
import items.AbstractItem;
import items.ItemEquip;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Player extends AbstractEntity {
    private static Player instancePlayer;

    static {
        try {
            instancePlayer = new Player();
        } catch (ErrorPositionOutOfBound errorPositionOutOfBound) {
            errorPositionOutOfBound.printStackTrace();
        }
    }

    public final static int MAX_INVENTORY_SIZE = 10;
    private int level;
    private int experiencePoints;
    private int hunger;
    private final List<AbstractItem> inventory;
    private ItemEquip attackItem;
    private ItemEquip defenceItem;
    private int money;
    private WhatHeroDoes whatHeroDoes;

    private Player(Position p, EntityType speciality) throws ErrorPositionOutOfBound {
        super(p, -1, speciality);
        level = 1;
        experiencePoints = 0;
        hunger = 100;  //100 is the max value for the Hunger Bar
        inventory = new ArrayList<>();
        money = 0;
        whatHeroDoes = WhatHeroDoes.MOVING;
    }

    private Player(EntityType speciality) throws ErrorPositionOutOfBound {
        this(new Position(0, 0), EntityType.HERO_WARRIOR);
    }

    private Player() throws ErrorPositionOutOfBound {
        this(EntityType.HERO_WARRIOR);
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

    public static List<AbstractItem> getInventory() {
        return Collections.unmodifiableList(instancePlayer.inventory);
    }

    public void pickElement(Position p) {
        WorldMap worldMap = WorldMap.getInstanceWorld();
        Cell c = worldMap.getCell(p);
        if(c.getItem() != null) {
            Room r = worldMap.getRooms().get(c.getBaseId());
            inventory.add(r.getItems().get(c.getItemId()));
        }
        else System.out.println("Nothing to pick");
    }

    public void earnXP(int xp) {
        int rate = (100 + (level - 1) * 50);
        experiencePoints += xp;
        GameWindow.addToLogs("+" + xp + " xp", Tools.WindowText.green);
        while (experiencePoints/rate >= 1){
            experiencePoints -= rate;
            levelUp();
        }
    }

    public int getLvl() {
        return level;
    }

    public void levelUp() {
        level ++;
        GameWindow.addToLogs(">>> LEVEL UP +1! <<<", Tools.WindowText.green);
        setAttackMax((int) (getAttackMax() * 1.08));
        setAttack(getAttackMax());
        setHPMax((int) (getHPMax() *  1.07));
        fullHeal();
        updateState(EntityState.NEUTRAL);
    }

    public int getMoney() {
        return money;
    }

    public EntityState getState() {
        return super.getState();
    }

    public boolean spendMoney(int cost) {
        if (cost > money) {
            System.out.println(Tools.TerminalText.red("Not enough money"));
            return false;
        }
        money -= cost;
        return true;
    }

    public void throwItem(int index) {
        AbstractItem ai = inventory.get(index);
        Cell currentCell = WorldMap.getInstanceWorld().getCell(getPosition());
        if (currentCell.getItem() != null) GameWindow.addToLogs("Can't drop item here.", Color.RED);
        else {
            currentCell.setItem(ai);
            inventory.remove(index);
            GameWindow.refreshInventory();
        }
    }

    public static void addItem() {
        if (instancePlayer.inventory.size() < MAX_INVENTORY_SIZE) {
            AbstractItem item = WorldMap.getInstanceWorld().getCell(getInstancePlayer().getPosition()).getItem();
            if (item != null){
                item.setPosition(null);
                instancePlayer.inventory.add(item);
                item.setLocation();
            }
        } else {
            GameWindow.addToLogs("!! Full inventory !!", Color.RED);
        }
    }

    public void incrementMoney(int x) {
        money += x;
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
    public void modifyHunger(int x) {
        hunger = Math.max(Math.min(hunger + x, 100), 0);
        if (getState() != EntityState.POISONED || x >= 0) GameWindow.addToLogs("You " + (x >= 0 ? "gain " + x : "lose " + (-x)) + " Hunger point" + (x > 1 || x < -1 ? "s" : "") + ".", Tools.WindowText.purple);
        if (hunger == 0) GameWindow.addToLogs("HERO DIED OF HUNGER", Color.RED);
        GameWindow.refreshInventory();
        //TODO: add death by hunger
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
        GameWindow.refreshInventory();
    }

    @Override
    public void decrementRemainingTime() {
        super.decrementRemainingTime();
        GameWindow.refreshInventory();
    }

    public boolean notFrozen() {
        return getState() != EntityState.FROZEN;
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
        if (getHP() == 0) GameWindow.addToLogs("HERO IS DEAD", Color.RED);
    }

    public boolean makeAction(boolean isAttacking, Move m, Position p) throws ErrorPositionOutOfBound {
        if((m == null && p == null) || getHP() == 0) return false;
        return isAttacking ? attack(p) : move(m);
    }

    private boolean move(Move move) throws ErrorPositionOutOfBound {
        WorldMap worldMap = WorldMap.getInstanceWorld();
        if(notFrozen()) {
            Cell oldCell = worldMap.getCell(getPosition());
            if (moveEntity(move)) {
                Cell currentCell = worldMap.getCell(getPosition());
                if (currentCell.getBaseContent().equals(CellElementType.END)) return false;
                whatHeroDoes.setP(getPosition());
                moveMonsters();
                EntityState.turnEffects(this);
                if (!currentCell.equals(oldCell)){
                    if (currentCell.getBaseContent().equals(CellElementType.CORRIDOR)){
                        worldMap.getCorridor().get(currentCell.getBaseId()).setVisited();
                    } else {
                        worldMap.getRooms().get(currentCell.getBaseId()).setVisited();
                    }
                }
                return true;
            }
            return false;
        }
        moveMonsters();
        EntityState.turnEffects(this);
        return true;
    }

    private boolean attack(Position position) {
        WorldMap worldMap = WorldMap.getInstanceWorld();
        Cell cell = worldMap.getCell(position);
        if (cell.getEntity() instanceof Monster && Position.distance(getPosition(), position) <= getRange()) {
            Monster m = (Monster) cell.getEntity();
            if (attackItem != null) attackItem.applyEffect(m);
            Attack.attack(this, m);
            moveMonsters();
            EntityState.turnEffects(this);
            return true;
        }
        return false;
    }

    public void setAttackItem(ItemEquip attackItem) {
        this.attackItem = attackItem;
    }

    public void setDefenceItem(ItemEquip defenceItem) {
        this.defenceItem = defenceItem;
    }

    public ItemEquip getAttackItem() {
        return attackItem;
    }

    public ItemEquip getDefenceItem() {
        return defenceItem;
    }

    @Override
    public int getAttack() {
        return super.getAttack() + (attackItem == null ? 0 : attackItem.getEquipmentType().getEffect());
    }
}
