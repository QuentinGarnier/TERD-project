package graphics.window.menu;

import graphics.Language;
import graphics.Tools;
import graphics.window.GameWindow;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
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
        super();
        setLayout(new BorderLayout());
        model = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable();
        table.setBackground(new Color(220, 220, 220));
        clearButton  = createMenuButton(Language.clear());
        goToMenu = createMenuButton(Language.back());

        setCellRenderer();
        setTableModel();
        setTable();
        addMouseEffect(clearButton, Effect.ERASE);
        addMouseEffect(goToMenu, Effect.BACK);
        makeSorter();

        setup();
    }

    private void setup() {

        // SCROLL PANE
        JPanel panel = new JPanel();
        JScrollPane js = new JScrollPane(table);
        panel.setOpaque(false);
        js.setOpaque(false);

        // TABLE SIZE & COLUMNS
        js.setPreferredSize(new Dimension(700,400));
        int[] columnsWidth = {90, 170, 100, 100, 100, 70, 70};
        int i = 0;
        for (int width : columnsWidth) {
            TableColumn column = table.getColumnModel().getColumn(i++);
            column.setPreferredWidth(width);
        }

        // BUTTON RETURN && CLEAR
        JPanel panel1 = new JPanel();
        panel1.setOpaque(false);
        panel1.add(goToMenu);
        panel1.add(clearButton);

        panel.add(js);
        add(panel, BorderLayout.CENTER);
        add(panel1, BorderLayout.SOUTH);
    }

    private void setTableModel() {
        model.addColumn(Language.date());
        model.addColumn(Language.end());
        model.addColumn(Language.difficulty());
        model.addColumn(Language.speciality());
        model.addColumn(Language.level());
        model.addColumn(Language.stage());
        model.addColumn(Language.heroName());
        if (Tools.Ranking.getRankings() != null)
            Arrays.stream(Tools.Ranking.getRankings()).forEach(model::addRow);
    }

    private void makeSorter() {
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
        Comparator<String> integerComparator = new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return Integer.compare(Integer.decode(o2), Integer.decode(o1));
            }
        };
        sorter.setComparator(4, integerComparator);
        sorter.setComparator(5, integerComparator);

        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        int columnIndexToSort = 0;
        sortKeys.add(new RowSorter.SortKey(columnIndexToSort, SortOrder.DESCENDING));

        sorter.setSortKeys(sortKeys);
        sorter.sort();
    }

    private void setTable() {
        table.setModel(model);
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(sorter);
        table.moveColumn(6,1);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);
    }

    private static class MyTableHeaderRenderer extends JLabel implements TableCellRenderer {

        private MyTableHeaderRenderer() {
            setFont(new Font("Consolas", Font.BOLD, 12));
            setForeground(Color.BLUE);
            setBorder(BorderFactory.createEtchedBorder());
            setVerticalAlignment(BOTTOM);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText(value.toString());
            setHorizontalAlignment(CENTER);
            return this;
        }
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            Color color1 = getBackground();
            Color color2 = color1.darker();
            int w = getWidth();
            int h = getHeight();
            GradientPaint gp = new GradientPaint(
                    0, 0, color1, 0, h, color2);
            g2d.setPaint(gp);
            g2d.fill(new Rectangle(0, 0, getWidth(), getHeight()));
            g2d.dispose();
            getUI().paint(g, this);
        }
    }

    private void setCellRenderer(){
        table.setDefaultRenderer(Object.class, new TableCellRenderer() {
            final JLabel comp = new JLabel();
            String val;

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                comp.setForeground(Color.BLACK); // text color

                if (value != null) {
                    val = value.toString();
                    comp.setText(val);
                    comp.setFont(new Font("Serif", Font.ITALIC, 12));
                    comp.setHorizontalAlignment(JTextField.CENTER);
                    if (val.equals(Tools.Victory_Death.ABANDON.toString())) {
                        comp.setForeground(Color.BLUE);
                    } else if (val.equals(Tools.Victory_Death.DEATH_BY_HP.toString())) {
                        comp.setForeground(Color.RED);
                    } else if (val.equals(Tools.Victory_Death.DEATH_BY_HUNGER.toString())) {
                        comp.setForeground(Color.RED);
                    } else if (val.equals(Tools.Victory_Death.WIN.toString())) {
                        comp.setForeground(Color.GREEN);
                    } else if (val.equals(Language.mageCL())) {
                        comp.setForeground(Tools.WindowText.blue);
                    } else if (val.equals(Language.warriorCL())){
                        comp.setForeground(Tools.WindowText.red);
                    } else if (val.equals(Language.archerCL())){
                        comp.setForeground(Tools.WindowText.green);
                    } else if (column == 3){
                        comp.setForeground(Color.DARK_GRAY);
                    }
                }
                return comp;
            }
        });
        table.getTableHeader().setDefaultRenderer(new MyTableHeaderRenderer());
    }


    /**
     * Add an effect to a JButton (click and hover).
     * @param button a JButton, should be not null
     * @param effect one of ERASE or BACK
     */
    private void addMouseEffect(JButton button, Effect effect) {
        Color bg = button.getBackground();
        button.addMouseListener(new MouseAdapter() {
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
        });
    }

    private enum Effect {
        ERASE, BACK
    }
}
