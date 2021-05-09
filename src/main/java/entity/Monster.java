package entity;

import graphics.elements.Position;
import graphics.window.GamePanel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class Monster extends AbstractEntity {
    public static final Monster boss = new Monster(new Position(10, 10), 0, EntityType.MONSTER_BOSS);
    public boolean isInAttackMode = false;

    public Monster(Position p, int id, EntityType et)  {
        super(p, id, et);
        Monster m = this;
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                GamePanel.placeAttackLabels(m);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                GamePanel.clearMonsterAttackRange();
            }
        });
    }

    public static Monster generateRandomMonster(Position pos, int id)  {
        EntityType[] monsterTypes = EntityType.monsters();
        int rndElt = new Random().nextInt(monsterTypes.length);
        EntityType monsterType = monsterTypes[rndElt];
        return new Monster(pos, id, monsterType);
    }

    public boolean canAttack(){
        if (isInAttackMode) return true;
        double distance = getPosition().distance(Player.getInstancePlayer().getPosition());
        isInAttackMode = distance <= getEntityType().attackZone;
        return isInAttackMode;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Monster){
            Monster m = (Monster) obj;
            return m.getPosition().equals(getPosition());
        }
        return false;
    }
}
