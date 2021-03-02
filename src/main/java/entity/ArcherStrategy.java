package entity;

import graphics.ColorStr;

public class ArcherStrategy implements PlayerStrategy {
    private double arrowProba;

    public ArcherStrategy(){
        arrowProba = 0.75;
    }

    private void inhibitAccuracy(){ arrowProba -= 0.25; }

    private void resetAccuracy(){ arrowProba += 0.25;}

    @Override
    public boolean attack(Monster monster) {

        if (monster == null){//monster not yet implemented
            System.out.println(ColorStr.encircled(" Specialty : Archer ") + " A vain archery (no monster within your reach");
            return false;
        }

        if (Player.getInstancePlayer().withinReach(monster, 5)) {
            if (Math.random() > arrowProba) { //to simulate a archer shooting
                monster.takeDamage(4);
                return true;
            } else System.out.println(ColorStr.red("Missed target"));
        } else System.out.println(ColorStr.encircled(" Specialty : Archer ") + " A vain archery (no monster within your reach");
        return false;

    }

    @Override
    public void applyStateEffect() { // to call before the player action
        Player player = Player.getInstancePlayer();
        PlayerState state = player.getState();
        switch (state){//TODO : to complete
            case FROZEN: break;
            case BURNT:
                player.takeDamage(2);
                System.out.println(ColorStr.red("The burn inflicted on you 2 damages"));
                break;
            case POISONED:
                player.takeDamage(1);
                inhibitAccuracy();
                System.out.println(ColorStr.magenta("You are suffering from poisoning (-1 HP and decreased accuracy)"));
                break;
            case PARALYSED: break;
            default: resetAccuracy(); break;
        }
    }

    @Override
    public String toString() {
        return ColorStr.green(ColorStr.encircled(" Specialty : Archer "));
    }
}
