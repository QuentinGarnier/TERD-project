package graphics.window.menu;

import graphics.Language;
import graphics.Tools;
import graphics.window.GameWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GameMenuOptionsPanel extends GameMenuCustomPanel {
    private JButton backButton, validateButton;
    private JLabel optionsLabel, setLangLabel, muteLabel, difficultyLabel;

    private Language language;
    private JButton langENButton;
    private JButton langFRButton;
    private JButton langITButton;
    private JButton langARButton;
    private final JCheckBox soundCheckBox = new JCheckBox();

    private JPanel diffEasyPanel, diffMediumPanel, diffHardPanel, diffNightmarePanel, diffEndlessPanel;
    private JLabel diffEasyLabel, diffMediumLabel, diffHardLabel, diffNightmareLabel, diffEndlessLabel;
    private GameWindow.Difficulty difficultySelected;

    GameMenuOptionsPanel() {
        super();
        language = GameWindow.language();
        difficultySelected = GameWindow.difficulty();
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
        langENButton = createFlagButton("data/images/menu/opt_uk.png");
        langFRButton = createFlagButton("data/images/menu/opt_fr.png");
        langITButton = createFlagButton("data/images/menu/opt_it.png");
        langARButton = createFlagButton("data/images/menu/opt_ar.png");
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

        JPanel muteP = new JPanel(new GridLayout(1, 2));
        muteP.setOpaque(false);
        muteLabel = createTitle(Language.gameSound() + " ", 16, Color.BLACK);
        muteLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        JPanel inter = new JPanel(new BorderLayout());
        inter.setOpaque(false);
        inter.add(muteLabel, BorderLayout.NORTH);
        muteP.add(inter);

        JPanel checkBoxPanel = new JPanel(new BorderLayout());
        checkBoxPanel.setOpaque(false);
        JPanel cbp = new JPanel(new BorderLayout());
        cbp.setPreferredSize(new Dimension(32, 32));
        cbp.setOpaque(false);
        cbp.add(soundCheckBox, BorderLayout.NORTH);
        checkBoxPanel.add(cbp, BorderLayout.WEST);
        soundCheckBox.setBorder(null);
        soundCheckBox.setOpaque(false);
        refreshCheckbox();
        soundCheckBox.addMouseListener(new MouseListener() {
            final Icon bg = new ImageIcon("data/images/system/checkbox.png");
            final ImageIcon hover = new ImageIcon("data/images/system/checkbox_hover.png");
            final Icon bgT = new ImageIcon("data/images/system/checkbox_true.png");
            final ImageIcon hoverT = new ImageIcon("data/images/system/checkbox_true_hover.png");
            @Override
            public void mouseClicked(MouseEvent e) {
                soundCheckBox.setIcon(soundCheckBox.isSelected()?hoverT:hover);
            }
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {
                soundCheckBox.setIcon(soundCheckBox.isSelected()?hoverT:hover);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                soundCheckBox.setIcon(soundCheckBox.isSelected()?bgT:bg);
            }
        });

        JPanel lastLastP = new JPanel();
        lastLastP.setOpaque(false);
        difficultyLabel = createTitle("— " + "Choose the difficulty" + " —", 18, Color.BLACK); //TODO: adapt with Language.chooseTheDifficulty()
        lastP.add(createDifficultiesPanel());
        diffBorders();

        muteP.add(checkBoxPanel);
        muteP.setPreferredSize(new Dimension(500, 50));
        langArea.add(setLangLabel, BorderLayout.NORTH);
        langArea.add(flagArea, BorderLayout.SOUTH);
        lastP.add(muteP, BorderLayout.NORTH);
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

        diffEasyLabel = createTitle("Easy", 14, Color.BLACK); //TODO: remplacer par "Language.xxxx()" pour les 5 labels ici
        diffMediumLabel = createTitle("Medium", 14, Color.BLACK);
        diffHardLabel = createTitle("Hard", 14, Color.BLACK);
        diffNightmareLabel = createTitle("Nightmare", 14, Color.BLACK);
        diffEndlessLabel = createTitle("Endless", 14, Color.BLACK);

        diffEasyPanel = buildDifficultyPanel(diffEasyLabel, "data/images/menu/difficulty_easy", GameWindow.Difficulty.EASY);
        diffMediumPanel = buildDifficultyPanel(diffMediumLabel, "data/images/menu/difficulty_medium", GameWindow.Difficulty.MEDIUM);
        diffHardPanel = buildDifficultyPanel(diffHardLabel, "data/images/menu/difficulty_hard", GameWindow.Difficulty.HARD);
        diffNightmarePanel = buildDifficultyPanel(diffNightmareLabel, "data/images/menu/difficulty_nightmare", GameWindow.Difficulty.NIGHTMARE);
        diffEndlessPanel = buildDifficultyPanel(diffEndlessLabel, "data/images/menu/difficulty_endless", GameWindow.Difficulty.ENDLESS);

        difficultiesPanel.add(diffEasyPanel);
        difficultiesPanel.add(diffMediumPanel);
        difficultiesPanel.add(diffHardPanel);
        difficultiesPanel.add(diffNightmarePanel);
        difficultiesPanel.add(diffEndlessPanel);

        bigPanel.add(difficultyLabel, BorderLayout.NORTH);
        bigPanel.add(difficultiesPanel);
        return bigPanel;
    }

    private JPanel buildDifficultyPanel(JLabel labelName, String pathImg, GameWindow.Difficulty difficulty) {
        JPanel diffPanel = new JPanel(new BorderLayout());
        diffPanel.setBackground(Color.GRAY);

        ImageIcon img = new ImageIcon(pathImg + ".png");
        ImageIcon imgHover = new ImageIcon(pathImg + "_hover.png");
        JLabel diffImgLabel = new JLabel(img);

        diffPanel.add(labelName, BorderLayout.SOUTH);
        diffPanel.add(diffImgLabel);

        diffPanel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setDifficulty(difficulty);
                diffBorders();
            }
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
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
        soundCheckBox.setSelected(!GameWindow.isMuted());
        soundCheckBox.setIcon(new ImageIcon("data/images/system/checkbox" + (soundCheckBox.isSelected()?"_true":"") + ".png"));
    }

    private void addForFlagArea(JPanel area, JButton button) {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setOpaque(false);
        panel.add(button);
        area.add(panel);
    }

    private JButton createFlagButton(String pathname) {
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
        muteLabel.setText(Language.gameSound() + " ");

        difficultyLabel.setText("— " + "Choose the difficulty"/*Language.chooseTheDifficulty()*/ + " —");  //TODO: remplacer par "Language.xxxx()" pour les 6 labels ici
        diffEasyLabel.setText("Easy"/*Language.easy()*/);
        diffMediumLabel.setText("Medium"/*Language.medium()*/);
        diffHardLabel.setText("Hard"/*Language.hard()*/);
        diffNightmareLabel.setText("Nightmare"/*Language.nightmare()*/);
        diffEndlessLabel.setText("Endless"/*Language.endless()*/);

        GameMenuPanel.getMenuPanel.setTexts();
    }

    private void setSettings() {
        Tools.Settings.saveSettings(language, soundCheckBox.isSelected(), difficultySelected);
        GameWindow.setSettings(language, soundCheckBox.isSelected(), difficultySelected);
        setTexts();
        GameWindow.playOrStopMenuMusic();
        GameMenuPanel.getMenuPanel.displayStartScreen();
    }

    void prepareScreen() {
        language = GameWindow.language();
        soundCheckBox.setSelected(!GameWindow.isMuted());
        difficultySelected = GameWindow.difficulty();
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
        ImageIcon hover = new ImageIcon("data/images/menu/opt_" + langURL + "_hover.png");
        button.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                language = l;
                langBorders();
            }
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
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
        Color hoverBG = new Color(180, 150, 110);

        button.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                button.setBackground(bg);
                switch (effect) {
                    case GOTO_START -> GameMenuPanel.getMenuPanel.displayStartScreen();
                    case SAVE_SETTINGS -> setSettings();
                }
            }
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverBG);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(bg);
            }
        });
    }


    private enum Effect {
        GOTO_START, SAVE_SETTINGS;
    }
}
