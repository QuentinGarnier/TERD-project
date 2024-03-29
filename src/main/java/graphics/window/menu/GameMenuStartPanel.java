package graphics.window.menu;

import graphics.Language;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

public class GameMenuStartPanel extends GameMenuCustomPanel {
    private JButton newGameButton, optionsButton, helpButton, rankingButton, exitButton;

    GameMenuStartPanel() {
        super();
        setOpaque(false);
        setBorder(null);
    }

    void fillScreen() {
        setLayout(new BorderLayout());
        JPanel midPanel = new JPanel(new GridLayout(0, 1, 0, 10));

        newGameButton = createMenuButton(Language.newGame());
        optionsButton = createMenuButton(Language.options());
        helpButton = createMenuButton(Language.help());
        exitButton = createMenuButton(Language.exitGame());
        rankingButton = createMenuButton(Language.ranking());
        addMenuButton(newGameButton, midPanel);
        addMenuButton(optionsButton, midPanel);
        addMenuButton(helpButton, midPanel);
        addMenuButton(rankingButton, midPanel);
        addMenuButton(exitButton, midPanel);

        addMouseEffect(newGameButton, Effect.GOTO_CHARA);
        addMouseEffect(optionsButton, Effect.GOTO_OPTIONS);
        addMouseEffect(helpButton, Effect.GOTO_HELP);
        addMouseEffect(rankingButton, Effect.GOTO_RANKING);
        addMouseEffect(exitButton, Effect.EXIT);

        JPanel titlePanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("data/images/menu/title.png"))));
        titlePanel.add(titleLabel);
        titlePanel.setPreferredSize(new Dimension(800, 146));
        titlePanel.setOpaque(false);
        midPanel.setBorder(null);
        midPanel.setOpaque(false);

        add(midPanel);
        add(titlePanel, BorderLayout.NORTH);
    }

    private void addMenuButton(JButton button, JPanel screen) {
        JPanel centerP = new JPanel(new GridLayout(1, 3));
        centerP.setOpaque(false);
        centerP.add(new JLabel());
        centerP.add(button);
        centerP.add(new JLabel());
        screen.add(centerP);
    }

    void setTexts() {
        newGameButton.setText(Language.newGame());
        optionsButton.setText(Language.options());
        helpButton.setText(Language.help());
        exitButton.setText(Language.exitGame());
        rankingButton.setText(Language.ranking());
    }

    /**
     * Add an effect to a JButton (click and hover).
     * @param button a JButton, should be not null
     * @param effect one of GOTO_CHARA, GOTO_OPTIONS, GOTO_HELP or EXIT
     */
    private void addMouseEffect(JButton button, Effect effect) {
        Color bg = button.getBackground();
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                button.setBackground(bg);
                switch (effect) {
                    case GOTO_CHARA -> GameMenuPanel.getMenuPanel.displayCharaScreen();
                    case GOTO_OPTIONS -> GameMenuPanel.getMenuPanel.displayOptionsScreen();
                    case GOTO_HELP -> GameMenuPanel.getMenuPanel.displayHelpScreen();
                    case GOTO_RANKING -> GameMenuPanel.getMenuPanel.displayRankingScreen();
                    case EXIT -> System.exit(0);
                }
            }
        });
    }


    private enum Effect {
        GOTO_CHARA, GOTO_OPTIONS, GOTO_HELP, GOTO_RANKING, EXIT;
    }
}
