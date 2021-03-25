package items.Collectables;

import entity.Monster;
import entity.Player;
import graphics.Tools;
import graphics.elements.Position;
import graphics.window.GameWindow;
import items.ItemType;

import javax.tools.Tool;

public class ItemEquip extends AbstractCollectableItems {
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

    @Override
    public String toString() {
        return et.toString();
    }

    @Override
    public String getEffect() {
        return et.getEffect();
    }
}
