package items.Collectables;

import entity.Merchant;
import entity.Player;
import graphics.Tools;
import graphics.elements.Position;
import graphics.elements.Room;
import graphics.map.WorldMap;
import graphics.window.GameWindow;

import java.util.Locale;
import java.util.Random;

public enum ConsumableTypes {
    HEALTH_POTION("Restores 10 % of your HP.", 15),
    TELEPORT_SCROLL("Go to Merchant Room.", 40);

    public final String effect;  //Useful for description in the inventory.
    public final int price;

    ConsumableTypes(String effect, int price) {
        this.effect = effect;
        this.price = price;
    }

    public static ConsumableTypes createConsumableTypes() {
        ConsumableTypes[] consumableTypes = ConsumableTypes.values();
        int rndElt = new Random().nextInt(consumableTypes.length);
        return ConsumableTypes.values()[rndElt];
    }

    public boolean applyEffect() {
        switch (this) {
            case HEALTH_POTION -> {
                Player pl = Player.getInstancePlayer();
                int heal = (int) (Math.round(pl.getHPMax() * 0.10));
                GameWindow.addToLogs("+" + ((heal + pl.getHP() > pl.getHPMax()) ? pl.getHPMax() - pl.getHP() : heal) + "HP", Tools.WindowText.green);
                pl.modifyHP(heal);
            }
            case TELEPORT_SCROLL -> teleport();
        }
        return true;
    }

    private void teleport() {
        Random rnd = new Random();
        WorldMap wp = WorldMap.getInstanceWorld();
        Player pl = Player.getInstancePlayer();
        Merchant mc = Merchant.getInstanceMerchant();

        Room room = wp.getRooms().get(mc.getSafeRoomId());

        int x = room.getTopLeft().getX() + rnd.nextInt(room.getWidth() - 1) + 1;
        int y = room.getTopLeft().getY() + rnd.nextInt(room.getHeight() - 1) + 1;
        wp.getCell(pl.getPosition()).entityLeft();
        if (wp.getCell(x, y).isAccessible() && wp.getCell(x, y).getItem() == null) {
            pl.setPosition(x, y);
            wp.getCell(x, y).setEntity(pl);
            pl.getWhatHeroDoes().setP(new Position(x, y));
            GameWindow.window.setScrollFrameBar();
        }
        else teleport();
    }

    private void potion() {

    }

    @Override
    public String toString() {
        return this.name().charAt(0) + this.name().substring(1).replace("_", " ").toLowerCase(Locale.ROOT);
    }

    public String getEffect(){
        return effect;
    }

    public int getPrice(){ return price;}
}
