package entity;

import graphics.Tools;
import graphics.elements.Position;
import graphics.elements.Room;
import graphics.elements.cells.Cell;
import graphics.map.WorldMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Strategy {
    private AbstractEntity hero;
    private final AbstractEntity currentEntity;

    public Strategy(AbstractEntity currentEntity) {
        this.currentEntity = currentEntity;
    }

    public void applyStrategy() {
        if(currentEntity.getState() == EntityState.FROZEN && currentEntity.entityType != EntityType.MONSTER_BOSS) return;  //Boss cannot be frozen
        hero = Player.getInstancePlayer();
        switch (currentEntity.entityType) {
            case MONSTER_GOBLIN -> Goblin();
            case MONSTER_ORC, MONSTER_SPIDER -> OrcSpider();
            case MONSTER_WIZARD -> Wizard();
            case ALLY_MERCHANT -> Merchant();
            case MONSTER_BOSS -> Boss();
        }
    }

    private void Boss(){
        boolean isFled = false;
        boolean fleeMode = currentEntity.getHP() <= currentEntity.getHPMax() / 2;
        boolean isInvulnerable = currentEntity.getState() != EntityState.INVULNERABLE;
        if (!isInvulnerable) increaseHP(2);

        if (currentEntity.getPosition().distance(hero.getPosition()) > 6 && currentEntity.getHP() < currentEntity.getHPMax()) increaseHP(4);

        if (fleeMode) {
            if (!withinReachBossByType()) {
                increaseHP(isInvulnerable ? 4 : 8);
                return;
            }
            else {increaseHP(1); isFled = bossFleeHero();}
        }

        if (!isFled) {
            if (currentEntity.withinReach(hero, currentEntity.entityType.rangeByType)) Attack.attack(currentEntity, hero);
            else if (!fleeMode) goCloseHero();
            else increaseHP(isInvulnerable ? 4 : 8);
        }
    }

    private boolean withinReachBossByType() {
        Position pBoss = Monster.boss.getPosition();
        for (int x = pBoss.getX() - 1; x <= pBoss.getX() + 1; x++) {
            for (int y = pBoss.getY() - 1; y <= pBoss.getY() + 1; y++) {
                if (Position.distance(hero.getPosition(), new Position(x, y)) <= hero.getRange()) {
                    return true;
                }
            }
        }
        return false;
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

    public boolean bossFleeHero() {
        WorldMap wp = WorldMap.getInstanceWorld();
        List<Position> res = new ArrayList<>();
        for (Position pos : currentEntity.getPosition().getNeighbor(true)) { if (pos.getNeighbor(true).stream().filter(p -> (wp.getCell(p).isAccessible() || Monster.boss.equals(wp.getCell(p).getEntity()))).count() == 4) res.add(pos); }

        if (res.size() == 0) return false;
        Position pres = currentEntity.getPosition();
        double oldDist = pres.distance(hero.getPosition());
        for (Position ps : res){
            if (!wp.getCell(ps).isDoor()) {
                double currentDist = ps.distance(hero.getPosition());
                if (currentDist > oldDist) {
                    pres = ps;
                    oldDist = currentDist;
                }
            }
        }
        if (!currentEntity.getPosition().equals(pres)) {currentEntity.goTo(pres); return true;}
        return false;
    }

    public void goCloseHero() {
        if(currentEntity.getState() == EntityState.FROZEN) return;
        Position pos = currentEntity.getPosition();
        Room r = WorldMap.getInstanceWorld().getCurrentRoom(pos);
        List<List<Position>> bfs = Tools.BFS(pos, r, null, null);
        List<Position> path = Tools.findPath(bfs, pos, Player.getInstancePlayer().getPosition(), r, null, null);
        if (path.size() < 2) return;
        Position res = path.get(path.size() - 2);
        Cell c = WorldMap.getInstanceWorld().getCell(res);
        if (c.isAccessible() || (c.getEntity().entityType.equals(EntityType.MONSTER_BOSS) && res.bossCanMove())) currentEntity.goTo(res);
    }

    public void increaseHP(int x) {
        currentEntity.modifyHP(x);
    }
}
