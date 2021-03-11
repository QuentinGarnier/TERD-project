package items;

import entity.Player;
import graphics.elements.Position;

public class ItemConsumable extends AbstractItem {
    private int effectID;
    private int[] amounts;

    public ItemConsumable(int id, String name, Position p) {
        super(id, name, ItemType.CONSUMABLE, p, false);
    }

    @Override
    public boolean use() {
        switch (effectID) {
            case 1:
                //[001] Regain HP by amounts[0]
                Player.getInstancePlayer().modifyHP(amounts[0]);
                break;

            default: break;
        }

        return false;
    }

    @Override
    void parseEffectLine() {
        String[] strSplit = this.effectLine.split("@");
        /* Pattern:
         * [0] : int -> ID of the effect (see switch-case in the use() function)
         * [1 et +] : int -> amounts for the effect
         */
        this.effectID = Integer.parseInt(strSplit[0]);
        this.amounts = new int[strSplit.length-1];
        for(int i=0; i<this.amounts.length; i++) this.amounts[i] = Integer.parseInt(strSplit[i+1]);
    }
}
