package org.turing.app.controllers;

import org.turing.app.common.State;
import org.turing.app.model.DataModel;
import org.turing.app.model.ProgramModel;
import org.turing.app.views.panels.StatePanel;
import org.turing.support.Logger;

public class ExecutionController {
    private final ProgramModel programModel;
    private final DataModel dataModel;

    private StatePanel statePanel;

    public ExecutionController( DataModel dataModel, ProgramModel programModel) {
        this.dataModel = dataModel;
        this.programModel = programModel;
    }

    public void clear() {
        dataModel.clear();
    }

    public void refreshStatePanel() {
        statePanel.updateAvailableStates(programModel.getAvailableStates());
        statePanel.updateState(dataModel.getState());
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

    public void setStatePanel(StatePanel statePanel) {
        this.statePanel = statePanel;
    }
}
