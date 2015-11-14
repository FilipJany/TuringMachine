package org.turing.app.common;

public class HaltState extends State {

    public static final HaltState HALT = new HaltState();

    private HaltState() {
        super("HALT", true);
    }
}
