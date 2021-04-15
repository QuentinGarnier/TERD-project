package entity;

import graphics.Language;
import graphics.Tools;
import graphics.elements.ErrorPositionOutOfBound;
import graphics.elements.Position;
import graphics.map.WorldMap;
import graphics.window.GameWindow;
import items.collectables.AbstractCollectableItem;
import items.collectables.ItemEquip;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

import static graphics.window.GameInterfacePanel.createLog;

public class Merchant extends AbstractEntity{
    private static Merchant instanceMerchant;
    static {
        try {
            instanceMerchant = new Merchant();
        } catch (ErrorPositionOutOfBound errorPositionOutOfBound) {
            errorPositionOutOfBound.printStackTrace();
        }
    }
    private int counter;
    public final static int marketSize = 20;
    private final List<AbstractCollectableItem> market;
    private int isMoving;
    private int safeRoomId;
    private final JDialog marketWindow;
    public final ImageIcon merchantIcon;

    public Merchant() throws ErrorPositionOutOfBound {
        super(new Position(0, 0), 0, EntityType.ALLY_MERCHANT);
        counter = WorldMap.MAX_X * WorldMap.MAX_Y + 1;
        isMoving = 0;
        market = new ArrayList<>();
        marketWindow = new JDialog(GameWindow.window, Language.merketTitle(), true);
        merchantIcon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("data/images/entities/merchant/merchant.png")));

        generateMarket();
        SellPanel.sellPanel.makeInventory(Player.getInventory());
        marketWindow.repaint(); marketWindow.revalidate();
        initializeWindow();

    }

    public static Merchant getInstanceMerchant() {
        return instanceMerchant;
    }

    public void generateMarket(){
        market.clear();
        BuyPanel.buyPanel.removeAll();

        for(int i = 0; i < marketSize; i++) {
            market.add(AbstractCollectableItem.generateAbstractCollItems(counter++, null));
        }
        BuyPanel.buyPanel.makeMarket(market);
        marketWindow.repaint(); marketWindow.revalidate();
    }

    private void initializeWindow(){
        marketWindow.setPreferredSize(new Dimension(900, 300));
        marketWindow.setResizable(false);
        marketWindow.pack();
        marketWindow.setLocationRelativeTo(null);
        marketWindow.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("data/images/system/market.png")));
        marketWindow.setIconImage(icon.getImage());

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab(Language.logBuyOrSell(true, true), new JScrollPane(BuyPanel.buyPanel));
        tabs.addTab(Language.logBuyOrSell(false, true), new JScrollPane(SellPanel.sellPanel));

        marketWindow.add(tabs);
    }

    public void openMarket(){ marketWindow.setVisible(true); }

    public boolean isMoving(){ return (isMoving % 3 == 0); }

    public void updateMoving(){ isMoving = isMoving % 3 + 1; }

    public int getSafeRoomId() { return safeRoomId; }

    public void setSafeRoomId(int safeRoom) { this.safeRoomId = safeRoom; }

    public List<AbstractCollectableItem> getMarket(){ return Collections.unmodifiableList(market); }

    public JDialog getMarketWindow() {return marketWindow; }

    public static void removeItem(AbstractCollectableItem ai){ instanceMerchant.market.remove(ai); }

    private static void createLine(boolean isBuy, AbstractCollectableItem ai) {
        String s1 = ai.toString(), s2 = ai.getEffect(), s3 = (isBuy ? ai.getPrice() : ai.getPrice()/2) + "$";
        JButton jButton = isBuy ? new BuyPanel.BuyItemButton(ai) : new SellPanel.SellItemButton(ai);
        JPanel panel = new JPanel(new BorderLayout());

        JLabel fstCol = createLog(s1, Color.GRAY);
        JLabel sndCol = createLog(s2, Color.GRAY);
        JLabel thrCol = createLog(s3, Color.GRAY);

        if (isBuy) {
            if (Player.getInstancePlayer().enoughMoney(ai.getPrice())) thrCol.setForeground(Color.green);
            else thrCol.setForeground(Color.red);

        } else { thrCol.setForeground(Tools.WindowText.golden); }

        fstCol.setHorizontalAlignment(SwingConstants.LEFT);
        sndCol.setHorizontalAlignment(SwingConstants.CENTER);
        thrCol.setHorizontalAlignment(SwingConstants.RIGHT);

        panel.add(fstCol, BorderLayout.WEST);
        panel.add(sndCol, BorderLayout.CENTER);
        panel.add(thrCol, BorderLayout.EAST);

        jButton.add(panel);

        if (isBuy) BuyPanel.buyPanel.add(jButton);
        else SellPanel.sellPanel.add(jButton);
    }



    public static class BuyPanel extends JPanel {

        public final static BuyPanel buyPanel = new BuyPanel();
        private BuyPanel() {
            setLayout(new GridLayout(0,1));
        }

        public void makeMarket(List<AbstractCollectableItem> items){ items.forEach((item) -> createLine(true, item)); }

        private static class BuyItemButton extends JButton {
            private final ActionListener al;

            BuyItemButton(AbstractCollectableItem ai) {
                super();
                al = e -> {
                    Player pl = Player.getInstancePlayer();
                    if (pl.getHP() == 0) return;
                    if (ai instanceof ItemEquip) {
                        ItemEquip ie = (ItemEquip) ai;
                        if (pl.getEntityType() != ie.getEquipmentType().getEntityType())
                            if (0 != JOptionPane.showConfirmDialog(Merchant.getInstanceMerchant().getMarketWindow(), Language.confirmDialog(true, false), Language.confirmDialog(true, true), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, instanceMerchant.merchantIcon)) return;
                    }
                    if (Player.getInventory().size() >= Player.MAX_INVENTORY_SIZE) { GameWindow.addToLogs(Language.logInventoryFull(), Color.RED); GameWindow.refreshInventory(); return; }
                    if (pl.enoughMoney(ai.getPrice())) {
                        pl.modifyMoney(-ai.getPrice());
                        if(!GameWindow.isMuted()) Tools.play(Objects.requireNonNull(getClass().getClassLoader().getResource("data/audio/SE/coin_buy.wav")), false);
                        GameWindow.addToLogs(ai + " " + Language.logBuyOrSell(true, false), Color.GREEN);
                        Merchant.removeItem(ai); buyPanel.remove(this); buyPanel.revalidate(); buyPanel.repaint();
                        Player.addItem(ai);
                        SellPanel.sellPanel.addSellInventory(ai);
                    }
                    else GameWindow.addToLogs(Language.logNotEnoughMoney(), Color.RED);
                    GameWindow.refreshInventory();
                };
                super.addActionListener(al);
            }
        }

        public void updateMarket() {
            Component[] tc = buyPanel.getComponents();
            for(Component c : tc) updateItem((JButton) c);
        }

        public void updateItem(JButton jb) {
            JLabel j = (JLabel) ((JPanel) jb.getComponent(0)).getComponent(2);
            if(Player.getInstancePlayer().enoughMoney(Integer.parseInt(j.getText().replaceFirst(".$","")))) j.setForeground(Color.green);
            else j.setForeground(Color.RED);
        }
    }

    public static class SellPanel extends JPanel {

        public final static SellPanel sellPanel = new SellPanel();
        private final GridLayout gl;
        private int lineCount;
        private SellPanel() {
            lineCount = 0;
            gl = new GridLayout(11, 1);
            setLayout(gl); }
        
        public void makeInventory(List<AbstractCollectableItem> items) {
            items.forEach((item) -> createLine(false, item));
        }

        public void addSellInventory(AbstractCollectableItem item) {
            createLine(false, item);
            lineCount++; gl.setRows(Math.max(11, lineCount));
            Merchant.getInstanceMerchant().getMarketWindow().repaint(); Merchant.getInstanceMerchant().getMarketWindow().revalidate();
        }

        public void removeSellInventory(AbstractCollectableItem item) {
            Component[] cs = sellPanel.getComponents();
            lineCount--; gl.setRows(Math.max(11, lineCount));
            for (Component c : cs){
                if ((((SellItemButton) c).ai).getId() == item.getId()) {
                    sellPanel.remove(c);
                    return;
                }
            }
        }

        public void clearSell() {
            Component[] cs = sellPanel.getComponents();
            gl.setRows(Math.max(11, 0));
            for (Component c : cs){
                sellPanel.remove(c);
                lineCount--;
            }
        }

        private static class SellItemButton extends JButton {
            private final ActionListener al;
            public final AbstractCollectableItem ai;

            SellItemButton(AbstractCollectableItem ai) {
                super();
                this.ai = ai;
                al = e -> {
                    if (Player.getInstancePlayer().getHP() == 0) return;
                    if (0 == JOptionPane.showConfirmDialog(Merchant.getInstanceMerchant().getMarketWindow(), Language.confirmDialog(false, false), Language.confirmDialog(false, true), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, instanceMerchant.merchantIcon)) {
                        int gain = (ai.getPrice()/2);
                        Player.getInstancePlayer().modifyMoney(gain);
                        if(!GameWindow.isMuted()) Tools.play(Objects.requireNonNull(getClass().getClassLoader().getResource("data/audio/SE/coin_sell.wav")), false);
                        GameWindow.addToLogs(ai.toString() + " " + Language.logBuyOrSell(false, false) + " (+" + gain + " " + Language.logMoney() + ")", Tools.WindowText.golden);
                        Player.removeItem(ai); sellPanel.remove(this); sellPanel.revalidate(); sellPanel.repaint();
                        createLine(true, ai); BuyPanel.buyPanel.revalidate(); BuyPanel.buyPanel.repaint();
                        GameWindow.refreshInventory();
                    }
                };
                super.addActionListener(al);
            }
        }
    }
}
