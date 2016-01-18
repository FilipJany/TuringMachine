package org.turing.app.controllers;

import org.turing.app.common.MoveDirection;
import org.turing.app.common.State;
import org.turing.app.common.Symbol;
import org.turing.app.importexport.Exporter;
import org.turing.app.importexport.Importer;
import org.turing.app.model.ActionTriple;
import org.turing.app.model.DataModel;
import org.turing.app.model.ProgramModel;
import java.util.List;
import java.util.stream.Collectors;

public class ImportExportController {
    private final DataModel dataModel;
    private final ProgramModel programModel;
    private final Exporter exporter;
    private final Importer importer;
    private final ProgramEditController programEditController;
    private final TapeEditController tapeEditController;

    public ImportExportController(DataModel dataModel, ProgramModel programModel, Exporter exporter, Importer importer,
                                  ProgramEditController programEditController, TapeEditController tapeEditController) {
        this.dataModel = dataModel;
        this.programModel = programModel;
        this.exporter = exporter;
        this.importer = importer;
        this.programEditController = programEditController;
        this.tapeEditController = tapeEditController;
    }

    public void exportProgram(String filename) {
        exporter.exportProgramToFile(filename);
    }

    public void exportTape(String filename) {
        exporter.exportTapeToFile(filename);
    }

    public void importProgram(String filename) {
        programModel.clear();
        try {
            importer.importProgramFromFile(filename);
        } finally {
            addMissingSymbolsAndTransitionsWhileImportingProgram();
            programEditController.fullyRefreshProgramTableAndStatePanel();
            tapeEditController.refreshTapePanel();
        }
    }

    public void importTape(String filename) {
        dataModel.clear();
        try {
            importer.importTapeFromFile(filename);
        } finally {
            addMissingSymbolsAndTransitionsWhileImportingTape();
            programEditController.fullyRefreshProgramTableAndStatePanel();
            tapeEditController.refreshTapePanel();
        }
    }

    private void addMissingSymbolsAndTransitionsWhileImportingTape()
    {
        if(programModel != null && dataModel != null)
        {
            dataModel.getLeftTape().stream().filter(s -> !programModel.getAvailableSymbols().contains(s)).forEach(s -> {
                programModel.addNewSymbol(s);
                for(State st : programModel.getAvailableStates())
                    programModel.addNewTransition(st, s, new ActionTriple(programModel.getStateAt(0), s, MoveDirection.NONE));
            });
            dataModel.getRightTape().stream().filter(s -> !programModel.getAvailableSymbols().contains(s)).forEach(s -> {
                programModel.addNewSymbol(s);
                for(State st : programModel.getAvailableStates())
                    programModel.addNewTransition(st, s, new ActionTriple(programModel.getStateAt(0), s, MoveDirection.NONE));
            });
            if(!programModel.getAvailableSymbols().contains(dataModel.read()))
                programModel.addNewSymbol(dataModel.read());
                for(State st : programModel.getAvailableStates())
                    programModel.addNewTransition(st, dataModel.read(), new ActionTriple(programModel.getStateAt(0), dataModel.read(), MoveDirection.NONE));
        }
    }

    private void addMissingSymbolsAndTransitionsWhileImportingProgram()
    {
        if(programModel != null && dataModel != null)
        {
            //potrzebuje te, ktore sa na tasnie a nie ma ich w programie
            List<Symbol> missing = dataModel.getLeftTape().stream().filter(s -> !programModel.getAvailableSymbols().contains(s)).collect(Collectors.toList());
            missing.addAll(dataModel.getRightTape().stream().filter(s -> !programModel.getAvailableSymbols().contains(s)).collect(Collectors.toList()));
            if(!programModel.getAvailableSymbols().contains(dataModel.read()))
                missing.add(dataModel.read());

            for (Symbol s : missing)
            {
                programModel.addNewSymbol(s);
                for(State st : programModel.getAvailableStates())
                    programModel.addNewTransition(st, s, new ActionTriple(programModel.getStateAt(0), s, MoveDirection.NONE));
            }

        }
    }
}
