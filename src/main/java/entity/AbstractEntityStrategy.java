package entity;

public abstract class AbstractEntityStrategy {
    private int attack;
    private int HP, HPmax;


    AbstractEntityStrategy(int att, int hp){
        attack = att;
        HP = hp;
        HPmax = hp;
    }

    public int getAttack() { return attack; }

    public int getHP() { return HP; }

    public int getHPmax() { return HPmax; }

    public void modifyAttack(int att) {
        attack = att;
        if(attack < 0) attack = 0;
    }

    public void modifyHP(int x) {
        if(HP + x > HPmax) HP = HPmax;
        else if(HP + x < 0) HP = 0;
        else HP += x;
    }

    public void takeDamage(int damage){ modifyHP(-damage); }

    public void toHeal(int health){ modifyHP(health); }

    public void setHPmax(int x) {
        HPmax = x;
    }

    public void fullHeal() { HP = HPmax; }

    public abstract boolean attack(AbstractEntity entity);
}
