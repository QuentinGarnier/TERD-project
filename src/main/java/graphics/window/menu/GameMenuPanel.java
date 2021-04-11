package graphics.window.menu;

import entity.Player;

import javax.swing.*;
import java.awt.*;

public class GameMenuPanel extends JPanel {
    public final static GameMenuPanel getMenuPanel = new GameMenuPanel();
    private final GameMenuStartPanel startScreen = new GameMenuStartPanel();  //Launch a new game, load a save, see options/credits or quit the game.
    private final GameMenuCharaPanel charaScreen = new GameMenuCharaPanel();  //Select a character between 3 specialities: warrior, archer or mage.
    private final GameMenuOptionsPanel optionsScreen = new GameMenuOptionsPanel();  //Change options of the game, like language.
    private final GameMenuInfoPanel infoPanel = new GameMenuInfoPanel();  //See all the info about the game.
    private final JPanel bigPanel;

    private static Screen state;
    private final int screenWidth, screenHeight;

    private GameMenuPanel() {
        super();
        setLayout(new BorderLayout());
        setFocusable(true);
        setBackground(Color.DARK_GRAY);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenWidth = screenSize.width;
        screenHeight = screenSize.height;

        bigPanel = new JPanel(new BorderLayout());
        setupBigPanel();
        fillScreens();

        state = Screen.START;
        replaceWith(startScreen);
    }

    private void fillScreens() {
        startScreen.fillScreen();
        charaScreen.fillScreen();
        optionsScreen.fillScreen();
    }

    public void display() {
        Player.getInstancePlayer().restorePlayer();
        displayStartScreen();
        /*switch (state) {
            case START -> displayStartScreen();
            case CHARA -> displayCharaScreen();
            case OPTIONS -> displayOptionsScreen();
            case HELP -> displayHelpScreen();
        }*/
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
            case HELP -> replaceWith(infoPanel);
        }
        state = screen;
    }

    private void setupBigPanel() {
        JLabel backgroundImage = new JLabel(new ImageIcon("data/images/system/title.png"));
        backgroundImage.setBounds(0, 0, screenWidth, screenHeight);
        JPanel panel1 = new JPanel(new GridBagLayout());
        panel1.setOpaque(false);
        panel1.setMinimumSize(new Dimension(760,480));

        Dimension dim = new Dimension(760,480);
        bigPanel.setPreferredSize(dim);
        bigPanel.setMinimumSize(dim);
        bigPanel.setMaximumSize(dim);
        bigPanel.setOpaque(false);

        panel1.add(bigPanel);
        add(backgroundImage);
        add(panel1, 0);
    }

    private void replaceWith(JPanel jPanel) {
        if(bigPanel.getComponentCount() > 0) bigPanel.removeAll();

        Dimension dim = new Dimension(760,480);
        jPanel.setPreferredSize(dim);
        jPanel.setMinimumSize(dim);
        bigPanel.add(jPanel);

        repaint();
        revalidate();
    }

    void setTexts() {
        startScreen.setTexts();
        charaScreen.setTexts();
        infoPanel.setTexts();
    }

    static void stateReset() {
        state = Screen.START;
    }

    private enum Screen {
        START, CHARA, OPTIONS, HELP;
    }
}
