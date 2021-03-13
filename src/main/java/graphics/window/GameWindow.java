package graphics.window;

import entity.Player;
import graphics.Tools;
import graphics.elements.Move;
import graphics.elements.Position;

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

    private static void refresh() {
        window.setSize(window.getWidth() + 1,window.getHeight() + 1);
        window.setSize(window.getWidth() - 1,window.getHeight() - 1);
    }

    private void displayGamePanels() {
        gamePanel.display();
        gameInterfacePanel.display();
    }

    private void setScrollFrameBar() {
        jScrollPane.getHorizontalScrollBar().setValue(gamePanel.getHeroPosition().x - 400);
        jScrollPane.getVerticalScrollBar().setValue(gamePanel.getHeroPosition().y - 300);
    }

    private static void applyCommand(Move m) {
        boolean b = Player.getInstancePlayer().makeAction(false, m, null);
        if(b) gamePanel.moveEntities();
    }

    private static void applyCommand2(Position p) {
        boolean b = Player.getInstancePlayer().makeAction(true, null, p);
        if(b) gamePanel.moveEntities();
    }

    public static void refreshInventory() {
        gameInterfacePanel.refresh();
        refresh();
    }


    private static class KeysActions implements KeyListener {


        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent e) {}

        @Override
        public void keyReleased(KeyEvent e) {
            int keyCode = e.getKeyCode();
            char key = (Tools.getKeyboard().equals("fr_FR")? Tools.universalCharOf(e.getKeyChar()) : e.getKeyChar());

            switch (key) {
                case 'w' -> applyCommand(Move.UP);
                case 'd' -> applyCommand(Move.RIGHT);
                case 's' -> applyCommand(Move.DOWN);
                case 'a' -> applyCommand(Move.LEFT);
                case 'q' -> applyCommand2(null); //here the position selected by user (instead of null)
            }
            window.setScrollFrameBar();
        }
    }
}
