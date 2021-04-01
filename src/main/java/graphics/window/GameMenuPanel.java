package graphics.window;

import javax.swing.*;
import java.awt.*;

public class GameMenuPanel extends JPanel {
    public final static GameMenuPanel getMenuPanel = new GameMenuPanel();
    private final GameMenuStartPanel startScreen = new GameMenuStartPanel();  //Launch a new game, load a save, see options/credits or quit the game.
    private final GameMenuCharaPanel charaScreen = new GameMenuCharaPanel();  //Select a character between 3 specialities: warrior, archer or mage.
    private final GameMenuOptionsPanel optionsScreen = new GameMenuOptionsPanel();  //Change options of the game, like language.
    private static Screen state;

    public final Color colorBG = new Color(60, 100, 120, 180);

    private final int screenWidth, screenHeight;

    private GameMenuPanel() {
        super();
        setLayout(new BorderLayout());
        setFocusable(true);
        setBackground(Color.DARK_GRAY);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenWidth = screenSize.width;
        screenHeight = screenSize.height;

        fillScreens();

        state = Screen.START;
        replaceWith(startScreen);
    }

    private void fillScreens() {
        startScreen.fillScreen();
        charaScreen.fillScreen();
        optionsScreen.fillScreen();
    }

    void display() {
        switch (state) {
            case START -> displayStartScreen();
            case CHARA -> displayCharaScreen();
            case OPTIONS -> displayOptionsScreen();
            case HELP -> displayHelpScreen();
        }
    }

    void displayStartScreen() {
        goToScreen(Screen.START);
    }

    void displayCharaScreen() {
        goToScreen(Screen.CHARA);
    }

    void displayOptionsScreen() {
        goToScreen(Screen.OPTIONS);
    }

    void displayHelpScreen(){
        goToScreen(Screen.HELP);
    }

    /**
     * Go to the screen with the associated number.
     * @param screen one of START, CHARA, OPTIONS or HELP
     */
    private void goToScreen(Screen screen) {
        if(state != screen) switch (screen) {
            case START -> replaceWith(startScreen);
            case CHARA -> replaceWith(charaScreen);
            case OPTIONS -> {
                optionsScreen.prepareScreen();
                replaceWith(optionsScreen);
            }
            case HELP -> {
                // TODO : ADD INFO PANEL
                JPanel panel = new JPanel();
                JButton b = new JButton("HELP");
                b.addActionListener(e -> {
                    goToScreen(Screen.START);
                });
                panel.add(b);
                replaceWith(panel);
            }
        }
        state = screen;
    }

    private void replaceWith(JPanel jPanel) {
        if(getComponentCount() > 0) removeAll();

        JLabel backgroundImage = new JLabel(new ImageIcon("data/images/system/title.png"));
        backgroundImage.setBounds(0, 0, screenWidth, screenHeight);
        add(backgroundImage);

        add(jPanel, 0);
        add(newBorder(), BorderLayout.NORTH);
        add(newBorder(), BorderLayout.EAST);
        add(newBorder(), BorderLayout.SOUTH);
        add(newBorder(), BorderLayout.WEST);

        repaint();
        revalidate();
    }

    private JPanel newBorder() {
        JPanel jPanel = new JPanel();
        jPanel.setBackground(Color.DARK_GRAY);
        jPanel.setPreferredSize(new Dimension(20, 60));
        return jPanel;
    }

    void setTexts() {
        startScreen.setTexts();
        charaScreen.setTexts();
    }

    static void stateReset() {
        state = Screen.START;
    }

    private enum Screen {
        START, CHARA, OPTIONS, HELP;
    }
}
