package org.turing.app.views.panels;

import com.sun.tools.internal.ws.wscompile.Options;
import org.turing.app.views.ApplicationConstraints;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.SpringLayout.*;

/**
 * Created by fifi on 15.11.2015.
 */
public class ProgramTablePanel extends JPanel
{
    private String[] colNames;
    private Object[][] data;
    private TuringTableModel tableModel;
    private TuringTable table;

    private SpringLayout layout;
    private JScrollPane sp;
    private JButton addColumn, deleteColumn, addRow, deleteRow;

    public ProgramTablePanel()
    {
        colNames = new String[]{"State", "A", "B", "C", "D"};
        data = new Object[][]{
                {"1", "1", "2", "-", "N"},
                {"2", "3", "1", "T", "4"},
                {"3", "2", "1", "4", "T"}
        };
        createTablePanel();
    }

    public ProgramTablePanel(String[] colNames, Object[][] data)
    {
        this.colNames = colNames;
        this.data = data;
        createTablePanel();
    }

    private void createTablePanel()
    {
        createAndInitTableModel();
        initProgramPanelComponents();
        setPanelSettings();
        setComponentsSettings();
        placeComponentsOnPanel();
        addComponentsToPanel();
        addListeners();
    }

    private void createAndInitTableModel()
    {
        tableModel = new TuringTableModel();
        tableModel.setColNames(colNames);
        tableModel.setData(data);
    }

    private void initProgramPanelComponents()
    {
        layout = new SpringLayout();
        table = new TuringTable(tableModel);
        sp = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        addColumn = new JButton("+");
        deleteColumn = new JButton("-");

        addRow = new JButton("+");
        deleteRow = new JButton("-");
    }

    private void setPanelSettings()
    {
        setLayout(layout);
    }

    private void addComponentsToPanel()
    {
        add(sp);
        add(addColumn);
        add(deleteColumn);
        add(addRow);
        add(deleteRow);
    }

    private void placeComponentsOnPanel()
    {
        layout.putConstraint(NORTH, sp, 5, NORTH, this);
        layout.putConstraint(WEST, sp, 5, WEST, this);

        layout.putConstraint(NORTH, addColumn, 5, NORTH, this);
        layout.putConstraint(WEST, addColumn, 5, EAST, sp);

        layout.putConstraint(NORTH, deleteColumn, 5, NORTH, this);
        layout.putConstraint(WEST, deleteColumn, 2, EAST, addColumn);

        layout.putConstraint(NORTH, addRow, 5, SOUTH, sp);
        layout.putConstraint(WEST, addRow, 10, WEST, this);

        layout.putConstraint(NORTH, deleteRow, 5, SOUTH, sp);
        layout.putConstraint(WEST, deleteRow, 2, EAST, addRow);
    }

    private void setComponentsSettings()
    {
        addColumn.setPreferredSize(new Dimension(ApplicationConstraints.tablePanelButtonWidth, ApplicationConstraints.tablePanelButtonHeight));
        deleteColumn.setPreferredSize(new Dimension(ApplicationConstraints.tablePanelButtonWidth, ApplicationConstraints.tablePanelButtonHeight));
        addRow.setPreferredSize(new Dimension(ApplicationConstraints.tablePanelButtonWidth, ApplicationConstraints.tablePanelButtonHeight));
        deleteRow.setPreferredSize(new Dimension(ApplicationConstraints.tablePanelButtonWidth, ApplicationConstraints.tablePanelButtonHeight));
        sp.setPreferredSize(new Dimension(ApplicationConstraints.tableMinimalWidth, ApplicationConstraints.tableMinimalHeight));
    }

    private void addListeners()
    {
        addColumn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //TODO:Implement
            }
        });
        deleteColumn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //TODO:Implement
            }
        });

        addRow.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //TODO:Implement
            }
        });
        deleteRow.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //TODO:Implement
            }
        });
    }
}

class TuringTable extends JTable
{
    private final int INTER_CELL_SPACE_WIDTH = 1;   //space between cells
    private final int ROW_HEIGHT = 12;              //row height

    private static final CellRendererPane CELL_RENDERER = new CellRendererPane();
    /**
     * KOnstruktor klasy QTable
     * @param tableModel - model tabeli
     */
    public TuringTable(AbstractTableModel tableModel)
    {
        super(tableModel);
        init();
    }
    /**
     * Inicjalizacja Tabeli
     */
    private void init()
    {
        setShowGrid(true);
        setTableHeader(createHeader());
        setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        setAutoscrolls(true);
        setGridColor(Color.GRAY.brighter());
        setRowHeight(ROW_HEIGHT);
        Dimension dim = new Dimension(INTER_CELL_SPACE_WIDTH, INTER_CELL_SPACE_WIDTH);
        setIntercellSpacing(new Dimension(dim));
    }

    private JTableHeader createHeader()
    {
        return new JTableHeader(getColumnModel())
        {
            @Override
            protected void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                JViewport vport = (JViewport)table.getParent();
                if(vport != null && table.getWidth() < vport.getWidth())
                {
                    int x = table.getWidth();
                    int width = vport.getWidth() - table.getWidth();
                    paintHeader(g, getTable(), x, width);
                }
            }
        };
    }

    private static void paintHeader(Graphics g, JTable t, int x, int width)
    {
        TableCellRenderer renderer = t.getTableHeader().getDefaultRenderer();
        Component comp = renderer.getTableCellRendererComponent(t, "", false, false, -1, 2);
        comp.setBounds(0, 0, width, t.getTableHeader().getHeight());
        ((JComponent)comp).setOpaque(false);
        CELL_RENDERER.paintComponent(g, comp, null, x, 0, width, t.getTableHeader().getHeight(), true);
    }
}

class TuringTableModel extends AbstractTableModel
{
    private String[] colNames;
    private Object[][] data;//[row][col]

    public void setColNames(String[] colNames)
    {
        this.colNames = colNames;
    }

    public void setData(Object[][] data)
    {
        this.data = data;
    }

    @Override
    public int getRowCount()
    {
        return data.length;
    }

    @Override
    public int getColumnCount()
    {
        return colNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        return data[rowIndex][columnIndex];
    }

    @Override
    public String getColumnName(int col)
    {
        return colNames[col];
    }

    @Override
    public boolean isCellEditable(int row, int col)
    {
        return true;
    }
}

class TuringTableException extends Exception
{
    public TuringTableException(String s)
    {
        System.err.println(s);
    }
}
