package items;

public class ItemConsumable extends AbstractItem {
    public ItemConsumable(int id, String name) {
        super(id, name, ItemType.CONSUMABLE);
    }

    @Override
    public boolean use() {
        /*
        AJOUTER ICI EFFET (dépendant du parseEffectLine)
         */

        return false;
    }

    @Override
    void parseEffectLine() {
        /*
        AJOUTER LE PARSER ICI
         */
    }
}
