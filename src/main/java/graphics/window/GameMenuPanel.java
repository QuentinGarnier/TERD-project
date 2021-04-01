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
    public final static GameMenuPanel getMenuPanel = new GameMenuPanel();
    private final JPanel startScreen = new JPanel();  //Launch a new game, load a save, see options/credits or quit the game.
    private final JPanel charaScreen = new JPanel();  //Select a character between 3 specialities: warrior, archer or mage.
    private final JPanel optionsScreen = new JPanel();  //Change options of the game, like language.
    private Screen state;

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

    private JButton newGameButton, optionsButton, helpButton, exitButton, backButton, backButton2, launchButton,validateButton;
    private JLabel specialityLabel, warLabel, arcLabel, magLabel, optionsLabel, setLangLabel, muteLabel;

    public final Color colorBG = new Color(60, 100, 120, 180);

    private final int screenWidth, screenHeight;

    private GameMenuPanel() {
        super();
        setLayout(new BorderLayout());
        setFocusable(true);
        setBackground(Color.DARK_GRAY);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenWidth = screenSize.width;
        screenHeight = screenSize.height;

        language = GameWindow.language();

        setupScreens();
        warSpecPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Tools.WindowText.golden, Tools.WindowText.dark_golden));

        state = Screen.START;
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
        screen.setBackground(colorBG);
        Color colorBGDark = new Color(20, 50, 70, 180);
        screen.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, colorBG, colorBGDark));
    }

    private void fillStartScreen() {
        startScreen.setLayout(new BorderLayout());
        JPanel midPanel = new JPanel(new GridLayout(4, 1));

        newGameButton = createMenuButton(Language.newGame());
        optionsButton = createMenuButton(Language.options());
        helpButton = createMenuButton("Help");
        exitButton = createMenuButton(Language.exitGame());
        addMenuButton(newGameButton, midPanel);
        addMenuButton(optionsButton, midPanel);
        addMenuButton(helpButton, midPanel);
        addMenuButton(exitButton, midPanel);

        addMouseEffect(newGameButton, Effect.GOTO_CHARA);
        addMouseEffect(optionsButton, Effect.GOTO_OPTIONS);
        addMouseEffect(helpButton, Effect.GOTO_HELP);
        addMouseEffect(exitButton, Effect.EXIT);

        JPanel titlePanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel(new ImageIcon("data/images/menu/title.png"));
        titlePanel.add(titleLabel);
        titlePanel.setPreferredSize(new Dimension(800, 146));
        titlePanel.setOpaque(false);
        midPanel.setBorder(null);
        midPanel.setOpaque(false);

        startScreen.add(midPanel);
        startScreen.add(titlePanel, BorderLayout.NORTH);
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
        addMouseEffect(backButton, Effect.GOTO_START);
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
        addMouseEffect(launchButton, Effect.LAUNCH);
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
        centerP.setOpaque(false);
        lastP.setOpaque(false);

        backButton2 = createMenuButton(Language.back());
        addMouseEffect(backButton2, Effect.GOTO_START);
        validateButton = createMenuButton(Language.validate());
        addMouseEffect(validateButton, Effect.SAVE_SETTINGS);
        JPanel footer = new JPanel();
        footer.setOpaque(false);
        footer.add(backButton2);
        footer.add(validateButton);
        optionsScreen.add(footer, BorderLayout.SOUTH);

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
                BorderFactory.createCompoundBorder(
                        (colored? BorderFactory.createBevelBorder(BevelBorder.RAISED, Tools.WindowText.golden, Tools.WindowText.dark_golden): BorderFactory.createBevelBorder(BevelBorder.RAISED)),
                        BorderFactory.createLineBorder((colored? Tools.WindowText.golden: Color.WHITE), 3)),
                BorderFactory.createLineBorder(colored?Tools.WindowText.dark_golden:Color.GRAY));
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

    void display() {
        switch (state) {
            case START -> displayStartScreen();
            case CHARA -> displayCharaScreen();
            case OPTIONS -> displayOptionsScreen();
            case HELP -> displayHelpScreen();
        }
    }

    private void displayStartScreen() {
        goToScreen(Screen.START);
    }

    private void displayCharaScreen() {
        goToScreen(Screen.CHARA);
    }

    private void displayOptionsScreen() {
        goToScreen(Screen.OPTIONS);
    }

    private void displayHelpScreen(){
        goToScreen(Screen.HELP);
    }

    /**
     * Go to the screen with the associated number.
     * @param screen one of START, CHARA, OPTIONS or HELP
     */
    private void goToScreen(Screen screen) {
        if(state != screen) switch (screen) {
            case START -> replaceWith(startScreen);
            case CHARA -> replaceWith(charaScreen);
            case OPTIONS -> {
                soundCheckBox.setSelected(!GameWindow.isMuted());
                language = GameWindow.language();
                langBorders();
                refreshCheckbox();
                replaceWith(optionsScreen);
            }
            case HELP -> {
                // TODO : ADD INFO PANEL
                JPanel panel = new JPanel();
                JButton b = new JButton("HELP");
                b.addActionListener(e -> {
                    goToScreen(Screen.START);
                });
                panel.add(b);
                replaceWith(panel);
            }
        }
        state = screen;
    }

    private void refreshCheckbox() {
        soundCheckBox.setSelected(!GameWindow.isMuted());
        soundCheckBox.setIcon(new ImageIcon("data/images/system/checkbox" + (soundCheckBox.isSelected()?"_true":"") + ".png"));
    }

    public static void replaceWith() {
        getMenuPanel.replaceWith(getMenuPanel.startScreen);
    }

    private void replaceWith(JPanel jPanel) {
        if(getComponentCount() > 0) removeAll();

        JLabel backgroundImage = new JLabel(new ImageIcon("data/images/system/title.png"));
        backgroundImage.setBounds(0, 0, screenWidth, screenHeight);
        add(backgroundImage);

        add(jPanel, 0);
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
        jPanel.setPreferredSize(new Dimension(20, 60));
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

    static JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(text.length() * 16 + 32,40));
        button.setBackground(new Color(220, 200, 160));
        button.setForeground(Color.BLACK);
        button.setIcon(new ImageIcon(""));
        Font font = button.getFont();
        button.setFont(new Font(font.getName(), Font.BOLD, 18));
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createBevelBorder(BevelBorder.RAISED, Tools.WindowText.golden, Tools.WindowText.dark_golden),
                        BorderFactory.createLineBorder(Tools.WindowText.golden, 4)),
                BorderFactory.createLineBorder(new Color(140, 110, 70))));
        button.setFocusable(false);
        return button;
    }

    private void addMenuButton(JButton button, JPanel screen) {
        JPanel centerP = new JPanel(new GridLayout(1, 3));
        centerP.setOpaque(false);
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
     * @param effect one of GOTO_CHARA, GOTO_OPTIONS, EXIT, GOTO_START, LAUNCH or SAVE_SETTINGS
     */
    private void addMouseEffect(JButton button, Effect effect) {
        Color bg = button.getBackground();
        Color hoverBG = new Color(180, 150, 110);

        button.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                button.setBackground(bg);
                switch (effect) {
                    case GOTO_CHARA -> displayCharaScreen();
                    case GOTO_OPTIONS -> displayOptionsScreen();
                    case GOTO_HELP -> displayHelpScreen();
                    case EXIT -> System.exit(0);
                    case GOTO_START -> displayStartScreen();
                    case LAUNCH -> launch();
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
        state = Screen.START;
        Player.chooseSpeciality(charaSelected);
        GameWindow.enterInGame();
    }

    private void setSettings() {
        Tools.Settings.saveSettings(language, soundCheckBox.isSelected());
        GameWindow.setSettings(language, soundCheckBox.isSelected());
        setTexts();
        GameWindow.playOrStopMenuMusic();
        displayStartScreen();
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

    private enum Screen {
        START, CHARA, OPTIONS, HELP;
    }

    private enum Effect {
        GOTO_CHARA, GOTO_OPTIONS, GOTO_HELP, EXIT, GOTO_START, LAUNCH, SAVE_SETTINGS;
    }
}
