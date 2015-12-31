package org.turing.app.views.constants;

import static org.turing.app.common.BlankSymbol.BLANK;

public interface ApplicationStrings {
    String MAIN_WINDOW_TITLE = "Turing Machine - main window";
    String ACTIONS_WINDOW_TITLE = "Turing Machine - actions window";
    String NEW_STATE_TEXT = "Name of new state:";
    String NEW_STATE_EXISTS_EXCEPTION = "Such state already exists.";
    String NEW_SYMBOL_TEXT = "Name of new symbol:";
    String NEW_SYMBOL_EXCEPTION = "Improper value of new symbol.";
    String NEW_SYMBOL_BLANK_EXCEPTION = "Blank symbol (" + BLANK.visibleRep() + ") is already defined.";
    String NEW_SYMBOL_EXISTS_EXCEPTION = "Such symbol already exists.";

}
