package org.turing.app.controllers;

import org.turing.app.common.Symbol;
import org.turing.app.exceptions.SymbolException;
import org.turing.app.exceptions.TapeException;
import org.turing.app.model.DataModel;
import org.turing.app.model.ProgramModel;
import org.turing.app.views.panels.TapePanel;
import org.turing.support.Logger;

import java.util.ArrayList;

import static org.turing.app.common.BlankSymbol.BLANK;

public class TapeEditController {

    private final DataModel dataModel;
    private final ProgramModel programModel;
    private TapePanel tapePanel;

    public TapeEditController(DataModel dataModel, ProgramModel programModel) {
        this.dataModel = dataModel;
        this.programModel = programModel;
    }

    public void setTapePanel(TapePanel tapePanel) {
        this.tapePanel = tapePanel;
    }

    public void refreshTapePanel() {
        int visibleSize = tapePanel.getVisibleTapeSize();
        ArrayList<Symbol> view = new ArrayList<>(visibleSize);

        for (int deep = visibleSize / 2 - 1; deep >= 0; deep--) {
            view.add(dataModel.getLeftTape().getAtDeep(deep));
        }
        view.add(dataModel.read());
        for (int deep = 0; deep < visibleSize / 2; deep++) {
            view.add(dataModel.getRightTape().getAtDeep(deep));
        }

        try {
            tapePanel.updateView(view);
        } catch (TapeException e) {
            Logger.error(e);
        }
    }

    public void clearTape() {
        int visibleSize = tapePanel.getVisibleTapeSize();
        ArrayList<Symbol> view = new ArrayList<>(visibleSize);

        for (int i = 0; i < visibleSize; i++)
            view.add(BLANK);

        try {
            tapePanel.updateView(view);
        } catch (TapeException e) {
            Logger.error(e);
        }
    }

    public void moveRight() {
        dataModel.moveRight();
        refreshTapePanel();
    }

    public void moveLeft() {
        dataModel.moveLeft();
        refreshTapePanel();
    }

    public void changeSymbol(String value) {
        Symbol postponedSymbol;
        try {
            postponedSymbol = new Symbol(value);
        } catch (SymbolException ex) {
            try {
                tapePanel.updateView(0, dataModel.read());
            } catch (TapeException e) {
                Logger.error(e);
            }
            return;
        }
        if (programModel.getAvailableSymbols().contains(postponedSymbol)) {
            dataModel.write(postponedSymbol);
        } else {
            try {
                tapePanel.updateView(0, dataModel.read());
            } catch (TapeException e) {
                Logger.error(e);
            }
        }
    }

    public void changeSymbol(int diffToHead, String value) {
        Symbol postponedSymbol;
        try {
            postponedSymbol = new Symbol(value);
        } catch (SymbolException ex) {
            try {
                tapePanel.updateView(diffToHead, dataModel.read(diffToHead));
            } catch (TapeException e) {
                Logger.error(e);
            }
            return;
        }

        if (programModel.getAvailableSymbols().contains(postponedSymbol)) {
            dataModel.write(postponedSymbol, diffToHead);
        } else {
            try {
                tapePanel.updateView(diffToHead, dataModel.read(diffToHead));
            } catch (TapeException e) {
                Logger.error(e);
            }
        }
    }
}
