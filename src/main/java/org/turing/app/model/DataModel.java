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

    public Symbol read(int diffToHead) {
        if (diffToHead > 0)
            return rightTape.getAtDeep(diffToHead - 1);
        else if (diffToHead < 0)
            return leftTape.getAtDeep(-diffToHead - 1);
        else
            return headSymbol;
    }

    public void write(Symbol symbol) {
        headSymbol = symbol;
    }

    public void write(Symbol symbol, int diffToHead) {
        adjustTapeModel(diffToHead);
        write(symbol);
        adjustTapeModel(-diffToHead);
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public InfiniteSymbolStack getLeftTape() {
        return leftTape;
    }

    public InfiniteSymbolStack getRightTape() {
        return rightTape;
    }

    private void adjustTapeModel(int diffToHead) {
        if (diffToHead > 0)
            while (diffToHead-- > 0)
                moveRight();
        else if (diffToHead < 0)
            while (diffToHead++ < 0)
                moveLeft();
    }
}
