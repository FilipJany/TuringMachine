package org.turing.app;

import org.turing.app.controllers.ExecutionController;
import org.turing.app.controllers.ImportExportController;
import org.turing.app.controllers.ProgramEditController;
import org.turing.app.controllers.TapeEditController;
import org.turing.app.engine.TuringMachine;
import org.turing.app.importexport.*;
import org.turing.app.model.DataModel;
import org.turing.app.model.ExecutionModel;
import org.turing.app.model.ProgramModel;
import org.turing.app.views.MainFrameView;
import org.turing.app.views.ProgramFrameView;
import org.turing.support.Logger;

import javax.swing.*;
import java.io.IOException;

public class Main {

    private static Application createApplication() {
        final DataModel dataModel = new DataModel();
        final ProgramModel programModel = new ProgramModel();
        final ExecutionModel executionModel = new ExecutionModel();

        final TuringMachine engine = new TuringMachine(programModel, dataModel);

        final ProgramEditController programEditController = new ProgramEditController(programModel, dataModel, engine);
        final ExecutionController executionController = new ExecutionController(dataModel, programModel, executionModel, engine);
        final TapeEditController tapeEditController = new TapeEditController(dataModel, programModel, executionController);
        final ProgramExporter programExporter = new ProgramExporter(programModel);
        final TapeExporter tapeExporter = new TapeExporter(dataModel);
        final Exporter exporter = new Exporter(programExporter, tapeExporter);
        final ProgramImporter programImporter = new ProgramImporter(programModel);
        final TapeImporter tapeImporter = new TapeImporter(dataModel);
        final Importer importer = new Importer(programImporter, tapeImporter);
        final ImportExportController importExportController = new ImportExportController(dataModel, programModel, exporter, importer, programEditController, tapeEditController);

        final ProgramFrameView mainFrameView = new ProgramFrameView(importExportController, programEditController, tapeEditController, executionController, dataModel, programModel);
        final MainFrameView programFrameView = new MainFrameView(executionController, tapeEditController, importExportController, programEditController, dataModel, programModel);

        Application application = new Application(mainFrameView, programFrameView);

        try {
            application.loadApplicationData();
        } catch (IOException e) {
            Logger.error(e);
            System.exit(1);
        }

        return application;
    }

    public static void main(String[] args) {
        final Application application = createApplication();
        SwingUtilities.invokeLater(application::initAndShow);
    }

}
