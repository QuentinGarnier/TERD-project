package Entity;

import World.Position;

public class Player extends AbstractEntity{

    private static final Player instancePlayer = new Player(new Position(0, 0), 100, 10);
    private Player(Position position, int HP, int attack){
        super(position, HP, attack);
    }

    public static Player getInstancePlayer() {
        return instancePlayer;
    }
}
