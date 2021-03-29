package entity;

import graphics.elements.ErrorPositionOutOfBound;
import graphics.elements.Position;
import items.Collectables.AbstractCollectableItems;

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
    public final static int marketSize = 10;
    private List<AbstractCollectableItems> market;
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

    private void generateMarket(){
        for(int i = 0; i < marketSize; i++) {
            market.add(AbstractCollectableItems.generateAbstractCollItems(counter++, null));
        }
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

        MarketPanel.marketPanel.makeMarket(market);
        marketWindow.add(MarketPanel.marketPanel);

        marketWindow.repaint(); marketWindow.revalidate();
    }

    public void openMarket(){
        marketWindow.setVisible(true);
    }

    public boolean isMoving(){ return (isMoving % 3 == 0); }

    public void updateMoving(){ isMoving = isMoving % 3 + 1; }

    public int getSafeRoomId() { return safeRoomId; }

    public void setSafeRoomId(int safeRoom) { this.safeRoomId = safeRoom; }

    public List<AbstractCollectableItems> getMarket(){ return Collections.unmodifiableList(market); }
}
