package graphics.window;

import entity.Merchant;
import entity.Monster;
import entity.Player;
import entity.WhatHeroDoes;
import graphics.elements.Position;
import graphics.elements.cells.Cell;
import graphics.elements.cells.CellElementType;
import graphics.map.WorldMap;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JPanel {
    private JPanel heroLabel = Player.getInstancePlayer();
    private final ImageIcon shadow = new ImageIcon("data/images/map/miscellaneous/shadow.png");
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
        heroLabel = Player.getInstancePlayer();
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

        // MERCHANT
        add(Merchant.getInstanceMerchant());

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
            JLabel jLabel = new JLabel();
            ImageIcon img = null;
            Cell currentCell = worldMap.getCell(x, y);
            CellElementType currentCet = currentCell.getBaseContent();
            CellElementType obstacle = currentCell.getObstacle();
            if(y > 0 && currentCet == CellElementType.HORIZONTAL_WALL) {
                CellElementType previous = worldMap.getCell(x, y - 1).getBaseContent();
                if (previous.isWall()) img = new ImageIcon(CellElementType.CORNER_BOT.getIcon().getImage());
                else if (y < WorldMap.MAX_Y - 1) {
                    CellElementType after = worldMap.getCell(x, y + 1).getBaseContent();
                    img = new ImageIcon((after.isWall() ? CellElementType.CORNER_TOP : currentCet).getIcon().getImage());
                }
            }
            else if(currentCet == CellElementType.CORNER_BOT) {
                if (y < WorldMap.MAX_Y - 1) {
                    CellElementType cet = worldMap.getCell(x, y + 1).getBaseContent();
                    img = new ImageIcon((cet.isWall() ? CellElementType.VERTICAL_WALL : currentCet).getIcon().getImage());
                }
            }
            else if(currentCet == CellElementType.CORNER_TOP) {
                if (y > 0) {
                    CellElementType cet = worldMap.getCell(x, y - 1).getBaseContent();
                    img = new ImageIcon((cet.isWall() ? CellElementType.VERTICAL_WALL : currentCet).getIcon().getImage());
                }
            }
            if (img == null) img = new ImageIcon(currentCet.getIcon().getImage());

            if (x > 0 && y > 0) {
                CellElementType leftC = worldMap.getCell(x - 1, y).getBaseContent();
                CellElementType topLeftC = worldMap.getCell(x - 1, y - 1).getBaseContent();
                if(leftC.isWall() && !currentCet.isWall() && topLeftC.isWall()) {
                    JLabel shad = new JLabel(shadow);
                    shad.setBounds(x * size, y*size, size, size);
                    add(shad);
                }
            }

            if (obstacle != null) {
                JLabel obstacleL = new JLabel(new ImageIcon(obstacle.getIcon().getImage()));
                obstacleL.setBounds(x * size, y * size, size, size);
                add(obstacleL);
            }
            jLabel.setIcon(img);
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
