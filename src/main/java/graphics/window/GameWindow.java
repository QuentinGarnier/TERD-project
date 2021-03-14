package graphics.window;

import entity.Player;
import graphics.ChooseAttackCell;
import graphics.Tools;
import graphics.elements.Move;
import graphics.elements.Position;
import graphics.WhatHeroDoes;

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


    private static class KeysActions implements KeyListener {
        WhatHeroDoes choice = WhatHeroDoes.MOVING;
        Position pos = null;

        private void applyCommand(WhatHeroDoes choice, Move m, Position pos){
            boolean b = false;
            switch (choice){
                case MOVING -> {
                    b = Player.getInstancePlayer().makeAction(false, m, null);
                }
                case CHOOSING_ATTACK -> {
                    this.pos = ChooseAttackCell.selectCase(pos, m);
                }
                case ATTACKING -> {
                    Player.getInstancePlayer().makeAction(true, null, pos);
                    ChooseAttackCell.unselectCase(pos);
                    this.pos = Player.getInstancePlayer().getPosition();
                }
            }
            if (b) gamePanel.moveEntities();
        }

        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent e) {}

        @Override
        public void keyReleased(KeyEvent e) {
            char key = (Tools.getKeyboard().equals("fr_FR")? Tools.universalCharOf(e.getKeyChar()) : e.getKeyChar());


            switch (key) {
                case 'w' -> applyCommand(choice, Move.UP, pos);
                case 'd' -> applyCommand(choice, Move.RIGHT, pos);
                case 's' -> applyCommand(choice, Move.DOWN, pos);
                case 'a' -> applyCommand(choice, Move.LEFT, pos);
                case 'q' -> {
                    switch (choice){
                        case MOVING -> {
                            System.out.println("SELECTING");
                            choice = WhatHeroDoes.CHOOSING_ATTACK;
                            applyCommand(WhatHeroDoes.CHOOSING_ATTACK, null, pos);
                        }
                        case CHOOSING_ATTACK -> {
                            choice = WhatHeroDoes.MOVING;
                            applyCommand(WhatHeroDoes.ATTACKING, null, pos);
                        }
                    }
                }
            }
            window.setScrollFrameBar();
        }
    }
}
