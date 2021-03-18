package items;

import entity.EntityState;
import entity.Player;
import graphics.Tools;
import graphics.elements.Position;
import graphics.map.WorldMap;
import graphics.window.GameWindow;

import java.awt.*;
import java.util.Random;

public class ItemTrap extends AbstractItem{

    private final int effect;
    private final Random rndTrap = new Random();

    ItemTrap(int i, String n, Position position) {
        super(i, n, ItemType.TRAP, position, true);
        effect = 3;
        //this.effect = rndTrap.nextInt(5);
    }

    @Override
    public boolean use() {
        Player player = Player.getInstancePlayer();
        switch (effect){
            case 0: GameWindow.addToLogs("You stepped on a burning trap!", Tools.WindowText.orange); player.updateState(EntityState.BURNT); break;
            case 1: GameWindow.addToLogs("An intense surprise freezes!", Tools.WindowText.cyan); player.updateState(EntityState.FROZEN); break;
            case 2: GameWindow.addToLogs("A poisonous trap!", Tools.WindowText.purple); player.updateState(EntityState.POISONED); break;
            case 3: GameWindow.addToLogs("You got caught by a teleporter trap!", Color.WHITE); WorldMap.getInstanceWorld().placePlayer(); break;
            case 4: GameWindow.addToLogs("A bomb was planted here! [-15 HP]", Tools.WindowText.red); player.takeDamage(15); break;
            default: return false;
        }
        return true;
    }

    @Override
    void parseEffectLine() {

    }
}
