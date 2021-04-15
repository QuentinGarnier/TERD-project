package graphics.window.menu;

import graphics.Language;
import graphics.Tools;
import graphics.window.GameWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.util.Objects;

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
            final Icon bg = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("data/images/system/checkbox.png")));
            final ImageIcon hover = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("data/images/system/checkbox_hover.png")));
            final Icon bgT = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("data/images/system/checkbox_true.png")));
            final ImageIcon hoverT = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("data/images/system/checkbox_true_hover.png")));
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
        difficultyLabel = createTitle("— " + Language.chooseTheDifficulty() + " —", 18, Color.BLACK);
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

        diffEasyLabel = createTitle(Language.easy(), 14, Color.BLACK);
        diffMediumLabel = createTitle(Language.medium(), 14, Color.BLACK);
        diffHardLabel = createTitle(Language.hard(), 14, Color.BLACK);
        diffNightmareLabel = createTitle(Language.nightmare(), 14, Color.BLACK);
        diffEndlessLabel = createTitle(Language.endless(), 14, Color.BLACK);

        diffEasyPanel = buildDifficultyPanel(diffEasyLabel, Objects.requireNonNull(getClass().getClassLoader().getResource("data/images/menu/difficulty_easy.png")),
                Objects.requireNonNull(getClass().getClassLoader().getResource("data/images/menu/difficulty_easy_hover.png")), GameWindow.Difficulty.EASY);
        diffMediumPanel = buildDifficultyPanel(diffMediumLabel, Objects.requireNonNull(getClass().getClassLoader().getResource("data/images/menu/difficulty_medium.png")),
                Objects.requireNonNull(getClass().getClassLoader().getResource("data/images/menu/difficulty_medium_hover.png")), GameWindow.Difficulty.MEDIUM);
        diffHardPanel = buildDifficultyPanel(diffHardLabel, Objects.requireNonNull(getClass().getClassLoader().getResource("data/images/menu/difficulty_hard.png")),
                Objects.requireNonNull(getClass().getClassLoader().getResource("data/images/menu/difficulty_hard_hover.png")), GameWindow.Difficulty.HARD);
        diffNightmarePanel = buildDifficultyPanel(diffNightmareLabel, Objects.requireNonNull(getClass().getClassLoader().getResource("data/images/menu/difficulty_nightmare.png")),
                Objects.requireNonNull(getClass().getClassLoader().getResource("data/images/menu/difficulty_nightmare_hover.png")), GameWindow.Difficulty.NIGHTMARE);
        diffEndlessPanel = buildDifficultyPanel(diffEndlessLabel, Objects.requireNonNull(getClass().getClassLoader().getResource("data/images/menu/difficulty_endless.png")),
                Objects.requireNonNull(getClass().getClassLoader().getResource("data/images/menu/difficulty_endless_hover.png")), GameWindow.Difficulty.ENDLESS);

        difficultiesPanel.add(diffEasyPanel);
        difficultiesPanel.add(diffMediumPanel);
        difficultiesPanel.add(diffHardPanel);
        difficultiesPanel.add(diffNightmarePanel);
        difficultiesPanel.add(diffEndlessPanel);

        bigPanel.add(difficultyLabel, BorderLayout.NORTH);
        bigPanel.add(difficultiesPanel);
        return bigPanel;
    }

    private JPanel buildDifficultyPanel(JLabel labelName, URL pathImg, URL pathImgHover, GameWindow.Difficulty difficulty) {
        JPanel diffPanel = new JPanel(new BorderLayout());
        diffPanel.setBackground(Color.GRAY);

        ImageIcon img = new ImageIcon(pathImg);
        ImageIcon imgHover = new ImageIcon(pathImgHover);
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
        muteLabel.setText(Language.gameSound() + " ");

        difficultyLabel.setText("— " + Language.chooseTheDifficulty() + " —");
        diffEasyLabel.setText(Language.easy());
        diffMediumLabel.setText(Language.medium());
        diffHardLabel.setText(Language.hard());
        diffNightmareLabel.setText(Language.nightmare());
        diffEndlessLabel.setText(Language.endless());

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
        ImageIcon hover = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("data/images/menu/opt_" + langURL + "_hover.png")));
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
