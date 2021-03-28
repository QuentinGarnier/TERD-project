package graphics.window;

import entity.Player;
import items.Collectables.AbstractCollectableItems;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import static graphics.window.GameInterfacePanel.createLog;

public class InventoryPanel extends JPanel {
    public final static InventoryPanel inventoryPane = new InventoryPanel();
    private InventoryPanel(){
        setLayout(new GridLayout(0,1));
    }

    private void createLine(AbstractCollectableItems ai) {
        String s1 = (ai.equals(Player.getInstancePlayer().getDefenceItem()) ||
                ai.equals(Player.getInstancePlayer().getAttackItem())? "(Equipped) " : "") + ai.toString(), s2 = ai.getEffect(), s3 = "" + ai.getPrice();
        JButton jButton = new myButton(ai);
        JPanel panel = new JPanel(new BorderLayout());
        GridBagConstraints c = new GridBagConstraints();
        JLabel fstCol = createLog(s1, Color.GRAY);
        JLabel sndCol = createLog(s2, Color.GRAY);
        JLabel thrCol = createLog(s3, Color.GRAY);

        fstCol.setHorizontalAlignment(SwingConstants.LEFT);
        sndCol.setHorizontalAlignment(SwingConstants.CENTER);
        thrCol.setHorizontalAlignment(SwingConstants.RIGHT);

        panel.add(fstCol, BorderLayout.WEST);
        panel.add(sndCol, BorderLayout.CENTER);
        panel.add(thrCol, BorderLayout.EAST);
        jButton.add(panel);
        add(jButton);
        Dimension parentSize = getComponent(0).getPreferredSize();
        int x = (int) parentSize.getWidth(), y = (int) parentSize.getHeight();
        if (fstCol.getPreferredSize().getWidth() != 0) {
            fstCol.setPreferredSize(new Dimension((int) (x * 11 / 24.0), y));
            sndCol.setPreferredSize(new Dimension((int) (x * 11 / 24.0), y));
            thrCol.setPreferredSize(new Dimension((int) (x *  2 / 24.0), y));
        }

    }

    public void updateInventory(){
        removeAll();
        List<AbstractCollectableItems> items = new ArrayList<>();//Player.getInventory();
        for (int i = 0; i < 20; i++)items.add(AbstractCollectableItems.generateAbstractCollItems(0, null));
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
                if (Player.getInstancePlayer().getHP() == 0) return;
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
