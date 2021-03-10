package entity;

public class Movement {
    public static boolean move(AbstractEntity entity){
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
