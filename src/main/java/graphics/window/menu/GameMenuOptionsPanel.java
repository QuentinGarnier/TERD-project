package graphics.window.menu;

import graphics.Language;
import graphics.Tools;
import graphics.window.GameWindow;
import graphics.window.GameWindow.KeyBindings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

public class GameMenuOptionsPanel extends GameMenuCustomPanel {
    private JButton backButton, validateButton, keysButton;
    private JLabel optionsLabel, setLangLabel, soundLabel, resolutionsLabel, difficultyLabel;
    private JComboBox resolutions;
    private int selectedRes;
    private static final String[] sizes = {"800 x 600", "1024 x 768", "1280 x 800", "1440 x 900", "1680 x 1050", "1920 x 1080"};
    private static char[] defaultKeys;
    private static ArrayList<JButton> keysButtons;

    private Language language;
    private JButton langENButton;
    private JButton langFRButton;
    private JButton langITButton;
    private JButton langARButton;
    private final JCheckBox soundCheckBox = new JCheckBox();

    private JPanel diffEasyPanel, diffMediumPanel, diffHardPanel, diffNightmarePanel, diffEndlessPanel;
    private JLabel diffEasyLabel, diffMediumLabel, diffHardLabel, diffNightmareLabel, diffEndlessLabel;
    private GameWindow.Difficulty difficultySelected;
    private final ImageIcon imgLocked;

    GameMenuOptionsPanel() {
        super();
        language = GameWindow.language();
        difficultySelected = GameWindow.difficulty();
        imgLocked = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("data/images/menu/locked.png")));
        keysButtons = new ArrayList<>();
    }
    
    void fillScreen() {
        setLayout(new BorderLayout());
        optionsLabel = createTitle(Language.options(), 26, Color.BLACK);
        add(optionsLabel, BorderLayout.NORTH);
        JPanel centerP = new JPanel(new BorderLayout());
        JPanel lastP = new JPanel(new BorderLayout());
        centerP.setOpaque(false);
        lastP.setOpaque(false);

        backButton = createMenuButton(Language.back());
        addMouseEffect(backButton, Effect.GOTO_START);
        validateButton = createMenuButton(Language.validate());
        addMouseEffect(validateButton, Effect.SAVE_SETTINGS);
        JPanel footer = new JPanel();
        footer.setOpaque(false);
        footer.add(backButton);
        footer.add(validateButton);
        add(footer, BorderLayout.SOUTH);

        JPanel langArea = new JPanel(new BorderLayout());
        langArea.setOpaque(false);
        langArea.setBorder(null);

        JPanel flagArea = new JPanel(new GridLayout(1,4));
        flagArea.setOpaque(false);
        flagArea.setBorder(BorderFactory.createLineBorder(new Color(0,0,0,0),14));
        flagArea.setMaximumSize(new Dimension(500, 75));
        langENButton = createFlagButton(Objects.requireNonNull(getClass().getClassLoader().getResource("data/images/menu/opt_uk.png")));
        langFRButton = createFlagButton(Objects.requireNonNull(getClass().getClassLoader().getResource("data/images/menu/opt_fr.png")));
        langITButton = createFlagButton(Objects.requireNonNull(getClass().getClassLoader().getResource("data/images/menu/opt_it.png")));
        langARButton = createFlagButton(Objects.requireNonNull(getClass().getClassLoader().getResource("data/images/menu/opt_ar.png")));

        langBorders();
        addMouseEffectFlag(langENButton, Language.EN);
        addMouseEffectFlag(langFRButton, Language.FR);
        addMouseEffectFlag(langITButton, Language.IT);
        addMouseEffectFlag(langARButton, Language.AR);

        addForFlagArea(flagArea, langENButton);
        addForFlagArea(flagArea, langFRButton);
        addForFlagArea(flagArea, langITButton);
        addForFlagArea(flagArea, langARButton);

        setLangLabel = createTitle(Language.selectTheLanguage(), 16, Color.BLACK);
        setLangLabel.setPreferredSize(new Dimension(500, 40));



        // ===== Middle panel (sound & resolution) =====
        JPanel inter = new JPanel(new GridLayout(1, 3, 20, 0));
        inter.setOpaque(false);


        //Sound part:
        soundLabel = createTitle(" " + Language.gameSound(), 16, Color.BLACK);
        JPanel interLeft = new JPanel(new GridLayout(1, 2));
        JPanel panelForBox = new JPanel(new BorderLayout());
        JPanel panelForLabel = new JPanel(new BorderLayout());
        JPanel aroundBox = new JPanel(new GridBagLayout());
        JPanel aroundLabel = new JPanel(new GridBagLayout());

        interLeft.setOpaque(false);
        panelForBox.setOpaque(false);
        panelForLabel.setOpaque(false);
        aroundBox.setOpaque(false);
        aroundLabel.setOpaque(false);

        aroundBox.add(soundCheckBox);
        panelForBox.add(aroundBox, BorderLayout.EAST);
        interLeft.add(panelForBox);
        aroundLabel.add(soundLabel);
        panelForLabel.add(aroundLabel, BorderLayout.WEST);
        interLeft.add(panelForLabel);
        inter.add(interLeft);

        soundCheckBox.setBorder(null);
        soundCheckBox.setOpaque(false);
        refreshCheckbox();
        soundCheckBox.addMouseListener(new MouseAdapter() {
            final Icon bg = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("data/images/system/checkbox.png")));
            final ImageIcon hover = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("data/images/system/checkbox_hover.png")));
            final Icon bgT = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("data/images/system/checkbox_true.png")));
            final ImageIcon hoverT = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("data/images/system/checkbox_true_hover.png")));
            @Override
            public void mouseClicked(MouseEvent e) {
                soundCheckBox.setIcon(soundCheckBox.isSelected()?hoverT:hover);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                soundCheckBox.setIcon(soundCheckBox.isSelected()?hoverT:hover);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                soundCheckBox.setIcon(soundCheckBox.isSelected()?bgT:bg);
            }
        });


        //Keys part:
        JPanel interCenter = new JPanel(new GridBagLayout());
        interCenter.setOpaque(false);

        keysButton = GameMenuCustomPanel.createMenuButton(Language.keyBindings());
        Color bg = keysButton.getBackground();
        keysButton.addActionListener(e -> {setKeys(); keysButton.setBackground(bg);});

        interCenter.add(keysButton);
        inter.add(interCenter);


        //Resolution part:
        JPanel interRight = new JPanel(new BorderLayout());
        JPanel resCenterPanel = new JPanel();
        resolutionsLabel = createTitle(Language.resolution(), 16, Color.BLACK);

        interRight.setOpaque(false);
        resCenterPanel.setOpaque(false);

        resolutions = new JComboBox(sizes);
        String sz = GameWindow.resolution()[0] + " x " + GameWindow.resolution()[1];
        for(int i = 0; i<resolutions.getItemCount(); i++) {
            if(resolutions.getItemAt(i).equals(sz)) {
                resolutions.setSelectedIndex(i);
                selectedRes = i;
                break;
            }
        }

        interRight.add(resolutionsLabel, BorderLayout.NORTH);
        resCenterPanel.add(resolutions);
        interRight.add(resCenterPanel);
        inter.add(interRight);



        // ===== Difficulties panel =====
        JPanel lastLastP = new JPanel();
        lastLastP.setOpaque(false);
        difficultyLabel = createTitle("— " + Language.chooseTheDifficulty() + " —", 18, Color.BLACK);
        JPanel bigDiffPanel = createDifficultiesPanel();
        lastP.add(bigDiffPanel);
        diffBorders();

        inter.setPreferredSize(new Dimension(600, 80));
        langArea.add(setLangLabel, BorderLayout.NORTH);
        langArea.add(flagArea, BorderLayout.SOUTH);
        lastP.add(inter, BorderLayout.NORTH);
        lastLastP.add(lastP, BorderLayout.NORTH);
        centerP.add(langArea, BorderLayout.NORTH);
        centerP.add(lastLastP);
        add(centerP);
    }

    private JPanel createDifficultiesPanel() {
        JPanel bigPanel = new JPanel(new BorderLayout());
        JPanel difficultiesPanel = new JPanel(new GridLayout(1, 5, 40, 0));
        bigPanel.setOpaque(false);
        difficultiesPanel.setOpaque(false);

        diffEasyLabel = createTitle(Language.easy(), 14, Color.BLACK);
        diffMediumLabel = createTitle(Language.medium(), 14, Color.BLACK);
        diffHardLabel = createTitle(Language.hard(), 14, Color.BLACK);
        diffNightmareLabel = createTitle(Language.nightmare(), 14, Color.BLACK);
        diffEndlessLabel = createTitle(Language.endless(), 14, Color.BLACK);

        boolean[] unlocked = GameWindow.getDifficultiesUnlocked();
        diffEasyPanel = buildDifficultyPanel(diffEasyLabel, Objects.requireNonNull(getClass().getClassLoader().getResource("data/images/menu/difficulty_easy.png")),
                Objects.requireNonNull(getClass().getClassLoader().getResource("data/images/menu/difficulty_easy_hover.png")), GameWindow.Difficulty.EASY, unlocked[0]);
        diffMediumPanel = buildDifficultyPanel(diffMediumLabel, Objects.requireNonNull(getClass().getClassLoader().getResource("data/images/menu/difficulty_medium.png")),
                Objects.requireNonNull(getClass().getClassLoader().getResource("data/images/menu/difficulty_medium_hover.png")), GameWindow.Difficulty.MEDIUM, unlocked[1]);
        diffHardPanel = buildDifficultyPanel(diffHardLabel, Objects.requireNonNull(getClass().getClassLoader().getResource("data/images/menu/difficulty_hard.png")),
                Objects.requireNonNull(getClass().getClassLoader().getResource("data/images/menu/difficulty_hard_hover.png")), GameWindow.Difficulty.HARD, unlocked[2]);
        diffNightmarePanel = buildDifficultyPanel(diffNightmareLabel, Objects.requireNonNull(getClass().getClassLoader().getResource("data/images/menu/difficulty_nightmare.png")),
                Objects.requireNonNull(getClass().getClassLoader().getResource("data/images/menu/difficulty_nightmare_hover.png")), GameWindow.Difficulty.NIGHTMARE, unlocked[3]);
        diffEndlessPanel = buildDifficultyPanel(diffEndlessLabel, Objects.requireNonNull(getClass().getClassLoader().getResource("data/images/menu/difficulty_endless.png")),
                Objects.requireNonNull(getClass().getClassLoader().getResource("data/images/menu/difficulty_endless_hover.png")), GameWindow.Difficulty.ENDLESS, unlocked[4]);

        difficultiesPanel.add(diffEasyPanel);
        difficultiesPanel.add(diffMediumPanel);
        difficultiesPanel.add(diffHardPanel);
        difficultiesPanel.add(diffNightmarePanel);
        difficultiesPanel.add(diffEndlessPanel);

        bigPanel.add(difficultyLabel, BorderLayout.NORTH);
        bigPanel.add(difficultiesPanel);
        return bigPanel;
    }

    private JPanel buildDifficultyPanel(JLabel labelName, URL pathImg, URL pathImgHover, GameWindow.Difficulty difficulty, boolean unlocked) {
        JPanel diffPanel = new JPanel(new BorderLayout());
        diffPanel.setBackground(Color.GRAY);

        JLabel diffImgLabel;
        ImageIcon img = new ImageIcon(pathImg);
        ImageIcon imgHover = new ImageIcon(pathImgHover);
        diffImgLabel = new JLabel(unlocked? img : imgLocked);

        diffPanel.add(labelName, BorderLayout.SOUTH);
        diffPanel.add(diffImgLabel);

        if(unlocked) diffPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setDifficulty(difficulty);
                diffBorders();
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                diffImgLabel.setIcon(imgHover);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                diffImgLabel.setIcon(img);
            }
        });

        return diffPanel;
    }

    private void setDifficulty(GameWindow.Difficulty d) {
        difficultySelected = d;
    }

    private void langBorders() {
        langENButton.setBorder(language == Language.EN? bigBorder(true) : bigBorder(false));
        langFRButton.setBorder(language == Language.FR? bigBorder(true) : bigBorder(false));
        langITButton.setBorder(language == Language.IT? bigBorder(true) : bigBorder(false));
        langARButton.setBorder(language == Language.AR? bigBorder(true) : bigBorder(false));
    }

    private void diffBorders() {
        diffEasyPanel.setBorder(difficultySelected == GameWindow.Difficulty.EASY? bigBorder(true) : bigBorder(false));
        diffMediumPanel.setBorder(difficultySelected == GameWindow.Difficulty.MEDIUM? bigBorder(true) : bigBorder(false));
        diffHardPanel.setBorder(difficultySelected == GameWindow.Difficulty.HARD? bigBorder(true) : bigBorder(false));
        diffNightmarePanel.setBorder(difficultySelected == GameWindow.Difficulty.NIGHTMARE? bigBorder(true) : bigBorder(false));
        diffEndlessPanel.setBorder(difficultySelected == GameWindow.Difficulty.ENDLESS? bigBorder(true) : bigBorder(false));
    }

    private void refreshCheckbox() {
        soundCheckBox.setSelected(GameWindow.hasSound());
        soundCheckBox.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("data/images/system/checkbox" + (soundCheckBox.isSelected()?"_true":"") + ".png"))));
    }

    private void addForFlagArea(JPanel area, JButton button) {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setOpaque(false);
        panel.add(button);
        area.add(panel);
    }

    private JButton createFlagButton(URL pathname) {
        ImageIcon img = new ImageIcon(pathname);
        JButton button = new JButton(img);
        button.setMaximumSize(new Dimension(175, 109));
        button.setBorder(null);
        return button;
    }

    void setTexts() {
        backButton.setText(Language.back());
        validateButton.setText(Language.validate());
        optionsLabel.setText(Language.options());
        setLangLabel.setText(Language.selectTheLanguage());
        soundLabel.setText(" " + Language.gameSound());
        keysButton.setText(Language.keyBindings());
        resolutionsLabel.setText(Language.resolution());

        difficultyLabel.setText("— " + Language.chooseTheDifficulty() + " —");
        diffEasyLabel.setText(Language.easy());
        diffMediumLabel.setText(Language.medium());
        diffHardLabel.setText(Language.hard());
        diffNightmareLabel.setText(Language.nightmare());
        diffEndlessLabel.setText(Language.endless());

        GameMenuPanel.getMenuPanel.setTexts();
    }

    private void setSettings() {
        selectedRes = resolutions.getSelectedIndex();
        String[] sz = sizes[resolutions.getSelectedIndex()].split(" x ");
        int[] res = {Integer.parseInt(sz[0]), Integer.parseInt(sz[1])};
        Tools.Settings.saveSettings(language, soundCheckBox.isSelected(), difficultySelected, GameWindow.getDifficultiesUnlocked(), res);
        GameWindow.setSettings(language, soundCheckBox.isSelected(), difficultySelected);
        setTexts();
        GameWindow.playOrStopMenuMusic();
        GameMenuPanel.getMenuPanel.displayStartScreen();
        GameWindow.window.setSize(res[0], res[1]);
        GameWindow.window.setLocationRelativeTo(null);
        GameMenuPanel.getMenuPanel.setTexts();
    }

    void prepareScreen() {
        language = GameWindow.language();
        soundCheckBox.setSelected(GameWindow.hasSound());
        difficultySelected = GameWindow.difficulty();
        defaultKeys = new char[KeyBindings.values().length];
        for(int i = 0; i < defaultKeys.length; i++) defaultKeys[i] = KeyBindings.values()[i].key;
        langBorders();
        diffBorders();
        refreshCheckbox();
    }


    private void addMouseEffectFlag(JButton button, Language l) {
        String langURL = switch (l) {
            case EN -> "uk";
            case FR -> "fr";
            case IT -> "it";
            case AR -> "ar";
        };
        Icon img = button.getIcon();
        ImageIcon hover = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("data/images/menu/opt_" + langURL + "_hover.png")));
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                language = l;
                langBorders();
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setIcon(hover);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                button.setIcon(img);
            }
        });
    }


    /**
     * Add an effect to a JButton (click and hover).
     * @param button a JButton, should be not null
     * @param effect one of GOTO_START or SAVE_SETTINGS
     */
    private void addMouseEffect(JButton button, Effect effect) {
        Color bg = button.getBackground();
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                button.setBackground(bg);
                switch (effect) {
                    case GOTO_START -> cancel();
                    case SAVE_SETTINGS -> setSettings();
                }
            }
        });
    }

    private void cancel() {
        resolutions.setSelectedIndex(selectedRes);
        KeyBindings.defaultKeys(defaultKeys);
        GameMenuPanel.getMenuPanel.displayStartScreen();
    }

    public static void setKeys() {
        JDialog dialog = new JDialog(GameWindow.window, true);
        JPanel panelTab = new JPanel(new GridLayout(0,1));
        JPanel buttons = new JPanel(new GridLayout(0, 3, 10, 0));

        dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        dialog.setUndecorated(true);
        panelTab.setFocusable(true);
        panelTab.setBorder(bigBorder(true));

        JButton bBack = createMenuButton(Language.cancel());
        JButton bDefault = createMenuButton(Language.reset());
        JButton bConfirm = createMenuButton(Language.confirm());
        bBack.addActionListener(e -> { KeyBindings.defaultKeys(defaultKeys); dialog.dispose(); });
        bDefault.addActionListener(e -> { KeyBindings.defaultKeys(); refreshButtonsText(); });
        bConfirm.addActionListener(e -> dialog.dispose());
        buttons.add(bBack);
        buttons.add(bDefault);
        buttons.add(bConfirm);

        keysButtons.clear();
        int x = 0;
        for (KeyBindings k : KeyBindings.values()) panelTab.add(makeLine(dialog, k, x++));
        panelTab.add(new JLabel());
        panelTab.add(buttons);
        dialog.setContentPane(panelTab);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    private static JPanel makeLine(JDialog father, KeyBindings k, int even) {
        JPanel panel = new JPanel(new GridLayout(0,2));
        JLabel label = new JLabel(" " + k.toString() + " ");
        JButton button = new JButton(k.key + "");
        if(even % 2 != 0) panel.setBackground(Color.LIGHT_GRAY);
        keysButtons.add(button);
        button.addActionListener(e -> {
            JDialog dialog = new JDialog(father, true);
            dialog.setUndecorated(true);
            JPanel panel1 = new JPanel();
            JLabel label1 = new JLabel(Language.enterKey() + " [A-Z]");
            panel1.setBorder(bigBorder(true));
            panel1.setFocusable(true);
            panel1.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    if((e.getKeyChar() >= 'a' && e.getKeyChar() <= 'z') || (e.getKeyChar() >= 'A' && e.getKeyChar() <= 'Z')) {
                        k.switchKey(Character.toUpperCase(e.getKeyChar()));
                        refreshButtonsText();
                        dialog.dispose();
                    }
                }
            });
            panel1.add(label1);
            dialog.setContentPane(panel1);
            dialog.pack();
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        });
        label.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(label);
        panel.add(button);
        return panel;
    }

    private static void refreshButtonsText() {
        for(int i = 0; i < keysButtons.size(); i++)
            keysButtons.get(i).setText(KeyBindings.values()[i].key + "");
    }


    private enum Effect {
        GOTO_START, SAVE_SETTINGS;
    }
}
