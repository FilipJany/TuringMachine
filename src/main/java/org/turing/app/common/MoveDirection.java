package org.turing.app.common;

public enum MoveDirection {
    LEFT("←"), RIGHT("→"), NONE("•");

    private final String symbol;

    MoveDirection(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }
}
