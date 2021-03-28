package graphics.window;

import entity.Player;
import graphics.ChooseAttackCell;
import graphics.Tools;
import graphics.elements.Move;
import graphics.elements.Position;
import entity.WhatHeroDoes;
import graphics.elements.cells.CellElementType;
import graphics.map.WorldMap;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

public class GameWindow extends JFrame {
    public static GameWindow window = new GameWindow();
    private static GameMenuPanel gameMenuPanel;
    private static GamePanel gamePanel;
    private static GameInterfacePanel gameInterfacePanel;
    private final JScrollPane jScrollPane;
    private static boolean inGame;

    private static Clip menuClip; //Destiny's Path, by Benjamin Groenke (free music from Jamendo.com)
    private static Clip gameClip; //A Dark Hero, by Grégoire Lourme (free music from Jamendo.com)

    private GameWindow() {
        super();
        setup();

        inGame = false;
        gameMenuPanel = new GameMenuPanel();
        gamePanel = new GamePanel();
        gameInterfacePanel = new GameInterfacePanel();

        jScrollPane = new JScrollPane(gamePanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane.setPreferredSize(new Dimension(800,600));
        jScrollPane.setBorder(null);

        //gameMenuPanel.addKeyListener(new MenuKeysActions()); //If we want to add keyboard interaction in the main menu.
        gamePanel.addKeyListener(new KeysActions());
    }

    public static void display() {
        if(inGame) {
            if(menuClip != null) {
                stop(menuClip);
                menuClip = null;
            }
            if(gameClip == null) gameClip = play("data/audio/BGM/Dark_Heroes.wav");
            window.displayGamePanels();
            window.setScrollFrameBar();
            window.setScrollFrameBar();  //Don't erase this double line.
            gamePanel.requestFocusInWindow();
        }
        else {
            if(gameClip != null) {
                stop(gameClip);
                gameClip = null;
            }
            if(menuClip == null) menuClip = play("data/audio/BGM/Destinys_Path.wav");
            window.displayMenuPanels();
        }
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
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.getImage("data/images/system/cursor.png");
        Cursor cursor = toolkit.createCustomCursor(image, new Point(0,0), "cursor");
        setCursor(cursor);
        getContentPane().setBackground(Color.DARK_GRAY);
    }

    private static void refresh() {
        window.setSize(window.getWidth() + 1,window.getHeight() + 1);
        window.setSize(window.getWidth() - 1,window.getHeight() - 1);
    }

    private void displayGamePanels() {
        clear();
        add(jScrollPane);
        add(gameInterfacePanel, BorderLayout.SOUTH);
        setScrollFrameBar();
        gamePanel.display();
        gameInterfacePanel.display();
    }

    private void displayMenuPanels() {
        clear();
        add(gameMenuPanel);
        gameMenuPanel.display();
    }

    private void clear() {
        if(getContentPane().getComponentCount() > 0) getContentPane().removeAll();
    }

    public void setScrollFrameBar() {
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

    private void setInGame(boolean b) {
        inGame = b;
    }

    public static void enterInGame() {
        window.setInGame(true);
        WorldMap.getInstanceWorld().generateWorld();
        display();
    }

    public static void returnToMenu() {
        window.setInGame(false);
        display();
    }

    private static Clip play(String pathname) {
        try {
            File audioFile1 = new File(pathname);
            AudioInputStream audioStream1 = AudioSystem.getAudioInputStream(audioFile1);

            AudioFormat format = audioStream1.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);

            Clip clip = (Clip) AudioSystem.getLine(info);

            clip.open(audioStream1);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            return clip;
        }
        catch(Exception e) {
            System.err.println("Error: failed to load music.");
            return null;
        }
    }

    private static void stop(Clip clip) {
        clip.stop();
        clip.close();
    }



    private static class KeysActions implements KeyListener {

        private void applyCommand(Move m){
            gamePanel.repaint();
            Player player = Player.getInstancePlayer();
            if (player.getHP() == 0) return;
            WhatHeroDoes choice = player.getWhatHeroDoes();
            Position pos = player.getWhatHeroDoes().getP();
            switch (choice){
                case MOVING -> player.makeAction(false, m, null);
                case CHOOSING_ATTACK -> {
                    Position p = ChooseAttackCell.selectCase(pos, m);
                    player.getWhatHeroDoes().setP(p);
                }
                case ATTACKING -> {
                    player.makeAction(true, null, player.getWhatHeroDoes().getP());
                    ChooseAttackCell.unselectCase(pos);
                    player.setWhatHeroDoes(WhatHeroDoes.MOVING);
                    player.getWhatHeroDoes().setP(player.getPosition());
                }
            }
            if (player.getHP() != 0) window.setScrollFrameBar();
            gamePanel.setObjective();
        }

        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent e) {}

        @Override
        public void keyReleased(KeyEvent e) {
            char key = (Tools.getKeyboard().equals("fr_FR")? Tools.universalCharOf(e.getKeyChar()) : e.getKeyChar());

            Player player = Player.getInstancePlayer();
            WorldMap worldMap = WorldMap.getInstanceWorld();
            WhatHeroDoes choice = player.getWhatHeroDoes();
            switch (key) {
                case 'w' -> applyCommand(Move.UP);
                case 'd' -> applyCommand(Move.RIGHT);
                case 's' -> applyCommand(Move.DOWN);
                case 'a' -> applyCommand(Move.LEFT);
                case 'q' -> {
                    if (worldMap.getCell(player.getPosition()).getBaseContent().equals(CellElementType.EMPTY)) {
                        Position oldPos = player.getWhatHeroDoes().getP();
                        switch (choice) {
                            case MOVING -> player.setWhatHeroDoes(WhatHeroDoes.CHOOSING_ATTACK);
                            case CHOOSING_ATTACK -> player.setWhatHeroDoes(WhatHeroDoes.ATTACKING);
                        }
                        player.getWhatHeroDoes().setP(oldPos);
                        applyCommand(null);
                    }
                }
                case 'i' -> {
                    gameInterfacePanel.displayRealInventory();
                    gameInterfacePanel.repaint();
                }
                case 'm' -> {
                    if (gameClip.isActive()) gameClip.stop(); else gameClip.start();
                }
                case '0' -> GameWindow.window.dispose();
                case 'r' -> {
                    player.restorePlayer();
                    worldMap.generateWorld();
                    display();
                }
            }
            refreshInventory();
        }
    }
}
