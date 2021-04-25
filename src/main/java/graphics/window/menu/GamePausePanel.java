package graphics.window.menu;

import entity.Player;
import graphics.Language;
import graphics.Tools;
import graphics.map.WorldMap;
import graphics.window.GameWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Objects;

import static graphics.Tools.Settings;

public class GamePausePanel extends JDialog{
    private final JPanel container;
    private final JCheckBox soundCheckBox;
    private final JButton quitGame;
    private final JButton playButton;
    private final JButton mainMenu;
    private final JButton restartGame;

    public GamePausePanel() {
        super(GameWindow.window, true);
        setResizable(false);
        container = new JPanel();
        container.setLayout(new GridLayout(0,1));
        container.setBorder(GameMenuCustomPanel.bigBorder(false));
        JLabel title = new JLabel("<html><center><h2>" + Language.options() + "</h></center></html>");
        container.add(title);
        soundCheckBox = new JCheckBox("");
        playButton = GameMenuCustomPanel.createMenuButton(Language.resume());
        mainMenu = GameMenuCustomPanel.createMenuButton(Language.menu());
        quitGame = GameMenuCustomPanel.createMenuButton(Language.quitTheGame());
        restartGame = GameMenuCustomPanel.createMenuButton(Language.restart());
        container.addKeyListener(new KeysActions(this, mainMenu, quitGame, restartGame));
        container.setFocusable(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setup();
    }

    private void setup(){
        setContentPane(container);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.getImage(Objects.requireNonNull(getClass().getClassLoader().getResource("data/images/system/cursor.png")));
        Cursor cursor = toolkit.createCustomCursor(image, new Point(0,0), "cursor");
        setCursor(cursor);

        getContentPane().setBackground(Color.LIGHT_GRAY);

        soundEffect();
        play();
        restartGame();
        mainMenu();
        quitGame();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setFocusable(true);
    }

    private void restartGame(){
        container.add(restartGame);
        restartGame.addActionListener(e -> {
            int apply = JOptionPane.showConfirmDialog(GameWindow.window,
                    Language.restartConfirmation(),
                    "", JOptionPane.YES_NO_OPTION);
            if (apply == JOptionPane.YES_OPTION) {
                dispose();
                Player player = Player.getInstancePlayer();
                WorldMap worldMap = WorldMap.getInstanceWorld();
                player.restorePlayer();
                worldMap.generateWorld();
                GameWindow.display();
            }
        });
    }

    private void mainMenu(){
        container.add(mainMenu);
        mainMenu.addActionListener(e -> {
            int apply = JOptionPane.showConfirmDialog(GameWindow.window,
                    Language.goToMenuConfirmation(),
                    "", JOptionPane.YES_NO_OPTION);
            if (apply == JOptionPane.YES_OPTION) {
                dispose();
                GameWindow.returnToMenu();
            }
        });
    }

    private void quitGame(){
        container.add(quitGame);
        quitGame.addActionListener(e -> {
            int apply = JOptionPane.showConfirmDialog(GameWindow.window,
                    Language.quitGameConfirmation(),
                    "", JOptionPane.YES_NO_OPTION);
            if (apply == JOptionPane.YES_OPTION) {
                dispose();
                GameWindow.window.dispose();
            }
        });
    }

    private void soundEffect(){
        container.add(soundCheckBox);
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
    }

    private void play(){
        playButton.addActionListener(e -> dispose());
        container.add(playButton);
    }

    private static class KeysActions extends KeyAdapter {
        private final JDialog jd;
        private final JButton mainMenu, quitGame, restartGame;
        public KeysActions(JDialog jd, JButton mainMenu, JButton quitGame, JButton restartGame){
            this.jd = jd;
            this.mainMenu = mainMenu; this.quitGame = quitGame; this.restartGame = restartGame;
        }
        @Override
        public void keyReleased(KeyEvent e) {
            switch (e.getKeyCode()){
                case KeyEvent.VK_ESCAPE, KeyEvent.VK_P -> jd.dispose();
                case KeyEvent.VK_R -> restartGame.doClick();
                case KeyEvent.VK_M -> mainMenu.doClick();
                case KeyEvent.VK_Q -> quitGame.doClick();
            }
        }
    }
}
