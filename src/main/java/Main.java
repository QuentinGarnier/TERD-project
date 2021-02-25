import entity.Player;
import graphics.map.WorldMap;

public class Main {
    public static void main(String[] args) {
        Player.showCommands();
        WorldMap.getInstanceWorld().repaint();
        Player.getInstancePlayer().gamePlayer();
    }
}
