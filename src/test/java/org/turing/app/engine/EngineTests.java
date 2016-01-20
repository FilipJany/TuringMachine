package org.turing.app.engine;

import org.junit.Test;
import org.turing.app.common.MoveDirection;
import org.turing.app.common.State;
import org.turing.app.common.Symbol;
import org.turing.app.model.ActionTriple;
import org.turing.app.model.DataModel;
import org.turing.app.model.ProgramModel;

/**
 * Created by fifi on 09.01.2016.
 */
public class EngineTests
{
    @Test
    public void testBackAndForward()
    {
        ProgramModel programModel = new ProgramModel();
        DataModel dataModel = new DataModel(programModel);

        //Fill programModel
        //Add symbols
        programModel.addNewSymbol(new Symbol("D"));
        programModel.addNewSymbol(new Symbol("O"));
        programModel.addNewSymbol(new Symbol("P"));
        programModel.addNewSymbol(new Symbol("A"));

        //Add states
        programModel.addNewState(new State("1", false));
        programModel.addNewState(new State("2", false));
        programModel.addNewState(new State("3", false));
        programModel.addNewState(new State("4", true));

        //Create transition table
        for(int i = 0; i < 3; ++i)
            programModel.addNewTransition(programModel.getStateAt(i), programModel.getSymbolAt(i),
                    new ActionTriple(programModel.getStateAt(i), programModel.getSymbolAt(i), MoveDirection.RIGHT));
        programModel.addNewTransition(programModel.getStateAt(3), programModel.getSymbolAt(3),
                new ActionTriple(programModel.getStateAt(3), programModel.getSymbolAt(3), MoveDirection.NONE));

        //Fill dataModel
        dataModel.write(programModel.getSymbolAt(0));
        dataModel.setState(programModel.getStateAt(0));

        //Init engine
        TuringMachine engine = new TuringMachine(programModel, dataModel);
        engine.startEngine();

        engine.stepForward();
        engine.stepForward();
        engine.stepBackward();
        engine.stepForward();
        engine.stepForward();
    }
}
