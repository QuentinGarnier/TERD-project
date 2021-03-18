package items;

import java.util.Random;

public enum ConsumableTypes {
    POTION, TELEPORTATION;

    public static ConsumableTypes createConsumableTypes(){
        ConsumableTypes[] consumableTypes = ConsumableTypes.values();
        int rndElt = new Random().nextInt(consumableTypes.length);
        return ConsumableTypes.values()[rndElt];
    }

    public boolean applyEffect(){
        switch (this){
            case POTION:        // TODO
            case TELEPORTATION: // TODO
        }
        return true;
    }

    private void teleport(){

    }
    private void potion(){

    }
}
