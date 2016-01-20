package org.turing.app.controllers;


import org.turing.app.common.State;
import org.turing.app.common.Symbol;
import org.turing.app.engine.TuringMachine;
import org.turing.app.exceptions.TapeException;
import org.turing.app.model.DataModel;
import org.turing.app.model.ExecutionModel;
import org.turing.app.model.ProgramModel;
import org.turing.app.views.constants.ExecutionStatus;
import org.turing.app.views.panels.ControlPanel;
import org.turing.app.views.panels.SliderPanel;
import org.turing.app.views.panels.StatePanel;
import org.turing.app.views.panels.TapePanel;
import org.turing.support.Logger;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import static org.turing.app.common.HaltState.HALT;

public class ExecutionController {
    private final ProgramModel programModel;
    private final DataModel dataModel;
    private final ExecutionModel executionModel;

    private final TuringMachine engine;

    private ControlPanel controlPanel;
    private StatePanel statePanel;
    private TapePanel tapePanel;
    private SliderPanel sliderPanel;

    public ExecutionController(DataModel dataModel, ProgramModel programModel, ExecutionModel executionModel, TuringMachine engine) {
        this.dataModel = dataModel;
        this.programModel = programModel;
        this.executionModel = executionModel;
        this.engine = engine;
    }


    public void refreshControlPanel() {
        controlPanel.updateStatus(executionModel.getExecutionStatus());
    }

    public void refreshStatePanel() {
        statePanel.updateAvailableStates(programModel.getAvailableStates());
        statePanel.updateState(dataModel.getState());
    }

    public void refreshTapePanel() {
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

    public int getExecutionDelay()
    {
        return executionModel.getExecutionDelay();
    }

    public void refreshSliderPanel() {
        int delay = executionModel.getExecutionDelay();
        sliderPanel.updateView(delay, delay == ExecutionModel.MAX_DELAY ? "Step by step mode" : "Delay between moves: " + delay + " s");
    }

    public synchronized void updateStepDelay(int delay) {
        executionModel.setExecutionDelay(delay);

        if (delay == ExecutionModel.MAX_DELAY) {
            executionModel.setExecutionStatus(ExecutionStatus.STEP);
            this.notify();
        } else if (executionModel.getExecutionStatus() == ExecutionStatus.STEP)
            executionModel.setExecutionStatus(ExecutionStatus.CONTINUOUS_STOP);

        refreshControlPanel();
        refreshSliderPanel();
    }

    public synchronized void updateState(State state) {
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

    public void clear() {
        pause();

        dataModel.clear();
        engine.restartEngine();

        refreshTapePanel();
        refreshStatePanel();
    }

    public synchronized void stepForward() {
        engine.stepForward();
        statePanel.updateState(dataModel.getState());
        refreshTapePanel();
    }

    public synchronized void stepBackward() {
        pause();

        engine.stepBackward();
        statePanel.updateState(dataModel.getState());
        refreshTapePanel();
    }

    public void play() {
        executionModel.setExecutionStatus(ExecutionStatus.CONTINUOUS_RUN);
        refreshControlPanel();

        new Thread(() -> {
            while (dataModel.getState() != HALT && executionModel.getExecutionStatus() == ExecutionStatus.CONTINUOUS_RUN) {
                synchronized (this) {
                    try {
                        engine.stepForward();
                        SwingUtilities.invokeAndWait(() -> {
                            statePanel.updateState(dataModel.getState());
                            refreshTapePanel();
                        });

                        if(executionModel.getExecutionDelay() != 0)
                            this.wait(executionModel.getExecutionDelay() * 1000);
                        else
                            this.wait(100);
                    } catch (InterruptedException e) {
                        Logger.warning("Executing thread interrupted: " + e);
                    } catch (InvocationTargetException e) {
                        Logger.error("Exception from SWING during execution: " + e);
                    }
                }
            }
            if (dataModel.getState() == HALT)
                pause();
        }).start();
    }

    public synchronized void pause() {
        if (executionModel.getExecutionStatus() == ExecutionStatus.CONTINUOUS_RUN) {
            executionModel.setExecutionStatus(ExecutionStatus.CONTINUOUS_STOP);
            this.notify();
            refreshControlPanel();
        }
    }

    public void playOrPauseDependingOnStatus() {
        if (executionModel.getExecutionStatus() == ExecutionStatus.CONTINUOUS_RUN) {
            pause();
        } else {
            play();
        }
    }

    public void setControlPanel(ControlPanel controlPanel) {
        this.controlPanel = controlPanel;
    }

    public void setStatePanel(StatePanel statePanel) {
        this.statePanel = statePanel;
    }

    public void setTapePanel(TapePanel tapePanel) {
        this.tapePanel = tapePanel;
    }

    public void setSliderPanel(SliderPanel sliderPanel) {
        this.sliderPanel = sliderPanel;
    }
}
