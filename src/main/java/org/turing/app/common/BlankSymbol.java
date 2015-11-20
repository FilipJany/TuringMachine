package org.turing.app.common;

public final class BlankSymbol extends Symbol {

    public static final BlankSymbol BLANK = new BlankSymbol();

    private BlankSymbol() {
        super("");
    }

    @Override
    public String toString() {
        return "BlankSymbol";
    }
}
