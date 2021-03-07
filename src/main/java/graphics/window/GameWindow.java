package graphics.window;

import graphics.elements.Move;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameWindow extends JFrame {
    public static GameWindow window = new GameWindow();
    private static GamePanel gamePanel;
    private final JScrollPane jScrollPane;

    private GameWindow() {
        super();
        gamePanel = new GamePanel();

        setup();
        jScrollPane = new JScrollPane(gamePanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane.setPreferredSize(new Dimension(800,600));

        add(jScrollPane);
        displayGamePanel();

        gamePanel.addKeyListener(new KeysActions());
        setScrollFrameBar();
    }

    public static void display() {
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

    private void displayGamePanel() {
        gamePanel.display();
    }

    private void clear() {
        gamePanel.clear();
    }



    private static class KeysActions implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent e) {}

        @Override
        public void keyReleased(KeyEvent e) {
            int keyCode = e.getKeyCode();
            switch (keyCode) {
                case KeyEvent.VK_UP -> gamePanel.moveHero(Move.UP);
                case KeyEvent.VK_RIGHT -> gamePanel.moveHero(Move.RIGHT);
                case KeyEvent.VK_DOWN -> gamePanel.moveHero(Move.DOWN);
                case KeyEvent.VK_LEFT -> gamePanel.moveHero(Move.LEFT);
            }
            window.setScrollFrameBar();
        }
    }

    private void setScrollFrameBar(){
        jScrollPane.getHorizontalScrollBar().setValue(gamePanel.getHeroPosition().x - 400);
        jScrollPane.getVerticalScrollBar().setValue(gamePanel.getHeroPosition().y - 300);
    }
}
