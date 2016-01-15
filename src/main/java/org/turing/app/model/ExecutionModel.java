package org.turing.app.model;

import org.turing.app.views.constants.ExecutionStatus;

/**
 * Created by Patryk Stopyra on 14.01.2016.
 */
public class ExecutionModel {
    public static final int MIN_DELAY = 0;
    public static final int MAX_DELAY = 30;

    private int executionDelay;
    private ExecutionStatus executionStatus;

    public ExecutionModel() {
        initialize();
    }

    public void initialize() {
        executionDelay = MAX_DELAY /2;
        executionStatus = ExecutionStatus.CONTINUOUS_STOP;
    }

    public int getExecutionDelay() {
        return executionDelay;
    }

    public void setExecutionDelay(int executionDelay) {
        this.executionDelay = executionDelay;
    }

    public ExecutionStatus getExecutionStatus() {
        return executionStatus;
    }

    public void setExecutionStatus(ExecutionStatus executionStatus) {
        this.executionStatus = executionStatus;
    }
}
