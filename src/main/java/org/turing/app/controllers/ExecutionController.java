package org.turing.app.controllers;

import org.turing.app.common.State;
import org.turing.app.common.Symbol;
import org.turing.app.engine.TuringMachine;
import org.turing.app.exceptions.TapeException;
import org.turing.app.model.DataModel;
import org.turing.app.model.ProgramModel;
import org.turing.app.views.panels.StatePanel;
import org.turing.app.views.panels.TapePanel;
import org.turing.support.Logger;

import java.util.ArrayList;

public class ExecutionController {
    private final ProgramModel programModel;
    private final DataModel dataModel;

    private final TuringMachine engine;

    private StatePanel statePanel;
    private TapePanel tapePanel;

    public ExecutionController(DataModel dataModel, ProgramModel programModel, TuringMachine engine) {
        this.dataModel = dataModel;
        this.programModel = programModel;
        this.engine = engine;
    }

    public void clear() {
        dataModel.clear();
        engine.restartEngine();
    }

    public void refreshStatePanel() {
        statePanel.updateAvailableStates(programModel.getAvailableStates());
        statePanel.updateState(dataModel.getState());
    }

    public void refreshTape() {
        int visibleSize = tapePanel.getVisibleTapeSize();
        ArrayList<Symbol> view = new ArrayList<>(visibleSize);

        for (int deep = visibleSize / 2 - 1; deep >= 0; deep--) {
            view.add(dataModel.getLeftTape().getAtDeep(deep));
        }
        view.add(dataModel.read());
        for (int deep = 0; deep < visibleSize / 2; deep++) {
            view.add(dataModel.getRightTape().getAtDeep(deep));
        }

        try {
            tapePanel.updateView(view);
        } catch (TapeException e) {
            Logger.error(e);
        }
    }

    public void updateState(State state) {
        if (state == null) {
            Logger.error("State is null.");
            return;
        }

        if (programModel.getAvailableStates().contains(state)) {
            dataModel.setState(state);
            statePanel.updateState(dataModel.getState());
        } else
            Logger.error("Improper state: " + state.getName() + ".");
    }

    public void stepForward() {
        engine.startEngine();
        engine.stepForward();
        refreshStatePanel();
        refreshTape();
    }

    public void stepBackward() {
        engine.startEngine();
        engine.stepBackward();
        refreshStatePanel();
        refreshTape();
    }

    public void setStatePanel(StatePanel statePanel) {
        this.statePanel = statePanel;
    }

    public void setTapePanel(TapePanel tapePanel) {
        this.tapePanel = tapePanel;
    }

}
