package graphics.window;

import graphics.Language;
import graphics.Tools;
import graphics.window.menu.GameMenuCustomPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GameOverPanel extends JDialog {
    private final JPanel container;
    private final JPanel buttonsPanel;
    private final JButton restartButton;
    private final JButton menuButton;
    private final JButton exitButton;

    public GameOverPanel() {
        super(GameWindow.window, true);
        setResizable(false);
        setUndecorated(true);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        container = new JPanel();
        container.setLayout(new GridLayout(0,1, 0, 10));
        container.setBorder(GameMenuCustomPanel.bigBorder(false));
        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1, 3, 10, 0));
        buttonsPanel.setOpaque(false);

        restartButton = GameMenuCustomPanel.createMenuButton(Language.restart(), false);
        menuButton = GameMenuCustomPanel.createMenuButton(Language.menu(), false);
        exitButton = GameMenuCustomPanel.createMenuButton(Language.quitTheGame(), false);

        setup();
    }

    private void setup() {
        setContentPane(container);
        getContentPane().setBackground(Color.LIGHT_GRAY);
        setCursor(Tools.cursor());

        JLabel title = new JLabel("<html><h2>" + Language.gameOver() + "</h2></html>");
        title.setHorizontalAlignment(SwingConstants.CENTER);

        container.add(title);
        container.add(buttonsPanel);

        addMouseEffects();
        buttonsPanel.add(restartButton);
        buttonsPanel.add(menuButton);
        buttonsPanel.add(exitButton);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addMouseEffects() {
        addMouseEffect(restartButton, Effect.RESTART);
        addMouseEffect(menuButton, Effect.MENU);
        addMouseEffect(exitButton, Effect.EXIT);
    }

    private void restartGame() {
        dispose();
        Tools.restartGame();
    }

    private void mainMenu() {
        dispose();
        GameWindow.returnToMenu();
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
                    case EXIT -> System.exit(0);
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
        RESTART, MENU, EXIT
    }
}
