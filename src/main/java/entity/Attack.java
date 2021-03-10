package entity;

public class Attack {
    public static boolean attack(AbstractEntity entity){
        switch (entity.entityType){

            case HERO_ARCHER:
            case HERO_WARRIOR:
            case HERO_WITCHER:

            case MONSTER_GOBLIN:
            case MONSTER_ORC:
            case MONSTER_SPIDER:
            case MONSTER_WIZARD:

            default: return false;
        }
    }
}
