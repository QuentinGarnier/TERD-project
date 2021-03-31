package entity;

import graphics.elements.ErrorPositionOutOfBound;
import graphics.elements.Position;
import items.collectables.AbstractCollectableItem;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

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
        counter = 0;// WorldMap.getInstanceWorld().getItems().size();
        isMoving = 0;
        market = new ArrayList<>();
        marketWindow = new JDialog();

        generateMarket();
        initializeWindow();

    }

    public static Merchant getInstanceMerchant() {
        return instanceMerchant;
    }

    public void generateMarket(){
        market.clear();
        MarketPanel.marketPanel.removeAll();
        for(int i = 0; i < marketSize; i++) {
            market.add(AbstractCollectableItem.generateAbstractCollItems(counter++, null));
        }
        MarketPanel.marketPanel.makeMarket(market);
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
        marketWindow.add(new JScrollPane(MarketPanel.marketPanel));


    }

    public void openMarket(){
        marketWindow.setVisible(true);
    }

    public boolean isMoving(){ return (isMoving % 3 == 0); }

    public void updateMoving(){ isMoving = isMoving % 3 + 1; }

    public int getSafeRoomId() { return safeRoomId; }

    public void setSafeRoomId(int safeRoom) { this.safeRoomId = safeRoom; }

    public List<AbstractCollectableItem> getMarket(){ return Collections.unmodifiableList(market); }

    public static void removeItem(AbstractCollectableItem ai){ instanceMerchant.market.remove(ai); }
}
