package graphics.window;

import entity.Player;
import items.Collecatables.AbstractCollectableItems;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

import static graphics.window.GameInterfacePanel.createLog;

public class InventoryPanel extends JPanel {
    public final static InventoryPanel inventoryPane = new InventoryPanel();
    private InventoryPanel(){
        setLayout(new GridLayout(0,1));
    }

    private void createLine(AbstractCollectableItems ai){
        String s1 = (ai.equals(Player.getInstancePlayer().getDefenceItem()) ||
                ai.equals(Player.getInstancePlayer().getAttackItem())? "(inUse) " : "") + ai.toString(), s2 = ai.getEffect(), s3 = "" + ai.getPrice();
        JButton jButton = new myButton(ai);
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        JLabel fstCol = createLog(s1, Color.GRAY);
        JLabel sndCol = createLog(s2, Color.GRAY);
        JLabel thrCol = createLog(s3, Color.GRAY);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        panel.add(fstCol, c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        panel.add(sndCol, c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.3;
        panel.add(thrCol, c);
        jButton.add(panel);
        add(jButton);
        Dimension parentSize = getComponent(0).getPreferredSize();
        int x = (int) parentSize.getWidth(), y = (int) parentSize.getHeight();
        if (fstCol.getPreferredSize().getWidth() == 0) {
            fstCol.setPreferredSize(new Dimension((int) (x * 0.3), y * 2));
            fstCol.setSize(new Dimension((int) (x * 0.3),  y));
            sndCol.setPreferredSize(new Dimension((int) (x * 0.6), y * 2));
            sndCol.setSize(new Dimension((int) (x * 0.6), y ));
            thrCol.setPreferredSize(new Dimension((int) (x * 0.1), y * 2));
            thrCol.setSize(new Dimension((int) (x * 0.1), y ));
        }
    }

    public void updateInventory(){
        removeAll();
        List<AbstractCollectableItems> items = Player.getInventory();
        items.forEach(this::createLine);
    }

    private void myRemove (){
        updateInventory();
    }

    private class myButton extends JButton{
        private final AbstractCollectableItems ai;
        private final ActionListener al;
        myButton(AbstractCollectableItems ai){
            super();
            setFocusable(false);
            this.ai = ai;
            al = e -> {
                ai.use();
                GameWindow.refreshInventory();
                removeToFather();
            };
            super.addActionListener(al);
        }
        private void removeToFather(){
            myRemove();
        }
    }
}
