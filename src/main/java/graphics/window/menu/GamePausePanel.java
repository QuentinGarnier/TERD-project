package graphics.window.menu;

import graphics.Language;
import graphics.Tools;
import graphics.window.GameWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Objects;

import static graphics.Tools.Settings;

public class GamePausePanel extends JDialog {
    private final JPanel container;
    private final JCheckBox soundCheckBox;
    private final JButton quitGame;
    private final JButton playButton;
    private final JButton mainMenu;
    private final JButton restartGame;

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
        playButton = GameMenuCustomPanel.createMenuButton(Language.resume());
        mainMenu = GameMenuCustomPanel.createMenuButton(Language.menu());
        quitGame = GameMenuCustomPanel.createMenuButton(Language.quitTheGame());
        restartGame = GameMenuCustomPanel.createMenuButton(Language.restart());
        container.addKeyListener(new KeysActions(this, soundCheckBox));
        container.setFocusable(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setup();
    }

    private void setup() {
        setContentPane(container);
        getContentPane().setBackground(Color.LIGHT_GRAY);
        setCursor(Tools.cursor());

        addMouseEffects();

        soundEffect();
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
                    Settings.saveSettings(GameWindow.language(), soundCheckBox.isSelected(), GameWindow.difficulty());
                    GameWindow.setSettings(GameWindow.language(), soundCheckBox.isSelected(), GameWindow.difficulty());
                    soundCheckBox.setText(Language.sound(Tools.Settings.isMuted()));
                    GameWindow.playOrStopMenuMusic();
                });
        soundCheckBox.setSelected(Tools.Settings.isMuted());
        soundCheckBox.doClick();
        container.add(soundCheckBox);
    }

    private void restartGame() {
        int apply = JOptionPane.showConfirmDialog(GameWindow.window,
                Language.restartConfirmation(),
                "", JOptionPane.YES_NO_OPTION);
        if (apply == JOptionPane.YES_OPTION) {
            dispose();
            Tools.restartGame();
        }
    }

    private void mainMenu() {
        int apply = JOptionPane.showConfirmDialog(GameWindow.window,
                Language.goToMenuConfirmation(),
                "", JOptionPane.YES_NO_OPTION);
        if (apply == JOptionPane.YES_OPTION) {
            dispose();
            GameWindow.returnToMenu();
        }
    }

    private void quitGame() {
        int apply = JOptionPane.showConfirmDialog(GameWindow.window,
                Language.quitGameConfirmation(),
                "", JOptionPane.YES_NO_OPTION);
        if(apply == JOptionPane.YES_OPTION) System.exit(0);
    }


    /**
     * Add an effect to a JButton (click and hover).
     * @param button a JButton, should be not null
     * @param effect one of RESTART, MENU, EXIT or RESUME
     */
    private void addMouseEffect(JButton button, Effect effect) {
        Color bg = button.getBackground();
        Color hoverBG = new Color(180, 150, 110);

        button.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                button.setBackground(bg);
                switch (effect) {
                    case RESTART -> restartGame();
                    case MENU -> mainMenu();
                    case EXIT -> quitGame();
                    case RESUME -> dispose();
                }
            }
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverBG);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(bg);
            }
        });
    }

    private enum Effect {
        RESTART, MENU, EXIT, RESUME
    }

    private static class KeysActions extends KeyAdapter {
        private final GamePausePanel jd;
        private final JCheckBox cb;

        public KeysActions(GamePausePanel jd, JCheckBox cb){
            this.jd = jd;
            this.cb = cb;
        }
        @Override
        public void keyReleased(KeyEvent e) {
            switch (e.getKeyCode()){
                case KeyEvent.VK_ESCAPE, KeyEvent.VK_P -> jd.dispose();
                case KeyEvent.VK_R -> jd.restartGame();
                case KeyEvent.VK_M -> jd.mainMenu();
                case KeyEvent.VK_Q -> jd.quitGame();
                case KeyEvent.VK_V -> cb.doClick();
            }
        }
    }
}
