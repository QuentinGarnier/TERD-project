package items;

import graphics.elements.Position;

public class ItemTrap extends AbstractItem{

    private final String effect;

    ItemTrap(int i, String n, Position position, String effect) {
        super(i, n, ItemType.TRAP, position, true);
        this.effect = effect;
    }

    @Override
    public boolean use() {
        switch (effect){
            case "Burn": //something like worldmap.getcell(position).getEntity.setState(EntityState.BURNT) si on considère monstre peuvent subir piège
            case "Freeze": // idem with frozen
            case "Poison": // idem with poisonned
            case "Teleport": //
            case "Explode": //entity.takedamage(15)
            default: return false;
        }
    }

    @Override
    void parseEffectLine() {

    }
}
