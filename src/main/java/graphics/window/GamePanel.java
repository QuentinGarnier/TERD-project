package graphics.window;

import entity.Player;
import graphics.elements.Move;
import graphics.elements.Position;
import graphics.elements.cells.Cell;
import graphics.elements.cells.CellElementType;
import graphics.map.WorldMap;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private JLabel heroLabel = new JLabel(CellElementType.HERO.getIcon());

    GamePanel() {
        super();
        setLayout(null);
        setFocusable(true);
        setBackground(Color.DARK_GRAY);
    }

    void clear() {
        removeAll();
    }

    void display() {
        displayMap();
        displayInterface();
    }

    private void displayMap() {
        add(heroLabel);
        for(int x = 0; x < WorldMap.MAX_X; x++) for(int y = 0; y < WorldMap.MAX_Y; y++) {
            Cell cell = WorldMap.getInstanceWorld().getCell(x, y);

            if(cell.getEntityContent() == CellElementType.HERO) {
                heroLabel.setBounds(x * 32, y * 32, 32, 32);
            } else {
                JLabel label = new JLabel(cell.getMainContentType().getIcon());
                label.setBounds(x * 32, y * 32, 32, 32);
                add(label);
            }

            //Display background when there is something else on:
            if(cell.getMainContentType() != cell.getBaseContent()) {
                JLabel label2 = new JLabel(cell.getBaseContent().getIcon());
                label2.setBounds(x*32, y*32, 32, 32);
                add(label2);
            }
        }
    }

    private void displayInterface() {
        JPanel playerInfo = new JPanel();
        add(playerInfo);
        playerInfo.setBounds(0,WorldMap.MAX_Y * 32, 800, 80);  //Dimensions test -> à modifier
        setBackground(Color.LIGHT_GRAY);
    }

    void moveHero(Move move) {
        //Should be a "canMoveToward(Move move)" function in Player.java to have a better code:
        Position p = new Position(Player.getInstancePlayer().getPos().getX(), Player.getInstancePlayer().getPos().getY());
        if(Player.getInstancePlayer().canMove()) {
            Player.getInstancePlayer().moveEntity(move);
            if(!Player.getInstancePlayer().getPos().equals(p))
                heroLabel.setLocation(heroLabel.getX() + (move==Move.LEFT?-32:(move==Move.RIGHT?32:0)), heroLabel.getY() + (move==Move.UP?-32:(move==Move.DOWN?32:0)));
        }
    }
}
