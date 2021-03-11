package graphics.window;

import entity.Monster;
import entity.Player;
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
    private final List<MonsterLabel> monsterLabels;
    private final List<ItemLabel> treasuresLabels; //Items and coins
    private final List<JLabel> baseLabels;
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
        baseLabels = new ArrayList<>();
    }

    void clear() {
        removeAll();
    }

    void display() {
        displayMap();
    }

    private void displayMap() {
        add(heroLabel);
        for(int x = 0; x < WorldMap.MAX_X; x++) for(int y = 0; y < WorldMap.MAX_Y; y++) {
            Cell cell = WorldMap.getInstanceWorld().getCell(x, y);

            if(cell.getEntity() != null && cell.getEntity().entityType.getCellElementType() == CellElementType.HERO) {
                heroLabel.setBounds(x * size, y * size, size, size);
            } else if (cell.getEntity() != null && cell.getEntity() instanceof Monster) {
                MonsterLabel label = new MonsterLabel((Monster) cell.getEntity());
                monsterLabels.add(label);
            } else if(cell.getItem() != null){
                ItemLabel label = new ItemLabel(cell.getItem());
                treasuresLabels.add(label);
            } else {
                JLabel label = new JLabel(cell.getMainContentType().getIcon());
                label.setBounds(x * size, y * size, size, size);
                baseLabels.add(label);
            }

            //Display background when there is something else on:
            if(cell.getMainContentType() != cell.getBaseContent()) {
                JLabel label2 = new JLabel(cell.getBaseContent().getIcon());
                label2.setBounds(x*size, y*size, size, size);
                baseLabels.add(label2);
            }
        }
        monsterLabels.forEach(this::add);
        treasuresLabels.forEach(this::add);
        baseLabels.forEach(this::add);
    }

    public void moveEntities() {
        worldMap.repaint();
        Player player = Player.getInstancePlayer();
        heroLabel.setLocation(player.getPosition().getX() * size , player.getPosition().getY() * size);
        Cell cell = worldMap.getCell(Player.getInstancePlayer().getPosition());
        worldMap.getRooms().get(cell.getBaseId()).getMonsters().forEach(monster -> {
            JLabel currentLabel =
                    (JLabel) (monsterLabels.stream().filter(x -> x.equals(monster))).toArray()[0];
            Position p = monster.getPosition();
            currentLabel.setLocation(p.getX() * size, p.getY() * size);
        });
        worldMap.getRooms().get(cell.getBaseId()).getItems().forEach(item -> {
            ArrayList<ItemLabel> currentLabel = new ArrayList<>();
            treasuresLabels.forEach(x -> {
                        if (x.getLocation().equals(heroLabel.getLocation())){
                            currentLabel.add(x);
                        }

            });
            if (currentLabel.size() != 0){
                currentLabel.get(0).setLocation(-size, -size);
                treasuresLabels.remove(currentLabel.get(0));
            }
        });
    }

    Point getHeroPosition() {
        return heroLabel.getLocation();
    }
}
