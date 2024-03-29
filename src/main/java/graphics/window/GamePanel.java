package graphics.window;

import entity.*;
import entity.Player.*;
import graphics.elements.Position;
import graphics.elements.cells.Cell;
import graphics.elements.cells.CellElementType;
import graphics.map.Theme;
import graphics.map.WorldMap;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GamePanel extends JPanel {
    private JPanel heroLabel = Player.getInstancePlayer();
    private final ImageIcon shadow = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("data/images/map/miscellaneous/shadow.png")));
    private final ImageIcon red = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("data/images/map/miscellaneous/square_red.png")));
    private final ImageIcon green = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("data/images/map/miscellaneous/square_green.png")));
    private final ImageIcon green2 = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("data/images/map/miscellaneous/slide_square_green.png")));
    private final JLabel squareLabel = new JLabel(green);
    private final JLabel[][] squareLabels;
    private final static List<JLabel> opaqueLabels = new ArrayList<>();
    private final static List<JLabel> opaqueLabelsMonsters = new ArrayList<>();
    private final static List<JLabel> opaqueLabelsMonsterActivationZone = new ArrayList<>();
    private final WorldMap worldMap = WorldMap.getInstanceWorld();
    public static final int size = 32;

    GamePanel() {
        super();
        setLayout(null);
        setFocusable(true);
        setBackground(Color.DARK_GRAY);
        setPreferredSize(new Dimension(WorldMap.MAX_X * size,WorldMap.MAX_Y * size));
        squareLabels = new JLabel[3][3];
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
        makeOpaqueLabel(Player.getInstancePlayer().getRange(), new Color(0,0,0,80), opaqueLabels);
        makeOpaqueLabel(20, new Color(200,0,0,70), opaqueLabelsMonsters);
        makeOpaqueLabel(20, new Color(0,0,100,50), opaqueLabelsMonsterActivationZone);

        // MONSTERS
        worldMap.getRooms().forEach(room -> room.getMonsters().forEach(this::add));

        // ITEMS
        worldMap.getItems().forEach(this::add);
        Player.getInventory().forEach(this::add);
        Merchant.getInstanceMerchant().getMarket().forEach(this::add);

        // AIMS
        add(squareLabel);
        squareLabel.setBounds(-size,-size, size, size);
        makeSquareLabels();


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

    private void makeOpaqueLabel(int range, Color color, List<JLabel> labels) {
        labels.clear();
        for (int i = 0; i < Math.pow((range + 1) * 2, 2); i++) {
            JLabel l = new JLabel();
            l.setOpaque(true);
            l.setBounds(-size, -size, size, size);
            l.setBackground(color);
            labels.add(l);
            add(l);
        }
    }

    private void makeSquareLabels() {
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

    public static void clearMonsterAttackRange(){
        opaqueLabelsMonsters.forEach(e -> e.setLocation(-size, -size));
        opaqueLabelsMonsterActivationZone.forEach(e -> e.setLocation(-size, -size));
    }

    public static void placeAttackLabels(AbstractEntity ae){
        boolean isMonster = (ae instanceof Monster);
        List<JLabel> labels =  isMonster ? GamePanel.opaqueLabelsMonsters : GamePanel.opaqueLabels;
        List<JLabel> labels2 = isMonster ? GamePanel.opaqueLabelsMonsterActivationZone : null;
        labels.forEach(e -> e.setLocation(-size, -size));
        ArrayList<Position> opaquePos = ae.getPosition().calcRangePosition(ae, true, ae.getRange());
        for (int i = 0; i < opaquePos.size(); i++){
            Position current = opaquePos.get(i);
            labels.get(i).setLocation(current.getX() * size, current.getY() * size);
        }
        if (labels2 != null) {
            labels2.forEach(e -> e.setLocation(-size, -size));
            opaquePos = ae.getPosition().calcRangePosition(ae, true, ae.getEntityType().attackZone);
            for (int i = 0; i < opaquePos.size(); i++){
                Position current = opaquePos.get(i);
                labels2.get(i).setLocation(current.getX() * size, current.getY() * size);
            }
        }
    }

    public void setObjective() {
        Player player = Player.getInstancePlayer();
        WhatHeroDoes whatHeroDoes = player.getWhatHeroDoes();
        if (whatHeroDoes == WhatHeroDoes.CHOOSING_ATTACK && player.getHP() > 0) {
            placeAttackLabels(player);
            if (player.entityType == EntityType.HERO_MAGE) {
                Position p = player.getAttackPosition();
                for (int x = 0; x < 3; x++) {
                    for (int y = 0; y < 3; y++) {
                        Position current = new Position(x-1 + p.getX(), y-1 + p.getY());
                        if (worldMap.getCell(p).getEntity() instanceof Monster && worldMap.getCell(current).getEntity() instanceof Monster && worldMap.getCell(p).getEntity() != Monster.boss && !current.equals(p)) squareLabels[x][y].setLocation(current.getX() * size, current.getY() * size);
                        else squareLabels[x][y].setLocation(-size, -size);
                    }
                }
            }

            Position p = player.getAttackPosition();
            squareLabel.setIcon(worldMap.getCell(p).getEntity() instanceof Monster ? green : red);
            squareLabel.setLocation(p.getX() * size, p.getY() * size);
        } else {
            opaqueLabels.forEach(x -> x.setLocation(-size, -size));
            squareLabel.setLocation(-size, -size);
            if (player.entityType == EntityType.HERO_MAGE) {
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
