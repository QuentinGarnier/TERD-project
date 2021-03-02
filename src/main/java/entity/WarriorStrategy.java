package entity;

import graphics.ColorStr;

public class WarriorStrategy extends AbstractPlayerStrategy {
    WarriorStrategy(){
        super(10, 100);
    }

    private void inhibitAttack(){ modifyAttack(2); }

    private void resetAttack(){ modifyAttack(10);}

    @Override
    public boolean attack(AbstractEntity entity) {
        if (entity == Player.getInstancePlayer()) return false;

        Monster monster = (Monster) entity;

        if (monster == null) {//monster not yet implemented
            System.out.println("Warrior : A vain blow (no monster in your range (> 1 <))");
            return false;
        }

        if (Player.getInstancePlayer().withinReach(monster, 1)){
            //monster.takeDamage(getAttack());
            return true;
        } else System.out.println("Warrior : A vain blow (no monster in your range (> 1 <))");
        return false;
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
                inhibitAttack();//here it is just the warriorstrategy attack which is modified, not player's one. //TODO : fix it
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
