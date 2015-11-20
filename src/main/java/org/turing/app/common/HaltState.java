package org.turing.app.common;

public final class HaltState extends State {

    public static final HaltState HALT = new HaltState();

    private HaltState() {
        super("HALT", true);
    }
}
