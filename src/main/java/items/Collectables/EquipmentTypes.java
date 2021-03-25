package items.Collectables;

import entity.EntityState;
import entity.EntityType;

import java.util.Locale;
import java.util.Random;

public enum EquipmentTypes {
    //Offensive equipments:
    WOODEN_SWORD(10, null, true, EntityType.HERO_WARRIOR),
    IRON_SWORD(20, null, true, EntityType.HERO_WARRIOR),
    MAGIC_SWORD(40, EntityState.BURNT, true, EntityType.HERO_WARRIOR),
    ICY_SWORD(30, EntityState.FROZEN, true, EntityType.HERO_WARRIOR),
    DRAGON_SWORD(100, EntityState.BURNT, true, EntityType.HERO_WARRIOR),

    SHORT_BOW(10, null, true, EntityType.HERO_ARCHER),
    LONG_BOW(40, null, true, EntityType.HERO_ARCHER),
    DRAGON_BOW(100, EntityState.BURNT, true, EntityType.HERO_ARCHER),

    WOODEN_STAFF(10, null, true, EntityType.HERO_MAGE),
    MAGIC_STAFF(40, EntityState.FROZEN, true, EntityType.HERO_MAGE),
    DRAGON_STAFF(100, EntityState.BURNT, true, EntityType.HERO_MAGE),


    //Defensive equipments:
    WOOD_SHIELD(10, null, false, EntityType.HERO_WARRIOR),
    IRON_SHIELD(20, null, false, EntityType.HERO_WARRIOR),
    DRAGON_SHIELD(80, null, false, EntityType.HERO_WARRIOR),

    LEATHER_ARMOR(20, null, false, EntityType.HERO_ARCHER),

    MAGIC_TUNIC(20, null, false, EntityType.HERO_MAGE);


    private final int effect;
    private final EntityState magicEffect;
    public final boolean isOffensive;
    public final EntityType entityType;

    EquipmentTypes(int coefficient, EntityState magic_effect, boolean isOffensive, EntityType entityType) {
        this.effect = coefficient; // TODO -> RANDOM EFFECT related to hero Lvl, XP ...
        this.magicEffect = magic_effect;
        this.isOffensive = isOffensive;
        this.entityType = entityType;
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
