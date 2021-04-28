package graphics.window;

import entity.Player;
import graphics.Language;
import graphics.Tools;
import items.collectables.AbstractCollectableItem;
import items.collectables.ItemConsumable;
import items.collectables.ItemEquip;
import items.collectables.ItemFood;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.Objects;

import static graphics.window.GameInterfacePanel.createLog;

public class InventoryPanel extends JPanel {
    public final static InventoryPanel inventoryPane = new InventoryPanel();
    private final JPanel contents;
    private final JLabel miniLog;
    private final JScrollPane scrollPane;
    private final GridLayout gl = new GridLayout(4,1);

    private InventoryPanel(){
        setLayout(new BorderLayout());
        miniLog = new JLabel();
        contents = new JPanel(gl);
        scrollPane = new JScrollPane(contents, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        miniLog.setHorizontalAlignment(SwingConstants.CENTER);
        setInventoryText();
        add(miniLog, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void createLine(AbstractCollectableItem ai) {
        String s1 = ai.toString(), s2 = ai.getEffect();
        int s3 = ai.getPrice();
        JPanel panel = new JPanel(new BorderLayout());
        JLabel fstCol = createLog(s1, Color.GRAY);
        fstCol.setForeground(ai.equals(Player.getInstancePlayer().getDefenceItem()) ||
                ai.equals(Player.getInstancePlayer().getAttackItem())? Color.GREEN : Color.GRAY);
        JLabel sndCol = createLog(s2, Color.GRAY);
        JLabel thrCol = createLog((s3 < 10 ? " " : "") + s3 + " $", Color.GRAY);

        fstCol.setHorizontalAlignment(SwingConstants.LEFT);
        sndCol.setHorizontalAlignment(SwingConstants.CENTER);
        thrCol.setHorizontalAlignment(SwingConstants.RIGHT);

        panel.add(fstCol, BorderLayout.WEST);
        panel.add(thrCol, BorderLayout.EAST);
        myButton jButton = new myButton(ai, panel);
        contents.add(jButton);
    }

    public void updateInventory(boolean refreshTest) {
        contents.removeAll();
        scrollPane.revalidate();
        if (refreshTest) setInventoryText();
        List<AbstractCollectableItem> items = Player.getInventory();
        gl.setRows(Math.max(4, items.size()));
        items.forEach(this::createLine);
    }

    public void setInventoryText(Color c, String cnt) {
        miniLog.setForeground(c);
        miniLog.setText(cnt);
    }
    private static int i = 0;
    public void setInventoryText() {
        System.out.println(i++);
        setInventoryText(Color.BLUE , Language.logInventory() + " (" + Player.getInventory().size() + "/" + Player.MAX_INVENTORY_SIZE + ")");
    }

    private void equipping(AbstractCollectableItem ai, boolean isEquipping){
        miniLog.setForeground(isEquipping ? Color.GREEN : Color.RED);
        miniLog.setText(
                (ai instanceof ItemEquip) ?
                        Language.translate(((ItemEquip) ai).getEquipmentType()) + " " + (((ItemEquip) ai).isEquipped() ? Language.logEquipped() : Language.logRejected()) :
                        (((ai instanceof ItemFood) ?
                                Language.logFood() : Language.translate(((ItemConsumable) ai).ct)) + " " + Language.logConsumed()));
    }



    private class myButton extends JButton {
        public final JPanel panel;
        public final AbstractCollectableItem ai;

        myButton(AbstractCollectableItem ai, JPanel panel) {
            super();
            setMargin(null);
            setFocusable(false);
            this.panel = panel;
            this.ai = ai;
            add(panel);
            ActionListener al = e -> {
                if (Player.getInstancePlayer().getHP() == 0) return;
                revalidate();
                removeAll();
                add(new choiceButton(ai));
            };
            ToolTipManager.sharedInstance().setInitialDelay(0);
            setToolTipText(ai.getDescription());
            super.addActionListener(al);
        }

        @Override
        public Point getToolTipLocation(MouseEvent event) {
            return new Point(panel.getLocation().x, panel.getLocation().y - 18);
        }
    }



    private class choiceButton extends JPanel {
        private void addMouseListener(JButton button){
            button.setBorder(null);
            button.addMouseListener(new MouseAdapter() {
                Color bg = button.getBackground();
                Color hoverBG = new Color(180, 150, 110);
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
        public choiceButton(AbstractCollectableItem ai) {
            setLayout(new GridLayout(0,3));
            Player player = Player.getInstancePlayer();
            JButton equip = new JButton((ai instanceof ItemEquip) ?
                    (((ItemEquip) ai).isEquipped() ? Language.buttonUnequip() : Language.buttonEquip()) :
                    Language.buttonConsume());
            JButton throwAway = new JButton(Language.buttonThrow());
            JButton esc = new JButton(Language.back());
            addMouseListener(equip);
            addMouseListener(throwAway);
            addMouseListener(esc);
            equip.addActionListener(e -> {
                equipping(ai, ai.use());
                if(GameWindow.hasSound()) Tools.play(Objects.requireNonNull(getClass().getClassLoader().getResource("data/audio/SE/" + ai.getSE() + ".wav")), false);
                GameWindow.refreshInventory(false);
            });
            throwAway.addActionListener(e -> {
                if(!player.throwItem(ai))
                    setInventoryText(Color.RED, Language.logCantDropItem());
                updateInventory(false);
            });
            esc.addActionListener(e -> {
                updateInventory(false);
            });
            add(equip);
            add(throwAway);
            add(esc);
            setFocusable(false);
            equip.setFocusable(false);
            throwAway.setFocusable(false);
            esc.setFocusable(false);
        }
    }
}
