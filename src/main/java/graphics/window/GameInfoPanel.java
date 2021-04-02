package graphics.window;

import graphics.Language;
import graphics.Tools;
import graphics.elements.Move;
import graphics.elements.cells.CellElementType;
import graphics.map.Theme;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GameInfoPanel extends GameMenuCustomPanel {
    public static final GameInfoPanel gameInfoPanel = new GameInfoPanel();
    private final JPanel leftPane;
    private final JPanel centerPane;
    private final JPanel helpPanel;
    private final JPanel aboutPanel;
    private final JPanel creditsPanel;
    private final JPanel historyPanel;
    private final JScrollPane scrollPane;
    private GameInfoPanel(){
        leftPane = new JPanel(new GridLayout(0,1));
        centerPane = new JPanel();
        helpPanel = new JPanel();
        creditsPanel = new JPanel();
        historyPanel = new JPanel();
        aboutPanel = new JPanel();

        scrollPane = new JScrollPane(centerPane);

        setup();
    }

    private void opaquePanel(JPanel panel){
        panel.setOpaque(false);
    }

    private void setup(){
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

    private void setLeftPane(){
        leftPane.removeAll();
        // HISTORY
        JButton history = createMenuButton(Language.logHistory());       // TODO : LANGUAGE
        history.addActionListener(e -> setCenterPane(historyPanel));
        leftPane.add(history);

        // ABOUT == Description of project
        JButton about = createMenuButton(Language.logDescription());
        about.addActionListener(e ->setCenterPane(aboutPanel));
        leftPane.add(about);

        // HELP
        JButton help = createMenuButton(Language.logKeys());          // TODO : LANGUAGE
        help.addActionListener(e -> setCenterPane(helpPanel));
        leftPane.add(help);

        // CREDITS == About us
        JButton credits = createMenuButton("Credits");   // TODO : LANGUAGE
        credits.addActionListener(e -> setCenterPane(creditsPanel));
        leftPane.add(credits);

        // EXIT
        JButton exit = createMenuButton(Language.back());        // TODO : LANGUAGE
        addMouseEffect(exit);
        leftPane.add(exit);

        leftPane.revalidate();
        leftPane.repaint();
    }

    private void setCenterPane(JPanel panel){
        centerPane.removeAll();
        centerPane.add(panel);

        centerPane.revalidate();
        centerPane.repaint();
    }

    // ABOUT PANEL
    private void setAboutPanel(){
        aboutPanel.removeAll();

        aboutPanel.setLayout(new BoxLayout(aboutPanel, BoxLayout.Y_AXIS));
        // Description
        JPanel description = new JPanel();
        description.add(new JLabel(Language.aboutDescription()));

        // ENTITIES
        JPanel entities = new JPanel();
        entities.add(new JLabel(Language.charList()));

        JPanel heroes = createLine(Language.logEroes(), CellElementType.HERO_A, CellElementType.HERO_M, CellElementType.HERO_W);
        JPanel merchant = createLine(Language.translate(Theme.MERCHANT), CellElementType.MERCHANT);
        JPanel enemies = createLine(Language.logEnemies(), CellElementType.SPIDER, CellElementType.WIZARD, CellElementType.ORC, CellElementType.GOBLIN);

        // ELEMENTS
        JPanel elements = new JPanel();
        elements.add(new JLabel(Language.itemList()));
        JPanel eltPanel = new JPanel(new GridLayout(2,2));
        eltPanel.add(createLine(Language.money(), CellElementType.COIN));
        eltPanel.add(createLine(Language.logItem(), CellElementType.ITEM));
        eltPanel.add(createLine(Language.logFood(), CellElementType.BURGER));
        eltPanel.add(createLine(Language.logTrap(), CellElementType.TRAP));

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
    private void setHelpPanel(){
        helpPanel.removeAll();

        helpPanel.setLayout(new BoxLayout(helpPanel, BoxLayout.Y_AXIS));

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
        thrLine.add(createImageIcon(x,"/home/fissore/Immagini/ksnip_20210331-101712.png"));
        thrLine.add(createImageIcon(x,"/home/fissore/Immagini/ksnip_20210331-101716.png"));
        thrLine.add(createImageIcon(x,"/home/fissore/Immagini/ksnip_20210331-101718.png"));

        JPanel fourthLine = new JPanel();
        fourthLine.add(new JLabel(Language.infoQ2()));

        helpPanel.add(zerLine);
        helpPanel.add(fstLine);
        helpPanel.add(sndLine);
        helpPanel.add(thrLine);
        helpPanel.add(fourthLine);

        helpPanel.revalidate();
        helpPanel.repaint();
    }

    private JPanel createLine(String title, CellElementType... icons){
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

    private JLabel createImageIcon(int xy, ImageIcon img){
        JLabel label = new JLabel();
        Image i = img.getImage().getScaledInstance(xy, xy, Image.SCALE_SMOOTH);
        img.setImage(i);
        label.setIcon(img);
        return label;
    }

    private JLabel createImageIcon(int xy, String s){
        return createImageIcon(xy, new ImageIcon(s));
    }

    private JPanel arrowFirstColumn(){
        JPanel panel = new JPanel(new BorderLayout());

        // ARROWS
        JPanel arrows = new JPanel(new GridLayout(2,3));
        arrows.add(makeSquareLabel(""));
        arrows.add(makeSquareLabel("" + Tools.universalCharOf('W')));
        arrows.add(makeSquareLabel(""));
        arrows.add(makeSquareLabel("" + Tools.universalCharOf('A')));
        arrows.add(makeSquareLabel("" + Tools.universalCharOf('S')));
        arrows.add(makeSquareLabel("" + Tools.universalCharOf('D')));
        panel.add(arrows, BorderLayout.WEST);

        // ARROW DESCRIPTION
        JPanel arrowD = new JPanel(new GridLayout(4, 1));
        arrowD.add(new JLabel(Tools.universalCharOf('W') + Language.logGoto(Move.UP)));
        arrowD.add(new JLabel(Tools.universalCharOf('A') + Language.logGoto(Move.LEFT)));
        arrowD.add(new JLabel(Tools.universalCharOf('S') + Language.logGoto(Move.DOWN)));
        arrowD.add(new JLabel(Tools.universalCharOf('D') + Language.logGoto(Move.RIGHT)));
        arrowD.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.add(arrowD);

        return panel;
    }

    private JPanel arrowSecondColumn(){
        JPanel panel = new JPanel(new GridLayout(0,1));
        panel.add(makeKeyDescription("I", Language.logGoToInventory()));
        panel.add(makeKeyDescription("R", Language.logNewGame()));
        panel.add(makeKeyDescription("Q", Language.logReadBelow()));
        return panel;
    }

    private JPanel makeKeyDescription(String key, String description){
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = makeSquareLabel(key);
        JLabel descr = new JLabel(description);
        descr.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.add(label, BorderLayout.WEST);
        panel.add(descr);
        return panel;
    }

    private JLabel makeSquareLabel(String s){
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

    public void setCreditsPanel(){
        creditsPanel.removeAll();

        creditsPanel.setLayout(new BoxLayout(creditsPanel, BoxLayout.Y_AXIS));
        creditsPanel.setAlignmentX(CENTER_ALIGNMENT);

        // TEXT
        JPanel fstLine = new JPanel();
        fstLine.add(new JLabel(Language.logCredits()));
        creditsPanel.add(fstLine);
        JPanel sndLine = new JPanel();

        // JAVA IMAGE
        sndLine.add(createImageIcon(140, "/home/fissore/Immagini/ksnip_20210331-101719.png"));
        creditsPanel.add(sndLine);

        creditsPanel.revalidate();
        creditsPanel.repaint();
    }

    private void setHistoryPanel(){
        historyPanel.removeAll();

        historyPanel.setOpaque(false);
        historyPanel.setLayout(new BoxLayout(historyPanel, BoxLayout.Y_AXIS));
        historyPanel.setAlignmentX(CENTER_ALIGNMENT);

        // TEXT
        JPanel fstLine = new JPanel();
        fstLine.add(new JLabel(Language.logHistoryText()));
        historyPanel.add(fstLine);

        historyPanel.revalidate();
        historyPanel.repaint();
    }

    private void addMouseEffect(JButton button) {
        Color bg = button.getBackground();
        Color hoverBG = new Color(180, 150, 110);

        button.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                button.setBackground(bg);
                GameMenuPanel.getMenuPanel.displayStartScreen();
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

    public void setTexts(){
        setup();
    }
}
