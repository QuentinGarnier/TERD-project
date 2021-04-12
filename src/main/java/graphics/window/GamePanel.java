package graphics.window;

import entity.*;
import graphics.elements.Position;
import graphics.elements.cells.Cell;
import graphics.elements.cells.CellElementType;
import graphics.map.Theme;
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
    private final ImageIcon green2 = new ImageIcon("data/images/map/miscellaneous/slide_square_green.png");//added by Antoine
    private final JLabel squareLabel = new JLabel(green);
    private final JLabel[][] squareLabels;//added by Antoine
    private final List<JLabel> opaqueLabels;
    private final WorldMap worldMap = WorldMap.getInstanceWorld();
    public static final int size = 32;

    GamePanel() {
        super();
        setLayout(null);
        setFocusable(true);
        setBackground(Color.DARK_GRAY);
        setPreferredSize(new Dimension(WorldMap.MAX_X * size,WorldMap.MAX_Y * size));
        squareLabels = new JLabel[3][3];//added by Antoine
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
        if(!worldMap.lastLevel()) add(Merchant.getInstanceMerchant());

        // FOG

        if (!worldMap.lastLevel())
        for(int x = 0; x < WorldMap.MAX_X; x++)
            for(int y = 0; y < WorldMap.MAX_Y; y++)
                add(worldMap.getCell(x, y));

        // OBJECTIVE SHADOW
        makeOpaqueLabel();

        // MONSTERS
        worldMap.getRooms().forEach(room -> room.getMonsters().forEach(this::add));

        // ITEMS
        worldMap.getItems().forEach(this::add);
        Player.getInventory().forEach(this::add);
        Merchant.getInstanceMerchant().getMarket().forEach(this::add);

        // AIMS
        add(squareLabel);
        squareLabel.setBounds(-size,-size, size, size);
        makeSquareLabels();//added by Antoine


        // ===== BASE CONTENT ===== //
        Position merchRoomTL = worldMap.getMerchantRoomTopLeft();
        Position merchRoomBR = worldMap.getMerchantRoomBottomRight();
        for(int x = 0; x < WorldMap.MAX_X; x++) for(int y = 0; y < WorldMap.MAX_Y; y++) {
            JLabel jLabel = new JLabel();
            ImageIcon img = null;
            Cell currentCell = worldMap.getCell(x, y);
            CellElementType currentCet = currentCell.getBaseContent();
            CellElementType obstacle = currentCell.getObstacle();
            boolean isInMerchantRoom = merchRoomTL != null && x >= merchRoomTL.getX() && x <= merchRoomBR.getX() && y >= merchRoomTL.getY() && y <= merchRoomBR.getY();
            if(!worldMap.lastLevel() && isInMerchantRoom)
                img = new ImageIcon(Theme.MERCHANT.themeFor(currentCet).getImage());
            else {
                if (y > 0 && currentCet == CellElementType.HORIZONTAL_WALL) {
                    CellElementType previous = worldMap.getCell(x, y - 1).getBaseContent();
                    if (previous.isWall()) img = new ImageIcon(worldMap.getTheme().getCorner_bot().getImage());
                    else if (y < WorldMap.MAX_Y - 1) {
                        CellElementType after = worldMap.getCell(x, y + 1).getBaseContent();
                        img = new ImageIcon((after.isWall() ? worldMap.getTheme().getCorner_top() : worldMap.getTheme().themeFor(currentCet)).getImage());
                    }
                } else if (currentCet == CellElementType.CORNER_BOT) {
                    if (y < WorldMap.MAX_Y - 1) {
                        CellElementType cet = worldMap.getCell(x, y + 1).getBaseContent();
                        img = new ImageIcon((cet.isWall() ? worldMap.getTheme().getWall_vertical() : worldMap.getTheme().themeFor(currentCet)).getImage());
                    }
                } else if (currentCet == CellElementType.CORNER_TOP) {
                    if (y > 0) {
                        CellElementType cet = worldMap.getCell(x, y - 1).getBaseContent();
                        img = new ImageIcon((cet.isWall() ? worldMap.getTheme().getWall_vertical() : worldMap.getTheme().themeFor(currentCet)).getImage());
                    }
                }
                if (img == null) img = new ImageIcon(worldMap.getTheme().themeFor(currentCet).getImage());
            }


            // ===== SHADOWS ===== //
            if (x > 0 && y > 0) {
                if(worldMap.getTheme() != Theme.ISLANDS || isInMerchantRoom) {
                    CellElementType leftC = worldMap.getCell(x - 1, y).getBaseContent();
                    CellElementType topLeftC = worldMap.getCell(x - 1, y - 1).getBaseContent();
                    if (leftC.isWall() && !currentCet.isWall() && topLeftC.isWall()) {
                        JLabel shad = new JLabel(shadow);
                        shad.setBounds(x * size, y * size, size, size);
                        add(shad);
                    }
                }
            }


            // ===== OBSTACLES ===== //
            if (obstacle != null) {
                JLabel obstacleL = new JLabel(isInMerchantRoom? CellElementType.BOX.getIcon() : new ImageIcon(obstacle.getIcon().getImage()));
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

    private void makeSquareLabels() {//added by Antoine
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                JLabel l = new JLabel(green2);
                l.setOpaque(true);
                l.setBounds(-size, -size, size, size);
                l.setBackground(new Color(0, 0, 0, 0));
                squareLabels[x][y] = l;
                add(l);
            }
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
            if (player.entityType == EntityType.HERO_MAGE) {//added by Antoine
                Position p = whatHeroDoes.getP();
                for (int x = 0; x < 3; x++) {
                    for (int y = 0; y < 3; y++) {
                        Position current = new Position(x-1 + p.getX(), y-1 + p.getY());
                        if (worldMap.getCell(whatHeroDoes.getP()).getEntity() instanceof Monster && worldMap.getCell(current).getEntity() instanceof Monster && !current.equals(whatHeroDoes.getP())) squareLabels[x][y].setLocation(current.getX() * size, current.getY() * size);
                        else squareLabels[x][y].setLocation(-size, -size);
                    }
                }
            }

            squareLabel.setIcon(worldMap.getCell(whatHeroDoes.getP()).getEntity() instanceof Monster ? green : red);
            squareLabel.setLocation(whatHeroDoes.getP().getX() * size, whatHeroDoes.getP().getY() * size);
        } else {
            opaqueLabels.forEach(x -> x.setLocation(-size, -size));
            squareLabel.setLocation(-size, -size);
            if (player.entityType == EntityType.HERO_MAGE) {//added by Antoine
                for (int x = 0; x < 3; x++) {
                    for (int y = 0; y < 3; y++) {
                        squareLabels[x][y].setBounds(-size, -size, size, size);
                    }
                }
            }
        }
    }

    Point getHeroPosition() {
        return heroLabel.getLocation();
    }
}
