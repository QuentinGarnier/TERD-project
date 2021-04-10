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

    public final int effect;
    private final Random rndTrap = new Random();

    ItemTrap(int i, Position position) {
        super(i, ItemType.TRAP, position, true);
        this.effect = rndTrap.nextInt(5);
    }

    @Override
    public boolean usePrivate() {
        switch (effect) {
            case 0 -> trapEffect(Trap.BURNING, Tools.WindowText.orange, EntityState.BURNT);
            case 1 -> trapEffect(Trap.FREEZING, Tools.WindowText.cyan, EntityState.FROZEN);
            case 2 -> trapEffect(Trap.POISONED, Tools.WindowText.purple, EntityState.POISONED);
            case 3 -> trapEffect(Trap.TELEPORT, Color.WHITE, null);
            case 4 -> trapEffect(Trap.BOMB, Tools.WindowText.red, null);
            default -> { return false; }
        }
        return true;
    }

    private void trapEffect(Trap trap, Color c, EntityState state) {
        Player player = Player.getInstancePlayer();
        if(!GameWindow.isMuted()) Tools.play("data/audio/SE/" + trap.getSE() + ".wav", false);
        GameWindow.addToLogs(Language.logTrap(trap), c);
        if(state != null) player.updateState(state);
        else {
            switch (trap) {
                case TELEPORT: WorldMap.getInstanceWorld().placePlayer(); break;
                case BOMB: player.takeDamage(15); break;
                default: break;
            }
        }
    }



    public enum Trap {
        BURNING("fire"),
        FREEZING("ice"),
        POISONED("dart"),
        TELEPORT("teleport"),
        BOMB("explosion");

        String se;
        Trap(String soundEffect) {
            this.se = soundEffect;
        }
        String getSE() {
            return se;
        }
    }
}
