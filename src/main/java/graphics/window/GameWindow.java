package graphics.window;

import entity.Player;
import graphics.ChooseAttackCell;
import graphics.Tools;
import graphics.elements.Move;
import graphics.elements.Position;
import entity.WhatHeroDoes;

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

    public static void refreshInventory() {
        gameInterfacePanel.refresh();
        refresh();
    }

    public static void addToLogs(String txt, Color c) {
        gameInterfacePanel.addToLogs(txt, c);
    }


    private static class KeysActions implements KeyListener {

        private void applyCommand(Move m){
            boolean b = false;
            Player player = Player.getInstancePlayer();
            WhatHeroDoes choice = player.getWhatHeroDoes();
            Position pos = player.getWhatHeroDoes().getP();
            switch (choice){
                case MOVING -> {
                    b = player.makeAction(false, m, null);
                }
                case CHOOSING_ATTACK -> {
                    Position p = ChooseAttackCell.selectCase(pos, m);
                    player.getWhatHeroDoes().setP(p);
                }
                case ATTACKING -> {
                    b = player.makeAction(true, null, player.getWhatHeroDoes().getP());
                    ChooseAttackCell.unselectCase(pos);
                    player.setWhatHeroDoes(WhatHeroDoes.MOVING);
                    player.getWhatHeroDoes().setP(player.getPosition());
                }
            }
            if (b) gamePanel.moveEntities();
            else gamePanel.setObjective();
        }

        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent e) {}

        @Override
        public void keyReleased(KeyEvent e) {
            char key = (Tools.getKeyboard().equals("fr_FR")? Tools.universalCharOf(e.getKeyChar()) : e.getKeyChar());

            Player player = Player.getInstancePlayer();
            WhatHeroDoes choice = player.getWhatHeroDoes();
            switch (key) {
                case 'w' -> applyCommand(Move.UP);
                case 'd' -> applyCommand(Move.RIGHT);
                case 's' -> applyCommand(Move.DOWN);
                case 'a' -> applyCommand(Move.LEFT);
                case 'q' -> {
                    Position oldPos = player.getWhatHeroDoes().getP();
                    switch (choice) {
                        case MOVING -> player.setWhatHeroDoes(WhatHeroDoes.CHOOSING_ATTACK);
                        case CHOOSING_ATTACK -> player.setWhatHeroDoes(WhatHeroDoes.ATTACKING);
                    }
                    player.getWhatHeroDoes().setP(oldPos);
                    applyCommand(null);
                    break;
                }
            }
            window.setScrollFrameBar();
        }
    }
}
