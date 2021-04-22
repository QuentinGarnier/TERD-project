package items.collectables;

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
        effect = et.getEffectInt() + Player.getInstancePlayer().getLvl() - 1 + rnd;
        price = et.getPrice() + Player.getInstancePlayer().getLvl() - 1 + rnd;
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
