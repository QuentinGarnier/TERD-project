package graphics.window;

import entity.Player;
import graphics.Language;
import items.collectables.AbstractCollectableItem;
import items.collectables.ItemConsumable;
import items.collectables.ItemEquip;
import items.collectables.ItemFood;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

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
        JLabel thrCol = createLog((s3 < 10 ? " " : "") + s3, Color.GRAY);

        fstCol.setHorizontalAlignment(SwingConstants.LEFT);
        sndCol.setHorizontalAlignment(SwingConstants.CENTER);
        thrCol.setHorizontalAlignment(SwingConstants.RIGHT);

        panel.add(fstCol, BorderLayout.WEST);
        panel.add(sndCol, BorderLayout.CENTER);
        panel.add(thrCol, BorderLayout.EAST);
        myButton jButton = new myButton(ai, panel);
        contents.add(jButton);
        Dimension parentSize = contents.getComponent(0).getPreferredSize();
        int x = (int) parentSize.getWidth(), y = (int) parentSize.getHeight();
        if (fstCol.getPreferredSize().getWidth() != 0) {
            fstCol.setPreferredSize(new Dimension((int) (x * 10 / 24.0), y));
            sndCol.setPreferredSize(new Dimension((int) (x * 11 / 24.0), y));
            thrCol.setPreferredSize(new Dimension((int) (x *  3 / 24.0), y));
        }
    }

    public void updateInventory(){
        contents.removeAll();
        scrollPane.revalidate();
        List<AbstractCollectableItem> items = Player.getInventory();//new ArrayList<>();
        gl.setRows(Math.max(4, items.size()));
        //for (int i = 0; i < 20; i++)items.add(AbstractCollectableItems.generateAbstractCollItems(0, null));
        items.forEach(this::createLine);
    }

    public void setInventoryText(Color c, String cnt){
        miniLog.setForeground(c);
        miniLog.setText(cnt);
    }

    public void setInventoryText(){
        setInventoryText(Color.BLUE , Language.logInventory());
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
                add(new choiceButton(ai, this));
            };
            setToolTipText(ai.getEffect());
            super.addActionListener(al);
        }
    }



    private class choiceButton extends JPanel {
        public choiceButton(AbstractCollectableItem ai, myButton mb) {
            setLayout(new GridLayout(0,3));
            Player player = Player.getInstancePlayer();
            JButton equip = new JButton();
            JButton throwAway = new JButton(Language.logThrow());
            JButton esc = new JButton(Language.back());
            equip.addActionListener(e -> {
                equipping(ai, ai.use());
                GameWindow.refreshInventory();
                updateInventory();
            });
            throwAway.addActionListener(e -> {
                if(!player.throwItem(ai))
                    setInventoryText(Color.RED, Language.logCantDropItem());
                updateInventory();
            });
            esc.addActionListener(e -> {
                updateInventory();
            });
            equip.setText(
                    (ai instanceof ItemEquip) ?
                            (((ItemEquip) ai).isEquipped() ? Language.logDisEquip() : Language.logEquip()) :
                            Language.logConsume());
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
