package items;

import entity.EntityState;

import java.util.Locale;
import java.util.Random;

public enum EquipmentTypes {
    WOOD_SWORD(10, null, true), IRON_SWORD(20, null, true),
    WOOD_SHIELD(10, null, false), IRON_SHIELD(20, null, false),
    MAGIC_SWORD(10, EntityState.BURNT, true);

    private final int effect;
    private final EntityState magicEffect;
    public final boolean isOffensive;
    EquipmentTypes(int coefficient, EntityState magic_effect, boolean isOffensive){
        this.effect = coefficient; // TODO -> RANDOM EFFECT related to hero Lvl, XP ...
        this.magicEffect = magic_effect;
        this.isOffensive = isOffensive;
    }

    public static EquipmentTypes createRandomEquip() {
        EquipmentTypes[] equipmentTypes = EquipmentTypes.values();
        int rndElt = new Random().nextInt(equipmentTypes.length);
        return EquipmentTypes.values()[rndElt];
    }

    public int getEffect() {
        return effect;
    }

    public EntityState getMagicEffect() {
        return magicEffect;
    }

    @Override
    public String toString() {
        // TODO
        String res = this.name().replace("_", " ").toLowerCase(Locale.ROOT);
        return res;
    }
}
