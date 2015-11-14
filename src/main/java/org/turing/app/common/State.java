package org.turing.app.common;

import com.google.common.base.Objects;

import static com.google.common.base.MoreObjects.toStringHelper;

public class State {

    private final String name;
    private final boolean isFinal;

    public State(String name, boolean isFinal) {
        this.name = name;
        this.isFinal = isFinal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        return Objects.equal(isFinal, state.isFinal) &&
                Objects.equal(name, state.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, isFinal);
    }

    @Override
    public String toString() {
        return toStringHelper(this)
                .add("name", name)
                .add("isFinal", isFinal)
                .toString();
    }
}
