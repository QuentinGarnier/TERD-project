package entity;

import graphics.ColorStr;

public class ArcherStrategy extends AbstractPlayerStrategy {
    private double arrowProba;

    public ArcherStrategy(){
        super(4, 50);
        arrowProba = 0.75;

    }

    private void inhibitAccuracy(){ arrowProba -= 0.25; }

    private void resetAccuracy(){ arrowProba += 0.25;}

    @Override
    public boolean attack(AbstractEntity entity) {
        if (entity == Player.getInstancePlayer()) return false;

        Monster monster = (Monster) entity;

        if (monster == null){//monster not yet implemented
            System.out.println("Archer : A vain archery (no monster in your range (> 5 <))");
            return false;
        }

        if (Player.getInstancePlayer().withinReach(monster, 5)) {
            if (Math.random() > arrowProba) { //to simulate a archer shooting
                //monster.takeDamage(getAttack());
                return true;
            } else System.out.println(ColorStr.red("Missed target"));
        } else System.out.println("Archer : A vain archery (no monster in your range (> 5 <))");
        return false;

    }

    @Override
    public void applyStateEffect() { // to call before the player action
        Player player = Player.getInstancePlayer();
        PlayerState state = player.getState();
        switch (state){//TODO : to complete
            case FROZEN: break;
            case BURNT:
                takeDamage(2);
                System.out.println(ColorStr.red("The burn inflicted on you 2 damages"));
                break;
            case POISONED:
                takeDamage(1);
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
