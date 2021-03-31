package items.collectables;

import entity.EntityState;
import entity.Merchant;
import entity.Player;
import graphics.Language;
import graphics.Tools;
import graphics.elements.Position;
import graphics.elements.Room;
import graphics.map.WorldMap;
import graphics.window.GameWindow;

import java.util.Locale;
import java.util.Random;

public enum ConsumableTypes {
    HEALTH_POTION( 15),
    REGENERATION_POTION(10),
    TELEPORT_SCROLL(40),
    DIVINE_BLESSING(30);

    public final int price;

    ConsumableTypes(int price) {
        this.price = price;
    }

    public static ConsumableTypes createConsumableTypes() {
        ConsumableTypes[] consumableTypes = ConsumableTypes.values();
        int rndElt = new Random().nextInt(consumableTypes.length);
        return ConsumableTypes.values()[rndElt];
    }

    public boolean applyEffect() {
        Player pl = Player.getInstancePlayer();
        switch (this) {
            case HEALTH_POTION -> {

                int heal = (int) (Math.round(pl.getHPMax() * 0.10));
                GameWindow.addToLogs("+" + ((heal + pl.getHP() > pl.getHPMax()) ? pl.getHPMax() - pl.getHP() : heal) + " " + Language.hp(), Tools.WindowText.green);
                pl.modifyHP(heal);
            }
            case REGENERATION_POTION -> pl.updateState(EntityState.HEALED);
            case TELEPORT_SCROLL -> teleport();
            case DIVINE_BLESSING -> pl.updateState(EntityState.INVULNERABLE);
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
        return Language.logItemCons(this);
    }

    public int getPrice(){ return price;}
}
