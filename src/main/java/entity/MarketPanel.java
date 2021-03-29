package entity;

import graphics.Language;
import graphics.window.GameWindow;
import items.Collectables.AbstractCollectableItems;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

import static graphics.window.GameInterfacePanel.createLog;

public class MarketPanel extends JPanel {
    public final static MarketPanel marketPanel = new MarketPanel();
    private MarketPanel(){
        setLayout(new GridLayout(0,1));
    }

    private void createLine(AbstractCollectableItems ai) {
        String s1 = ai.toString(), s2 = ai.getEffect(), s3 = ai.getPrice() + "$";
        JButton jButton = new itemButton(ai);
        JPanel panel = new JPanel(new BorderLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel fstCol = createLog(s1, Color.GRAY);
        JLabel sndCol = createLog(s2, Color.GRAY);
        JLabel thrCol = createLog(s3, Color.GRAY);
        if (Player.getInstancePlayer().enoughMoney(ai.getPrice())) thrCol.setForeground(Color.green);
        else thrCol.setForeground(Color.red);

        fstCol.setHorizontalAlignment(SwingConstants.LEFT);
        sndCol.setHorizontalAlignment(SwingConstants.CENTER);
        thrCol.setHorizontalAlignment(SwingConstants.RIGHT);

        panel.add(fstCol, BorderLayout.WEST);
        panel.add(sndCol, BorderLayout.CENTER);
        panel.add(thrCol, BorderLayout.EAST);

        jButton.add(panel);
        add(jButton);

    }

    public void makeMarket(List<AbstractCollectableItems> items){
        items.forEach(this::createLine);
    }

    private static class itemButton extends JButton{
        private final AbstractCollectableItems ai;
        private final ActionListener al;

        itemButton(AbstractCollectableItems ai){
            super();
            setFocusable(false);
            this.ai = ai;
            al = e -> {
                if (Player.getInstancePlayer().getHP() == 0) return;
                if (Player.getInstancePlayer().enoughMoney(ai.getPrice())) {
                    Player.getInstancePlayer().modifyMoney(-ai.getPrice());
                    GameWindow.addToLogs(ai.toString() + " acheté !", Color.GREEN);//to translate
                    Merchant.removeItem(ai);
                    marketPanel.remove(this);
                    marketPanel.revalidate();
                    marketPanel.repaint();
                    Player.addItem(ai);
                }
                else GameWindow.addToLogs(Language.logNotEnoughMoney(), Color.RED);
                GameWindow.refreshInventory();
            };
            super.addActionListener(al);
        }
    }

    public void updateMarket(){
        Component[] tc = marketPanel.getComponents();
        for (Component c : tc){
            updateItem((JButton) c);
        }
    }

    public void updateItem(JButton jb){
        JLabel j = (JLabel) ((JPanel) jb.getComponent(0)).getComponent(2);
        if (Player.getInstancePlayer().enoughMoney(Integer.parseInt(j.getText().replaceFirst(".$","")))) j.setForeground(Color.green);
        else j.setForeground(Color.RED);
    }
}
