import graphics.map.WorldMap;
import graphics.window.GameWindow;

public class Main {
    public static void main(String[] args) {
        WorldMap.getInstanceWorld().generateWorld();
        GameWindow.display();
    }
}