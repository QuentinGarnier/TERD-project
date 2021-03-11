package items;

import entity.Player;
import graphics.elements.Position;

public class ItemFood extends AbstractItem {
    private int hungerGain;
    private int hpGain;
    private int[] effects;

    public ItemFood(int id, String name, Position p) {
        super(id, name, ItemType.FOOD, p,false);
        this.hungerGain = 0;
        this.hpGain = 0;
    }

    @Override
    public boolean use() {
        Player.getInstancePlayer().modifyHunger(this.hungerGain);
        Player.getInstancePlayer().modifyHP(this.hpGain);

        if(this.effects.length > 0) {
            //Here, add special effect in the switch case whenever you want to add one (effect's ID in the items.data file)
        }

        return false;
    }

    @Override
    void parseEffectLine() {
        String[] strSplit = this.effectLine.split("@");
        /* Pattern:
         * [0] : int -> amount of hunger recovered
         * [1] : int -> amount of hp recovered
         * [2 et +] : additional effects, refer to the use() list
         */
        this. hungerGain = Integer.parseInt(strSplit[0]);
        this. hpGain = Integer.parseInt(strSplit[0]);
        this.effects = new int[strSplit.length-2];
        for(int i=0; i<this.effects.length; i++) this.effects[i] = Integer.parseInt(strSplit[i+2]);
    }
}
