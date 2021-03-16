package entity;

import graphics.elements.Position;

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

    public void makeMove(boolean goClose, Position p){
        Position[] neighbors = currentEntity.getPosition().getNeighbor(false);
        if (neighbors.length == 0) return;
        Position res = neighbors[0];
        double oldDist = res.distance(p);
        for (Position ps : neighbors){
            double currentDist = ps.distance(p);
            if (goClose ? currentDist < oldDist : currentDist > oldDist){
                res = ps;
                oldDist = currentDist;
            }
        }
        currentEntity.goTo(res);
    }

    public void fleeHero(){
        makeMove(false, hero.getPosition());
    }

    public void goCloseHero(){
        makeMove(true, hero.getPosition());
    }

    public void increaseHP(int x){ currentEntity.modifyHP(x); }
}
