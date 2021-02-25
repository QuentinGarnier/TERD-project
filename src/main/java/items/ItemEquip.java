package items;

public class ItemEquip extends AbstractItem {
    private boolean isEquipped;

    public ItemEquip(int id, String name) {
        super(id, name, ItemType.EQUIP);
        isEquipped = false;
    }

    public void equip(boolean equip) {
        this.isEquipped = equip;
    }

    public boolean isEquipped() {
        return this.isEquipped;
    }

    @Override
    public boolean use() {
        this.isEquipped = !this.isEquipped;

        /*
        AJOUTER ICI GAINS/PERTE DE STATS (si equip/désequip)
         */

        return true;
    }

    @Override
    void parseEffectLine() {
        /*
        AJOUTER LE PARSER ICI
         */
    }
}
