import entity.Player;
import graphics.map.WorldMap;

public class Main {
    public static void main(String[] args) {
        WorldMap.getInstanceWorld().repaint();
        WorldMap.gamePlayer();
    }
}
