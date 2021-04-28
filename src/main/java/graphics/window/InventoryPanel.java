package graphics.window;

import entity.Player;
import graphics.Language;
import graphics.Tools;
import items.collectables.AbstractCollectableItem;
import items.collectables.ItemConsumable;
import items.collectables.ItemEquip;
import items.collectables.ItemFood;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static graphics.window.GameInterfacePanel.createLog;

public class InventoryPanel extends JPanel {
    public final static InventoryPanel inventoryPane = new InventoryPanel();
    private final JPanel contents;
    private final JLabel miniLog;
    private final JScrollPane scrollPane;
    private final GridLayout gl = new GridLayout(4,1);
    private final List<myButton> buttons;

    private InventoryPanel(){
        setLayout(new BorderLayout());
        miniLog = new JLabel();
        contents = new JPanel(gl);
        buttons = new ArrayList<>();
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
        JLabel fstCol = createLog(s1, Color.BLACK);
        fstCol.setForeground(ai.equals(Player.getInstancePlayer().getDefenceItem()) ||
                ai.equals(Player.getInstancePlayer().getAttackItem())? Color.GREEN : Color.BLACK);
        JLabel thrCol = createLog((s3 < 10 ? " " : "") + s3 + " $", Color.BLACK);

        fstCol.setHorizontalAlignment(SwingConstants.LEFT);
        thrCol.setHorizontalAlignment(SwingConstants.RIGHT);

        panel.add(fstCol, BorderLayout.WEST);
        panel.add(thrCol, BorderLayout.EAST);
        myButton jButton = new myButton(ai, panel);
        myAddMouseListener(jButton);
        buttons.add(jButton);
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
            panel.setOpaque(false);
            add(panel);
            ActionListener al = e -> {
                if (Player.getInstancePlayer().getHP() == 0) return;
                setBorder(null);
                revalidate();
                removeAll();
                add(new choiceButton(ai));
                restoreOtherButtons();
            };
            ToolTipManager.sharedInstance().setInitialDelay(0);
            setToolTipText(ai.getDescription());
            super.addActionListener(al);
        }

        public void restoreContent(){
            if (ai instanceof ItemEquip){
                ItemEquip ie = (ItemEquip) ai;
                panel.getComponent(0).setForeground(ie.isEquipped() ? Color.GREEN : Color.BLACK);
            }
            removeAll();
            add(panel);
            repaint();
        }

        private void restoreOtherButtons(){
            buttons.forEach(b -> {
                if (!b.ai.equals(this.ai)) {
                    b.restoreContent();
                    addBorder(b);
                }
            });
        }

        @Override
        public Point getToolTipLocation(MouseEvent event) {
            return new Point(panel.getLocation().x, panel.getLocation().y - 18);
        }
    }

    private static void addBorder(JButton button){
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createBevelBorder(BevelBorder.RAISED, Tools.WindowText.golden, Tools.WindowText.dark_golden),
                        BorderFactory.createLineBorder(Tools.WindowText.golden, 2)),
                BorderFactory.createLineBorder(new Color(140, 110, 70))));
    }

    public static void myAddMouseListener(JButton button){
        addBorder(button);
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

    private class choiceButton extends JPanel {
        private final AbstractCollectableItem ai;

        public choiceButton(AbstractCollectableItem ai) {
            this.ai = ai;
            setLayout(new GridLayout(0,3));
            Player player = Player.getInstancePlayer();
            JButton equip = new JButton((ai instanceof ItemEquip) ?
                    (((ItemEquip) ai).isEquipped() ? Language.buttonUnequip() : Language.buttonEquip()) :
                    Language.buttonConsume());
            JButton throwAway = new JButton(Language.buttonThrow());
            JButton esc = new JButton(Language.back());
            InventoryPanel.myAddMouseListener(equip);
            InventoryPanel.myAddMouseListener(throwAway);
            InventoryPanel.myAddMouseListener(esc);
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
