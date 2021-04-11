package graphics.window.menu;

import graphics.Tools;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import java.awt.*;

public class GameMenuCustomPanel extends JPanel {
    final Color colorBG = new Color(160, 140, 100, 230);

    GameMenuCustomPanel() {
        super();
        setup();
    }

    private void setup() {
        Dimension dim = new Dimension(500, 500);
        setPreferredSize(dim);
        setBackground(colorBG);
        Color colorBGDark = new Color(80, 60, 30, 220);
        setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, colorBG, colorBGDark));
    }

    protected JLabel createTitle(String txt, int fontSize, Color c) {
        JLabel title = new JLabel(txt);
        Font font = title.getFont();
        title.setFont(new Font(font.getName(), Font.BOLD, fontSize));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setForeground(c);
        return title;
    }

    public static JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(text.length() * 16 + 32,40));
        button.setBackground(new Color(220, 200, 160));
        button.setForeground(Color.BLACK);
        button.setIcon(new ImageIcon(""));
        Font font = button.getFont();
        button.setFont(new Font(font.getName(), Font.BOLD, 18));
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createBevelBorder(BevelBorder.RAISED, Tools.WindowText.golden, Tools.WindowText.dark_golden),
                        BorderFactory.createLineBorder(Tools.WindowText.golden, 4)),
                BorderFactory.createLineBorder(new Color(140, 110, 70))));
        button.setFocusable(false);
        return button;
    }

    public static Border bigBorder(boolean colored) {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createCompoundBorder(
                        (colored? BorderFactory.createBevelBorder(BevelBorder.RAISED, Tools.WindowText.golden, Tools.WindowText.dark_golden): BorderFactory.createBevelBorder(BevelBorder.RAISED)),
                        BorderFactory.createLineBorder((colored? Tools.WindowText.golden: Color.WHITE), 3)),
                BorderFactory.createLineBorder(colored?Tools.WindowText.dark_golden:Color.GRAY));
    }
}
