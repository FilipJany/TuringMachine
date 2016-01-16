package org.turing.app.controllers;

import org.turing.app.importexport.Exporter;
import org.turing.app.importexport.Importer;
import org.turing.app.model.DataModel;
import org.turing.app.model.ProgramModel;

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
            programEditController.refreshProgramTableAndStatePanel();
            tapeEditController.refreshTapePanel();
        }
    }

    public void importTape(String filename) {
        dataModel.clear();
        try {
            importer.importTapeFromFile(filename);
        } finally {
            programEditController.refreshProgramTableAndStatePanel();
            tapeEditController.refreshTapePanel();
        }
    }
}
