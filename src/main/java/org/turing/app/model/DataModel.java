package org.turing.app.model;

import org.turing.app.common.InfiniteSymbolStack;
import org.turing.app.common.State;
import org.turing.app.common.Symbol;

import java.util.List;

import static org.turing.app.common.BlankSymbol.BLANK;
import static org.turing.app.common.HaltState.HALT;

public class DataModel {

    private InfiniteSymbolStack leftTape;
    private InfiniteSymbolStack rightTape;
    private Symbol headSymbol;
    private State state;

    public DataModel() {
        initialize();
    }

    public void initialize() {
        leftTape = new InfiniteSymbolStack();
        rightTape = new InfiniteSymbolStack();
        headSymbol = BLANK;
        state = HALT;
    }

    public void initialize(List<Symbol> initialTape) {
        initialize();

        for (int i = initialTape.size() - 1; i > 0; i--)
            rightTape.push(initialTape.get(i));
        headSymbol = initialTape.get(0);
    }

    public void clear() {
        initialize();
    }

    public Symbol moveRight() {
        leftTape.push(headSymbol);
        headSymbol = rightTape.pop();

        return headSymbol;
    }

    public Symbol moveLeft() {
        rightTape.push(headSymbol);
        headSymbol = leftTape.pop();

        return headSymbol;
    }

    public Symbol read() {
        return headSymbol;
    }

    public void write(Symbol symbol) {
        headSymbol = symbol;
    }

    public void writeAt(Symbol symbol, int index) {
        if (index == 0)
            write(symbol);
        else if (index > 0)
            rightTape.replace(symbol, index - 1);
        else
            leftTape.replace(symbol, index - 1);
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

}
