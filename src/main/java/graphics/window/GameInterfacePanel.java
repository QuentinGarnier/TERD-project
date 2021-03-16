package graphics.window;

import entity.EntityState;
import entity.Player;

import javax.swing.*;
import java.awt.*;

public class GameInterfacePanel extends JPanel {
    private final JPanel logsPanel;
    private final JPanel statsPanel;
    private final JPanel inventoryPanel;

    private final Color green = new Color(80, 140, 50);
    private final Color violet = new Color(100,60,120);
    private final Color cyan = new Color(80,140,180);
    private final Color red = new Color(140,30,30);
    private final Color golden = new Color(210,170,60);

    GameInterfacePanel() {
        super();
        setLayout(new BorderLayout());
        setBackground(Color.LIGHT_GRAY);
        setPreferredSize(new Dimension(800,200));

        logsPanel = new JPanel(new GridLayout(4,1));
        logsPanel.setBackground(Color.BLACK);
        logsPanel.setPreferredSize(new Dimension(800,70));
        statsPanel = new JPanel(new GridLayout(3,2));
        statsPanel.setBackground(Color.LIGHT_GRAY);
        inventoryPanel = new JPanel(new BorderLayout());
        inventoryPanel.setBackground(Color.GRAY);

        setup();
    }

    void display() {
        displayStats();
        displayInventory();
    }

    void refresh() {
        statsPanel.removeAll();  //clear the components
        inventoryPanel.removeAll();  //clear the components
        display();  //re-add the actualized components
    }

    private void setup() {
        JPanel mainPanel = new JPanel(new GridLayout(1,2));
        mainPanel.add(statsPanel);
        mainPanel.add(inventoryPanel);

        add(logsPanel, BorderLayout.NORTH);
        add(mainPanel);
    }

    private void displayStats() {
        Player player = Player.getInstancePlayer();

        //Speciality:
        createLabelForStats(player.getEntityType().toString(), null);

        //Level:
        createLabelForStats("Level: " + player.getLvl(), green);

        //Hunger:
        createLabelForStats("Hunger: " + player.getHungerState(), violet);

        //HP:
        createLabelForStats("HP: " + player.getHP() + "/" + player.getHPMax() + " ♥", red);

        //Attack:
        createLabelForStats("Attack: " + player.getAttack() + " ⚔", cyan);

        //State:
        createLabelForStats(player.getState().toString() + (player.getState() != EntityState.NEUTRAL ? " (" + player.getRemainingTime() + ")" : ""), violet);

        //Blank:
        statsPanel.add(new JLabel());
    }

    private void displayInventory() {
        createLabelForInventory("INVENTORY (press [i] to open)", null);

        //Money:
        JLabel moneyLabel = new JLabel("Money: " + Player.getInstancePlayer().getMoney() + " ●");
        moneyLabel.setHorizontalAlignment(SwingConstants.CENTER);
        moneyLabel.setForeground(golden);
        moneyLabel.setPreferredSize(new Dimension(400, 50));
        inventoryPanel.add(moneyLabel, BorderLayout.SOUTH);
    }

    private void createTxtLabel(JPanel p, String str, Color c) {
        JLabel label = new JLabel(str);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        if(c != null) label.setForeground(c);
        p.add(label);
    }

    private void createLabelForStats(String str, Color c) {
        createTxtLabel(statsPanel, str, c);
    }

    private void createLabelForInventory(String str, Color c) {
        createTxtLabel(inventoryPanel, str, c);
    }

    private String getInfo() {
        return "";
    }

    public void addToLogs(String txt, Color c) {
        JLabel log = new JLabel(txt);
        if(c != null) log.setForeground(c);
        log.setHorizontalAlignment(SwingConstants.CENTER);
        if(logsPanel.getComponentCount() == 4) logsPanel.remove(0);
        logsPanel.add(log);
    }
}
