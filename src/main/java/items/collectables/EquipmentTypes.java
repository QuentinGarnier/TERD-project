package items.collectables;

import entity.EntityState;
import entity.EntityType;
import graphics.Language;
import graphics.map.WorldMap;
import graphics.window.GameWindow;

import java.util.Arrays;
import java.util.Locale;
import java.util.Random;

public enum EquipmentTypes {
    //Offensive equipments:
    WOODEN_SWORD(10, null, true, EntityType.HERO_WARRIOR, EquipmentRarity.COMMON),
    IRON_SWORD(20, null, true, EntityType.HERO_WARRIOR, EquipmentRarity.RARE),
    MAGIC_SWORD(40, EntityState.BURNT, true, EntityType.HERO_WARRIOR, EquipmentRarity.EPIC),
    ICY_SWORD(30, EntityState.FROZEN, true, EntityType.HERO_WARRIOR, EquipmentRarity.EPIC),
    DRAGON_SWORD(60, EntityState.BURNT, true, EntityType.HERO_WARRIOR, EquipmentRarity.LEGENDARY),

    SHORT_BOW(10, null, true, EntityType.HERO_ARCHER, EquipmentRarity.COMMON),
    LONG_BOW(20, null, true, EntityType.HERO_ARCHER, EquipmentRarity.RARE),
    CROSSBOW(35, null, true, EntityType.HERO_ARCHER, EquipmentRarity.EPIC),
    MAGIC_BOW(25, EntityState.FROZEN, true, EntityType.HERO_ARCHER, EquipmentRarity.EPIC),
    DRAGON_BOW(50, EntityState.BURNT, true, EntityType.HERO_ARCHER, EquipmentRarity.LEGENDARY),

    WOODEN_STAFF(10, null, true, EntityType.HERO_MAGE, EquipmentRarity.COMMON),
    IRON_STAFF(20, null, true, EntityType.HERO_MAGE, EquipmentRarity.RARE),
    GOLDEN_STAFF(30, null, true, EntityType.HERO_MAGE, EquipmentRarity.EPIC),
    MAGIC_STAFF(20, EntityState.FROZEN, true, EntityType.HERO_MAGE, EquipmentRarity.EPIC),
    DRAGON_STAFF(45, null, true, EntityType.HERO_MAGE, EquipmentRarity.LEGENDARY),


    //Defensive equipments:
    WOOD_SHIELD(5, null, false, EntityType.HERO_WARRIOR, EquipmentRarity.COMMON),
    IRON_SHIELD(9, null, false, EntityType.HERO_WARRIOR, EquipmentRarity.RARE),
    DIAMOND_SHIELD(14, null, false, EntityType.HERO_WARRIOR, EquipmentRarity.EPIC),
    DRAGON_SHIELD(20, null, false, EntityType.HERO_WARRIOR, EquipmentRarity.LEGENDARY),

    LEATHER_GAUNTLET(5, null, false, EntityType.HERO_ARCHER, EquipmentRarity.COMMON),
    LEATHER_ARMBAND(7, null, false, EntityType.HERO_ARCHER, EquipmentRarity.RARE),
    LEATHER_HELMET(10, null, false, EntityType.HERO_ARCHER, EquipmentRarity.EPIC),
    LEATHER_CHESTPLATE(14, null, false, EntityType.HERO_ARCHER, EquipmentRarity.LEGENDARY),

    MAGIC_HAT(5, null, false, EntityType.HERO_MAGE, EquipmentRarity.COMMON),
    MAGIC_TUNIC(8, null, false, EntityType.HERO_MAGE, EquipmentRarity.RARE),
    MAGIC_GRIMOIRE(11, null, false, EntityType.HERO_MAGE, EquipmentRarity.EPIC),
    MAGIC_SPHERE(15, null, false, EntityType.HERO_MAGE, EquipmentRarity.LEGENDARY);


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

    public static EquipmentRarity calcRar(double a, boolean cond1, boolean cond2){
        if (cond1) return a <= 0.50 ?
                EquipmentRarity.COMMON : a <= 0.80 ?
                EquipmentRarity.RARE : a <= 0.95 ?
                EquipmentRarity.EPIC : EquipmentRarity.LEGENDARY;
        else if (cond2) return a <= 0.50 ?
                EquipmentRarity.COMMON : a <= 0.80 ?
                EquipmentRarity.RARE : EquipmentRarity.EPIC;
        else return a <= 0.50 ?
                    EquipmentRarity.COMMON : EquipmentRarity.RARE;
    }

    public static EquipmentTypes createRandomEquip() {
        double rn = Math.random();
        EquipmentRarity raritySelected;
        if (GameWindow.difficulty() == GameWindow.Difficulty.ENDLESS){
            int stageNum = WorldMap.stageNum;
            raritySelected = calcRar(rn, stageNum > 20, stageNum > 40);
        } else {
            double div = WorldMap.stageNum / (float) GameWindow.difficulty().stagesNumber;
            raritySelected = calcRar(rn, div > 3 / 4.0, div > 2 / 4.0);
        }
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
        COMMON(2), RARE(4), EPIC(8), LEGENDARY(16);

        public final int multiplier;

        EquipmentRarity(int multi) {
            multiplier = multi;
        }
    }
}
