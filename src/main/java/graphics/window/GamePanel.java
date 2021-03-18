package graphics.window;

import entity.Monster;
import entity.Player;
import entity.WhatHeroDoes;
import graphics.elements.Position;
import graphics.elements.cells.Cell;
import graphics.map.WorldMap;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JPanel {
    private final JLabel heroLabel = new JLabel(Player.getInstancePlayer().getEntityType().getCellElementType().getIcon());
    private final ImageIcon red = new ImageIcon("data/images/map/miscellaneous/square_red.png");
    private final ImageIcon green = new ImageIcon("data/images/map/miscellaneous/square_green.png");
    private final ImageIcon black = new ImageIcon("data/images/map/miscellaneous/square_black.png");
    private final JLabel squareLabel = new JLabel(green);
    private final List<MonsterLabel> monsterLabels;
    private final List<ItemLabel> treasuresLabels; //Items and coins
    private final List<JLabel> baseLabels;
    private final List<JLabel> opaqueLabels;
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
        opaqueLabels = new ArrayList<>();
    }

    void clear() {
        removeAll();
    }

    void display() {
        displayMap();
    }

    private void displayMap() {
        Position pos = Player.getInstancePlayer().getPosition();
        add(heroLabel);
        squareLabel.setBounds(-size,-size, size, size);
        heroLabel.setBounds(pos.getX() * size, pos.getY() * size, size, size);
        makeOpaqueLabel();
        for(int x = 0; x < WorldMap.MAX_X; x++) for(int y = 0; y < WorldMap.MAX_Y; y++) {
            Cell cell = WorldMap.getInstanceWorld().getCell(x, y);
            if (cell.getEntity() instanceof Monster)
                monsterLabels.add(new MonsterLabel((Monster) cell.getEntity()));
            if(cell.getItem() != null)
                treasuresLabels.add(new ItemLabel(cell.getItem()));
            JLabel label = new JLabel(cell.getBaseContent().getIcon());
            label.setBounds(x * size, y * size, size, size);
            baseLabels.add(label);
        }
        monsterLabels.forEach(this::add);
        treasuresLabels.forEach(this::add);
        add(squareLabel);
        baseLabels.forEach(this::add);
    }

    private void makeOpaqueLabel(){
        for (int i = 0; i < Math.pow(Player.getInstancePlayer().getRange() * 2, 2); i++) {
            JLabel l = new JLabel();
            l.setOpaque(true);
            l.setBounds(-size, -size, size, size);
            l.setBackground(new Color(0, 0, 0, 120));
            opaqueLabels.add(l);
            add(l);
        }
    }

    public void moveEntities() {
        repaint();
        squareLabel.setLocation(-size, -size);
        WorldMap.getInstanceWorld().repaint();
        Player player = Player.getInstancePlayer();
        heroLabel.setLocation(player.getPosition().getX() * size , player.getPosition().getY() * size);
        Cell cell = worldMap.getCell(Player.getInstancePlayer().getPosition());
        worldMap.getRooms().get(cell.getBaseId()).getMonsters().forEach(monster -> {
            JLabel currentLabel =
                    (JLabel) (monsterLabels.stream().filter(x -> x.equals(monster))).toArray()[0];
            Position p = monster.getPosition();
            //System.out.println(monster.getHP());
            if (monster.getHP() > 0)currentLabel.setLocation(p.getX() * size, p.getY() * size);
            else {
                currentLabel.setLocation(-size, -size);
                monsterLabels.remove(monster);
            }
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

    public void setObjective(){
        Player player = Player.getInstancePlayer();
        WhatHeroDoes whatHeroDoes = player.getWhatHeroDoes();
        if (whatHeroDoes == WhatHeroDoes.CHOOSING_ATTACK) {
            ArrayList<Position> opaquePos = Position.calcRangePosition();
            for (int i = 0; i < opaquePos.size(); i++){
                Position current = opaquePos.get(i);
                opaqueLabels.get(i).setLocation(current.getX() * size, current.getY() * size);
                System.out.println("CIAO");
            }
            squareLabel.setIcon(worldMap.getCell(whatHeroDoes.getP()).getEntity() instanceof Monster ? green : red);
            squareLabel.setLocation(whatHeroDoes.getP().getX() * size, whatHeroDoes.getP().getY() * size);
        }
        else {
            opaqueLabels.forEach(x -> x.setLocation(-size, -size));
            squareLabel.setLocation(-size, -size);
        }
    }

    Point getHeroPosition() {
        return heroLabel.getLocation();
    }
}
