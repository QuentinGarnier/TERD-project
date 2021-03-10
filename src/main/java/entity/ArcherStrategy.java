package entity;

import graphics.ColorStr;

public class ArcherStrategy extends AbstractPlayerStrategy {
    private double arrowProba;

    public ArcherStrategy(){
        super(4, 50, 5);
        arrowProba = 0.75;

    }

    private void inhibitAccuracy(){ arrowProba -= 0.25; }

    private void resetAccuracy(){ arrowProba += 0.25;}

    @Override
    public boolean attack(AbstractEntity entity) {
        Player player = Player.getInstancePlayer();

        if (entity == Player.getInstancePlayer()) return false;

        Monster monster = (Monster) entity;

        if (monster == null) return false;

        if (Player.getInstancePlayer().withinReach(monster, getRange())) {
            if (Math.random() > arrowProba) { //to simulate a archer shooting
                monster.takeDamage(getAttack());
                return true;
            } else System.out.println(ColorStr.red("Missed target"));
        } else System.out.printf("Archer : A vain archery (no monster in your range (> % <))", getRange());
        return false;

    }

    @Override
    public void applyStateEffect() {
        Player player = Player.getInstancePlayer();
        PlayerState state = player.getState();
        switch (state){//TODO : to complete
            case FROZEN://add effect
                System.out.println(ColorStr.blue("The freezes immobilizes you"));
                break;
            case BURNT:
                takeDamage(2);
                System.out.println(ColorStr.red("The burn inflicted on you 2 damages"));
                break;
            case POISONED:
                takeDamage(1);
                inhibitAccuracy();
                System.out.println(ColorStr.magenta("You are suffering from poisoning (-1 HP and decreased accuracy)"));
                break;
            case PARALYSED:
                System.out.println(ColorStr.yellow("You are paralized" ));
                break;
            default: resetAccuracy(); break;
        }
    }

    @Override
    public String toString() {
        return ColorStr.green(ColorStr.encircled(" Specialty : Archer "));
    }
}
