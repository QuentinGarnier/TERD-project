package graphics.window;

import entity.EntityState;
import entity.EntityType;
import entity.Player;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;

public class GameInterfacePanel extends JPanel {
    private final JPanel centerP;
    private final JPanel logsPanel;
    private final JPanel statsPanel;
    private final JPanel inventoryPanel;
    private final InventoryPanel realInventoryPanel;
    private final JScrollPane inventoryScrollPane;

    private final Color green = new Color(80, 140, 50);
    private final Color violet = new Color(100,60,120);
    private final Color cyan = new Color(80,140,180);
    private final Color red = new Color(140,30,30);
    private final Color golden = new Color(210,170,60);

    private final int maxLog = 10;
    private boolean displayInventory = true;

    GameInterfacePanel() {
        super();
        centerP = new JPanel(new GridLayout(1,3));
        logsPanel = new JPanel(new GridLayout(maxLog,1));
        logsPanel.setBackground(Color.BLACK);
        statsPanel = new JPanel(new GridLayout(2,2));
        statsPanel.setBackground(Color.LIGHT_GRAY);
        inventoryPanel = new JPanel(new GridLayout(1,3));
        inventoryPanel.setBackground(Color.GRAY);
        realInventoryPanel = InventoryPanel.inventoryPane;
        inventoryScrollPane = new JScrollPane(realInventoryPanel);
        setup();
    }

    void display() {
        displayStats();
        displayInventory();
        realInventoryPanel.updateInventory();
    }

    void refresh() {
        statsPanel.removeAll();  //clear the components
        inventoryPanel.removeAll();  //clear the components

        display();  //re-add the actualized components
    }

    private void setup() {
        Dimension dim = new Dimension(800,160);
        setPreferredSize(new Dimension(800, 170));
        setBackground(Color.DARK_GRAY);

        centerP.setPreferredSize(dim);
        centerP.setMinimumSize(dim);
        centerP.setMaximumSize(dim);
        centerP.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));

        Box box = new Box(BoxLayout.Y_AXIS);
        box.add(centerP);
        box.add(Box.createVerticalGlue());
        add(box);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(statsPanel);
        mainPanel.add(inventoryPanel, BorderLayout.SOUTH);

        centerP.add(mainPanel);
        centerP.add(logsPanel);
    }

    private void displayStats() {
        Player player = Player.getInstancePlayer();

        //Speciality & level:
        JPanel spec = new JPanel(new GridLayout(2,1));
        spec.setBackground(Color.LIGHT_GRAY);
        createTxtLabel(spec, player.getEntityType().toString(), (player.getEntityType() == EntityType.HERO_WARRIOR? red: (player.getEntityType() == EntityType.HERO_ARCHER? green: cyan)), 16);
        createTxtLabel(spec, "Level: " + player.getLvl(), null);
        statsPanel.add(spec);

        //HP (add bar in the future and group it with this label):
        createBarGroup(statsPanel,"HP: " + player.getHP() + "/" + player.getHPMax() + " ♥", red, "bar_red.png", player.getHPMax(), player.getHP());

        //Hunger (add bar in the future and group it with this label):
        createBarGroup(statsPanel,"Hunger: " + player.getHungerState(), violet, "bar_violet.png", 100, player.getHunger());

        //State:
        createLabelForStats(player.getState().toString() + (player.getState() != EntityState.NEUTRAL ? " (" + player.getRemainingTime() + ")" : ""), violet, 14);

        //Attack:
        createLabelForStats("Attack: " + player.getAttack() + " ⚔", cyan, 14);

        //Range:
        createLabelForStats("Range: " + player.getRange() + " ◎", green, 14);
    }

    private void displayInventory() {
        createLabelForInventory("Press [i] to open INVENTORY", null);

        //Money:
        JLabel moneyLabel = new JLabel("Money: " + Player.getInstancePlayer().getMoney() + " ●");
        moneyLabel.setHorizontalAlignment(SwingConstants.CENTER);
        moneyLabel.setForeground(golden);
        moneyLabel.setPreferredSize(new Dimension(400, 50));
        inventoryPanel.add(moneyLabel, BorderLayout.SOUTH);
    }



    public void displayRealInventory(){
        if (displayInventory) {
            centerP.remove(logsPanel);
            centerP.add(inventoryScrollPane);
        } else {
            centerP.remove(inventoryScrollPane);
            centerP.add(logsPanel);
        }
        displayInventory = !displayInventory;
    }

    private void createTxtLabel(JPanel p, String str, Color c, int fontSize) {
        JLabel label = new JLabel(str);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        if(c != null) label.setForeground(c);
        if(fontSize > 0) {
            Font font = label.getFont();
            label.setFont(new Font(font.getName(), Font.BOLD, fontSize));
        }
        p.add(label);
    }

    private void createTxtLabel(JPanel p, String str, Color c) {
        createTxtLabel(p, str, c, 0);
    }

    private void createLabelForStats(String str, Color c, int fontSize) {
        createTxtLabel(statsPanel, str, c, fontSize);
    }

    private void createLabelForStats(String str, Color c) {
        createTxtLabel(statsPanel, str, c);
    }

    private void createLabelForInventory(String str, Color c) {
        createTxtLabel(inventoryPanel, str, c);
    }

    private void createBarGroup(JPanel p, String str, Color c, String imageName, float maxVal, float val) {
        JPanel group = new JPanel(new GridLayout(2,1));
        ImageIcon imageIcon = new ImageIcon("data/images/interfaces/" + imageName);
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

    private String getInfo() {
        return "";
    }

    public void addToLogs(String txt, Color c) {
        JLabel log = createLog(txt, c);
        if(logsPanel.getComponentCount() == maxLog) logsPanel.remove(0);
        logsPanel.add(log);
    }

    public static JLabel createLog(String txt, Color c){
        JLabel log = new JLabel(txt);
        if(c != null) log.setForeground(c);
        log.setHorizontalAlignment(SwingConstants.CENTER);
        return log;
    }
}
