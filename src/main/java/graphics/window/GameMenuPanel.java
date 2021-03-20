package graphics.window;

import graphics.Tools;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameMenuPanel extends JPanel {
    private final JPanel startScreen = new JPanel();  //Launch a new game, load a save, see options/credits or quit the game.
    private final JPanel charaScreen = new JPanel();  //Select a character between 3 specialities: warrior, archer or mage.
    private final JPanel optionsScreen = new JPanel();  //Change options of the game, like language.
    private int state;  //0 for startScreen, 1 for charaScreen and 2 for optionsScreen.

    GameMenuPanel() {
        super();
        setLayout(null);
        setFocusable(true);
        setBackground(Color.DARK_GRAY);

        setupScreens();

        state = 0;
        add(startScreen);
    }

    private void setupScreens() {
        Dimension dim = new Dimension(700, 500);
        startScreen.setPreferredSize(dim);
        charaScreen.setPreferredSize(dim);
        optionsScreen.setPreferredSize(dim);

        startScreen.setBackground(Color.GRAY);
        charaScreen.setBackground(Color.GRAY);
        optionsScreen.setBackground(Color.GRAY);
    }

    //Launch the game:
    private void launch() {

    }

    void display() {
        switch (state) {
            case 0 -> displayStartScreen();
            case 1 -> displayCharaScreen();
            case 2 -> displayOptionsScreen();
        }
    }

    private void displayStartScreen() {
        goToScreen(0);
    }

    private void displayCharaScreen() {
        goToScreen(1);
    }

    private void displayOptionsScreen() {
        goToScreen(2);
    }

    private void goToScreen(int n) {
        removeAll();
        switch (n) {
            case 0 -> add(startScreen);
            case 1 -> add(charaScreen);
            case 2 -> add(optionsScreen);
        }
        state = n;
    }
}
