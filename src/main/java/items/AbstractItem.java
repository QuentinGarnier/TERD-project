package items;

import graphics.Language;
import graphics.elements.ErrorPositionOutOfBound;
import graphics.elements.Position;
import graphics.map.WorldMap;
import graphics.window.GamePanel;
import graphics.window.GameWindow;
import items.Collectables.ItemConsumable;
import items.Collectables.ItemEquip;
import items.Collectables.ItemFood;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import java.util.Random;

public abstract class AbstractItem extends JLabel {
    public final static AbstractItem end = new AbstractItem(0, ItemType.END, null, true) {
        @Override
        public boolean usePrivate() throws ErrorPositionOutOfBound {
            GameWindow.addToLogs(Language.logLowerFloor(), Color.GREEN);
            WorldMap worldMap = WorldMap.getInstanceWorld();
            worldMap.generateWorld();
            GameWindow.display();
            // TODO
            return false;
        }
    };
    private static int idCounter = 0;
    public final ItemType type;
    private final int idPosRoom;
    private final int id;

    public final boolean immediateUse;
    private Position position;

    private final int size;

    public AbstractItem(int i, ItemType t, Position position, boolean immediateUse) {
        this.idPosRoom = i;
        this.type = t;
        this.immediateUse = immediateUse;
        this.position = position;
        this.id = idCounter++;

        // Graphics
        this.size = GamePanel.size;
        setIcon(type.cellElementType.getIcon());
        setSize(size, size);
        setLocation();
    }

    // Graphics
    public void setLocation() {
        if (position == null) super.setLocation(-size, -size);
        else super.setLocation(position.getX() * size, position.getY() * size);
    }

    public static AbstractItem generateRandomItem(int id, Position p) {
        if(new Random().nextInt(2) == 0) return new ItemCoin(id, p);
        else {
            ItemType[] itemTypes = ItemType.values();
            int rndElt = new Random().nextInt(itemTypes.length);
            AbstractItem res = null;
            ItemType itemType = itemTypes[rndElt];
            switch (itemType) {
                case COIN -> res = new ItemCoin(id, p);
                case CONSUMABLE -> res = new ItemConsumable(id, p);
                case EQUIP -> res = new ItemEquip(id, p);
                case FOOD -> res = new ItemFood(id, p);
                case TRAP -> res = new ItemTrap(id, p);
                case END -> res = generateRandomItem(id, p);
            }
            return res;
        }
    }

    public int getIdPosRoom() {
        return this.idPosRoom;
    }

    public Position getPosition() {
        return position;
    }

    public boolean use() throws ErrorPositionOutOfBound {
        this.position = null;
        setLocation();
        return usePrivate();
    }

    public abstract boolean usePrivate() throws ErrorPositionOutOfBound;  //return true if not consumed (equip), else return false (food, consumable)

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof AbstractItem){
            return ((AbstractItem) o).id == id;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "[#" + (id < 100 ? (id < 10 ? "00" : "0") : "") + id + "] ";
    }
}
