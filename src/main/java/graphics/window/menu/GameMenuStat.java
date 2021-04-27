package graphics.window.menu;

import graphics.Language;
import graphics.Tools;
import graphics.window.GameWindow;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class GameMenuStat extends GameMenuCustomPanel {
    private final JButton clearButton;
    private final JButton goToMenu;
    private final DefaultTableModel model;
    private final JTable table;

    public GameMenuStat() {
        setLayout(new BorderLayout());
        model = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable();
        clearButton  = createMenuButton(Language.clear());
        goToMenu = createMenuButton(Language.back());

        setTableModel();
        setTable();
        addMouseEffect(clearButton, Effect.ERASE);
        addMouseEffect(goToMenu, Effect.BACK);
        makeSorter();

        setup();

    }

    private void setup() {
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

    private void setTableModel() {
        model.addColumn(Language.date());
        model.addColumn(Language.heroName());
        model.addColumn(Language.end());
        model.addColumn(Language.difficulty());
        model.addColumn(Language.speciality());
        model.addColumn(Language.level());
        model.addColumn(Language.stage());
        if (Tools.Ranking.getRankings() != null)
            Arrays.stream(Tools.Ranking.getRankings()).forEach(model::addRow);

    }

    private void makeSorter(){
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(sorter);

        sorter.setComparator(0, new Comparator<String>() {
            private int[] splitDate(String s){
                String[] split = s.split("/");
                return new int[]{Integer.decode(split[0]), Integer.decode(split[1]), Integer.decode(split[2])};
            }

            @Override
            public int compare(String name1, String name2) {
                int[] date1 = splitDate(name1);
                int[] date2 = splitDate(name2);
                for (int i = date1.length - 1; i > -1; i--){
                    if (date1[i] != date2[i]) return Integer.compare(date1[i], date2[i]);
                }
                return Integer.compare(0, 0);
            }
        });
        Comparator<Integer> integerComparator = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        };
        sorter.setComparator(5, integerComparator);
        sorter.setComparator(6, integerComparator);

        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        int columnIndexToSort = 0;
        sortKeys.add(new RowSorter.SortKey(columnIndexToSort, SortOrder.DESCENDING));

        sorter.setSortKeys(sortKeys);
        sorter.sort();
    }

    private void setTable() {
        table.setModel(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(sorter);
    }


    /**
     * Add an effect to a JButton (click and hover).
     * @param button a JButton, should be not null
     * @param effect one of ERASE or BACK
     */
    private void addMouseEffect(JButton button, Effect effect) {
        Color bg = button.getBackground();
        Color hoverBG = new Color(180, 150, 110);

        button.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                button.setBackground(bg);
                switch (effect) {
                    case ERASE -> {
                        String[] options = {Language.erase(), Language.cancel()};
                        int apply = JOptionPane.showOptionDialog(GameWindow.window, Language.confirmClear(), "",
                                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);
                        if (apply == JOptionPane.YES_OPTION) {
                            Tools.Ranking.clearRanking();
                            for (int i = model.getRowCount() - 1; i > -1; i--) {
                                model.removeRow(i);
                            }
                        }}
                    case BACK -> GameMenuPanel.getMenuPanel.displayStartScreen();
                }
            }
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverBG);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(bg);
            }
        });
    }

    private enum Effect {
        ERASE, BACK
    }
}
