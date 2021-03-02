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

    private GameWindow() {
        super();
        gamePanel = new GamePanel();

        setup();
        displayGamePanel();

        gamePanel.addKeyListener(new KeysActions());
    }

    public static void display() {
        window.setVisible(true);
    }

    private void setup() {
        setTitle("That time the Hero saved the Village"); //temp game name
        setSize(1400,900);
        setMinimumSize(new Dimension(800,600));
        setLocationRelativeTo(null);
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon icon = new ImageIcon("data/images/system/icon.png");
        setIconImage(icon.getImage());
        getContentPane().setBackground(Color.DARK_GRAY);
        add(gamePanel);
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
                case KeyEvent.VK_UP:
                    gamePanel.moveHero(Move.UP); break;

                case KeyEvent.VK_RIGHT:
                    gamePanel.moveHero(Move.RIGHT); break;

                case KeyEvent.VK_DOWN:
                    gamePanel.moveHero(Move.DOWN); break;

                case KeyEvent.VK_LEFT:
                    gamePanel.moveHero(Move.LEFT); break;
            }
        }
    }

}
