package entity;

import graphics.elements.ErrorPositionOutOfBound;
import graphics.elements.Position;
import items.*;

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
    private final Random rd = new Random();
    public final static ItemType[] salableItems = {ItemType.FOOD, ItemType.CONSUMABLE, ItemType.EQUIP};
    public final static int marketSize = 10;
    public final List<AbstractItem> market;
    private int isMoving;

    public Merchant() throws ErrorPositionOutOfBound {
        super(new Position(0, 0), 0, EntityType.ALLY_MERCHANT);
        counter = 0;//WorldMap.getInstanceWorld().getItems().size();
        market = generateMarket();
        isMoving = 0;
    }

    public static Merchant getInstanceMerchant() {
        return instanceMerchant;
    }

    private List<AbstractItem> generateMarket(){
        ArrayList<AbstractItem> m = new ArrayList<>();
        for(int i = 0; i < marketSize; i++) {
            ItemType it = Objects.requireNonNull(salableItems)[rd.nextInt(salableItems.length)];
            switch (it) {
                case CONSUMABLE -> m.add(new ItemConsumable(++counter, null));
                case EQUIP -> m.add(new ItemEquip(++counter, null));
                case FOOD -> m.add(new ItemFood(++counter, null));
            }
        }
        return Collections.unmodifiableList(m);
    }

    public boolean isMoving(){ return (isMoving % 3 == 0); }

    public void updateMoving(){ isMoving = isMoving % 3 + 1; }


}
