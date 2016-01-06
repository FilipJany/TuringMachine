package org.turing.app.engine;

/**
 * Created by FiFi on 12/21/2015.
 */
public class TuringEngineConstraints
{
    static String NoProgramLoadedExceptionMessage = "No program data has been loaded - please correct!";
    static String NoSymbolsInProgramFound = "No symbols in program model have been found - please correct!";
    static String NoStatesInProgramFound = "No states in program model have been found - please correct!";
    static String NoTransitionsInProgramFound = "No transitions in program model have been found - please correct!";

    static String NoDataLoadedExceptionMessage = "No data has been loaded - please correct!";
    static String PreviousSymbolStackEmpty = "Previous steps stack is empty!";

    static String ProgramAlreadyLoaded = "Warning! Program is already loaded!";
    static String DataAlreadyLoaded = "Warning! Data is already loaded!";

    static String NoActionForStateAndSymbolFound = "ERROR! No action is defined for given state and symbol!";
}
