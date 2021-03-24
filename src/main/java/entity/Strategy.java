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
        if (currentEntity.getHP() > currentEntity.getHPMax() / 2 ) {
            if (currentEntity.withinReach(hero, 1)) Attack.attack(currentEntity, hero);
            else goCloseHero();
        } else {
            if (!currentEntity.withinReach(hero, 2)) increaseHP(2);
            else fleeHero();
        }
    }

    private void OrcSpider(){
        if (currentEntity.withinReach(hero, 1)) Attack.attack(currentEntity, hero);
        else goCloseHero();
    }

    private void Wizard(){
        if (currentEntity.withinReach(hero, 1)) fleeHero();
        else if (!currentEntity.withinReach(hero, 3)) goCloseHero();
        else Attack.attack(currentEntity, hero);
    }

    private void Merchant(){
        if ((Merchant.getInstanceMerchant().isMoving())) makeRandomMove();
        //Merchant.getInstanceMerchant().updateMoving();
    }

    public void makeMove(boolean goClose, Position p){
        Position[] neighbors = currentEntity.getPosition().getNeighbor(false);
        WorldMap worldMap = WorldMap.getInstanceWorld();

        if (neighbors.length == 0) return;
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

    public void fleeHero(){
        makeMove(false, hero.getPosition());
    }

    public void goCloseHero(){
        makeMove(true, hero.getPosition());
    }

    public void increaseHP(int x){ currentEntity.modifyHP(x); }
}
