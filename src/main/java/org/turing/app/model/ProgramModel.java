package org.turing.app.model;

import com.google.common.collect.*;
import org.turing.app.common.MoveDirection;
import org.turing.app.common.State;
import org.turing.app.common.Symbol;

import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;
import static java.util.Objects.isNull;
import static org.turing.app.common.BlankSymbol.BLANK;
import static org.turing.app.common.HaltState.HALT;

public class ProgramModel {

    private Table<State, Symbol, ActionTriple> transitionTable;
    private Set<State> legalStates;
    private Set<Symbol> legalSymbols;

    public ProgramModel() {
        initialize();
    }

    private void initialize() {
        transitionTable = HashBasedTable.create();
        legalStates = Sets.newHashSet(HALT);
        legalSymbols = Sets.newHashSet(BLANK);
    }

    public void addNewState(State state) {
        legalStates.add(state);
    }

    public void addNewSymbol(Symbol symbol) {
        legalSymbols.add(symbol);
    }

    public void addNewTransition(State state, Symbol symbol, ActionTriple actionTriple) {
        verifyLegality(state);
        verifyLegality(symbol);
        verifyLegality(actionTriple.getState());
        verifyLegality(actionTriple.getSymbol());

        transitionTable.put(state, symbol, actionTriple);
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

    public void clear() {
        initialize();
    }

    public Set<Symbol> getAvailableSymbols() {
        return ImmutableSet.copyOf(legalSymbols);
    }

    public Set<State> getAvailableStates() {
        return ImmutableSet.copyOf(legalStates);
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
