package graphics.window;

import entity.Player;
import graphics.Language;
import graphics.Tools;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GameMenuPanel extends JPanel {
    private final JPanel startScreen = new JPanel();  //Launch a new game, load a save, see options/credits or quit the game.
    private final JPanel charaScreen = new JPanel();  //Select a character between 3 specialities: warrior, archer or mage.
    private final JPanel optionsScreen = new JPanel();  //Change options of the game, like language.
    private int state;  //0 for startScreen, 1 for charaScreen and 2 for optionsScreen.

    private JPanel warSpecPanel;
    private JPanel arcSpecPanel;
    private JPanel magSpecPanel;
    private int charaSelected = 0;
    private final JLabel descriptionLabel = new JLabel(descriptionForSpec());
    private final JCheckBox soundCheckBox = new JCheckBox();

    private Language language;
    private JButton langENButton;
    private JButton langFRButton;
    private JButton langITButton;
    private JButton langARButton;

    private JButton newGameButton, optionsButton, exitButton, backButton, backButton2, launchButton,validateButton;
    private JLabel specialityLabel, warLabel, arcLabel, magLabel, optionsLabel, setLangLabel, muteLabel;

    GameMenuPanel() {
        super();
        setLayout(new BorderLayout());
        setFocusable(true);
        setBackground(Color.DARK_GRAY);

        language = GameWindow.language();

        setupScreens();
        warSpecPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Tools.WindowText.golden, Tools.WindowText.dark_golden));

        state = 0;
        replaceWith(startScreen);
    }

    private void setupScreens() {
        setupScreen(startScreen);
        setupScreen(charaScreen);
        setupScreen(optionsScreen);

        fillStartScreen();
        fillCharaScreen();
        fillOptionsScreen();
    }

    private void setupScreen(JPanel screen) {
        Dimension dim = new Dimension(500, 500);
        screen.setPreferredSize(dim);
        screen.setBackground(Color.GRAY);
        screen.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
    }

    private void fillStartScreen() {
        startScreen.setLayout(new GridLayout(5, 1));
        startScreen.add(createTitle("That time the Hero saved the Village", 32, Tools.WindowText.red));

        newGameButton = createMenuButton(Language.newGame());
        optionsButton = createMenuButton(Language.options());
        exitButton = createMenuButton(Language.exitGame());
        addMenuButton(newGameButton, startScreen);
        addMenuButton(optionsButton, startScreen);
        addMenuButton(exitButton, startScreen);

        addMouseEffect(newGameButton, 0);
        addMouseEffect(optionsButton, 1);
        addMouseEffect(exitButton, 2);
    }

    private void fillCharaScreen() {
        charaScreen.setLayout(new GridBagLayout());
        GridBagConstraints cons = new GridBagConstraints();

        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.weightx = 1;
        cons.gridwidth = 3;
        cons.gridx = 0;
        cons.gridy = 0;
        cons.weighty = 0.1;
        specialityLabel = createTitle(Language.chooseYourSpeciality(), 26, Color.BLACK);
        charaScreen.add(specialityLabel, cons);

        cons.weighty = 0.8;
        cons.gridx = 0;
        cons.gridy = 1;
        cons.fill = GridBagConstraints.BOTH;
        charaScreen.add(createSpecPanel(), cons);

        backButton = createMenuButton(Language.back());
        addMouseEffect(backButton, 3);
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.gridwidth = 1;
        cons.gridx = 0;
        cons.gridy = 2;
        cons.weightx = 0.2;
        cons.weighty = 0.1;
        cons.ipadx = 40;
        charaScreen.add(backButton, cons);

        cons.gridx = 1;
        cons.weightx = 0.5;
        charaScreen.add(new JLabel(), cons);

        launchButton = createMenuButton(Language.startTheQuest());
        addMouseEffect(launchButton, 4);
        cons.gridx = 2;
        cons.weightx = 0.3;
        charaScreen.add(launchButton, cons);
    }

    private void fillOptionsScreen() {
        optionsScreen.setLayout(new BorderLayout());
        optionsLabel = createTitle(Language.options(), 26, Color.BLACK);
        optionsScreen.add(optionsLabel, BorderLayout.NORTH);
        JPanel centerP = new JPanel(new BorderLayout());
        JPanel lastP = new JPanel(new BorderLayout());
        centerP.setBackground(Color.GRAY);
        lastP.setBackground(Color.GRAY);

        backButton2 = createMenuButton(Language.back());
        addMouseEffect(backButton2, 3);
        validateButton = createMenuButton(Language.validate());
        addMouseEffect(validateButton, 5);
        JPanel footer = new JPanel();
        footer.setBackground(Color.GRAY);
        footer.add(backButton2);
        footer.add(validateButton);
        optionsScreen.add(footer, BorderLayout.SOUTH);

        JPanel langArea = new JPanel(new BorderLayout());
        langArea.setBackground(Color.GRAY);
        langArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        JPanel flagArea = new JPanel(new GridLayout(1,3));
        flagArea.setBackground(Color.GRAY);
        flagArea.setMaximumSize(new Dimension(500, 109));
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
        muteP.setBackground(Color.GRAY);
        muteLabel = createTitle(Language.gameSound(), 16, Color.BLACK);
        muteLabel.setPreferredSize(new Dimension(500, 40));
        muteLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        muteP.add(muteLabel);

        JPanel checkBoxPanel = new JPanel(new BorderLayout());
        checkBoxPanel.setBackground(Color.GRAY);
        checkBoxPanel.add(soundCheckBox, BorderLayout.WEST);
        soundCheckBox.setBackground(Color.GRAY);
        soundCheckBox.setSelected(!GameWindow.isMuted());

        muteP.add(checkBoxPanel);
        muteP.setPreferredSize(new Dimension(500, 100));
        langArea.add(setLangLabel, BorderLayout.NORTH);
        langArea.add(flagArea, BorderLayout.SOUTH);
        centerP.add(langArea, BorderLayout.NORTH);
        centerP.add(lastP);
        lastP.add(muteP, BorderLayout.NORTH);
        optionsScreen.add(centerP);
    }

    private void langBorders() {
        langENButton.setBorder(language == Language.EN? bigBorder(true) : bigBorder(false));
        langFRButton.setBorder(language == Language.FR? bigBorder(true) : bigBorder(false));
        langITButton.setBorder(language == Language.IT? bigBorder(true) : bigBorder(false));
        langARButton.setBorder(language == Language.AR? bigBorder(true) : bigBorder(false));
    }

    private Border bigBorder(boolean colored) {
        return BorderFactory.createCompoundBorder(
                (colored? BorderFactory.createBevelBorder(BevelBorder.RAISED, Tools.WindowText.golden, Tools.WindowText.dark_golden): BorderFactory.createBevelBorder(BevelBorder.RAISED)),
                BorderFactory.createLineBorder((colored? Tools.WindowText.golden: Color.WHITE), 4));
    }

    private void addForFlagArea(JPanel area, JButton button) {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBackground(Color.GRAY);
        panel.add(button);
        area.add(panel);
    }

    private JButton createFlagButton(String pathname) {
        ImageIcon img = new ImageIcon(pathname);
        JButton button = new JButton(img);
        button.setMaximumSize(new Dimension(175, 109));
        button.setBorder(null);
        //button.setContentAreaFilled(false);
        return button;
    }

    void display() {
        switch (state) {
            case 0 -> displayStartScreen();
            case 1 -> displayCharaScreen();
            case 2 -> displayOptionsScreen();
        }
    }

    private void displayStartScreen() {
        goToScreen(0);
    }

    private void displayCharaScreen() {
        goToScreen(1);
    }

    private void displayOptionsScreen() {
        goToScreen(2);
    }

    /**
     * Go to the screen with the associated number.
     * @param n one of 0 (StartScreen), 1 (CharaScreen) or 2 (OptionsScreen)
     */
    private void goToScreen(int n) {
        if(state != n) switch (n) {
            case 0 -> replaceWith(startScreen);
            case 1 -> replaceWith(charaScreen);
            case 2 -> {
                soundCheckBox.setSelected(!GameWindow.isMuted());
                language = GameWindow.language();
                langBorders();
                replaceWith(optionsScreen);
            }
        }
        state = n;
    }

    private void replaceWith(JPanel jPanel) {
        if(getComponentCount() > 0) removeAll();
        add(jPanel);
        add(newBorder(), BorderLayout.NORTH);
        add(newBorder(), BorderLayout.EAST);
        add(newBorder(), BorderLayout.SOUTH);
        add(newBorder(), BorderLayout.WEST);
        repaint();
        revalidate();
    }

    private JPanel newBorder() {
        JPanel jPanel = new JPanel();
        jPanel.setBackground(Color.DARK_GRAY);
        jPanel.setPreferredSize(new Dimension(80, 80));
        return jPanel;
    }

    private JPanel createSpecPanel() {
        JPanel bigPanel = new JPanel(new BorderLayout());
        bigPanel.setBackground(Color.BLACK);
        JPanel specialitiesPanel = new JPanel(new GridLayout(1, 3));
        specialitiesPanel.setBackground(Color.BLACK);
        warLabel = createTitle(Language.warriorCL(), 16, Tools.WindowText.red);
        arcLabel = createTitle(Language.archerCL(), 16, Tools.WindowText.green);
        magLabel = createTitle(Language.mageCL(), 16, Tools.WindowText.blue);
        warSpecPanel = buildSpecPanel(warLabel, "data/images/menu/spec_war.png", 0);
        arcSpecPanel = buildSpecPanel(arcLabel, "data/images/menu/spec_arc.png", 1);
        magSpecPanel = buildSpecPanel(magLabel, "data/images/menu/spec_mag.png", 2);

        specialitiesPanel.add(warSpecPanel);
        specialitiesPanel.add(arcSpecPanel);
        specialitiesPanel.add(magSpecPanel);

        JPanel descriptionPanel = new JPanel();
        descriptionPanel.setBackground(Color.GRAY);
        descriptionPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        descriptionPanel.add(descriptionLabel);

        bigPanel.add(specialitiesPanel);
        bigPanel.add(descriptionPanel, BorderLayout.SOUTH);
        return bigPanel;
    }

    /**
     * Set the border of each panel when focused.
     * @param n one of 0 (warSpecPanel), 1 (arcSpecPanel) or 2 (magSpecPanel)
     */
    private void specBorderOn(int n) {
        warSpecPanel.setBorder(n == 0? BorderFactory.createBevelBorder(BevelBorder.RAISED, Tools.WindowText.golden, Tools.WindowText.dark_golden) : BorderFactory.createBevelBorder(BevelBorder.RAISED));
        arcSpecPanel.setBorder(n == 1? BorderFactory.createBevelBorder(BevelBorder.RAISED, Tools.WindowText.golden, Tools.WindowText.dark_golden) : BorderFactory.createBevelBorder(BevelBorder.RAISED));
        magSpecPanel.setBorder(n == 2? BorderFactory.createBevelBorder(BevelBorder.RAISED, Tools.WindowText.golden, Tools.WindowText.dark_golden) : BorderFactory.createBevelBorder(BevelBorder.RAISED));
    }

    private JPanel buildSpecPanel(JLabel nameLabel, String pathImg, int numMouseEffect) {
        JPanel specPanel = new JPanel(new BorderLayout());
        JLabel specImgLabel = new JLabel(new ImageIcon(pathImg));

        specPanel.setBackground(Color.BLACK);
        specPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        nameLabel.setPreferredSize(new Dimension(0, 40));

        specPanel.add(specImgLabel);
        specPanel.add(nameLabel, BorderLayout.SOUTH);

        Color hoverColor = new Color(20, 20, 20);
        specPanel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setCharaSelected(numMouseEffect);
                specBorderOn(numMouseEffect);
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {
                specPanel.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                specPanel.setBackground(Color.BLACK);
            }
        });

        return specPanel;
    }

    private JLabel createTitle(String txt, int fontSize, Color c) {
        JLabel title = new JLabel(txt);
        Font font = title.getFont();
        title.setFont(new Font(font.getName(), Font.BOLD, fontSize));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setForeground(c);
        return title;
    }

    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(120, 90, 60));
        button.setForeground(new Color(0, 0, 0));
        Font font = button.getFont();
        button.setFont(new Font(font.getName(), Font.BOLD, 20));
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        button.setFocusable(false);
        return button;
    }

    private void addMenuButton(JButton button, JPanel screen) {
        JPanel centerP = new JPanel(new GridLayout(1, 3));
        centerP.setBackground(Color.GRAY);
        centerP.add(new JLabel());
        centerP.add(button);
        centerP.add(new JLabel());
        screen.add(centerP);
    }

    private String descriptionForSpec() {
        return switch (charaSelected) {
            case 0 -> Language.warriorDescription();
            case 1 -> Language.archerDescription();
            case 2 -> Language.mageDescription();
            default -> "No speciality selected.";
        };
    }

    private void setCharaSelected(int n) {
        charaSelected = n;
        descriptionLabel.setText(descriptionForSpec());
    }

    /**
     * Add an effect to a JButton (click and hover).
     * @param button a JButton, should be not null
     * @param effect one of 0 (new game), 1 (options), 2 (exit), 3 (back to StartScreen), 4 (launch) or 5 (save settings)
     */
    private void addMouseEffect(JButton button, int effect) {
        Color bg = button.getBackground();
        Color hoverBG = new Color(120, 80, 60);

        button.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                button.setBackground(bg);
                switch (effect) {
                    case 0 -> goToScreen(1);
                    case 1 -> goToScreen(2);
                    case 2 -> System.exit(0);
                    case 3 -> goToScreen(0);
                    case 4 -> launch();
                    case 5 -> setSettings();
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

    //Launch the game:
    private void launch() {
        state = 0;
        Player.chooseSpeciality(charaSelected);
        GameWindow.enterInGame();
    }

    private void setSettings() {
        Tools.Settings.saveSettings(language, soundCheckBox.isSelected());
        GameWindow.setSettings(language, soundCheckBox.isSelected());
        setTexts();
        GameWindow.playOrStopMenuMusic();
    }

    private void setTexts() {
        newGameButton.setText(Language.newGame());
        optionsButton.setText(Language.options());
        exitButton.setText(Language.exitGame());
        backButton.setText(Language.back());
        backButton2.setText(Language.back());
        launchButton.setText(Language.startTheQuest());
        validateButton.setText(Language.validate());
        optionsLabel.setText(Language.options());
        setLangLabel.setText(Language.selectTheLanguage());
        muteLabel.setText(Language.gameSound());
        specialityLabel.setText(Language.chooseYourSpeciality());
        warLabel.setText(Language.warriorCL());
        arcLabel.setText(Language.archerCL());
        magLabel.setText(Language.mageCL());
        setCharaSelected(charaSelected);
    }
}
