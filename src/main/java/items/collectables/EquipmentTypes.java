package items.collectables;

import entity.EntityState;
import entity.EntityType;
import graphics.Language;

import java.util.Arrays;
import java.util.Locale;
import java.util.Random;

public enum EquipmentTypes {
    //Offensive equipments:
    WOODEN_SWORD(10, null, true, EntityType.HERO_WARRIOR, EquipmentRarity.COMMON),
    IRON_SWORD(20, null, true, EntityType.HERO_WARRIOR, EquipmentRarity.RARE),
    MAGIC_SWORD(40, EntityState.BURNT, true, EntityType.HERO_WARRIOR, EquipmentRarity.EPIC),
    ICY_SWORD(30, EntityState.FROZEN, true, EntityType.HERO_WARRIOR, EquipmentRarity.EPIC),
    DRAGON_SWORD(100, EntityState.BURNT, true, EntityType.HERO_WARRIOR, EquipmentRarity.LEGENDARY),

    SHORT_BOW(10, null, true, EntityType.HERO_ARCHER, EquipmentRarity.COMMON),
    LONG_BOW(20, null, true, EntityType.HERO_ARCHER, EquipmentRarity.EPIC),
    DRAGON_BOW(80, EntityState.BURNT, true, EntityType.HERO_ARCHER, EquipmentRarity.LEGENDARY),

    WOODEN_STAFF(10, null, true, EntityType.HERO_MAGE, EquipmentRarity.COMMON),
    MAGIC_STAFF(40, EntityState.FROZEN, true, EntityType.HERO_MAGE, EquipmentRarity.RARE),
    DRAGON_STAFF(90, EntityState.BURNT, true, EntityType.HERO_MAGE, EquipmentRarity.LEGENDARY),


    //Defensive equipments:
    WOOD_SHIELD(6, null, false, EntityType.HERO_WARRIOR, EquipmentRarity.COMMON),
    IRON_SHIELD(9, null, false, EntityType.HERO_WARRIOR, EquipmentRarity.RARE),
    DRAGON_SHIELD(20, null, false, EntityType.HERO_WARRIOR, EquipmentRarity.LEGENDARY),

    LEATHER_GAUNTLET(5, null, false, EntityType.HERO_ARCHER, EquipmentRarity.COMMON),
    LEATHER_ARMBAND(7, null, false, EntityType.HERO_ARCHER, EquipmentRarity.RARE),
    LEATHER_CHESTPLATE(10, null, false, EntityType.HERO_ARCHER, EquipmentRarity.EPIC),

    MAGIC_HAT(5, null, false, EntityType.HERO_MAGE, EquipmentRarity.COMMON),
    MAGIC_TUNIC(8, null, false, EntityType.HERO_MAGE, EquipmentRarity.RARE),
    MAGIC_SPHERE(12, null, false, EntityType.HERO_MAGE, EquipmentRarity.EPIC);


    private final int effect;
    private final EntityState magicEffect;
    public final boolean isOffensive;
    public final EntityType entityType;
    private final EquipmentRarity rarity;

    EquipmentTypes(int coefficient, EntityState magic_effect, boolean isOffensive, EntityType entityType, EquipmentRarity rarity) {
        this.effect = coefficient;
        this.magicEffect = magic_effect;
        this.isOffensive = isOffensive;
        this.entityType = entityType;
        this.rarity = rarity;
    }

    public static EquipmentTypes createRandomEquip() {
        double rn = Math.random();
        EquipmentRarity raritySelected = rn <= 0.50 ? EquipmentRarity.COMMON : rn <= 0.80 ? EquipmentRarity.RARE : rn <= 0.95 ? EquipmentRarity.EPIC : EquipmentRarity.LEGENDARY;
        EquipmentTypes[] equipmentTypesByRarity = Arrays.stream(EquipmentTypes.values()).filter(elt -> elt.rarity == raritySelected).toArray(EquipmentTypes[]::new);
        int rndElt = new Random().nextInt(equipmentTypesByRarity.length);
        return equipmentTypesByRarity[rndElt];
    }

    public int getEffectInt() {
        return effect;
    }

    public EntityState getMagicEffect() {
        return magicEffect;
    }

    public EntityType getEntityType() { return entityType; }

    public EquipmentRarity getRarity() {
        return rarity;
    }

    @Override
    public String toString() {
        return this.name().charAt(0) + this.name().substring(1).replace("_", " ").toLowerCase(Locale.ROOT);
    }

    public String getEffect(int val) {
        return "+" + val + " " + (isOffensive ? Language.attack() : Language.defense()) + (magicEffect == null ? "" : " + " + Language.logEffect(magicEffect));
    }

    public enum EquipmentRarity {
        COMMON, RARE, EPIC, LEGENDARY
    }
}
