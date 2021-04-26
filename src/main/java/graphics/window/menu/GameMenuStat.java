package graphics.window.menu;

import graphics.Language;
import graphics.Tools;
import graphics.window.GameWindow;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.Arrays;

public class GameMenuStat extends GameMenuCustomPanel {
    private final JButton clearButton;
    private final JButton goToMenu;
    private final DefaultTableModel model;
    private final JTable table;

    public GameMenuStat(){
        setLayout(new BorderLayout());
        model = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable();
        clearButton  = createMenuButton("Clear");
        goToMenu = createMenuButton(Language.back());

        setTableModel();
        setTable();
        setClearButton();
        setGoToMenu();

        setup();

    }

    private void setup(){
        add(table.getTableHeader(), BorderLayout.NORTH);
        JPanel panel = new JPanel();
        JScrollPane js = new JScrollPane(table);
        panel.add(js);
        js.setFocusable(false);
        add(table, BorderLayout.CENTER);
        JPanel panel1 = new JPanel();
        panel1.add(goToMenu);
        panel1.add(clearButton);
        add(panel1, BorderLayout.SOUTH);
    }

    private void setTableModel(){
        model.addColumn("Date");
        model.addColumn("Name");
        model.addColumn("Speciality");
        model.addColumn(Language.level());
        model.addColumn(Language.stage());
        if (Tools.Ranking.getRankings() != null)
            Arrays.stream(Tools.Ranking.getRankings()).forEach(model::addRow);

    }

    private void setTable(){
        table.setModel(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(sorter);
    }

    private void setClearButton(){
        clearButton.addActionListener(e -> {
            int apply = JOptionPane.showConfirmDialog(GameWindow.window,
                    "Are you sure you want to clear the entire ranking history ?",
                    "", JOptionPane.YES_NO_OPTION);
            if (apply == JOptionPane.YES_OPTION) {
                Tools.Ranking.clearRanking();
                for (int i = model.getRowCount() - 1; i > -1; i--) {
                    model.removeRow(i);
                }
            }
        });
    }

    private void setGoToMenu(){
        goToMenu.addActionListener(e -> GameMenuPanel.getMenuPanel.displayStartScreen());
    }
}
