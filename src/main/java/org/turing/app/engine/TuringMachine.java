package org.turing.app.engine;

import org.turing.app.common.MoveDirection;
import org.turing.app.common.State;
import org.turing.app.common.Symbol;
import org.turing.app.model.ActionTriple;
import org.turing.app.model.DataModel;
import org.turing.app.model.ProgramModel;
import org.turing.support.Logger;

import java.util.Stack;

/**
 * Created by FiFi on 12/7/2015.
 */
public class TuringMachine implements ITuringMachine
{
    private ProgramModel programModel;
    private DataModel dataModel;

    private Stack<ActionTriple> previousActions;//actions that have already been completed
    private ActionTriple previousAction; //action that has already been completed

    private boolean isOnline = false;

    //Constructors Section (Engine needs to be started manually!!)
    public TuringMachine()
    {
        previousActions = new Stack<>();
    }


    public TuringMachine(ProgramModel programModel, DataModel dataModel)
    {
        previousActions = new Stack<>();
        this.programModel = programModel;
        this.dataModel = dataModel;
    }

    //Data & Program Model Setters (if they are added later)
    public void setProgramModel(ProgramModel programModel)
    {
        if(programModel == null)
            this.programModel = programModel;
        else
            Logger.log(TuringEngineConstraints.ProgramAlreadyLoaded);
    }

    public void setDataModel(DataModel dataModel)
    {
        if(dataModel == null)
            this.dataModel = dataModel;
        else
            Logger.log(TuringEngineConstraints.DataAlreadyLoaded);
    }

    //Check Section - checks if everything is prepared to run engine
    private void checkIfProgramIsLoaded() throws EngineException
    {
        if(programModel == null)
            throw new EngineException(TuringEngineConstraints.NoProgramLoadedExceptionMessage);
        if(programModel.getAvailableStates().size() < 1)
            throw new EngineException(TuringEngineConstraints.NoStatesInProgramFound);
        if(programModel.getAvailableSymbols().size() < 1)
            throw new EngineException(TuringEngineConstraints.NoSymbolsInProgramFound);
        if(programModel.getTransitionTable().size() < 1)
            throw new EngineException(TuringEngineConstraints.NoTransitionsInProgramFound);
    }

    private void checkIfDataHasBeenLoaded() throws EngineException
    {
        if(dataModel == null)
            throw new EngineException(TuringEngineConstraints.NoDataLoadedExceptionMessage);
    }

    //"Work" Section - performs given work (steps forward || backwards)
    //Start Engine work
    public void startEngine()
    {
        if(!isOnline)
        {
            try
            {
                checkIfProgramIsLoaded();
                checkIfDataHasBeenLoaded();
            }
            catch(Exception e)
            {
                Logger.log(e.getMessage());
            }
            //If everything is okay, load starting data to engine
            Symbol newSymbol = dataModel.read();
            State newState = dataModel.getState();
            previousAction = programModel.getActionForStateAndSymbol(newState, newSymbol);
        }
        isOnline = true;
    }

    //Stop engine and clear the execution stack
    public void stopEngine()
    {
        if(isOnline)
        {
            previousActions.clear();
            isOnline = false;
        }
    }

    //Restarts Engine (Clears the execution stack too)
    public void restartEngine()
    {
        stopEngine();
        startEngine();
    }

    @Override
    public void stepForward()
    {
        //Change direction of move for 'back' action
        ActionTriple returnAction;
        if(previousAction.getMoveDirection() == MoveDirection.LEFT)
            returnAction = new ActionTriple(previousAction.getState(), previousAction.getSymbol(), MoveDirection.RIGHT);
        else if(previousAction.getMoveDirection() == MoveDirection.RIGHT)
            returnAction = new ActionTriple(previousAction.getState(), previousAction.getSymbol(), MoveDirection.LEFT);
        else
            returnAction = new ActionTriple(previousAction.getState(), previousAction.getSymbol(), MoveDirection.NONE);
        previousActions.push(returnAction);

        //Continue with forward step
        makeStep(previousAction.getMoveDirection());

        try
        {
            getNextTripleAndReplaceOldOne();
        }
        catch (Exception e)
        {
            Logger.log(e.getMessage());
        }
    }

    @Override
    public void stepBackward()
    {
        if(previousActions.size() > 0)
        {
            previousAction = previousActions.pop();
            //Set back the state
            dataModel.setState(previousAction.getState());
            //Set proper symbol
            makeStep(previousAction.getMoveDirection());
        }
        else
            Logger.log(TuringEngineConstraints.PreviousSymbolStackEmpty);
    }

    private void makeStep(MoveDirection direction)
    {
        switch (direction)
        {
            case LEFT:
                dataModel.moveLeft();
                break;
            case RIGHT:
                dataModel.moveRight();
                break;
            default:
                //do nothing, just stay as you were standing (NONE move)
                break;
        }
    }

    private void getNextTripleAndReplaceOldOne() throws EngineException
    {
        Symbol newSymbol = dataModel.read();
        State newState = dataModel.getState();
        ActionTriple newActionTriple = programModel.getActionForStateAndSymbol(newState, newSymbol);
        if(newActionTriple == null)
            throw new EngineException(TuringEngineConstraints.NoActionForStateAndSymbolFound);
        previousAction = newActionTriple;
    }

    //Rest of interface methods
    @Override
    public ActionTriple getCurrentActionTriple()
    {
        return previousAction;
    }

    @Override
    public ActionTriple getLatestCompletedActionTriple()
    {
        return previousActions.get(0);
    }

    @Override
    public Stack<ActionTriple> getExecutionStack()
    {
        return previousActions;
    }
}

class EngineException extends Exception
{
    public EngineException(String message)
    {
        super(message);
    }
}