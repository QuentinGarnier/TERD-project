package graphics.window;

import javax.swing.*;
import java.awt.*;

public class GameInterfacePanel extends JPanel {
    GameInterfacePanel() {
        super();
        setLayout(new GridLayout());
        setBackground(Color.LIGHT_GRAY);
        setPreferredSize(new Dimension(800,200));
    }

    void display() {
        displayInterface();
    }

    private void displayInterface() {
        JLabel playerName = new JLabel("NameTag");
        JLabel test1 = new JLabel("Test 1 / 1000");
        add(playerName);
        add(test1);
    }

    private String getInfo() {
        return "";
    }
}
