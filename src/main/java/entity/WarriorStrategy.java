package entity;

import graphics.ColorStr;

public class WarriorStrategy extends AbstractPlayerStrategy {
    WarriorStrategy(){
        super(10, 100, 1);
    }

    private void inhibitAttack(){ modifyAttack(2); }

    private void resetAttack(){ modifyAttack(10);}

    @Override
    public boolean attack(AbstractEntity entity) {
        Player player = Player.getInstancePlayer();

        if (entity == player) return false;

        Monster monster = (Monster) entity;

        if (monster == null) return false;

        if (player.withinReach(monster, getRange())){
            monster.takeDamage(getAttack()); return true;
        } else System.out.printf("Warrior : A vain blow (no monster in your range (> %d <))", getRange()); return false;
    }

    @Override
    public void applyStateEffect() {
        Player player = Player.getInstancePlayer();
        PlayerState state = player.getState();
        switch (state){//TODO : to complete
            case FROZEN:
                //add effect
                System.out.println(ColorStr.blue("The freezes immobilizes you"));
                break;
            case BURNT:
                takeDamage(2);
                System.out.println(ColorStr.red("The burn inflicted on you 2 damages"));
                break;
            case POISONED:
                takeDamage(1);
                player.modifyHunger(-1);
                System.out.println(ColorStr.magenta("You are suffering from poisoning (-1 HP, -1 Hunger and disorientation)"));//implement random move when move key is pressed (and his proba)
                break;
            case PARALYSED:
                inhibitAttack();
                System.out.println(ColorStr.yellow("You are paralized (your attack is less strong)" ));
                break;
            default:
                resetAttack();
                break;
        }
    }


    @Override
    public String toString() {
        return ColorStr.red(ColorStr.encircled(" Specialty : Warrior "));
    }
}
