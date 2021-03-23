package graphics.window;

import entity.Monster;
import entity.Player;
import entity.WhatHeroDoes;
import graphics.elements.Position;
import graphics.map.WorldMap;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JPanel {
    private final JPanel heroLabel = Player.getInstancePlayer();
    private final ImageIcon red = new ImageIcon("data/images/map/miscellaneous/square_red.png");
    private final ImageIcon green = new ImageIcon("data/images/map/miscellaneous/square_green.png");
    private final JLabel squareLabel = new JLabel(green);
    private final List<JLabel> opaqueLabels;
    private final WorldMap worldMap = WorldMap.getInstanceWorld();
    public static final int size = 32;

    GamePanel() {
        super();
        setLayout(null);
        setFocusable(true);
        setBackground(Color.DARK_GRAY);
        setPreferredSize(new Dimension(WorldMap.MAX_X * size,WorldMap.MAX_Y * size));
        opaqueLabels = new ArrayList<>();
    }

    void display() {
        displayMap();
    }

    private void displayMap() {
        removeAll();
        repaint();
        revalidate();

        // HERO
        Position pos = Player.getInstancePlayer().getPosition();
        add(heroLabel);
        heroLabel.setBounds(pos.getX() * size, pos.getY() * size, size, size);

        // FOG
        for(int x = 0; x < WorldMap.MAX_X; x++)
            for(int y = 0; y < WorldMap.MAX_Y; y++) add(worldMap.getCell(x, y));

        // OBJECTIVE SHADOW
        makeOpaqueLabel();

        // MONSTERS
        worldMap.getRooms().forEach(room -> room.getMonsters().forEach(this::add));

        // ITEMS
        worldMap.getItems().forEach(this::add);

        // AIMS
        add(squareLabel);
        squareLabel.setBounds(-size,-size, size, size);

        // BASE CONTENT
        for(int x = 0; x < WorldMap.MAX_X; x++) for(int y = 0; y < WorldMap.MAX_Y; y++) {
            JLabel jLabel = new JLabel(new ImageIcon(worldMap.getCell(x, y).getBaseContent().getIcon().getImage()));
            jLabel.setBounds(x * size, y * size, size, size);
            add(jLabel);
        }
    }

    private void makeOpaqueLabel() {
        opaqueLabels.clear();
        for (int i = 0; i < Math.pow((Player.getInstancePlayer().getRange() + 1) * 2, 2); i++) {
            JLabel l = new JLabel();
            l.setOpaque(true);
            l.setBounds(-size, -size, size, size);
            l.setBackground(new Color(0, 0, 0, 120));
            opaqueLabels.add(l);
            add(l);
        }
    }

    public void setObjective() {
        Player player = Player.getInstancePlayer();
        WhatHeroDoes whatHeroDoes = player.getWhatHeroDoes();
        if (whatHeroDoes == WhatHeroDoes.CHOOSING_ATTACK) {
            ArrayList<Position> opaquePos = player.getPosition().calcRangePosition(player.getRange(), true);
            for (int i = 0; i < opaquePos.size(); i++){
                Position current = opaquePos.get(i);
                opaqueLabels.get(i).setLocation(current.getX() * size, current.getY() * size);
            }
            squareLabel.setIcon(worldMap.getCell(whatHeroDoes.getP()).getEntity() instanceof Monster ? green : red);
            squareLabel.setLocation(whatHeroDoes.getP().getX() * size, whatHeroDoes.getP().getY() * size);
        } else {
            opaqueLabels.forEach(x -> x.setLocation(-size, -size));
            squareLabel.setLocation(-size, -size);
        }
    }

    Point getHeroPosition() {
        return heroLabel.getLocation();
    }
}
