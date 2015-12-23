package org.turing.app.engine;

/**
 * Created by Patryk Stopyra on 10.11.2015.
 */
public interface ITuringMachine {

    public void stepForward();
    public void stepBackward();
    public void updateSpeed(float newMultiplier);
}
