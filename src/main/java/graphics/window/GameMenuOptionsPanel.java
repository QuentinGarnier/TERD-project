package graphics.window;

import graphics.Language;
import graphics.Tools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GameMenuOptionsPanel extends GameMenuCustomPanel {
    private JButton backButton, validateButton;
    private JLabel optionsLabel, setLangLabel, muteLabel;

    private Language language;
    private JButton langENButton;
    private JButton langFRButton;
    private JButton langITButton;
    private JButton langARButton;
    private final JCheckBox soundCheckBox = new JCheckBox();

    GameMenuOptionsPanel() {
        super();
        language = GameWindow.language();
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
        muteLabel = createTitle(Language.gameSound(), 16, Color.BLACK);
        muteLabel.setPreferredSize(new Dimension(500, 40));
        muteLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        muteP.add(muteLabel);

        JPanel checkBoxPanel = new JPanel(new BorderLayout());
        checkBoxPanel.setOpaque(false);
        checkBoxPanel.add(soundCheckBox, BorderLayout.WEST);
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

        muteP.add(checkBoxPanel);
        muteP.setPreferredSize(new Dimension(500, 100));
        langArea.add(setLangLabel, BorderLayout.NORTH);
        langArea.add(flagArea, BorderLayout.SOUTH);
        centerP.add(langArea, BorderLayout.NORTH);
        centerP.add(lastP);
        lastP.add(muteP, BorderLayout.NORTH);
        add(centerP);
    }

    private void langBorders() {
        langENButton.setBorder(language == Language.EN? bigBorder(true) : bigBorder(false));
        langFRButton.setBorder(language == Language.FR? bigBorder(true) : bigBorder(false));
        langITButton.setBorder(language == Language.IT? bigBorder(true) : bigBorder(false));
        langARButton.setBorder(language == Language.AR? bigBorder(true) : bigBorder(false));
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
        muteLabel.setText(Language.gameSound());

        GameMenuPanel.getMenuPanel.setTexts();
    }

    private void setSettings() {
        Tools.Settings.saveSettings(language, soundCheckBox.isSelected());
        GameWindow.setSettings(language, soundCheckBox.isSelected());
        setTexts();
        GameWindow.playOrStopMenuMusic();
        GameMenuPanel.getMenuPanel.displayStartScreen();
    }

    void prepareScreen() {
        language = GameWindow.language();
        soundCheckBox.setSelected(!GameWindow.isMuted());
        langBorders();
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
