package entity;

public abstract class AbstractEntityStrategy {

    private int attack;
    private int HP, HPmax;
    private int range;

    AbstractEntityStrategy(int att, int hp, int r){
        attack = att;
        HP = hp;
        HPmax = hp;
        range = r;
    }

    public int getAttack() { return attack; }

    public int getHP() { return HP; }

    public int getHPmax() { return HPmax; }

    public int getRange() { return range; }

    public void modifyAttack(int att) {
        attack = att;
        if(attack < 0) attack = 0;
    }

    public void modifyHP(int x) {
        HP += x;
        if(HP > HPmax) HP = HPmax;
        else if(HP < 0) HP = 0;
    }

    public void takeDamage(int damage){ modifyHP(-damage); }

    public void toHeal(int health){ modifyHP(health); }

    public void setHPmax(int x) { HPmax = x; }

    public void fullHeal() { HP = HPmax; }

    public abstract boolean attack(AbstractEntity entity);
}
