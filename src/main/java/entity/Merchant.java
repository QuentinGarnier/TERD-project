package entity;

import graphics.Language;
import graphics.Tools;
import graphics.elements.Position;
import graphics.map.WorldMap;
import graphics.window.GameWindow;
import items.collectables.AbstractCollectableItem;
import items.collectables.ItemEquip;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import static graphics.window.GameInterfacePanel.createLog;

public class Merchant extends AbstractEntity{
    private static final Merchant instanceMerchant = new Merchant();
    private int counter;
    public final static int marketSize = 20;
    private final List<AbstractCollectableItem> market;
    private int isMoving;
    private int safeRoomId;
    private final JDialog marketWindow;
    public final ImageIcon merchantIcon;

    public Merchant()  {
        super(new Position(0, 0), 0, EntityType.ALLY_MERCHANT);
        counter = WorldMap.MAX_X * WorldMap.MAX_Y + 1;
        isMoving = 0;
        market = new ArrayList<>();
        marketWindow = new JDialog(GameWindow.window, true);
        merchantIcon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("data/images/entities/merchant/merchant.png")));

        initializeWindow();
    }

    public static Merchant getInstanceMerchant() {
        return instanceMerchant;
    }

    public void generateMarket() {
        market.clear();

        BuyPanel.buyPanel.removeAll();

        for(int i = 0; i < marketSize; i++)
            market.add(AbstractCollectableItem.generateAbstractCollItems(counter++, null));

        BuyPanel.buyPanel.makeMarket(market);
        marketWindow.repaint();
        marketWindow.revalidate();
        marketWindow.getContentPane().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ESCAPE) marketWindow.setVisible(false);
            }
        });
        marketWindow.getContentPane().setFocusable(true);
    }

    public void refreshWindows() {
        marketWindow.setTitle(Language.merketTitle());
        JTabbedPane jtp = (JTabbedPane) marketWindow.getContentPane().getComponent(0);
        jtp.setTitleAt(0, Language.logBuyOrSell(true, true));
        jtp.setTitleAt(1, Language.logBuyOrSell(false, true));
        generateMarket();
    }

    private void initializeWindow() {
        marketWindow.setPreferredSize(new Dimension(850, 300));
        marketWindow.setResizable(false);
        marketWindow.pack();
        marketWindow.setLocationRelativeTo(null);
        marketWindow.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("data/images/system/market.png")));
        marketWindow.setIconImage(icon.getImage());
        Language.setOptionPaneLang();

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab(Language.logBuyOrSell(true, true), new JScrollPane(BuyPanel.buyPanel));
        tabs.addTab(Language.logBuyOrSell(false, true), new JScrollPane(SellPanel.sellPanel));
        tabs.setFocusable(false);

        marketWindow.add(tabs);

        refreshWindows();

        SellPanel.sellPanel.makeInventory(Player.getInventory());

        marketWindow.repaint(); marketWindow.revalidate();
    }

    public void openMarket() {
        marketWindow.setVisible(true);
    }

    public boolean isMoving(){ return (isMoving % 3 == 0); }

    public void updateMoving(){ isMoving = isMoving % 3 + 1; }

    public int getSafeRoomId() { return safeRoomId; }

    public void setSafeRoomId(int safeRoom) { this.safeRoomId = safeRoom; }

    public List<AbstractCollectableItem> getMarket(){ return Collections.unmodifiableList(market); }

    public JDialog getMarketWindow() {return marketWindow; }

    public static void removeItem(AbstractCollectableItem ai){ instanceMerchant.market.remove(ai); }

    private static void createLine(boolean isBuy, AbstractCollectableItem ai) {
        String s1 = ai.toString(), s2 = ai.getEffect(), s3 = (isBuy ? ai.getPrice() : ai.getPrice()/2) + "$";
        Color c = Color.BLACK;
        if (ai instanceof ItemEquip) {
            ItemEquip ie = (ItemEquip) ai;
            switch (ie.getEquipmentType().getEntityType()) {
                case HERO_WARRIOR -> c = Tools.WindowText.red;
                case HERO_MAGE -> c = Tools.WindowText.blue;
                case HERO_ARCHER -> c = Tools.WindowText.green;
            }
        }
        
        JButton jButton = isBuy ? new BuyPanel.BuyItemButton(ai) : new SellPanel.SellItemButton(ai);
        JPanel panel = new JPanel(new FlowLayout());

        JLabel fstCol = createLog(s1, c);
        JLabel sndCol = createLog("<html><body><i />" + s2 + "</body></html>", Color.DARK_GRAY);
        JLabel thrCol = createLog(s3, Color.BLACK);

        if (isBuy) {
            if (Player.getInstancePlayer().enoughMoney(ai.getPrice())) thrCol.setForeground(Color.green);
            else thrCol.setForeground(Color.red);

        } else { thrCol.setForeground(Tools.WindowText.golden); }


        fstCol.setPreferredSize(new Dimension(200, (int)fstCol.getPreferredSize().getHeight()));
        sndCol.setPreferredSize(new Dimension(500, (int)fstCol.getPreferredSize().getHeight()));
        thrCol.setPreferredSize(new Dimension(50, (int)fstCol.getPreferredSize().getHeight()));

        panel.add(fstCol);
        panel.add(sndCol);
        panel.add(thrCol);

        jButton.add(panel);
        MerchantMouseListener(jButton);

        if (isBuy) BuyPanel.buyPanel.add(jButton);
        else SellPanel.sellPanel.add(jButton);
    }

    private static void MerchantMouseListener(JButton button) {
        button.getComponent(0).setBackground(Color.WHITE);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createBevelBorder(BevelBorder.RAISED, Tools.WindowText.golden, Tools.WindowText.dark_golden),
                        BorderFactory.createLineBorder(Tools.WindowText.golden, 2)),
                BorderFactory.createLineBorder(new Color(140, 110, 70))));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {button.getComponent(0).setBackground(new Color(180, 150, 110)); }
            @Override
            public void mouseExited(MouseEvent e) { button.getComponent(0).setBackground(Color.WHITE); }
        });
    }

    private static void restoreFocus() {
        instanceMerchant.marketWindow.setFocusable(true);
    }

    public static class BuyPanel extends JPanel {

        public final static BuyPanel buyPanel = new BuyPanel();
        private BuyPanel() {
            setLayout(new GridLayout(0,1));
            setFocusable(false);
        }

        public void makeMarket(List<AbstractCollectableItem> items) {
            items.forEach((item) -> createLine(true, item));
        }

        private static class BuyItemButton extends JButton {

            BuyItemButton(AbstractCollectableItem ai) {
                super();
                ActionListener al = e -> {
                    Player pl = Player.getInstancePlayer();
                    String confirmText = Language.confirmBuy();

                    if (Player.getInventory().size() >= Player.MAX_INVENTORY_SIZE) {
                        GameWindow.addToLogs(Language.logInventoryFull(), Color.RED);
                        GameWindow.refreshInventory(true);
                        JOptionPane.showMessageDialog(Merchant.getInstanceMerchant().getMarketWindow(), Language.logInventoryFullMarket(), Language.logInventoryMarket(), JOptionPane.WARNING_MESSAGE, instanceMerchant.merchantIcon);
                        restoreFocus();
                        return;
                    }

                    if (ai instanceof ItemEquip) {
                        ItemEquip ie = (ItemEquip) ai;
                        if (pl.getEntityType() != ie.getEquipmentType().getEntityType())
                            confirmText = Language.confirmDialog(true, false);
                    }
                    if (JOptionPane.showConfirmDialog(Merchant.getInstanceMerchant().getMarketWindow(), confirmText, Language.confirmDialog(true, true),
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, instanceMerchant.merchantIcon) != JOptionPane.YES_OPTION) {
                        restoreFocus();
                        return;
                    }

                    if (pl.enoughMoney(ai.getPrice())) {
                        pl.modifyMoney(-ai.getPrice());
                        if (GameWindow.hasSound())
                            Tools.play(Objects.requireNonNull(getClass().getClassLoader().getResource("data/audio/SE/coin_buy.wav")), false);
                        GameWindow.addToLogs(ai + " " + Language.logBuyOrSell(true, false), Color.GREEN);
                        Merchant.removeItem(ai);
                        buyPanel.remove(this);
                        buyPanel.revalidate();
                        buyPanel.repaint();
                        Player.addItem(ai);
                        SellPanel.sellPanel.addSellInventory(ai);
                    }
                    else GameWindow.addToLogs(Language.logNotEnoughMoney(), Color.RED);
                    GameWindow.refreshInventory(true);
                    restoreFocus();
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
            setLayout(gl);
            setFocusable(false);
        }
        
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
            public final AbstractCollectableItem ai;

            SellItemButton(AbstractCollectableItem ai) {
                super();
                this.ai = ai;
                ActionListener al = e -> {
                    if (JOptionPane.showConfirmDialog(Merchant.getInstanceMerchant().getMarketWindow(), Language.confirmDialog(false, false),
                            Language.confirmDialog(false, true), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, instanceMerchant.merchantIcon) == JOptionPane.YES_OPTION) {
                        int gain = (ai.getPrice() / 2);
                        Player.getInstancePlayer().modifyMoney(gain);
                        if (GameWindow.hasSound())
                            Tools.play(Objects.requireNonNull(getClass().getClassLoader().getResource("data/audio/SE/coin_sell.wav")), false);
                        GameWindow.addToLogs(ai.toString() + " " + Language.logBuyOrSell(false, false) + " (+" + gain + " " + Language.logMoney() + ")", Tools.WindowText.golden);
                        Player.removeItem(ai);
                        sellPanel.remove(this);
                        sellPanel.revalidate();
                        sellPanel.repaint();
                        createLine(true, ai);
                        BuyPanel.buyPanel.revalidate();
                        BuyPanel.buyPanel.repaint();
                        GameWindow.refreshInventory(true);
                    }
                    restoreFocus();
                };
                super.addActionListener(al);
            }
        }
    }
}
