package graphics.window;

import entity.Player;
import graphics.Language;
import items.Collectables.AbstractCollectableItems;
import items.Collectables.ItemConsumable;
import items.Collectables.ItemEquip;
import items.Collectables.ItemFood;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import static graphics.window.GameInterfacePanel.createLog;

public class InventoryPanel extends JPanel {
    public final static InventoryPanel inventoryPane = new InventoryPanel();
    private final JPanel contents;
    private final JLabel miniLog;
    private final JScrollPane scrollPane;
    private InventoryPanel(){
        setLayout(new BorderLayout());
        miniLog = new JLabel();
        contents = new JPanel(new GridLayout(0,1));
        scrollPane = new JScrollPane(contents);
        miniLog.setHorizontalAlignment(SwingConstants.CENTER);
        setInventoryText();
        add(miniLog, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void createLine(AbstractCollectableItems ai) {
        String s1 = ai.toString(), s2 = ai.getEffect();
        int s3 = ai.getPrice();
        JButton jButton = new myButton(ai);
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
        jButton.add(panel);
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
        List<AbstractCollectableItems> items = Player.getInventory();//new ArrayList<>();
        //for (int i = 0; i < 20; i++)items.add(AbstractCollectableItems.generateAbstractCollItems(0, null));
        items.forEach(this::createLine);
        if (items.size() == 0) {
            scrollPane.repaint();
            setInventoryText();
        }
    }

    public void setInventoryText(){
        miniLog.setForeground(Color.BLUE);
        miniLog.setText(Language.logInventory());
    }

    private void equipping(AbstractCollectableItems ai, boolean isEquipping){
        miniLog.setForeground(isEquipping ? Color.GREEN : Color.RED);
         miniLog.setText(
                 (ai instanceof ItemEquip) ?
                         Language.translate(((ItemEquip) ai).getEquipmentType()) + " " + (((ItemEquip) ai).isEquipped() ? Language.logEquipped() : Language.logRejected()) :
                         (((ai instanceof ItemFood) ?
                                 Language.logFood() : Language.translate(((ItemConsumable) ai).ct)) + " " + Language.logConsumed()));
    }

    private class myButton extends JButton{
        myButton(AbstractCollectableItems ai){
            super();
            setMargin(null);
            setFocusable(false);
            ActionListener al = e -> {
                if (Player.getInstancePlayer().getHP() == 0) return;
                equipping(ai, ai.use());
                GameWindow.refreshInventory();
                updateInventory();
            };
            super.addActionListener(al);
        }
    }
}
