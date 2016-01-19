package org.turing.app.controllers;

import org.turing.app.common.State;
import org.turing.app.common.Symbol;
import org.turing.app.engine.TuringMachine;
import org.turing.app.exceptions.SymbolException;
import org.turing.app.model.ActionTriple;
import org.turing.app.model.DataModel;
import org.turing.app.model.ProgramModel;
import org.turing.app.views.constants.ApplicationStrings;
import org.turing.app.views.panels.StatePanel;
import org.turing.app.views.panels.TapePanel;
import org.turing.support.LoggerGUI;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;

import static org.turing.app.common.BlankSymbol.BLANK;
import static org.turing.app.common.HaltState.HALT;
import static org.turing.app.common.MoveDirection.NONE;

public class ProgramEditController {
    private static final ActionTriple defaultActionTriple = new ActionTriple(HALT, BLANK, NONE);

    private final ProgramModel programModel;
    private final DataModel dataModel;
    private final TuringMachine engine;

    private JFrame programFrame;
    private JTable programTable;
    private StatePanel statePanel;
    private TapePanel tapePanel;

    public ProgramEditController(ProgramModel programModel, DataModel dataModel, TuringMachine engine) {
        this.engine = engine;
        this.programModel = programModel;
        this.dataModel = dataModel;
    }

    public void onStateAddition() {
        String stateName = JOptionPane.showInputDialog(programFrame, ApplicationStrings.NEW_STATE_TEXT);

        if (stateName != null) {
            State newState = new State(stateName, false);

            if (programModel.getAvailableStates().contains(newState)) {
                JOptionPane.showMessageDialog(
                        programFrame, ApplicationStrings.NEW_STATE_EXISTS_EXCEPTION, "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            programModel.addNewState(newState);
            for (Symbol symbol : programModel.getAvailableSymbols())
                programModel.addNewTransition(newState, symbol, defaultActionTriple);

            refreshProgramTableAndStatePanel();
        }
    }

    public void onStateDeletion() {
        try {
            if (programTable.getSelectedRow() >= 0) {
                if(dataModel.getState() != programModel.getStateAt(programTable.getSelectedRow()))
                {
                    State delState = programModel.getStateAt(programTable.getSelectedRow());
                    programModel.deleteState(delState);
                    if(engine.deleteHistoryIfOccurs(delState))
                        LoggerGUI.showInfoDialog(programFrame, "Deleted state on execution state - backwards debug unavailable", "Information");
                }
                else
                {
                    JOptionPane.showMessageDialog(
                            programFrame, "Cannot delete state " + dataModel.getState().getName() + " it is current state.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(
                    programFrame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        refreshProgramTableAndStatePanel();
    }

    private void refreshProgramTableAndStatePanel() {
        ((AbstractTableModel) programTable.getModel()).fireTableDataChanged();
        statePanel.updateAvailableStates(programModel.getAvailableStates());
    }

    public void fullyRefreshProgramTableAndStatePanel() {
        ((AbstractTableModel) programTable.getModel()).fireTableStructureChanged();
        statePanel.updateAvailableStates(programModel.getAvailableStates());
    }

    public void onSymbolAddition() {
        String symbolName = JOptionPane.showInputDialog(programFrame, ApplicationStrings.NEW_SYMBOL_TEXT);

        if (symbolName != null) {
            Symbol newSymbol;

            try {
                newSymbol = new Symbol(symbolName);
            } catch (SymbolException ex) {
                JOptionPane.showMessageDialog(
                        programFrame, ApplicationStrings.NEW_SYMBOL_EXCEPTION, "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (newSymbol.equals(BLANK)) {
                JOptionPane.showMessageDialog(
                        programFrame, ApplicationStrings.NEW_SYMBOL_BLANK_EXCEPTION, "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (programModel.getAvailableSymbols().contains(newSymbol)) {
                JOptionPane.showMessageDialog(
                        programFrame, ApplicationStrings.NEW_SYMBOL_EXISTS_EXCEPTION, "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            programModel.addNewSymbol(newSymbol);
            for (State state : programModel.getAvailableStates())
                programModel.addNewTransition(state, newSymbol, defaultActionTriple);
            refreshProgramTable();
        }
    }

    public void onSymbolDeletion() {
        try {
            if (programTable.getSelectedColumn() > 0) {
                if(canSymbolBeRemoved(programModel.getSymbolAt(programTable.getSelectedColumn() - 1)))
                {
                    Symbol delSymbol = programModel.getSymbolAt(programTable.getSelectedColumn() - 1);
                    programModel.deleteSymbol(delSymbol);
                    if(engine.deleteHistoryIfOccurs(delSymbol))
                        LoggerGUI.showInfoDialog(programFrame, "Deleted symbol on execution state - backwards debug unavailable", "Information");
                }
                else
                {
                    JOptionPane.showMessageDialog(
                            programFrame, "Cannot delete symbol " + dataModel.read().getValue() + ". It is still used on a tape.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(
                    programFrame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        refreshProgramTable();
    }

    private void refreshProgramTable() {
        ((AbstractTableModel) programTable.getModel()).fireTableStructureChanged();
    }

    private boolean canSymbolBeRemoved(Symbol symbol)
    {
        boolean canBeRemoved = true;
        canBeRemoved &= !symbol.getValue().equalsIgnoreCase(dataModel.read().getValue());
        canBeRemoved &= !dataModel.getLeftTape().contains(symbol);
        canBeRemoved &= !dataModel.getRightTape().contains(symbol);
        return canBeRemoved;
    }

    public ProgramModel getProgramModel() {
        return programModel;
    }

    public void setProgramFrame(JFrame programFrame) {
        this.programFrame = programFrame;
    }

    public void setProgramTable(JTable programTable) {
        this.programTable = programTable;
    }

    public void setStatePanel(StatePanel statePanel) {
        this.statePanel = statePanel;
    }

    public void setTapePanel(TapePanel tapePanel) {
        this.tapePanel = tapePanel;
    }
}
