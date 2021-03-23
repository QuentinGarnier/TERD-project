package graphics.window;

import entity.Player;
import graphics.Tools;

import javax.swing.*;
import javax.swing.border.BevelBorder;
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

    GameMenuPanel() {
        super();
        setLayout(new BorderLayout());
        setFocusable(true);
        setBackground(Color.DARK_GRAY);

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

        JButton newGameButton = createMenuButton("New Game");
        JButton optionsButton = createMenuButton("Options");
        JButton exitButton = createMenuButton("Exit Game");
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
        charaScreen.add(createTitle("Choose your speciality", 26, Color.BLACK), cons);

        cons.weighty = 0.8;
        cons.gridx = 0;
        cons.gridy = 1;
        cons.fill = GridBagConstraints.BOTH;
        charaScreen.add(createSpecPanel(), cons);

        JButton backButton = createMenuButton("Back");
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

        JButton launchButton = createMenuButton("Start the Quest");
        addMouseEffect(launchButton, 4);
        cons.gridx = 2;
        cons.weightx = 0.3;
        charaScreen.add(launchButton, cons);
    }

    private void fillOptionsScreen() {
        optionsScreen.setLayout(new BorderLayout());
        optionsScreen.add(createTitle("Options", 26, Color.BLACK));

        JButton backButton = createMenuButton("Back");
        addMouseEffect(backButton, 3);
        optionsScreen.add(backButton, BorderLayout.SOUTH);
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
            case 2 -> replaceWith(optionsScreen);
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

        warSpecPanel = buildSpecPanel("WARRIOR", "data/images/menu/spec_war.png", Tools.WindowText.red, 0);
        arcSpecPanel = buildSpecPanel("ARCHER", "data/images/menu/spec_arc.png", Tools.WindowText.green, 1);
        magSpecPanel = buildSpecPanel("MAGE", "data/images/menu/spec_mag.png", Tools.WindowText.blue, 2);

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

    private JPanel buildSpecPanel(String name, String pathImg, Color fg, int numMouseEffect) {
        JPanel specPanel = new JPanel(new BorderLayout());
        JLabel specImgLabel = new JLabel(new ImageIcon(pathImg));
        JLabel nameLabel = createTitle(name, 16, fg);

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
            case 0 -> "<html><p style=\"text-align: center;\">The warrior deals great melee damage and has a large amount of HP.<br />In counterpart, he loses 1 Hunger Point for each attack.</p></html>";
            case 1 -> "<html><p style=\"text-align: center;\">The archer deals good distance damage with his very long range but has few HP.<br />Each attack has a chance to deal more damage, inflict an effect... or miss.</p></html>";
            case 2 -> "<html><p style=\"text-align: center;\">The mage deals moderate damage in a medium range.<br />His power lies in his ability to burn his opponents and heal himself slightly with each attack.</p></html>";
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
     * @param effect one of 0 (new game), 1 (options), 2 (exit), 3 (back to StartScreen) or 4 (launch)
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

    //Launch the game:
    private void launch() {
        state = 0;
        Player.chooseSpeciality(charaSelected);
        GameWindow.enterInGame();
    }
}
