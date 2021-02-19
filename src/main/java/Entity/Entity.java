package Entity;

import World.Position;

public interface Entity {
    Position getPos();
    int getHP();
    int getAttack();
}
