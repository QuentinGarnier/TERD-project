package graphics.window;

import entity.Player;
import graphics.elements.Move;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameWindow extends JFrame {
    public static GameWindow window = new GameWindow();
    private static GamePanel gamePanel;
    private static GameInterfacePanel gameInterfacePanel;
    private final JScrollPane jScrollPane;

    private GameWindow() {
        super();
        gamePanel = new GamePanel();
        gameInterfacePanel = new GameInterfacePanel();

        setup();
        jScrollPane = new JScrollPane(gamePanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane.setPreferredSize(new Dimension(800,600));

        add(jScrollPane);
        add(gameInterfacePanel, BorderLayout.SOUTH);

        gamePanel.addKeyListener(new KeysActions());

        displayGamePanels();
        setScrollFrameBar();
    }

    public static void display() {
        window.setScrollFrameBar(); //The window is centered (on the Player) at the start
        window.setVisible(true);
    }

    private void setup() {
        setTitle("That time the Hero saved the Village"); //temp game name
        setSize(800,600);
        setMinimumSize(new Dimension(800,600));
        setLocationRelativeTo(null);
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon icon = new ImageIcon("data/images/system/icon.png");
        setIconImage(icon.getImage());
        getContentPane().setBackground(Color.DARK_GRAY);
    }

    private void displayGamePanels() {
        gamePanel.display();
        gameInterfacePanel.display();
    }

    private void clear() {
        gamePanel.clear();
    }

    private void setScrollFrameBar() {
        jScrollPane.getHorizontalScrollBar().setValue(gamePanel.getHeroPosition().x - 400);
        jScrollPane.getVerticalScrollBar().setValue(gamePanel.getHeroPosition().y - 300);
    }



    private static class KeysActions implements KeyListener {
        private char universalCharOf(char c) {
            return switch (c) {
                case 'z' -> 'w';
                case 'q' -> 'a';
                case 'a' -> 'q';
                case 'w' -> 'z';
                case 'm' -> ';';
                case ',' -> 'm';
                default -> c;
            };
        }

        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent e) {}

        @Override
        public void keyReleased(KeyEvent e) {
            int keyCode = e.getKeyCode();
            char key = universalCharOf(e.getKeyChar());

            switch (key) {
                case 'w' -> gamePanel.moveHero(Move.UP);
                case 'd' -> gamePanel.moveHero(Move.RIGHT);
                case 's' -> gamePanel.moveHero(Move.DOWN);
                case 'a' -> gamePanel.moveHero(Move.LEFT);
            }
            window.setScrollFrameBar();
            System.out.println(Player.getInstancePlayer().getMoney());
        }
    }
}
