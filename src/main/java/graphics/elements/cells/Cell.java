package graphics.elements.cells;

import entity.AbstractEntity;
import entity.EntityType;
import graphics.Tools;
import graphics.elements.Position;
import graphics.map.WorldMap;
import graphics.window.GamePanel;
import items.AbstractItem;

import javax.swing.*;
import java.util.Objects;

public class Cell extends JLabel {

    /**
     * In the cell there can be either:
     * 1. MONSTER, OBSTACLE (WALL, TREE or whatever), CELL not inside the lab... -> in all of these cases NOT_ACCESSIBLE
     * 2. ITEM or EMPTY --> ACCESSIBLE
     */

    // room | corridor | outside room
    private final int baseId;
    private final CellElementType baseContent;
    private CellElementType obstacleContent;

    // item
    private AbstractItem item;

    // entity
    private AbstractEntity entity;

    private boolean isAimed;

    private final int size;
    private final Position position;

    private final Cell[][] lab;

    public Cell(CellElementType e, int id, Position position, Cell[][] lab) {
        super();
        this.baseContent = e;
        this.baseId = id;
        this.position = position;
        this.lab = lab;

        // Graphics
        this.size = GamePanel.size;
        setIcon(new ImageIcon("data/images/map/grounds/fog.png"));
        setSize(size, size);
        super.setLocation(position.getX() * size, position.getY() * size);
    }

    // Graphics
    public void removeFog() {
        setLocation(-size, -size);
    }

    public void entityLeft(){
        setEntity(null);
    }

    public void heroPickItem() {
        this.item = null;
    }

    public CellElementType getBaseContent() {
        return baseContent;
    }

    public void setItem(AbstractItem it) {
        this.item = it;
    }

    public void setEntity(AbstractEntity entity) {
        if (entity != null && EntityType.MONSTER_BOSS.equals(entity.entityType)){
            for (int x = position.getX() - 1; x <= position.getX() + 1; x++)
                for (int y = position.getY() - 1; y <= position.getY() + 1; y++)
                    lab[x][y].entity = entity;
        }
        this.entity = entity;
    }

    public void setObstacle(CellElementType obstacleContent) {
        this.obstacleContent = obstacleContent;
    }

    public CellElementType getObstacle() {
        return obstacleContent;
    }

    public int getBaseId() {
        return baseId;
    }

    public int getItemId() {
        return item.getIdPosRoom();
    }

    public AbstractItem getItem() {
        return item;
    }

    public AbstractEntity getEntity(){
        return entity;
    }

    public CellElementType getMainContentType() {
        if (entity != null) return entity.getEntityType().getCellElementType();
        if (item != null) return item.type.cellElementType;
        return baseContent;
    }

    public boolean isWall(){
        return baseContent.isWall();
    }

    public boolean isRoom(Cell[][] lab){
        return baseContent.equals(CellElementType.EMPTY);
    }

    public boolean isDoor(Cell[][] lab){
        int x = position.getX(), y = position.getY();
        if (x + 1 == WorldMap.MAX_X ||
                y + 1 == WorldMap.MAX_Y ||
                x == 0 || y == 0) return false;
        return (lab[x + 1][y].isWall() && lab[x - 1][y].isWall()) ||
                (lab[x][y + 1].isWall() && lab[x][y - 1].isWall());
    }

    public boolean isDoor(){
        WorldMap worldMap = WorldMap.getInstanceWorld();
        int x = position.getX(), y = position.getY();
        return (worldMap.getCell(x + 1, y).isWall() && worldMap.getCell(x - 1, y).isWall()) ||
                (worldMap.getCell(x, y + 1).isWall() && worldMap.getCell(x, y - 1).isWall());
    }

    public boolean isAimed(){
        return isAimed;
    }

    public void setAimed(boolean b){
        isAimed = b;
    }

    public boolean isAccessible() {
        return getMainContentType().isAccessible() && obstacleContent == null;
    }

    @Override
    public String toString() {
        String res = obstacleContent == null ? getMainContentType().toString() : getObstacle().toString();
        return isAimed? Tools.TerminalText.encircled(res) : res;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Cell){
            Cell cell = (Cell) o;
            if (cell.baseId == baseId) {
                return switch (baseContent) {
                    case CORRIDOR -> cell.baseContent == CellElementType.CORRIDOR;
                    case HORIZONTAL_WALL, VERTICAL_WALL, CORNER_BOT, CORNER_TOP, EMPTY -> cell.baseContent == CellElementType.EMPTY;
                    default -> false;
                };
            }
            //return cell.getBaseId() == baseId && cell.getBaseContent().equals(baseContent);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(baseId, baseContent);
    }
}
