package org.turing.app.engine;

import org.turing.app.common.Symbol;
import org.turing.app.model.ActionTriple;
import org.turing.app.model.DataModel;
import org.turing.app.model.ProgramModel;
import org.turing.app.exceptions.TuringEngineException;

/**
 * Created by FiFi on 12/7/2015.
 */
public class TuringMachine implements ITuringMachine
{
    private ProgramModel programModel;
    private DataModel dataModel;

    private float speedMultiplier = 1.0f;
    private boolean shouldWorkStepByStep = false;

    private ActionTriple currenttriple, nextTriple;
    private Symbol currentSymbol;

    public TuringMachine()
    {
        if(programModel == null)
            programModel = new ProgramModel();
        if(dataModel == null)
            dataModel = new DataModel();
        try
        {
            loadDataForFirstTime();
        }
        catch(Exception e)
        {
            System.err.println(e.getMessage());
        }
    }

    public TuringMachine(ProgramModel programModel, DataModel dataModel)
    {
        this.programModel = programModel;
        this.dataModel = dataModel;
        try
        {
            loadDataForFirstTime();
        }
        catch(Exception e)
        {
            System.err.println(e.getMessage());
        }
    }

    private void loadDataForFirstTime() throws Exception
    {
        if(programModel == null)
            throw new TuringEngineException("Program Model is broken, please check if all data has been loaded correctly.");
        if(dataModel == null)
            throw new TuringEngineException("Data Model is broken, please check if all data has been loaded correctly.");
        currentSymbol = dataModel.read();
    }

    @Override
    public void stepForward()
    {
        try
        {
            wait((long) (TuringEngineConstraints.timeoutValue * speedMultiplier));
        }
        catch(Exception e)
        {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void stepBackward()
    {
        try
        {
            wait((long) (TuringEngineConstraints.timeoutValue * speedMultiplier));
        }
        catch(Exception e)
        {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void updateSpeed(float newMultiplier)
    {
        this.speedMultiplier = newMultiplier;
        shouldWorkStepByStep =  speedMultiplier == 0.0f;
    }


}
