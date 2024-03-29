package graphics.window.menu;

import graphics.Language;
import graphics.Tools;
import graphics.window.GameWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;

import static graphics.Tools.Settings;
import static graphics.window.menu.GameMenuInfoPanel.setHelpPanel;

public class GamePausePanel extends JDialog {
    private final JPanel container;
    private final JCheckBox soundCheckBox;
    private final JButton helpButton, keysButton;
    private final JButton restartGame, mainMenu, quitGame;
    private final JButton playButton;

    public GamePausePanel() {
        super(GameWindow.window, true);
        setResizable(false);
        setUndecorated(true);
        container = new JPanel();
        container.setLayout(new GridLayout(0,1, 0, 10));
        container.setBorder(GameMenuCustomPanel.bigBorder(false));
        JLabel title = new JLabel("<html><h2>" + Language.options().toUpperCase() + "</h2></html>");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        container.add(title);
        soundCheckBox = new JCheckBox("");
        helpButton = GameMenuCustomPanel.createMenuButton(Language.help());
        keysButton = GameMenuCustomPanel.createMenuButton(Language.keyBindings());
        playButton = GameMenuCustomPanel.createMenuButton(Language.resume());
        mainMenu = GameMenuCustomPanel.createMenuButton(Language.menu());
        quitGame = GameMenuCustomPanel.createMenuButton(Language.quitTheGame());
        restartGame = GameMenuCustomPanel.createMenuButton(Language.restart());
        container.addKeyListener(new KeysActions(this));
        container.setFocusable(true);
        soundCheckBox.setFocusable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setup();
    }

    private void setup() {
        setContentPane(container);
        getContentPane().setBackground(Color.LIGHT_GRAY);
        setCursor(Tools.cursor());

        addMouseEffects();

        soundEffect();
        container.add(helpButton);
        container.add(keysButton);
        container.add(new JLabel()); //An empty separator for the buttons
        container.add(restartGame);
        container.add(mainMenu);
        container.add(quitGame);
        container.add(new JLabel()); //An empty separator for the buttons
        container.add(playButton);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addMouseEffects() {
        addMouseEffect(helpButton, Effect.HELP);
        addMouseEffect(keysButton, Effect.KEYS);
        addMouseEffect(restartGame, Effect.RESTART);
        addMouseEffect(mainMenu, Effect.MENU);
        addMouseEffect(quitGame, Effect.EXIT);
        addMouseEffect(playButton, Effect.RESUME);
    }

    private void soundEffect() {
        soundCheckBox.setHorizontalAlignment(SwingConstants.CENTER);
        soundCheckBox.setOpaque(false);
        soundCheckBox.addActionListener(
                e -> {
                    soundCheckBox.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("data/images/system/checkbox" + (soundCheckBox.isSelected()?"_true":"") + ".png"))));
                    Settings.saveSettings(GameWindow.language(), soundCheckBox.isSelected(), GameWindow.difficulty(), GameWindow.getDifficultiesUnlocked(), GameWindow.resolution());
                    GameWindow.setSettings(GameWindow.language(), soundCheckBox.isSelected(), GameWindow.difficulty());
                    soundCheckBox.setText(Language.sound(Tools.Settings.isMuted()));
                    GameWindow.playOrStopMenuMusic();
                });
        soundCheckBox.setSelected(Tools.Settings.isMuted());
        soundCheckBox.doClick();
        container.add(soundCheckBox);
    }

    private void helpPanel() {
        new KeyPanel(this);
    }

    private void restartGame() {
        int apply = JOptionPane.showConfirmDialog(GameWindow.window,
                Language.restartConfirmation(),
                "", JOptionPane.YES_NO_OPTION);
        if (apply == JOptionPane.YES_OPTION) {
            dispose();
            Tools.Ranking.saveRanking(Tools.Victory_Death.ABANDON);
            Tools.restartGame();
        }
    }

    private void mainMenu() {
        int apply = JOptionPane.showConfirmDialog(GameWindow.window,
                Language.goToMenuConfirmation(),
                "", JOptionPane.YES_NO_OPTION);
        if (apply == JOptionPane.YES_OPTION) {
            dispose();
            Tools.Ranking.saveRanking(Tools.Victory_Death.ABANDON);
            GameWindow.returnToMenu();
        }
    }

    private void quitGame() {
        int apply = JOptionPane.showConfirmDialog(GameWindow.window,
                Language.quitGameConfirmation(),
                "", JOptionPane.YES_NO_OPTION);
        if(apply == JOptionPane.YES_OPTION) {
            Tools.Ranking.saveRanking(Tools.Victory_Death.ABANDON);
            System.exit(0);
        }
    }


    /**
     * Add an effect to a JButton (click and hover).
     * @param button a JButton, should be not null
     * @param effect one of RESTART, MENU, EXIT or RESUME
     */
    private void addMouseEffect(JButton button, Effect effect) {
        Color bg = button.getBackground();
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                button.setBackground(bg);
                switch (effect) {
                    case HELP -> helpPanel();
                    case KEYS -> GameMenuOptionsPanel.setKeys(true);
                    case RESTART -> restartGame();
                    case MENU -> mainMenu();
                    case EXIT -> quitGame();
                    case RESUME -> dispose();
                }
            }
        });
    }

    private enum Effect {
        HELP, KEYS, RESTART, MENU, EXIT, RESUME
    }

    private static class KeysActions extends KeyAdapter {
        private final GamePausePanel jd;

        public KeysActions(GamePausePanel jd){
            this.jd = jd;
        }
        @Override
        public void keyReleased(KeyEvent e) {
            char key = Character.toUpperCase(e.getKeyChar());
            if (key == GameWindow.KeyBindings.restart.key) jd.restartGame();
            else if (key == GameWindow.KeyBindings.options.key || e.getKeyCode() == KeyEvent.VK_ESCAPE) jd.dispose();
        }
    }

    private static class KeyPanel extends JDialog {
        private final JButton backButton;

        KeyPanel(GamePausePanel gmp) {
            super(gmp, true);
            setResizable(false);
            setUndecorated(true);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);

            JPanel bigPanel = new JPanel(new BorderLayout());
            bigPanel.setOpaque(false);
            JPanel container = new JPanel();
            JScrollPane scroller = new JScrollPane(container);
            scroller.setOpaque(false);
            scroller.setPreferredSize(new Dimension(760,480));
            setHelpPanel(container);
            bigPanel.setBorder(GameMenuCustomPanel.bigBorder(true));
            bigPanel.setFocusable(true);
            bigPanel.add(scroller);

            backButton = GameMenuCustomPanel.createMenuButton(Language.back());
            backButton.addMouseListener(new MouseAdapter() {
                final Color bg = backButton.getBackground();
                final Color hoverBG = new Color(180, 150, 110);
                @Override
                public void mouseClicked(MouseEvent e) {
                    backButton.setBackground(bg);
                    dispose();
                }
                @Override
                public void mouseEntered(MouseEvent e) {
                    backButton.setBackground(hoverBG);
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    backButton.setBackground(bg);
                }
            });
            JPanel southPanel = new JPanel(new GridBagLayout());
            southPanel.setOpaque(false);
            southPanel.add(backButton);
            bigPanel.add(southPanel, BorderLayout.SOUTH);

            setContentPane(bigPanel);
            setCursor(Tools.cursor());

            bigPanel.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    if(e.getKeyCode() == KeyEvent.VK_ESCAPE) dispose();
                }
            });
            bigPanel.setFocusable(true);

            pack();
            setLocationRelativeTo(null);
            setVisible(true);
        }
    }
}
