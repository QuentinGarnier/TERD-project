package items;

import entity.EntityState;
import entity.Player;
import graphics.Language;
import graphics.Tools;
import graphics.elements.Position;
import graphics.map.WorldMap;
import graphics.window.GameWindow;

import java.awt.*;
import java.util.Random;

public class ItemTrap extends AbstractItem{

    private final int effect;
    private final Random rndTrap = new Random();

    ItemTrap(int i, Position position) {
        super(i, ItemType.TRAP, position, true);
        this.effect = rndTrap.nextInt(5);
    }

    @Override
    public boolean usePrivate() {
        Player player = Player.getInstancePlayer();
        switch (effect) {
            case 0: GameWindow.addToLogs(Language.logTrap(effect), Tools.WindowText.orange); player.updateState(EntityState.BURNT); break;
            case 1: GameWindow.addToLogs(Language.logTrap(effect), Tools.WindowText.cyan); player.updateState(EntityState.FROZEN); break;
            case 2: GameWindow.addToLogs(Language.logTrap(effect), Tools.WindowText.purple); player.updateState(EntityState.POISONED); break;
            case 3: GameWindow.addToLogs(Language.logTrap(effect), Color.WHITE); WorldMap.getInstanceWorld().placePlayer(); break;
            case 4: GameWindow.addToLogs(Language.logTrap(effect), Tools.WindowText.red); player.takeDamage(15); break;
            default: return false;
        }
        return true;
    }
}
