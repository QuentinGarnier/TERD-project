package items.collectables;

import entity.*;
import graphics.Language;
import graphics.Tools;
import graphics.elements.Room;
import graphics.map.WorldMap;
import graphics.window.GameWindow;

import java.awt.*;
import java.util.Locale;
import java.util.Random;

public enum ConsumableTypes {
    HEALTH_POTION( 15, "consume_potion"),
    REGENERATION_POTION(20, "consume_potion"),
    TELEPORT_SCROLL(40, "teleport"),
    DIVINE_BLESSING(30, "divine"),
    DRAGON_EXPLOSION(55, "explosion"),
    FREEZING_SCROLL(90, "ice");

    public final int price;
    private final String soundEffectName;

    ConsumableTypes(int price, String soundEffect) {
        this.price = price;
        this.soundEffectName = soundEffect;
    }

    public static ConsumableTypes createConsumableTypes() {
        ConsumableTypes[] consumableTypes = ConsumableTypes.values();
        int rndElt = new Random().nextInt(consumableTypes.length);
        return ConsumableTypes.values()[rndElt];
    }

    public void applyEffect() {
        Player pl = Player.getInstancePlayer();
        WorldMap map = WorldMap.getInstanceWorld();
        switch (this) {
            case HEALTH_POTION -> {
                int heal = (int) (Math.round(pl.getHPMax() * 0.10));
                GameWindow.addToLogs("+" + ((heal + pl.getHP() > pl.getHPMax()) ? pl.getHPMax() - pl.getHP() : heal) + " " + Language.hp(), Tools.WindowText.green);
                pl.modifyHP(heal);
            }
            case REGENERATION_POTION -> pl.updateState(EntityState.HEALED);
            case TELEPORT_SCROLL -> {
                if (map.lastLevel()) GameWindow.addToLogs(Language.logTeleportBossRoom(), Color.WHITE);
                else teleport();
            }
            case DIVINE_BLESSING -> pl.updateState(EntityState.INVULNERABLE);
            case DRAGON_EXPLOSION -> {
                Room r = map.getCurrentRoom(pl.getPosition());
                if (r == null) {
                    GameWindow.addToLogs(Language.logDragonExplo1(), Color.WHITE);
                    break;
                }
                if (map.getCell(pl.getPosition()).getBaseId() == Merchant.getInstanceMerchant().getSafeRoomId() && !map.lastLevel()) {
                    GameWindow.addToLogs(Language.logDragonExplo2(), Color.WHITE);
                    break;
                }
                int nbr = 0;
                for (Monster m : map.getCurrentRoom(pl.getPosition()).getMonsters()) {
                    if (m.getHP() != 0) { m.takeDamage((int) (m.getHPMax() * (m.entityType == EntityType.MONSTER_BOSS? 0.05 : 0.25))); nbr++;}
                    if (m.getHP() != 0) m.updateState(EntityState.BURNT);
                    else map.getCell(m.getPosition()).entityLeft();
                }
                GameWindow.window.repaint();
                GameWindow.addToLogs(Language.logDragonExplo3(nbr), Color.WHITE);
            }
            case FREEZING_SCROLL -> {
                Room r = map.getCurrentRoom(pl.getPosition());
                if (r == null) { GameWindow.addToLogs(Language.logFreezingScroll1(), Color.WHITE); break; }
                if (map.getCell(pl.getPosition()).getBaseId() == Merchant.getInstanceMerchant().getSafeRoomId()) { GameWindow.addToLogs(Language.logFreezingScroll2(), Color.WHITE); break;}
                for (Monster m : map.getCurrentRoom(pl.getPosition()).getMonsters()) m.updateState(EntityState.FROZEN);
                GameWindow.window.repaint();
                GameWindow.addToLogs(Language.logFreezingScroll3(), Color.WHITE);
            }
        }
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
            GameWindow.window.setScrollFrameBar();
        }
        else teleport();
    }

    @Override
    public String toString() {
        return this.name().charAt(0) + this.name().substring(1).replace("_", " ").toLowerCase(Locale.ROOT);
    }

    public String getEffect() {
        return Language.descriptionItemCons(this);
    }

    public int getPrice() {
        return price;
    }

    public String getSE() {
        return soundEffectName;
    }
}
