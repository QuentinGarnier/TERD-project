package entity;

import graphics.elements.ErrorPositionOutOfBound;
import graphics.elements.Position;
import graphics.map.WorldMap;
import items.*;
import items.Collectables.AbstractCollectableItems;
import items.Collectables.ItemConsumable;
import items.Collectables.ItemEquip;
import items.Collectables.ItemFood;

import java.util.*;

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

    public Merchant() throws ErrorPositionOutOfBound {
        super(new Position(0, 0), 0, EntityType.ALLY_MERCHANT);
        counter = 0;// WorldMap.getInstanceWorld().getItems().size();
        isMoving = 0;
        market = new ArrayList<>();
        generateMarket();
    }

    public static Merchant getInstanceMerchant() {
        return instanceMerchant;
    }

    private void generateMarket(){
        for(int i = 0; i < marketSize; i++) {
            market.add(AbstractCollectableItems.generateAbstractCollItems(counter++, null));
        }
    }

    public boolean isMoving(){ return (isMoving % 3 == 0); }

    public void updateMoving(){ isMoving = isMoving % 3 + 1; }

    public int getSafeRoomId() { return safeRoomId; }

    public void setSafeRoomId(int safeRoom) { this.safeRoomId = safeRoom; }

    public List<AbstractCollectableItems> getMarket(){ return Collections.unmodifiableList(market); }
}
