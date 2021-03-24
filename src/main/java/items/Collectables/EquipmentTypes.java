package items.Collectables;

import entity.EntityState;

import java.util.Locale;
import java.util.Random;

public enum EquipmentTypes {
    //Offensive equipments:
    WOODEN_SWORD(10, null, true),
    IRON_SWORD(20, null, true),
    MAGIC_SWORD(40, EntityState.BURNT, true),
    ICY_SWORD(30, EntityState.FROZEN, true),
    DRAGON_SWORD(100, EntityState.BURNT, true),

    //Defensive equipments:
    WOOD_SHIELD(10, null, false),
    IRON_SHIELD(20, null, false),
    DRAGON_SHIELD(80, null, false);

    private final int effect;
    private final EntityState magicEffect;
    public final boolean isOffensive;
    EquipmentTypes(int coefficient, EntityState magic_effect, boolean isOffensive) {
        this.effect = coefficient; // TODO -> RANDOM EFFECT related to hero Lvl, XP ...
        this.magicEffect = magic_effect;
        this.isOffensive = isOffensive;
    }

    public static EquipmentTypes createRandomEquip() {
        EquipmentTypes[] equipmentTypes = EquipmentTypes.values();
        int rndElt = new Random().nextInt(equipmentTypes.length);
        return EquipmentTypes.values()[rndElt];
    }

    public int getEffectInt() {
        return effect;
    }

    public EntityState getMagicEffect() {
        return magicEffect;
    }

    @Override
    public String toString() {
        return this.name().charAt(0) + this.name().substring(1).replace("_", " ").toLowerCase(Locale.ROOT);
    }

    public String getEffect() {
        return "+ " + effect + " " + (isOffensive ? "attack" : "defense") + (magicEffect == null ? "" : " + " + magicEffect.toString() + " effect");
    }
}
