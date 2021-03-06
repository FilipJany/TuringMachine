package org.turing.app.model;

import com.google.common.collect.*;
import org.turing.app.common.MoveDirection;
import org.turing.app.common.State;
import org.turing.app.common.Symbol;
import org.turing.support.Logger;

import java.util.List;

import static com.google.common.collect.Sets.newHashSet;
import static java.util.Objects.isNull;
import static org.turing.app.common.BlankSymbol.BLANK;
import static org.turing.app.common.HaltState.HALT;

public class ProgramModel {

    private Table<State, Symbol, ActionTriple> transitionTable;
    private List<State> legalStates;
    private List<Symbol> legalSymbols;

    public ProgramModel() {
        initialize();
    }

    private void initialize() {
        transitionTable = HashBasedTable.create();
        legalStates = Lists.newArrayList(HALT);
        legalSymbols = Lists.newArrayList(BLANK);
    }

    public void addNewState(State state) {
        if(!legalStates.contains(state)) {
            legalStates.add(state);
        }
    }

    public void addNewSymbol(Symbol symbol) {
        if(!legalSymbols.contains(symbol)) {
            legalSymbols.add(symbol);
        }
    }

    public void addNewTransition(State state, Symbol symbol, ActionTriple actionTriple) {
        verifyLegality(state);
        verifyLegality(symbol);
        verifyLegality(actionTriple.getState());
        verifyLegality(actionTriple.getSymbol());

        transitionTable.put(state, symbol, actionTriple);

//        Logger.warning("Transition Table:");
//        for (State st : legalStates)
//            for (Symbol sy: legalSymbols)
//                Logger.warning("[" + st.getName() + "," + sy.getValue() + "] " + transitionTable.get(st, sy));
    }

    public State getStateAt(int index) {
        return legalStates.get(index);
    }

    public Symbol getSymbolAt(int index) {
        return legalSymbols.get(index);
    }

    public void deleteState(State state) {
        if (state.equals(HALT)) {
            throw new RuntimeException("Nope. Cannot remove HALT state");
        }

        removeTransitionsByState(state);
        legalStates.remove(state);
    }

    public void deleteSymbol(Symbol symbol) {
        if (symbol.equals(BLANK)) {
            throw new RuntimeException("Nope. Cannot remove BLANK");
        }

        removeTransitionsBySymbol(symbol);
        legalSymbols.remove(symbol);
    }

    public void deleteTransition(State state, Symbol symbol) {
        transitionTable.remove(state, symbol);
    }

    public void renameSymbol() {
        // TODO: Discuss
    }

    public void renameState() {
        //TODO: Discuss
    }

    public void clear() {
        initialize();
    }

    public List<Symbol> getAvailableSymbols() {
        return ImmutableList.copyOf(legalSymbols);
    }

    public List<State> getAvailableStates() {
        return ImmutableList.copyOf(legalStates);
    }

    public Table<State, Symbol, ActionTriple> getTransitionTable() {
        return ImmutableTable.copyOf(transitionTable);
    }

    public ActionTriple getActionForStateAndSymbol(State state, Symbol symbol) {
        ActionTriple action = transitionTable.get(state, symbol);
        return isNull(action) ? getDefaultAction(symbol) : action;
    }

    private ActionTriple getDefaultAction(Symbol symbol) {
        return new ActionTriple(HALT, symbol, MoveDirection.NONE);
    }

    private void removeTransitionsByState(State state) {
        if (transitionTable.containsRow(state)) {
            for (Symbol symbol : newHashSet(transitionTable.row(state).keySet())) {
                transitionTable.remove(state, symbol);
            }
        }
    }

    private void removeTransitionsBySymbol(Symbol symbol) {
        if (transitionTable.containsColumn(symbol)) {
            for (State state : newHashSet(transitionTable.column(symbol).keySet())) {
                transitionTable.remove(state, symbol);
            }
        }
    }

    private void verifyLegality(State state) {
        if(!legalStates.contains(state)) {
            throw new RuntimeException("Oops. state " + state + " is illegal");
        }
    }

    private void verifyLegality(Symbol symbol) {
        if(!legalSymbols.contains(symbol)) {
            throw new RuntimeException("Oops. symbol " + symbol + " is illegal");
        }
    }
}
