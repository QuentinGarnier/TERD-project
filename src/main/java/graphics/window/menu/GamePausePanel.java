package graphics.window.menu;

import graphics.Tools;
import graphics.window.GameWindow;

import javax.swing.*;
import java.awt.*;

import static graphics.Tools.*;

public class GamePausePanel extends JDialog{
    private final JPanel container;
    private final JCheckBox soundCheckBox;
    private final JButton playButton;
    private final JButton mainMenu;
    public GamePausePanel(){
        super(GameWindow.window, true);
        setResizable(false);
        container = new JPanel();
        container.setLayout(new GridLayout(0,1));
        container.setBorder(GameMenuCustomPanel.bigBorder(false));
        JLabel title = new JLabel("<html><center><h2>Game in pause</h></center></html>");
        container.add(title);
        soundCheckBox = new JCheckBox("Sound");
        playButton = GameMenuCustomPanel.createMenuButton("Play");
        mainMenu = GameMenuCustomPanel.createMenuButton("Menu");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setup();
    }

    private void setup(){
        setContentPane(container);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.getImage("data/images/system/cursor.png");
        Cursor cursor = toolkit.createCustomCursor(image, new Point(0,0), "cursor");
        setCursor(cursor);

        getContentPane().setBackground(Color.LIGHT_GRAY);

        soundEffect();
        play();
        mainMenu();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void mainMenu(){
        container.add(mainMenu);
        mainMenu.addActionListener(e -> {
            GameWindow.returnToMenu();
            dispose();
        });
    }

    private void soundEffect(){
        container.add(soundCheckBox);
        soundCheckBox.addActionListener(
            e -> {
                soundCheckBox.setIcon(new ImageIcon("data/images/system/checkbox" + (soundCheckBox.isSelected()?"_true":"") + ".png"));
                Settings.saveSettings(GameWindow.language(), soundCheckBox.isSelected(), GameWindow.difficulty());
                GameWindow.playOrStopMenuMusic();
            });
        soundCheckBox.setSelected(Tools.Settings.isMuted());
        soundCheckBox.doClick();
    }

    private void play(){
        playButton.addActionListener(e -> dispose());
        container.add(playButton);
    }
}