package graphics.window.menu;

import entity.Merchant;
import entity.Player;
import graphics.window.GameWindow;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

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

    void displayRankingScreen(){
        goToScreen(Screen.RANKING);
    }

    /**
     * Go to the screen with the associated number.
     * @param screen one of START, CHARA, OPTIONS or HELP
     */
    private void goToScreen(Screen screen) {
        if(state != screen) switch (screen) {
            case START -> {
                replaceWith(startScreen);
                Merchant.getInstanceMerchant().refreshWindows();
            }
            case CHARA -> {
                replaceWith(charaScreen);
                GameWindow.name = JOptionPane.showInputDialog("Before starting the game, enter your name :");
                System.out.println(GameWindow.name);
            }
            case OPTIONS -> {
                optionsScreen.prepareScreen();
                replaceWith(optionsScreen);
            }
            case HELP -> replaceWith(infoPanel);
            case RANKING -> replaceWith(new GameMenuStat());
        }
        state = screen;
    }

    private void setupBigPanel() {
        JLabel backgroundImage = new JLabel(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("data/images/system/title.png"))));
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
        START, CHARA, OPTIONS, HELP, RANKING;
    }
}
