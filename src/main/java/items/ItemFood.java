package items;

public class ItemFood extends AbstractItem {
    public ItemFood(int id, String name) {
        super(id, name, ItemType.FOOD);
    }

    @Override
    public boolean use() {
        /*
        AJOUTER ICI REGAINS (hunger, hp... and amount -> dependant du parseEffectLine)
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
