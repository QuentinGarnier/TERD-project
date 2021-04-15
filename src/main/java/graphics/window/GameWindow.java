package graphics.window;

import entity.Player;
import entity.WhatHeroDoes;
import graphics.ChooseAttackCell;
import graphics.Language;
import graphics.Tools;
import graphics.elements.Move;
import graphics.elements.Position;
import graphics.elements.cells.CellElementType;
import graphics.map.WorldMap;
import graphics.window.menu.GameMenuPanel;
import graphics.window.menu.GamePausePanel;

import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameWindow extends JFrame {
    public static GameWindow window = new GameWindow();

    private static Language lang;
    private static boolean muted;
    private static Difficulty difficulty;

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
        gameMenuPanel = GameMenuPanel.getMenuPanel;
        gamePanel = new GamePanel();
        gameInterfacePanel = new GameInterfacePanel();

        jScrollPane = new JScrollPane(gamePanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane.setPreferredSize(new Dimension(800,600));
        jScrollPane.setBorder(null);

        gamePanel.addKeyListener(new KeysActions());
    }

    public static void display() {
        playMusics();
        if(inGame) {
            window.displayGamePanels();
            window.setScrollFrameBar();
            gamePanel.requestFocusInWindow();
        }
        else window.displayMenuPanels();
        window.setVisible(true);
    }

    private static void playMusics() {
        if(inGame) {
            if(menuClip != null) {
                stop(menuClip);
                menuClip = null;
            }
            if(gameClip == null && !muted) gameClip = play("data/audio/BGM/Dark_Heroes.wav");
        }
        else {
            if(gameClip != null) {
                stop(gameClip);
                gameClip = null;
            }
            if(menuClip == null && !muted) menuClip = play("data/audio/BGM/Destinys_Path.wav");
        }
    }

    public static void playOrStopMenuMusic() {
        muted = Tools.Settings.isMuted();
        if(!inGame) {
            if(!muted && menuClip == null) menuClip = play("data/audio/BGM/Destinys_Path.wav");
            else if(muted && menuClip != null) {
                stop(menuClip);
                menuClip = null;
            }
        } else  {
            if(!muted && gameClip == null) gameClip = play("data/audio/BGM/Dark_Heroes.wav");
            else if(muted && gameClip != null) {
                stop(gameClip);
                gameClip = null;
            }
        }
    }

    private void setup() {
        setTitle("That Time the Hero saved the Village"); //temp game name
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
        loadSettings();
    }

    private void loadSettings() {
        Tools.Settings.loadSettings();
        lang = Tools.Settings.getLanguage();
        muted = Tools.Settings.isMuted();
        difficulty = Tools.Settings.getDifficulty();
    }

    public static void setSettings(Language l, boolean sound, Difficulty d) {
        lang = l;
        muted = !sound;
        difficulty = d;
    }

    public static Language language() {
        return lang;
    }

    public static boolean isMuted() {
        return muted;
    }

    public static Difficulty difficulty() {
        return difficulty;
    }

    private void displayGamePanels() {
        clear();
        add(jScrollPane);
        add(gameInterfacePanel, BorderLayout.SOUTH);
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
        getContentPane().revalidate();
        getContentPane().repaint();
    }

    public void setScrollFrameBar() {
        Point pos = gamePanel.getHeroPosition();
        jScrollPane.getHorizontalScrollBar().setValue(pos.x - 400);
        jScrollPane.getVerticalScrollBar().setValue(pos.y - 150);
    }

    public static void refreshInventory() {
        gameInterfacePanel.refresh();
    }

    public static void addToLogs(String txt, Color c) {
        gameInterfacePanel.addToLogs(txt, c);
    }

    private void setInGame(boolean b) {
        inGame = b;
    }

    public static void enterInGame() {
        window.setInGame(true);
        WorldMap.getInstanceWorld().setDifficulty(difficulty);
        WorldMap.getInstanceWorld().generateWorld();
        display();
    }

    public static void returnToMenu() {
        window.setInGame(false);
        display();
    }

    private static Clip play(String pathname) {
        return Tools.play(pathname, true);
    }

    private static void stop(Clip clip) {
        clip.stop();
        clip.close();
    }



    private static class KeysActions implements KeyListener {

        private void applyCommand(Move m) {
            Player player = Player.getInstancePlayer();
            if (!player.listenerOn()) return;
            if (player.getHP() == 0) return;
            WhatHeroDoes choice = player.getWhatHeroDoes();
            Position pos = player.getWhatHeroDoes().getP();
            switch (choice) {
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
            InventoryPanel.inventoryPane.setInventoryText();
            gamePanel.setObjective();
            WorldMap.getInstanceWorld().repaint();
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
                case '0' -> GameWindow.window.dispose();
                case 'r' -> {
                    player.restorePlayer();
                    worldMap.generateWorld();
                    display();
                }
                case 'p' -> new GamePausePanel();
            }
            refreshInventory();

            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) new GamePausePanel();
        }
    }


    public enum Difficulty {
        TUTORIAL(2), EASY(10), MEDIUM(20), HARD(50), NIGHTMARE(100), ENDLESS(-1);
        /* ===== No Final Boss in Endless mod ===== */

        public final int stagesNumber;

        Difficulty(int x) {
            stagesNumber = x;
        }
    }
}
