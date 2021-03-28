import graphics.map.WorldMap;
import graphics.window.GameWindow;

public class Main {
    public static void main(String[] args) {
        boolean a = true;
        if (a) GameWindow.display();
        else WorldMap.getInstanceWorld();
    }
}