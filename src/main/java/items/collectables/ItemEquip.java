package items.collectables;

import entity.EntityType;
import entity.Monster;
import entity.Player;
import graphics.Language;
import graphics.Tools;
import graphics.elements.Position;
import graphics.window.GameWindow;
import items.ItemType;

public class ItemEquip extends AbstractCollectableItem {
    private boolean isEquipped;
    private final EquipmentTypes et;
    private final int effect;
    private final int price;

    public ItemEquip(int id, Position p) {
        super(id, ItemType.EQUIP, p, false);
        isEquipped = false;
        et = EquipmentTypes.createRandomEquip();
        int rnd = (int) (Math.random()*6);
        effect = et.getEffectInt() + (Player.getInstancePlayer() == null ? 0 : Player.getInstancePlayer().getLvl()) - 1 + rnd;
        int plus;
        if(et.getMagicEffect() == null) plus = 0;
        else plus = switch(et.getMagicEffect()) {
            case FROZEN -> 150;
            case BURNT, PARALYSED -> 100;
            case POISONED -> 50;
            default -> 0;
        };
        double tmpPrice = (effect * et.getRarity().multiplier * (et.isOffensive? 3 : 1) + plus);
        tmpPrice = tmpPrice * (et.getEntityType().equals(EntityType.HERO_WARRIOR) && et.getRarity().multiplier > 4? 0.9 : 1);
        price = (int)(tmpPrice);
    }

    @Override
    public boolean usePrivate() {
        Player player = Player.getInstancePlayer();
        if(!this.getEquipmentType().entityType.equals(player.entityType)) {
            GameWindow.addToLogs("Wrong speciality.", Tools.WindowText.red);
            return false;
        }
        ItemEquip oldEquip = (getEquipmentType().isOffensive ? player.getAttackItem() : player.getDefenceItem());
        if (oldEquip != null) oldEquip.isEquipped = false;
        boolean res = this.equals(oldEquip);
        if (et.isOffensive) player.setAttackItem(res ? null : this);
        else player.setDefenceItem(res ? null : this);
        isEquipped = !res;
        return true;
    }

    public void applyEffect(Monster monster) {
        monster.updateState(et.getMagicEffect());
    }

    public EquipmentTypes getEquipmentType() {
        return et;
    }

    public boolean isEquipped() {
        return isEquipped;
    }

    public int getEffectInt() {
        return effect;
    }

    @Override
    public String toString() {
        return Language.translate(et);
    }

    @Override
    public String getEffect() {
        return et.getEffect(effect);
    }

    @Override
    public String getDescription() {
        return getEffect();
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public String getSE() {
        return "equip";
    }
}
