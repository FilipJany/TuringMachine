package org.turing.app.views.panels;

import org.turing.app.common.MoveDirection;
import org.turing.app.common.State;
import org.turing.app.common.Symbol;
import org.turing.app.controllers.ProgramEditController;
import org.turing.app.model.ActionTriple;
import org.turing.app.model.ProgramModel;
import org.turing.app.views.constants.ApplicationConstraints;
import org.turing.app.views.elements.ActionTripleComboBox;
import org.turing.support.Logger;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.EventObject;

import static javax.swing.SpringLayout.*;
import static org.turing.app.common.BlankSymbol.BLANK;

/**
 * Created by fifi on 15.11.2015.
 */
public class ProgramTablePanel extends JPanel {
    private final ProgramEditController programEditController;

    private TuringTableModel tableModel;
    private TuringTable table;

    private SpringLayout layout;
    private JScrollPane sp;
    private JButton addColumn, deleteColumn, addRow, deleteRow;

    public ProgramTablePanel(ProgramEditController programEditController) {
        this.programEditController = programEditController;

        createTablePanel();
    }

    public TuringTable getTable() {
        return table;
    }

    private void createTablePanel() {
        createAndInitTableModel();
        initProgramPanelComponents();
        setPanelSettings();
        setComponentsSettings();
        placeComponentsOnPanel();
        addComponentsToPanel();
        addListeners();
    }

    private void createAndInitTableModel() {
        tableModel = new TuringTableModel(programEditController.getProgramModel());
    }

    private void initProgramPanelComponents() {
        layout = new SpringLayout();
        table = new TuringTable(tableModel);
        sp = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        addColumn = new JButton("+");
        deleteColumn = new JButton("-");

        addRow = new JButton("+");
        deleteRow = new JButton("-");
    }

    private void setPanelSettings() {
        setLayout(layout);
    }

    private void addComponentsToPanel() {
        add(sp);
        add(addColumn);
        add(deleteColumn);
        add(addRow);
        add(deleteRow);
    }

    private void placeComponentsOnPanel() {
        layout.putConstraint(NORTH, sp, 2, NORTH, this);
        layout.putConstraint(SOUTH, sp, -20, SOUTH, this);
        layout.putConstraint(WEST, sp, 2, WEST, this);
        layout.putConstraint(EAST, sp, -40, EAST, this);

        layout.putConstraint(NORTH, addColumn, 5, NORTH, this);
        layout.putConstraint(WEST, addColumn, 5, EAST, sp);

        layout.putConstraint(NORTH, deleteColumn, 5, NORTH, this);
        layout.putConstraint(WEST, deleteColumn, 2, EAST, addColumn);

        layout.putConstraint(NORTH, addRow, 2, SOUTH, sp);
        layout.putConstraint(WEST, addRow, 4, WEST, this);

        layout.putConstraint(NORTH, deleteRow, 2, SOUTH, sp);
        layout.putConstraint(WEST, deleteRow, 2, EAST, addRow);
    }

    private void setComponentsSettings() {
        addColumn.setPreferredSize(new Dimension(ApplicationConstraints.tablePanelButtonWidth, ApplicationConstraints.tablePanelButtonHeight));
        deleteColumn.setPreferredSize(new Dimension(ApplicationConstraints.tablePanelButtonWidth, ApplicationConstraints.tablePanelButtonHeight));
        addRow.setPreferredSize(new Dimension(ApplicationConstraints.tablePanelButtonWidth, ApplicationConstraints.tablePanelButtonHeight));
        deleteRow.setPreferredSize(new Dimension(ApplicationConstraints.tablePanelButtonWidth, ApplicationConstraints.tablePanelButtonHeight));
        sp.setPreferredSize(new Dimension(ApplicationConstraints.tableMinimalWidth, ApplicationConstraints.tableMinimalHeight));
    }

    private void addListeners() {
        addRow.addActionListener(e -> programEditController.onStateAddition());
        deleteRow.addActionListener(e -> programEditController.onStateDeletion());

        addColumn.addActionListener(e -> programEditController.onSymbolAddition());
        deleteColumn.addActionListener(e -> programEditController.onSymbolDeletion());
    }
}

class TuringTable extends JTable {
    private final int INTER_CELL_SPACE = 1;   //space between cells
    private final int ROW_HEIGHT = 24;        //row height

    private static final CellRendererPane CELL_RENDERER = new CellRendererPane();

    private int columnWidth = ApplicationConstraints.columnWidth;
    private int selectedColumnWidth = ApplicationConstraints.selectedColumnWidth;

    /**
     * Konstruktor klasy JTable
     *
     * @param tableModel - model tabeli
     */
    public TuringTable(TuringTableModel tableModel) {
        super(tableModel);
        init(tableModel.getProgramModel());
    }

    /**
     * Inicjalizacja Tabeli
     */
    private void init(ProgramModel model) {
        setDefaultRenderer(ActionTriple.class, new ActionTripleCellRenderer(model));
        setDefaultEditor(ActionTriple.class, new ActionTripleCellEditor(model));
        setShowGrid(true);
        setTableHeader(createHeader());
        setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        setAutoscrolls(true);
        setGridColor(Color.GRAY.brighter());
        setRowHeight(ROW_HEIGHT);
        setCellSelectionEnabled(true);
        Dimension dim = new Dimension(INTER_CELL_SPACE, INTER_CELL_SPACE);
        setIntercellSpacing(new Dimension(dim));
    }

    private JTableHeader createHeader() {
        return new JTableHeader(getColumnModel()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                JViewport vport = (JViewport) table.getParent();
                if (vport != null && table.getWidth() < vport.getWidth()) {
                    int x = table.getWidth();
                    int width = vport.getWidth() - table.getWidth();
                    paintHeader(g, getTable(), x, width);
                }
            }
        };
    }

    private static void paintHeader(Graphics g, JTable t, int x, int width) {
        TableCellRenderer renderer = t.getTableHeader().getDefaultRenderer();
        Component comp = renderer.getTableCellRendererComponent(t, "", false, false, -1, 2);
        comp.setBounds(0, 0, width, t.getTableHeader().getHeight());
        ((JComponent) comp).setOpaque(false);
        CELL_RENDERER.paintComponent(g, comp, null, x, 0, width, t.getTableHeader().getHeight(), true);
    }

    @Override
    public void doLayout() {
        super.doLayout();

        if (getSelectedColumn() != -1) {
            for (int i = 1; i < getColumnCount(); i++)
                if (i == getSelectedColumn())
                    getColumnModel().getColumn(i).setPreferredWidth(selectedColumnWidth);
                else
                    getColumnModel().getColumn(i).setPreferredWidth(columnWidth);

            repaint();
        }
    }

    @Override
    public void columnSelectionChanged(ListSelectionEvent e) {
        super.columnSelectionChanged(e);
        doLayout();

    }


}

class TuringTableModel extends AbstractTableModel {
    private final ProgramModel programModel;

    TuringTableModel(ProgramModel programModel) {
        this.programModel = programModel;
    }

    @Override
    public int getRowCount() {
        return programModel.getAvailableStates().size();
    }

    @Override
    public int getColumnCount() {
        return programModel.getAvailableSymbols().size() + 1;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnIndex == 0 ? String.class : ActionTriple.class;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex == 0)
            return programModel.getStateAt(rowIndex).getName();
        else
            return programModel.getActionForStateAndSymbol(
                    programModel.getStateAt(rowIndex),
                    programModel.getSymbolAt(columnIndex - 1));
    }

    @Override
    public String getColumnName(int columnIndex) {
        if (columnIndex == 0)
            return "";
        else {
            Symbol columnSymbol = programModel.getSymbolAt(columnIndex - 1);
            return columnSymbol.equals(BLANK) ? BLANK.visibleRep() : columnSymbol.getValue();
        }
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return col != 0;
    }

    public ProgramModel getProgramModel() {
        return programModel;
    }
}

class ActionTripleCellEditor extends AbstractCellEditor implements TableCellEditor {

    ActionTripleCell<JLabel> whenDeselected;
    ActionTripleCell<JComboBox> whenSelected;

    ProgramModel programModel;
    int row;
    int column;

    public ActionTripleCellEditor(ProgramModel model) {
        programModel = model;
        whenDeselected = new ActionTripleCell<JLabel>(model, new JLabel(), new JLabel(), new JLabel()) {
            @Override
            ActionTripleCell<JLabel> reload(ActionTriple value) {
                nextState.setText(value.getState().getName());
                nextSymbol.setText(value.getSymbol().equals(BLANK) ? BLANK.visibleRep() : value.getSymbol().getValue());
                nextMove.setText(value.getMoveDirection().getSymbol());
                return this;
            }
        };
        whenSelected = new ActionTripleCell<JComboBox>(model, new ActionTripleComboBox<State>(),
                new ActionTripleComboBox<Symbol>(), new ActionTripleComboBox<>(MoveDirection.values())) {
            @Override
            ActionTripleCell<JComboBox> reload(ActionTriple value) {
                nextState.removeAllItems();
                for (State s : model.getAvailableStates())
                    nextState.addItem(s);
                nextState.setSelectedItem(value.getState());

                nextSymbol.removeAllItems();
                for (Symbol s : model.getAvailableSymbols())
                    nextSymbol.addItem(s);
                nextSymbol.setSelectedItem(value.getSymbol());

                nextMove.setSelectedItem(value.getMoveDirection());

                return this;
            }

            @Override
            ActionTriple getValue() {
                return new ActionTriple((State) nextState.getSelectedItem(),
                        (Symbol) nextSymbol.getSelectedItem(),
                        (MoveDirection) nextMove.getSelectedItem());
            }
        };
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        this.row = row;
        this.column = column;
        return whenSelected.reload((ActionTriple) value)
                    .setSelectionBackground(table.getSelectionBackground());
    }

    @Override
    public Object getCellEditorValue() {
        return null;
    }

    @Override
    public boolean isCellEditable(EventObject anEvent) {
        return true;
    }

    @Override
    public boolean shouldSelectCell(EventObject anEvent) {
        return true;
    }

    @Override
    public boolean stopCellEditing() {
        programModel.addNewTransition(programModel.getStateAt(row), programModel.getSymbolAt(column-1), whenSelected.getValue());
        return true;
    }

    @Override
    public void cancelCellEditing() {

    }

    @Override
    public void addCellEditorListener(CellEditorListener l) {

    }

    @Override
    public void removeCellEditorListener(CellEditorListener l) {

    }
}

class ActionTripleCellRenderer implements TableCellRenderer {

    ActionTripleCell<JLabel> whenDeselected;
    ActionTripleCell<JComboBox> whenSelected;

    public ActionTripleCellRenderer(ProgramModel model) {
        whenDeselected = new ActionTripleCell<JLabel>(model, new JLabel(), new JLabel(), new JLabel()) {
            @Override
            ActionTripleCell<JLabel> reload(ActionTriple value) {
                nextState.setText(value.getState().getName());
                nextSymbol.setText(value.getSymbol().equals(BLANK) ? BLANK.visibleRep() : value.getSymbol().getValue());
                nextMove.setText(value.getMoveDirection().getSymbol());
                return this;
            }
        };
        whenSelected = new ActionTripleCell<JComboBox>(model, new ActionTripleComboBox<State>(),
                new ActionTripleComboBox<Symbol>(), new ActionTripleComboBox<>(MoveDirection.values())) {
            @Override
            ActionTripleCell<JComboBox> reload(ActionTriple value) {
                nextState.removeAllItems();
                for (State s : model.getAvailableStates())
                    nextState.addItem(s);
                nextState.setSelectedItem(value.getState());

                nextSymbol.removeAllItems();
                for (Symbol s : model.getAvailableSymbols())
                    nextSymbol.addItem(s);
                nextSymbol.setSelectedItem(value.getSymbol());

                nextMove.setSelectedItem(value.getMoveDirection());

                return this;
            }
        };
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (value instanceof ActionTriple) {
            if (isSelected)
                return whenSelected.reload((ActionTriple) value)
                        .setSelectionBackground(table.getSelectionBackground());
            else
                return whenDeselected.reload((ActionTriple) value);
        } else {
            return null;
        }
    }
}

abstract class ActionTripleCell<T extends Component> extends JPanel {

    ProgramModel model;

    T nextState;
    T nextSymbol;
    T nextMove;

    ActionTripleCell(ProgramModel model, T nextState, T nextSymbol, T nextMove) {
        this.model = model;
        this.nextState = nextState;
        this.nextSymbol = nextSymbol;
        this.nextMove = nextMove;

        initLayout();
    }

    abstract ActionTripleCell<T> reload(ActionTriple value);

    ActionTriple getValue() {
        return null;
    }

    ActionTripleCell<T> setSelectionBackground(Color c) {
        setBackground(c);
        return this;
    }

    private void initLayout() {
        setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 0;
        c.gridy = 0;
        add(nextState, c);

        c.gridx = 1;
        add(nextSymbol, c);

        c.gridx = 2;
        add(nextMove, c);
    }
}
