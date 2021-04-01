package graphics.window;

import entity.Player;
import graphics.Language;
import graphics.Tools;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GameMenuCharaPanel extends GameMenuCustomPanel {
    private JButton backButton, launchButton;
    private JLabel specialityLabel, warLabel, arcLabel, magLabel;

    private JPanel warSpecPanel;
    private JPanel arcSpecPanel;
    private JPanel magSpecPanel;
    int charaSelected = 0;
    private final JLabel descriptionLabel = new JLabel(descriptionForSpec());

    GameMenuCharaPanel() {
        super();
    }

    void fillScreen() {
        setLayout(new GridBagLayout());
        GridBagConstraints cons = new GridBagConstraints();

        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.weightx = 1;
        cons.gridwidth = 3;
        cons.gridx = 0;
        cons.gridy = 0;
        cons.weighty = 0.1;
        specialityLabel = createTitle(Language.chooseYourSpeciality(), 26, Color.BLACK);
        add(specialityLabel, cons);

        cons.weighty = 0.8;
        cons.gridx = 0;
        cons.gridy = 1;
        cons.fill = GridBagConstraints.BOTH;
        add(createSpecPanel(), cons);

        backButton = createMenuButton(Language.back());
        addMouseEffect(backButton, Effect.GOTO_START);
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.gridwidth = 1;
        cons.gridx = 0;
        cons.gridy = 2;
        cons.weightx = 0.2;
        cons.weighty = 0.1;
        cons.ipadx = 40;
        add(backButton, cons);

        cons.gridx = 1;
        cons.weightx = 0.5;
        add(new JLabel(), cons);

        launchButton = createMenuButton(Language.startTheQuest());
        addMouseEffect(launchButton, Effect.LAUNCH);
        cons.gridx = 2;
        cons.weightx = 0.3;
        add(launchButton, cons);
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

        warSpecPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Tools.WindowText.golden, Tools.WindowText.dark_golden));

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

    void setTexts() {
        backButton.setText(Language.back());
        launchButton.setText(Language.startTheQuest());
        specialityLabel.setText(Language.chooseYourSpeciality());
        warLabel.setText(Language.warriorCL());
        arcLabel.setText(Language.archerCL());
        magLabel.setText(Language.mageCL());
        setCharaSelected(charaSelected);
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
     * @param effect one of GOTO_START or LAUNCH
     */
    private void addMouseEffect(JButton button, Effect effect) {
        Color bg = button.getBackground();
        Color hoverBG = new Color(180, 150, 110);

        button.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                button.setBackground(bg);
                switch (effect) {
                    case LAUNCH -> launch();
                    case GOTO_START -> GameMenuPanel.getMenuPanel.displayStartScreen();
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
        GameMenuPanel.stateReset();
        Player.chooseSpeciality(charaSelected);
        GameWindow.enterInGame();
    }


    private enum Effect {
        GOTO_START, LAUNCH;
    }
}
