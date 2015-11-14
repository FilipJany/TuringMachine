package org.turing.app.controllers;

import org.turing.app.model.DataModel;
import org.turing.app.model.ProgramModel;

public class ImportController {
    private final DataModel dataModel;
    private final ProgramModel programModel;

    public ImportController(DataModel dataModel, ProgramModel programModel) {
        this.dataModel = dataModel;
        this.programModel = programModel;
    }
}
