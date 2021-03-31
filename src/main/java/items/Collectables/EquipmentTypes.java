package items.collectables;

import entity.EntityState;
import entity.EntityType;
import graphics.Language;

import java.util.Locale;
import java.util.Random;

public enum EquipmentTypes {
    //Offensive equipments:
    WOODEN_SWORD(10, null, true, EntityType.HERO_WARRIOR, 5),
    IRON_SWORD(20, null, true, EntityType.HERO_WARRIOR, 10),
    MAGIC_SWORD(40, EntityState.BURNT, true, EntityType.HERO_WARRIOR, 30),
    ICY_SWORD(30, EntityState.FROZEN, true, EntityType.HERO_WARRIOR, 25),
    DRAGON_SWORD(100, EntityState.BURNT, true, EntityType.HERO_WARRIOR, 80),

    SHORT_BOW(10, null, true, EntityType.HERO_ARCHER, 5),
    LONG_BOW(40, null, true, EntityType.HERO_ARCHER, 30),
    DRAGON_BOW(100, EntityState.BURNT, true, EntityType.HERO_ARCHER, 80),

    WOODEN_STAFF(10, null, true, EntityType.HERO_MAGE, 5),
    MAGIC_STAFF(40, EntityState.FROZEN, true, EntityType.HERO_MAGE, 30),
    DRAGON_STAFF(100, EntityState.BURNT, true, EntityType.HERO_MAGE, 80),


    //Defensive equipments:
    WOOD_SHIELD(10, null, false, EntityType.HERO_WARRIOR, 5),
    IRON_SHIELD(20, null, false, EntityType.HERO_WARRIOR, 10),
    DRAGON_SHIELD(80, null, false, EntityType.HERO_WARRIOR, 50),

    LEATHER_ARMOR(20, null, false, EntityType.HERO_ARCHER, 10),

    MAGIC_TUNIC(20, null, false, EntityType.HERO_MAGE, 10);


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
