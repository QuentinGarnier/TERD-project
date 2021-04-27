package graphics.window.menu;

import graphics.Language;
import graphics.elements.Move;
import graphics.elements.cells.CellElementType;
import graphics.map.Theme;
import graphics.window.GameWindow;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.util.Objects;

public class GameMenuInfoPanel extends GameMenuCustomPanel {
    private final JPanel leftPane;
    private final JPanel centerPane;
    private final JPanel helpPanel;
    private final JPanel aboutPanel;
    private final JPanel creditsPanel;
    private final JPanel historyPanel;
    private final JScrollPane scrollPane;

    GameMenuInfoPanel() {
        super();
        setOpaque(false);
        setBorder(null);

        leftPane = new JPanel(new GridLayout(0, 1, 0, 10));
        centerPane = new JPanel();
        helpPanel = new JPanel();
        creditsPanel = new JPanel();
        historyPanel = new JPanel();
        aboutPanel = new JPanel();
        scrollPane = new JScrollPane(centerPane);
        scrollPane.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));

        setup();
    }

    private void opaquePanel(JPanel panel) {
        panel.setOpaque(false);
    }

    private void setup() {
        setLayout(new BorderLayout());

        add(leftPane, BorderLayout.WEST);
        add(scrollPane, BorderLayout.CENTER);
        setLeftPane();
        setHelpPanel();
        setCreditsPanel();
        setHistoryPanel();
        setAboutPanel();

        // MAKE PANEL OPAQUE
        opaquePanel(leftPane);
        opaquePanel(centerPane);
        opaquePanel(historyPanel);
        opaquePanel(creditsPanel);
        opaquePanel(historyPanel);
        opaquePanel(aboutPanel);

        // SET CENTER PANEL
        setCenterPane(historyPanel);
    }

    private void setLeftPane() {
        leftPane.removeAll();

        // HISTORY
        JButton history = createMenuButton(Language.history());
        addMouseEffect(history, false);
        history.addActionListener(e -> setCenterPane(historyPanel));
        leftPane.add(history);

        // ABOUT == Description of project
        JButton about = createMenuButton(Language.description());
        addMouseEffect(about, false);
        about.addActionListener(e ->setCenterPane(aboutPanel));
        leftPane.add(about);

        // HELP
        JButton help = createMenuButton(Language.keys());
        addMouseEffect(help, false);
        help.addActionListener(e -> setCenterPane(helpPanel));
        leftPane.add(help);

        // CREDITS == About us
        JButton credits = createMenuButton(Language.credits());
        addMouseEffect(credits, false);
        credits.addActionListener(e -> setCenterPane(creditsPanel));
        leftPane.add(credits);

        // EXIT
        JButton exit = createMenuButton(Language.back());
        addMouseEffect(exit, true);
        leftPane.add(new JLabel());
        leftPane.add(exit);

        leftPane.revalidate();
        leftPane.repaint();
    }

    private void setCenterPane(JPanel panel) {
        centerPane.removeAll();
        centerPane.add(panel);

        centerPane.revalidate();
        centerPane.repaint();
    }

    // ABOUT PANEL
    private void setAboutPanel() {
        aboutPanel.removeAll();

        aboutPanel.setLayout(new BoxLayout(aboutPanel, BoxLayout.Y_AXIS));
        // Description
        JPanel description = new JPanel();
        description.add(new JLabel(Language.aboutDescription()));

        // ENTITIES
        JPanel entities = new JPanel();
        entities.add(new JLabel(Language.charList()));

        JPanel heroes = createLine(Language.heroes(), CellElementType.HERO_W, CellElementType.HERO_A, CellElementType.HERO_M);
        JPanel merchant = createLine(Language.translate(Theme.MERCHANT), CellElementType.MERCHANT);
        JPanel enemies = createLine(Language.enemies(), CellElementType.SPIDER, CellElementType.WIZARD, CellElementType.ORC, CellElementType.GOBLIN);

        // ELEMENTS
        JPanel elements = new JPanel();
        elements.add(new JLabel(Language.itemList()));
        JPanel eltPanel = new JPanel(new GridLayout(2,2));
        eltPanel.add(createLine(Language.money(), CellElementType.COIN));
        eltPanel.add(createLine(Language.item(), CellElementType.ITEM));
        eltPanel.add(createLine(Language.logFood(), CellElementType.BURGER));
        eltPanel.add(createLine(Language.trap(), CellElementType.TRAP));

        aboutPanel.add(entities);
        aboutPanel.add(heroes);
        aboutPanel.add(enemies);
        aboutPanel.add(merchant);
        aboutPanel.add(elements);
        aboutPanel.add(eltPanel);
        aboutPanel.add(description);

        aboutPanel.revalidate();
        aboutPanel.repaint();
    }

    // HELP PANEL
    private void setHelpPanel() {
        setHelpPanel(helpPanel);
    }
    public static void setHelpPanel(JPanel panel) {
        panel.removeAll();

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // ZERO LINE
        JPanel zerLine = new JPanel();
        zerLine.add(new JLabel(Language.mainKeys()));

        // FIRST LINE
        JPanel fstLine = new JPanel(new GridLayout(0,2));
        fstLine.add(arrowFirstColumn());
        fstLine.add(arrowSecondColumn());

        // SECOND LINE
        JPanel sndLine  = new JPanel();
        JLabel txtLabel = new JLabel(Language.infoQ1());
        sndLine.add(txtLabel);

        // THIRD LINE
        JPanel thrLine = new JPanel();
        int x = (int) fstLine.getPreferredSize().getHeight();
        thrLine.add(createImageIcon(x, Objects.requireNonNull(GameMenuInfoPanel.class.getClassLoader().getResource("data/images/menu/helpPane/attack1.png"))));
        thrLine.add(createImageIcon(x, Objects.requireNonNull(GameMenuInfoPanel.class.getClassLoader().getResource("data/images/menu/helpPane/attack2.png"))));
        thrLine.add(createImageIcon(x, Objects.requireNonNull(GameMenuInfoPanel.class.getClassLoader().getResource("data/images/menu/helpPane/attack3.png"))));

        JPanel fourthLine = new JPanel();
        fourthLine.add(new JLabel(Language.infoQ2()));

        panel.add(zerLine);
        panel.add(fstLine);
        panel.add(sndLine);
        panel.add(thrLine);
        panel.add(fourthLine);

        panel.revalidate();
        panel.repaint();
    }

    private JPanel createLine(String title, CellElementType... icons) {
        JPanel panel = new JPanel();
        panel.add(new JLabel(title));
        JPanel iconsPane = new JPanel();
        iconsPane.setAlignmentX(LEFT_ALIGNMENT);
        for (CellElementType ct : icons){
            iconsPane.add(createImageIcon(32, ct.getIcon()));
        }
        panel.add(iconsPane);
        return panel;
    }

    private static JLabel createImageIcon(int xy, ImageIcon img) {
        JLabel label = new JLabel();
        Image i = img.getImage().getScaledInstance(xy, xy, Image.SCALE_SMOOTH);
        img.setImage(i);
        label.setIcon(img);
        return label;
    }

    private static JLabel createImageIcon(int xy, URL url) {
        return createImageIcon(xy, new ImageIcon(url));
    }

    private static JPanel arrowFirstColumn() {
        JPanel panel = new JPanel(new BorderLayout());

        // ARROWS
        JPanel arrows = new JPanel(new GridLayout(3,3));
        arrows.add(makeSquareLabel(""));
        arrows.add(makeSquareLabel(GameWindow.language() == Language.FR? "Z": "W"));
        arrows.add(makeSquareLabel(""));
        arrows.add(makeSquareLabel(GameWindow.language() == Language.FR? "Q": "A"));
        arrows.add(makeSquareLabel("S"));
        arrows.add(makeSquareLabel("D"));
        arrows.add(makeSquareLabel(""));
        arrows.add(makeSquareLabel(GameWindow.language() == Language.FR? "A": "Q"));
        arrows.add(makeSquareLabel(""));
        panel.add(arrows, BorderLayout.WEST);

        // ARROW DESCRIPTION
        JPanel arrowD = new JPanel(new GridLayout(5, 1));
        arrowD.add(new JLabel((GameWindow.language() == Language.FR? "Z": "W") + " : " + Language.directions(Move.UP)));
        arrowD.add(new JLabel((GameWindow.language() == Language.FR? "Q": "A") + " : " + Language.directions(Move.LEFT)));
        arrowD.add(new JLabel("S : " + Language.directions(Move.DOWN)));
        arrowD.add(new JLabel("D : " + Language.directions(Move.RIGHT)));
        arrowD.add(new JLabel((GameWindow.language() == Language.FR? "A": "Q") + " : " + Language.interactionReadBelow()));
        arrowD.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.add(arrowD);

        return panel;
    }

    private static JPanel arrowSecondColumn() {
        JPanel panel = new JPanel(new GridLayout(0,1));
        panel.add(makeKeyDescription("I", Language.openTheInventory()));
        panel.add(makeKeyDescription("P", "/ [ESC] : " + Language.options()));
        panel.add(makeKeyDescription("R", Language.newGameSameHero()));
        return panel;
    }

    private static JPanel makeKeyDescription(String key, String description) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = makeSquareLabel(key);
        JLabel descr = new JLabel(description);
        descr.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.add(label, BorderLayout.WEST);
        panel.add(descr);
        return panel;
    }

    private static JLabel makeSquareLabel(String s) {
        JLabel label = new JLabel(s);
        label.setOpaque(true);
        label.setFont(new Font("monospaced", Font.PLAIN, 30));
        Border border = BorderFactory.createLineBorder(Color.BLACK, 3);
        label.setBorder(border);
        label.setForeground(Color.WHITE);
        label.setBackground(Color.BLACK);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }

    public void setCreditsPanel() {
        creditsPanel.removeAll();

        creditsPanel.setLayout(new BoxLayout(creditsPanel, BoxLayout.Y_AXIS));
        creditsPanel.setAlignmentX(CENTER_ALIGNMENT);

        // TEXT
        JPanel fstLine = new JPanel();
        fstLine.add(new JLabel(Language.creditsText()));
        creditsPanel.add(fstLine);
        JPanel sndLine = new JPanel();

        // JAVA IMAGE
        sndLine.add(createImageIcon(140, Objects.requireNonNull(getClass().getClassLoader().getResource("data/images/menu/helpPane/java.png"))));
        creditsPanel.add(sndLine);

        creditsPanel.revalidate();
        creditsPanel.repaint();
    }

    private void setHistoryPanel() {
        historyPanel.removeAll();

        historyPanel.setOpaque(false);
        historyPanel.setLayout(new BoxLayout(historyPanel, BoxLayout.Y_AXIS));
        historyPanel.setAlignmentX(CENTER_ALIGNMENT);

        // TEXT
        JPanel fstLine = new JPanel();
        fstLine.add(new JLabel(Language.historyText()));
        historyPanel.add(fstLine);

        historyPanel.revalidate();
        historyPanel.repaint();
    }

    private void addMouseEffect(JButton button, boolean exitButton) {
        Color bg = button.getBackground();
        Color hoverBG = new Color(180, 150, 110);

        button.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(exitButton) {
                    button.setBackground(bg);
                    GameMenuPanel.getMenuPanel.displayStartScreen();
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

    public void setTexts() {
        setup();
    }
}
