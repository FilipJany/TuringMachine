package org.turing.app.common;

import org.json.simple.JSONAware;

public enum MoveDirection implements JSONAware {
    LEFT("←"), RIGHT("→"), NONE("•");

    private final String symbol;

    MoveDirection(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }

    @Override
    public String toJSONString() {
        return "\"" + super.toString() + "\"";
    }
}
