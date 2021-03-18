package graphics;

import entity.Player;
import graphics.elements.Move;
import graphics.elements.Position;
import graphics.elements.cells.Cell;
import graphics.map.WorldMap;

public class ChooseAttackCell {
    private static final Player hero = Player.getInstancePlayer();
    private static final WorldMap worldMap = WorldMap.getInstanceWorld();

    public static Position selectCase(Position pos, Move m){
        if (pos == null) return selectCase();
        if (m != null){
            Cell cell = worldMap.getCell(pos);
            cell.setAimed(false);
            Position newPos = Position.sumPos(pos, m);
            if (newPos == null ||
                    hero.getPosition().distance(newPos) > hero.getRange() ||
                    !worldMap.getCell(newPos).getBaseContent().isAccessible()
            ) return pos;
            pos = newPos;
        }
        worldMap.getCell(pos).setAimed(true);
        return pos;
    }

    public static Position selectCase(){
        Cell cell = worldMap.getCell(hero.getPosition());
        cell.setAimed(true);
        return hero.getPosition();
    }

    public static void unselectCase(Position p){
        worldMap.getCell(p).setAimed(false);
    }
}
