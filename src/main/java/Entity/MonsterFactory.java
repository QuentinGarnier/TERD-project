package entity;

public class MonsterFactory {
    private static final MonsterFactory instanceFactory = new MonsterFactory();
    private MonsterFactory(){}

    public static MonsterFactory getInstanceFactory() {
        return instanceFactory;
    }

    //public Entity create(){}//TODO
}
