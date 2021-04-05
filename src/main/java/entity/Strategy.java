package entity;

import graphics.Tools;
import graphics.elements.Position;
import graphics.elements.Room;
import graphics.elements.cells.Cell;
import graphics.map.WorldMap;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Strategy {
    private AbstractEntity hero;
    private final AbstractEntity currentEntity;

    public Strategy(AbstractEntity currentEntity) {
        this.currentEntity = currentEntity;
    }

    public void applyStrategy(){
        hero = Player.getInstancePlayer();
        switch (currentEntity.entityType){
            case MONSTER_GOBLIN -> Goblin();
            case MONSTER_ORC, MONSTER_SPIDER -> OrcSpider();
            case MONSTER_WIZARD -> Wizard();
            case ALLY_MERCHANT -> Merchant();
            case MONSTER_BOSS -> Boss();
        }
    }

    private void Boss(){
        if (currentEntity.withinReach(hero, 3)) Attack.attack(currentEntity, hero);
        else goCloseHero();
    }

    private void Goblin() {
        boolean b = false;
        boolean fleeMode = currentEntity.getHP() <= currentEntity.getHPMax() / 2;
        if (fleeMode) {
            if (!currentEntity.withinReach(hero, currentEntity.entityType.rangeByType)) increaseHP(2);
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
        if(currentEntity.getState() == EntityState.FROZEN) return false;
        List<Position> neighbors = currentEntity.getPosition().getNeighbor(false);
        WorldMap worldMap = WorldMap.getInstanceWorld();
        if (neighbors.size() == 0) return false;
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
        List<Position> neighbors = currentEntity.getPosition().getNeighbor(false);
        neighbors = neighbors.stream().filter(p -> !wp.getCell(p).isDoor()).collect(Collectors.toList());
        List<Position> neighborsTemp = neighbors;

        if(!wp.getCell(currentEntity.getPosition()).isDoor()){
            neighbors = neighbors.stream().filter(p -> !Position.isBlockingPosition(p)).collect(Collectors.toList());
            if (neighbors.size() == 0 && Position.isBlockingPosition(currentEntity.getPosition())) neighbors = neighborsTemp;
        }

        if (neighbors.size() == 0) return;
        Position rndPos = neighbors.get(rd.nextInt(neighbors.size()));
        List<Position> playerNeighbor = Player.getInstancePlayer().getPosition().getNeighbor(true);
        if (playerNeighbor.size() == 1 && rndPos.equals(playerNeighbor.get(0))) makeRandomMove();
        else currentEntity.goTo(rndPos);
    }

    public boolean fleeHero() {
        return makeMove(false, hero.getPosition());
    }
    public void goCloseHero() {
        if(currentEntity.getState() == EntityState.FROZEN) return;
        Position pos = currentEntity.getPosition();
        Room r = WorldMap.getInstanceWorld().getCurrentRoom(pos);
        List<List<Position>> bfs = Tools.BFS(pos, r, null, null);
        List<Position> path = Tools.findPath(bfs, pos, Player.getInstancePlayer().getPosition(), r, null, null);
        Position res = path.get(path.size() - 2);
        Cell c = WorldMap.getInstanceWorld().getCell(res);
        if (c.isAccessible() || c.getEntity().entityType.equals(EntityType.MONSTER_BOSS)) currentEntity.goTo(res);
    }

    public void increaseHP(int x) {
        currentEntity.modifyHP(x);
    }
}
