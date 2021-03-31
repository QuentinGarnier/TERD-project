package entity;

import graphics.Language;
import graphics.Tools;
import graphics.elements.ErrorPositionOutOfBound;
import graphics.elements.Position;
import graphics.map.WorldMap;
import graphics.window.GameWindow;
import items.collectables.AbstractCollectableItem;

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

    public Merchant() throws ErrorPositionOutOfBound {
        super(new Position(0, 0), 0, EntityType.ALLY_MERCHANT);
        counter = WorldMap.MAX_X * WorldMap.MAX_Y + 1;// WorldMap.getInstanceWorld().getItems().size();
        isMoving = 0;
        market = new ArrayList<>();
        marketWindow = new JDialog();

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
        marketWindow.setTitle("Merchant's market");
        marketWindow.setPreferredSize(new Dimension(900, 300));
        marketWindow.setResizable(false);
        marketWindow.setLocationRelativeTo(null);
        marketWindow.pack();
        marketWindow.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        ImageIcon icon = new ImageIcon("data/images/system/market.png");
        marketWindow.setIconImage(icon.getImage());
        marketWindow.setModal(true);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Acheter", new JScrollPane(BuyPanel.buyPanel));
        tabs.addTab("Vendre", new JScrollPane(SellPanel.sellPanel));

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

    public static class BuyPanel extends JPanel {
        public final static BuyPanel buyPanel = new BuyPanel();
        private BuyPanel(){
            setLayout(new GridLayout(0,1));
        }

        private void createLine(AbstractCollectableItem ai) {
            String s1 = ai.toString(), s2 = ai.getEffect(), s3 = ai.getPrice() + "$";
            JButton jButton = new BuyItemButton(ai);
            JPanel panel = new JPanel(new BorderLayout());

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

        public void makeMarket(List<AbstractCollectableItem> items){
            items.forEach(this::createLine);
        }

        private static class BuyItemButton extends JButton{
            private final ActionListener al;

            BuyItemButton(AbstractCollectableItem ai){
                super();
                al = e -> {
                    if (Player.getInstancePlayer().getHP() == 0) return;
                    if (Player.getInstancePlayer().enoughMoney(ai.getPrice())) {
                        Player.getInstancePlayer().modifyMoney(-ai.getPrice());
                        GameWindow.addToLogs(ai.toString() + " acheté(e) !", Color.GREEN);//to translate
                        Merchant.removeItem(ai); buyPanel.remove(this); buyPanel.revalidate(); buyPanel.repaint();
                        Player.addItem(ai); //SellPanel.sellPanel.add(); SellPanel.sellPanel.revalidate(); SellPanel.sellPanel.repaint();
                        SellPanel.sellPanel.addSellInventory(ai);
                    }
                    else GameWindow.addToLogs(Language.logNotEnoughMoney(), Color.RED);
                    GameWindow.refreshInventory();
                };
                super.addActionListener(al);
            }
        }

        public void updateMarket(){
            Component[] tc = buyPanel.getComponents();
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

    public static class SellPanel extends JPanel {
        public final static SellPanel sellPanel = new SellPanel();
        private SellPanel() { setLayout(new GridLayout(0, 1)); }

        private void createLine(AbstractCollectableItem ai) {
            String s1 = ai.toString(), s2 = ai.getEffect(), s3 = ai.getPrice()/2 + "$";
            JButton jButton = new SellItemButton(ai);
            JPanel panel = new JPanel(new BorderLayout());

            JLabel fstCol = createLog(s1, Color.GRAY);
            JLabel sndCol = createLog(s2, Color.GRAY);
            JLabel thrCol = createLog(s3, Color.GRAY);

            thrCol.setForeground(Tools.WindowText.golden);

            fstCol.setHorizontalAlignment(SwingConstants.LEFT);
            sndCol.setHorizontalAlignment(SwingConstants.CENTER);
            thrCol.setHorizontalAlignment(SwingConstants.RIGHT);

            panel.add(fstCol, BorderLayout.WEST);
            panel.add(sndCol, BorderLayout.CENTER);
            panel.add(thrCol, BorderLayout.EAST);

            jButton.add(panel);
            add(jButton);

        }

        public void makeInventory(List<AbstractCollectableItem> items){
            items.forEach(this::createLine);
        }

        public void addSellInventory(AbstractCollectableItem item) {
            createLine(item);
            Merchant.getInstanceMerchant().getMarketWindow().repaint(); Merchant.getInstanceMerchant().getMarketWindow().revalidate();
        }

        private static class SellItemButton extends JButton {
            private final ActionListener al;

            SellItemButton(AbstractCollectableItem ai) {
                super();
                al = e -> {
                    if (Player.getInstancePlayer().getHP() == 0) return;

                    if (0 == JOptionPane.showConfirmDialog(Merchant.getInstanceMerchant().getMarketWindow(), "Voulez-vous vraiment vendre l'objet ?", "Confirmation de vente", JOptionPane.YES_NO_OPTION)) {
                        int gain = (ai.getPrice()/2);
                        Player.getInstancePlayer().modifyMoney(gain);
                        GameWindow.addToLogs(ai.toString() + " vendu(e) ! (+" + gain + " pièces)", Tools.WindowText.golden);//to translate
                        Player.removeItem(ai); sellPanel.remove(this); sellPanel.revalidate(); sellPanel.repaint();
                        BuyPanel.buyPanel.createLine(ai); BuyPanel.buyPanel.revalidate(); BuyPanel.buyPanel.repaint();
                        GameWindow.refreshInventory();
                    }
                };

                super.addActionListener(al);
            }
        }
    }
}
