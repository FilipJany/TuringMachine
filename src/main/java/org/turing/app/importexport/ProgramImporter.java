package org.turing.app.importexport;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.turing.app.common.MoveDirection;
import org.turing.app.common.State;
import org.turing.app.common.Symbol;
import org.turing.app.exceptions.ImportExportException;
import org.turing.app.exceptions.SymbolException;
import org.turing.app.model.ActionTriple;
import org.turing.app.model.ProgramModel;

import static org.turing.app.importexport.ImportExportUtils.*;

public class ProgramImporter {

    private final ProgramModel programModel;

    public ProgramImporter(ProgramModel programModel) {
        this.programModel = programModel;
    }

    public void readIntoModel(JSONObject programData) throws SymbolException {
        readSymbols(programData);
        readStates(programData);
        readTransitions(programData);
    }

    private void readTransitions(JSONObject programData) throws SymbolException {
        JSONObject transitions = get(programData, ImportExportConstants.TRANSITIONS_PARAM, JSONObject.class);
        for (Object stateAsObject : transitions.keySet()) {
            State state = getStateFromObject(stateAsObject);
            JSONObject symbolsToActions = get(transitions, stateAsObject, JSONObject.class);
            for (Object symbolAsObject : symbolsToActions.keySet()) {
                Symbol symbol = getSymbolFromObject(symbolAsObject);
                ActionTriple actionTriple = getActionTripleFromJsonArray(get(symbolsToActions, symbolAsObject, JSONArray.class));
                programModel.addNewTransition(state, symbol, actionTriple);
            }
        }
    }

    private ActionTriple getActionTripleFromJsonArray(JSONArray jsonArray) throws SymbolException {
        if(jsonArray.size() != 3) {
            throw new ImportExportException("action triple should be a triple. Makes sense, right?");
        }
        State state = State.fromJsonString(getAsString(jsonArray.get(0)));
        Symbol symbol = Symbol.fromJsonString(getAsString(jsonArray.get(1)));
        MoveDirection moveDirection = MoveDirection.valueOf(getAsString(jsonArray.get(2)));
        return new ActionTriple(state, symbol, moveDirection);
    }

    private void readStates(JSONObject programData) {
        JSONArray states = get(programData, ImportExportConstants.STATES_PARAM, JSONArray.class);
        for (Object stateAsObject : states) {
            State state = getStateFromObject(stateAsObject);
            programModel.addNewState(state);
        }
    }

    private void readSymbols(JSONObject programData) throws SymbolException {
        JSONArray symbols = get(programData, ImportExportConstants.SYMBOLS_PARAM, JSONArray.class);
        for (Object symbolAsObject : symbols) {
            Symbol symbol = getSymbolFromObject(symbolAsObject);
            programModel.addNewSymbol(symbol);
        }
    }

}
