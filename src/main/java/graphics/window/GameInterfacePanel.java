package graphics.window;

import entity.EntityState;
import entity.EntityType;
import entity.Player;
import graphics.Language;
import graphics.Tools;
import graphics.map.WorldMap;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.util.Objects;

public class GameInterfacePanel extends JPanel {
    private final JPanel bigPanel;
    private final JPanel centerP;
    private final JPanel topPanel;
    private final JPanel logsPanel;
    private final JPanel statsPanel;
    private final JPanel inventoryPanel;
    private final InventoryPanel realInventoryPanel;

    private final int maxLog = 10;
    private boolean displayInventory = true;

    GameInterfacePanel() {
        super();
        bigPanel = new JPanel(new BorderLayout());
        centerP = new JPanel(new GridLayout(1,2));
        topPanel = new JPanel(new GridLayout(2, 2));
        logsPanel = new JPanel(new GridLayout(maxLog,1));
        logsPanel.setBackground(Color.BLACK);
        statsPanel = new JPanel(new GridLayout(2,2));
        statsPanel.setBackground(Color.LIGHT_GRAY);
        inventoryPanel = new JPanel(new GridBagLayout());
        inventoryPanel.setBackground(Color.GRAY);
        realInventoryPanel = InventoryPanel.inventoryPane;
        setup();
    }

    void display() {
        if(statsPanel.getComponentCount() > 0) statsPanel.removeAll();
        displayStats();
        if(inventoryPanel.getComponentCount() > 0) inventoryPanel.removeAll();
        displayInventory();
        if(topPanel.getComponentCount() > 0) topPanel.removeAll();
        displayTopPanel();
        if(logsPanel.getComponentCount() > 0) clearLogs();
        realInventoryPanel.updateInventory(true);
    }

    void refresh(boolean refreshInventoryLabel) {
        refreshStats();
        refreshInventory();
        refreshTopPanel();
        realInventoryPanel.updateInventory(refreshInventoryLabel);
        repaint();
        revalidate();
    }

    private void setup() {
        Dimension dim = new Dimension(800,160);
        setPreferredSize(new Dimension(800, 210));
        setBackground(Color.DARK_GRAY);

        centerP.setPreferredSize(dim);
        centerP.setMinimumSize(dim);
        centerP.setMaximumSize(dim);
        centerP.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));

        Dimension dim2 = new Dimension(800,200);
        bigPanel.setPreferredSize(dim2);
        bigPanel.setMinimumSize(dim2);
        bigPanel.setMaximumSize(dim2);
        bigPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));

        Box box = new Box(BoxLayout.Y_AXIS);
        box.add(bigPanel);
        box.add(Box.createVerticalGlue());
        add(box);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(statsPanel);
        mainPanel.add(inventoryPanel, BorderLayout.SOUTH);

        centerP.add(mainPanel);
        centerP.add(logsPanel);

        bigPanel.add(centerP);
        bigPanel.add(topPanel, BorderLayout.NORTH);
    }

    private void displayStats() {
        Player player = Player.getInstancePlayer();
        Color cyan = Tools.WindowText.cyan;
        Color green = Tools.WindowText.green;
        Color violet = Tools.WindowText.purple;
        Color red = Tools.WindowText.red;

        //Speciality:
        JPanel spec = new JPanel(new GridLayout(1,1));
        spec.setBackground(Color.LIGHT_GRAY);
        createTxtLabel(spec, Language.translate(player.getEntityType()), (player.getEntityType() == EntityType.HERO_WARRIOR? red: (player.getEntityType() == EntityType.HERO_ARCHER? green: cyan)), 16);
        statsPanel.add(spec);

        //HP (add bar in the future and group it with this label):
        createBarGroup(statsPanel, Language.hp() + ": " + player.getHP() + "/" + player.getHPMax() + " ♥", red, "bar_red.png", player.getHPMax(), player.getHP());

        //Hunger (add bar in the future and group it with this label):
        createBarGroup(statsPanel,Language.hunger() + ": " + Language.translateHungerState(player.getHungerState()), violet, "bar_violet.png", 100, player.getHunger());

        //State:
        createLabelForStats(Language.translate(player.getState()) + (player.getState() != EntityState.NEUTRAL ? " (" + player.getRemainingTime() + ")" : ""), violet, 14);

        //Attack:
        createLabelForStats(Language.attack() + ": " + player.getAttack() + " ⚔", cyan, 14);

        //Range:
        createLabelForStats(Language.range() + ": " + player.getRange() + " ◎", green, 14);
    }

    private void displayInventory() {
        GridBagConstraints cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridwidth = 3;
        cons.weightx = 0.7;
        createTxtLabel(inventoryPanel, GameWindow.name, null, 0, cons);

        //Money:
        JLabel moneyLabel = new JLabel(Language.money() + ": " + Player.getInstancePlayer().getMoney() + " ● ");
        moneyLabel.setHorizontalAlignment(SwingConstants.CENTER);
        moneyLabel.setForeground(Tools.WindowText.golden);
        moneyLabel.setPreferredSize(new Dimension(400, 50));
        cons.gridx = 3;
        cons.gridwidth = 1;
        cons.weightx = 0.3;
        inventoryPanel.add(moneyLabel, cons);
    }

    public void displayTopPanel() {
        Player p = Player.getInstancePlayer();
        ImageIcon imageIcon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("data/images/interfaces/bar_xp.png")));
        float ratio = (float)(p.getXP()) / (float)(p.getMaxXP());
        int length = (int)(imageIcon.getIconWidth() * ratio);
        Image image = imageIcon.getImage().getScaledInstance(length==0? 1: length, imageIcon.getIconHeight(), Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(image);
        JLabel bar = new JLabel(imageIcon);
        bar.setHorizontalAlignment(SwingConstants.LEFT);
        topPanel.add(bar);
        createTxtLabel(topPanel, Language.stage() + ": " + WorldMap.stageNum + "/" + GameWindow.difficulty().stagesNumber, null);
        createTxtLabel(topPanel, Language.level() + ": " + p.getLvl() + " — (" + p.getXP() + "/" + p.getMaxXP() + " XP)", null);
        createTxtLabel(topPanel, "—  " + WorldMap.getInstanceWorld().getTheme() + "  —", null);
    }

    public void displayRealInventory() {
        if (displayInventory) {
            centerP.remove(logsPanel);
            centerP.add(realInventoryPanel);
        } else {
            centerP.remove(realInventoryPanel);
            centerP.add(logsPanel);
        }
        displayInventory = !displayInventory;
    }

    private void refreshStats() {
        Player player = Player.getInstancePlayer();
        refreshBarGroup((JPanel)(statsPanel.getComponent(1)), Language.hp() + ": " + player.getHP() + "/" + player.getHPMax() + " ♥", player.getHPMax(), player.getHP(), "bar_red.png");
        refreshBarGroup((JPanel)(statsPanel.getComponent(2)), Language.hunger() + ": " + Language.translateHungerState(player.getHungerState()), 100, player.getHunger(), "bar_violet.png");
        ((JLabel)(statsPanel.getComponent(3))).setText(Language.translate(player.getState()) + (player.getState() != EntityState.NEUTRAL ? " (" + player.getRemainingTime() + ")" : ""));
        ((JLabel)(statsPanel.getComponent(4))).setText(Language.attack() + ": " + player.getAttack() + " ⚔");
        ((JLabel)(statsPanel.getComponent(5))).setText(Language.range() + ": " + player.getRange() + " ◎");
    }

    private void refreshInventory() {
        ((JLabel)(inventoryPanel.getComponent(0))).setText(GameWindow.name);
        ((JLabel)(inventoryPanel.getComponent(1))).setText(Language.money() + ": " + Player.getInstancePlayer().getMoney() + " ● ");
    }

    private void refreshTopPanel() {
        Player p = Player.getInstancePlayer();
        ImageIcon imageIcon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("data/images/interfaces/bar_xp.png")));
        float ratio = (float)(p.getXP()) / (float)(p.getMaxXP());
        int length = (int)(imageIcon.getIconWidth() * ratio);
        Image image = imageIcon.getImage().getScaledInstance(length==0? 1: length, imageIcon.getIconHeight(), Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(image);

        ((JLabel)(topPanel.getComponent(0))).setIcon(imageIcon);
        ((JLabel)(topPanel.getComponent(1))).setText(Language.stage() + ": " + WorldMap.stageNum +  "/" + GameWindow.difficulty().stagesNumber);
        ((JLabel)(topPanel.getComponent(2))).setText(Language.level() + ": " + p.getLvl() + " — (" + p.getXP() + "/" + p.getMaxXP() + " XP)");
        ((JLabel)(topPanel.getComponent(3))).setText("—  " + WorldMap.getInstanceWorld().getTheme() + "  —");
    }

    private void createTxtLabel(JPanel p, String str, Color c, int fontSize, GridBagConstraints cons) {
        JLabel label = new JLabel(str);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        if(c != null) label.setForeground(c);
        if(fontSize > 0) {
            Font font = label.getFont();
            label.setFont(new Font(font.getName(), Font.BOLD, fontSize));
        }
        if(cons != null) p.add(label, cons);
        else p.add(label);
    }

    private void createTxtLabel(JPanel p, String str, Color c, int fontSize) {
        createTxtLabel(p, str, c, fontSize, null);
    }

    private void createTxtLabel(JPanel p, String str, Color c) {
        createTxtLabel(p, str, c, 0);
    }

    private void createLabelForStats(String str, Color c, int fontSize) {
        createTxtLabel(statsPanel, str, c, fontSize);
    }

    private void createBarGroup(JPanel p, String str, Color c, String imageName, float maxVal, float val) {
        JPanel group = new JPanel(new GridLayout(2,1));
        ImageIcon imageIcon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("data/images/interfaces/" + imageName)));
        int length = (int)(imageIcon.getIconWidth() * 0.8 * (val/maxVal));
        Image image = imageIcon.getImage().getScaledInstance(length==0? 1: length, imageIcon.getIconHeight(), Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(image);
        JLabel bar = new JLabel(imageIcon);
        bar.setHorizontalAlignment(SwingConstants.LEFT);
        group.setBackground(Color.LIGHT_GRAY);
        group.add(bar);
        createTxtLabel(group,str, c);
        p.add(group);
    }

    private void refreshBarGroup(JPanel p, String str, float maxVal, float val, String imageName) {
        ImageIcon imageIcon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("data/images/interfaces/" + imageName)));
        int length = (int)(imageIcon.getIconWidth() * 0.8 * (val/maxVal));
        Image image = imageIcon.getImage().getScaledInstance(length==0? 1: length, imageIcon.getIconHeight(), Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(image);

        ((JLabel)(p.getComponent(0))).setIcon(imageIcon);
        ((JLabel)(p.getComponent(1))).setText(str);
    }

    public void addToLogs(String txt, Color c) {
        JLabel log = createLog(txt, c);
        if(logsPanel.getComponentCount() == maxLog) logsPanel.remove(0);
        logsPanel.add(log);
    }

    public static JLabel createLog(String txt, Color c) {
        JLabel log = new JLabel(txt);
        if(c != null) log.setForeground(c);
        log.setHorizontalAlignment(SwingConstants.CENTER);
        return log;
    }

    public void clearLogs() {
        logsPanel.removeAll();
    }
}
