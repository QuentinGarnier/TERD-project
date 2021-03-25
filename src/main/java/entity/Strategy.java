package entity;

import graphics.elements.Position;
import graphics.map.WorldMap;

import java.util.Arrays;
import java.util.Random;

public class Strategy {
    private final AbstractEntity hero = Player.getInstancePlayer();
    private final AbstractEntity currentEntity;

    public Strategy(AbstractEntity currentEntity) {
        this.currentEntity = currentEntity;
    }

    public void applyStrategy(){
        switch (currentEntity.entityType){
            case MONSTER_GOBLIN -> Goblin();
            case MONSTER_ORC, MONSTER_SPIDER -> OrcSpider();
            case MONSTER_WIZARD -> Wizard();
            case ALLY_MERCHANT -> Merchant();
        }
    }

    private void Goblin() {
        boolean b = false;
        boolean fleeMode = currentEntity.getHP() <= currentEntity.getHPMax() / 2;
        if (fleeMode) {
            if (!currentEntity.withinReach(hero, 2)) increaseHP(2);
            else b = fleeHero();
        }
        if (!b) {
            if (currentEntity.withinReach(hero, 1)) Attack.attack(currentEntity, hero);
            else if (!fleeMode) goCloseHero();
            else increaseHP(2);
        }
    }

    private void OrcSpider() {
        if (currentEntity.withinReach(hero, 1)) Attack.attack(currentEntity, hero);
        else goCloseHero();
    }

    private void Wizard() {
        boolean b = false;
        if (currentEntity.withinReach(hero, 1)) b = fleeHero();
        if(!b) {
            if (!currentEntity.withinReach(hero, 3)) goCloseHero();
            else Attack.attack(currentEntity, hero);
        }
    }

    private void Merchant() {
        if ((Merchant.getInstanceMerchant().isMoving())) makeRandomMove();
        Merchant.getInstanceMerchant().updateMoving();
    }

    public boolean makeMove(boolean goClose, Position p) {
        Position[] neighbors = currentEntity.getPosition().getNeighbor(false);
        WorldMap worldMap = WorldMap.getInstanceWorld();

        if (neighbors.length == 0) return false;
        Position res = currentEntity.getPosition();
        double oldDist = res.distance(p);
        for (Position ps : neighbors){
            if (!worldMap.getCell(ps).isDoor()) {
                double currentDist = ps.distance(p);
                if (goClose ? currentDist < oldDist : currentDist > oldDist) {
                    res = ps;
                    oldDist = currentDist;
                }
            }
        }
        currentEntity.goTo(res);
        return !p.equals(res);
    }

    public void makeRandomMove(){
        Random rd = new Random();
        WorldMap wp = WorldMap.getInstanceWorld();
        Position[] neighbors = currentEntity.getPosition().getNeighbor(false);
        neighbors = Arrays.stream(neighbors).filter(p -> !wp.getCell(p).isDoor()).toArray(Position[]::new);
        Position[] neighborsTemp = neighbors;

        if(!wp.getCell(currentEntity.getPosition()).isDoor()){
            neighbors = Arrays.stream(neighbors).filter(p -> !Position.isBlockingPosition(p)).toArray(Position[]::new);
            if (neighbors.length == 0 && Position.isBlockingPosition(currentEntity.getPosition())) neighbors = neighborsTemp;
        }

        if (neighbors.length == 0) return;
        Position rndPos = neighbors[rd.nextInt(neighbors.length)];
        currentEntity.goTo(rndPos);

    }

    public boolean fleeHero() {
        return makeMove(false, hero.getPosition());
    }

    public void goCloseHero() {
        makeMove(true, hero.getPosition());
    }

    public void increaseHP(int x) {
        currentEntity.modifyHP(x);
    }
}
