package org.turing.app.engine;

import org.turing.app.model.ActionTriple;

import java.util.Stack;

/**
 * Created by Patryk Stopyra on 10.11.2015.
 */
public interface ITuringMachine {

    public void stepForward();
    public void stepBackward();
    public ActionTriple getCurrentActionTriple();
    public ActionTriple getLatestCompletedActionTriple();
    public Stack<ActionTriple> getExecutionStack();
}
