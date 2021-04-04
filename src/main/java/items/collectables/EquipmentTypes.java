package items.collectables;

import entity.EntityState;
import entity.EntityType;
import graphics.Language;

import java.util.Locale;
import java.util.Random;

public enum EquipmentTypes {
    //Offensive equipments:
    WOODEN_SWORD(10, null, true, EntityType.HERO_WARRIOR, 10),
    IRON_SWORD(20, null, true, EntityType.HERO_WARRIOR, 20),
    MAGIC_SWORD(40, EntityState.BURNT, true, EntityType.HERO_WARRIOR, 50),
    ICY_SWORD(30, EntityState.FROZEN, true, EntityType.HERO_WARRIOR, 35),
    DRAGON_SWORD(100, EntityState.BURNT, true, EntityType.HERO_WARRIOR, 90),

    SHORT_BOW(10, null, true, EntityType.HERO_ARCHER, 10),
    LONG_BOW(40, null, true, EntityType.HERO_ARCHER, 30),
    DRAGON_BOW(100, EntityState.BURNT, true, EntityType.HERO_ARCHER, 110),

    WOODEN_STAFF(10, null, true, EntityType.HERO_MAGE, 10),
    MAGIC_STAFF(40, EntityState.FROZEN, true, EntityType.HERO_MAGE, 35),
    DRAGON_STAFF(100, EntityState.BURNT, true, EntityType.HERO_MAGE, 105),


    //Defensive equipments:
    WOOD_SHIELD(20, null, false, EntityType.HERO_WARRIOR, 20),
    IRON_SHIELD(50, null, false, EntityType.HERO_WARRIOR, 55),
    DRAGON_SHIELD(90, null, false, EntityType.HERO_WARRIOR, 100),

    LEATHER_GAUNTLET(10, null, false, EntityType.HERO_ARCHER, 15),
    LEATHER_ARMBAND(20, null, false, EntityType.HERO_ARCHER, 30),
    LEATHER_CHESTPLATE(50, null, false, EntityType.HERO_ARCHER, 70),

    MAGIC_HAT(10, null, false, EntityType.HERO_MAGE, 10),
    MAGIC_TUNIC(25, null, false, EntityType.HERO_MAGE, 30),
    MAGIC_SPHERE(50, null, false, EntityType.HERO_MAGE, 60);


    private final int effect;
    private final EntityState magicEffect;
    public final boolean isOffensive;
    public final EntityType entityType;
    public final int price;

    EquipmentTypes(int coefficient, EntityState magic_effect, boolean isOffensive, EntityType entityType, int price) {
        this.effect = coefficient; // TODO -> RANDOM EFFECT related to hero Lvl, XP ...
        this.magicEffect = magic_effect;
        this.isOffensive = isOffensive;
        this.entityType = entityType;
        this.price = price;
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

    public EntityType getEntityType() { return entityType; }

    @Override
    public String toString() {
        return this.name().charAt(0) + this.name().substring(1).replace("_", " ").toLowerCase(Locale.ROOT);
    }

    public String getEffect() {
        return "+" + effect + " " + (isOffensive ? Language.attack() : Language.defense()) + (magicEffect == null ? "" : " + " + Language.logEffect(magicEffect));
    }

    public int getPrice() {
        return price;
    }
}
