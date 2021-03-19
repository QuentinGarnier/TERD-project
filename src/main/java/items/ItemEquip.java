package items;

import entity.Monster;
import entity.Player;
import graphics.elements.Position;

public class ItemEquip extends AbstractItem {
    private boolean isEquipped;
    private final EquipmentTypes et;

    public ItemEquip(int id, Position p) {
        super(id, ItemType.EQUIP, p, false);
        isEquipped = false;
        et = EquipmentTypes.createRandomEquip();
    }

    @Override
    public boolean usePrivate() {
        Player player = Player.getInstancePlayer();
        ItemEquip oldEquip = player.getAttackItem();
        if (oldEquip != null) oldEquip.isEquipped = false;
        boolean res = this.equals(oldEquip);
        if (et.isOffensive) player.setAttackItem(res ? null : this);
        else player.setDefenceItem(res ? null : this);
        isEquipped = !res;
        return true;
    }

    public void applyEffect(Monster monster) {
        monster.setState(et.getMagicEffect());
    }

    public EquipmentTypes getEquipmentType() {
        return et;
    }

    public boolean isEquipped() {
        return isEquipped;
    }
}
