package items;

import java.util.Locale;
import java.util.Random;

public enum ConsumableTypes {
    HEALTH_POTION("Restores part of your HP."),
    TELEPORTATION_SCROLL("Teleports you in the last Merchant Room visited.");

    public final String effect;  //Useful for description in the inventory.

    ConsumableTypes(String effect) {
        this.effect = effect;
    }

    public static ConsumableTypes createConsumableTypes() {
        ConsumableTypes[] consumableTypes = ConsumableTypes.values();
        int rndElt = new Random().nextInt(consumableTypes.length);
        return ConsumableTypes.values()[rndElt];
    }

    public boolean applyEffect() {
        switch (this) {
            case HEALTH_POTION:        // TODO
            case TELEPORTATION_SCROLL: // TODO
        }
        return true;
    }

    private void teleport(){

    }
    private void potion(){

    }

    @Override
    public String toString() {
        return this.name().charAt(0) + this.name().substring(1).replace("_", " ").toLowerCase(Locale.ROOT);
    }

    public String getEffect(){
        return effect;
    }
}
