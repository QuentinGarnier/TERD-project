package graphics.window;

import entity.Player;
import graphics.elements.Move;
import graphics.elements.Position;
import graphics.elements.cells.Cell;
import graphics.elements.cells.CellElementType;
import graphics.map.WorldMap;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JPanel {
    private final JLabel heroLabel = new JLabel(CellElementType.HERO.getIcon());
    private final List<JLabel> monsterLabels;
    private final List<JLabel> treasuresLabels; //Items and coins
    private final WorldMap worldMap = WorldMap.getInstanceWorld();
    public static final int size = 32;

    GamePanel() {
        super();
        setLayout(null);
        setFocusable(true);
        setBackground(Color.DARK_GRAY);
        setPreferredSize(new Dimension(WorldMap.MAX_X * size,WorldMap.MAX_Y * size));
        monsterLabels = new ArrayList<>();
        treasuresLabels = new ArrayList<>();
    }

    void clear() {
        removeAll();
    }

    void display() {
        displayMap();
    }

    private void displayMap() {
        add(heroLabel);
        worldMap.getRooms().forEach(room -> {
            room.getMonsters().forEach(monster -> {
                JLabel label = new JLabel(monster.getEntityType().getCellElementType().getIcon());
                Position p = monster.getPosition();
                label.setBounds(p.getX() * size, p.getY() * size, size, size);
                monsterLabels.add(label);
                add(label);
            });
        });

        for(int x = 0; x < WorldMap.MAX_X; x++) for(int y = 0; y < WorldMap.MAX_Y; y++) {
            Cell cell = WorldMap.getInstanceWorld().getCell(x, y);

            if(cell.getEntityContent() == CellElementType.HERO) heroLabel.setBounds(x * size, y * size, size, size);
            else if(cell.getMainContentType() != cell.getEntityContent()) {
                JLabel label = new JLabel(cell.getMainContentType().getIcon());
                label.setBounds(x * size, y * size, size, size);
                if(cell.getItemContent() != null) treasuresLabels.add(label);
                add(label);
            }

            //Display background when there is something else on:
            if(cell.getMainContentType() != cell.getBaseContent()) {
                JLabel label2 = new JLabel(cell.getBaseContent().getIcon());
                label2.setBounds(x*size, y*size, size, size);
                add(label2);
            }
        }
    }

    /**
     * Should be in WorldMap class.
     * @param move direction for the movement.
     */
    void moveHero(Move move) {
        Position pos = move.getMove();
        //Should be a "canMoveToward(Move move)" function in Player.java to have a better code:
        Player player = Player.getInstancePlayer();
        Cell cell = worldMap.getCell(player.getPosition());
        if(Player.getInstancePlayer().canMove()) {
            if(Player.getInstancePlayer().moveEntity(move)) {
                heroLabel.setLocation(heroLabel.getX() + pos.getX() * size , heroLabel.getY() + pos.getY() * size);
                if(cell.getBaseContent().equals(CellElementType.EMPTY)) {
                    worldMap.getRooms().get(cell.getBaseId()).getMonsters().forEach(e -> {
                        JLabel currentLabel =
                                (JLabel) (monsterLabels.stream().filter(x ->
                                        x.getBounds().getLocation().equals(new Point(e.getPosition().getX() * size, e.getPosition().getY() * size)))).toArray()[0];
                        e.applyStrategy();
                        currentLabel.setBounds(e.getPosition().getX() * size, e.getPosition().getY() * size, size, size);
                        System.out.println(e.getPosition());
                    });
                }
            }
        }
    }

    Point getHeroPosition() {
        return heroLabel.getLocation();
    }
}
