package org.turing.app.model;

import com.google.common.base.Objects;
import org.turing.app.common.MoveDirection;
import org.turing.app.common.State;
import org.turing.app.common.Symbol;

import static com.google.common.base.MoreObjects.toStringHelper;

public class ActionTriple {

    private final State state;
    private final Symbol symbol;
    private final MoveDirection moveDirection;

    public ActionTriple(State state, Symbol symbol, MoveDirection moveDirection) {
        this.state = state;
        this.symbol = symbol;
        this.moveDirection = moveDirection;
    }

    public State getState() {
        return state;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public MoveDirection getMoveDirection() {
        return moveDirection;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActionTriple that = (ActionTriple) o;
        return Objects.equal(state, that.state) &&
                Objects.equal(symbol, that.symbol) &&
                Objects.equal(moveDirection, that.moveDirection);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(state, symbol, moveDirection);
    }

    @Override
    public String toString() {
        return toStringHelper(this)
                .add("state", state)
                .add("symbol", symbol)
                .add("moveDirection", moveDirection)
                .toString();
    }
}
