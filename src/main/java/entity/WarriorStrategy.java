package entity;

import graphics.ColorStr;

public class WarriorStrategy implements PlayerStrategy {

    @Override
    public boolean attack(Monster monster) {
        System.out.println("[warrior] null monster");
        if (monster == null) return false;
        if (Player.getInstancePlayer().withinReach(monster, 1)){
            monster.takeDamage(10);
            return true;
        }
        return false;
    }

    @Override
    public void applyStateEffect() {
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
                System.out.println(ColorStr.magenta("You are suffering from poisoning (-1 HP and disorientation)"));//implement random move when move key is pressed (and his proba)
                break;
            case PARALYSED: break;
            default: ;break;
        }
    }
}
