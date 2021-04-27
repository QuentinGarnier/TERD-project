package graphics.window;

import entity.Player;
import entity.Player.WhatHeroDoes;
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
import java.net.URL;
import java.util.Objects;

public class GameWindow extends JFrame {
    public static String name = "";
    public static GameWindow window = new GameWindow();

    private static Language lang;
    private static boolean muted;
    private static Difficulty difficulty;
    private static boolean[] difficultiesUnlocked;

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
            if(gameClip == null && !muted) gameClip = play(Objects.requireNonNull(GameWindow.class.getClassLoader().getResource("data/audio/BGM/Dark_Heroes.wav")));
        }
        else {
            if(gameClip != null) {
                stop(gameClip);
                gameClip = null;
            }
            if(menuClip == null && !muted) menuClip = play(Objects.requireNonNull(GameWindow.class.getClassLoader().getResource("data/audio/BGM/Destinys_Path.wav")));
        }
    }

    public static void playOrStopMenuMusic() {
        if(!inGame) {
            if(!muted && menuClip == null) menuClip = play(Objects.requireNonNull(GameWindow.class.getClassLoader().getResource("data/audio/BGM/Destinys_Path.wav")));
            else if(muted && menuClip != null) {
                stop(menuClip);
                menuClip = null;
            }
        } else  {
            if(!muted && gameClip == null) gameClip = play(Objects.requireNonNull(GameWindow.class.getClassLoader().getResource("data/audio/BGM/Dark_Heroes.wav")));
            else if(muted && gameClip != null) {
                stop(gameClip);
                gameClip = null;
            }
        }
    }

    private void setup() {
        setTitle("That Time the Hero saved the Village");
        setSize(800,600);
        setMinimumSize(new Dimension(800,600));
        setLocationRelativeTo(null);
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("data/images/system/icon.png")));
        setIconImage(icon.getImage());
        setCursor(Tools.cursor());
        getContentPane().setBackground(Color.BLACK);
        loadSettings();

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (inGame) Tools.Ranking.saveRanking(Tools.Victory_Death.ABANDON);
            }
        });
    }

    private void loadSettings() {
        Tools.Settings.loadSettings();
        lang = Tools.Settings.getLanguage();
        muted = Tools.Settings.isMuted();
        difficulty = Tools.Settings.getDifficulty();
        difficultiesUnlocked = Tools.Settings.getDifficultiesUnlocked();
    }

    public static void setSettings(Language l, boolean sound, Difficulty d) {
        lang = l;
        muted = !sound;
        difficulty = d;
    }

    public static Language language() {
        return lang;
    }

    public static boolean hasSound() {
        return !muted;
    }

    public static Difficulty difficulty() {
        return difficulty;
    }

    public static boolean[] getDifficultiesUnlocked() {
        return difficultiesUnlocked;
    }

    public static void unlock(boolean[] dif, boolean needToRefresh) {
        difficultiesUnlocked = dif;
        if(needToRefresh) gameMenuPanel.refreshDifficulties();
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

    public static void clear() {
        if(window.getContentPane().getComponentCount() > 0) window.getContentPane().removeAll();
        window.getContentPane().repaint();
        window.getContentPane().revalidate();
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

    private static Clip play(URL pathname) {
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
            Player.WhatHeroDoes choice = player.getWhatHeroDoes();
            Position pos = player.getAttackPosition();
            switch (choice) {
                case MOVING -> {
                    player.restoreAttackPos();
                    player.makeAction(false, m, null);
                }
                case CHOOSING_ATTACK -> {
                    if (player.getPosition().distance(player.getAttackPosition()) > player.getRange())
                        player.restoreAttackPos();
                    player.setAttackPosition(ChooseAttackCell.selectCase(player.getAttackPosition(), m));
                }
                case ATTACKING -> {
                    player.makeAction(true, null, player.getAttackPosition());
                    ChooseAttackCell.unselectCase(pos);
                    player.setWhatHeroDoes(Player.WhatHeroDoes.MOVING);
                }
            }
            InventoryPanel.inventoryPane.setInventoryText();
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
                        switch (choice) {
                            case MOVING -> player.setWhatHeroDoes(WhatHeroDoes.CHOOSING_ATTACK);
                            case CHOOSING_ATTACK -> player.setWhatHeroDoes(WhatHeroDoes.ATTACKING);
                        }
                        applyCommand(null);
                    }
                }
                case 'i' -> {
                    gameInterfacePanel.displayRealInventory();
                    gameInterfacePanel.repaint();
                }
                case '0' -> GameWindow.window.dispose();
                case 'r' -> {
                    int apply = JOptionPane.showConfirmDialog(GameWindow.window,
                            Language.restartConfirmation(),
                            "", JOptionPane.YES_NO_OPTION);
                    if (apply == JOptionPane.YES_OPTION) {
                        Tools.Ranking.saveRanking(Tools.Victory_Death.ABANDON);
                        Tools.restartGame();
                    }
                }
                case 'p' -> new GamePausePanel();
            }
            refreshInventory();

            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) new GamePausePanel();
        }
    }


    public enum Difficulty {
        TUTORIAL(2, 0), EASY(10, 1), MEDIUM(20, 2), HARD(50, 3), NIGHTMARE(100, 4), ENDLESS(-1, 5);
        /* ===== No Final Boss in Endless mod ===== */

        public final int stagesNumber;
        public final int id;

        Difficulty(int stagesNumber, int id) {
            this.stagesNumber = stagesNumber;
            this.id = id;
        }

        public static Difficulty findById(int id){
            for (Difficulty diff : Difficulty.values()){
                if (id == diff.id) return diff;
            }
            throw new RuntimeException("Int not known in Difficulty");
        }

        @Override
        public String toString() {
            return switch (this){
                case TUTORIAL -> "Tutorial";
                case EASY -> Language.easy();
                case MEDIUM -> Language.medium();
                case HARD -> Language.hard();
                case NIGHTMARE -> Language.nightmare();
                case ENDLESS -> Language.endless();
            };
        }
    }
}
