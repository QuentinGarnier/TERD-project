import graphics.window.GameWindow;
import graphics.window.menu.GamePausePanel;

public class Main {
    public static void main(String[] args) {
        boolean a = true;
        if (a) GameWindow.display();
        else new GamePausePanel();
    }
}