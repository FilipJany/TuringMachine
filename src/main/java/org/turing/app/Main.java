package org.turing.app;

import org.turing.app.controllers.ExecutionController;
import org.turing.app.controllers.ImportController;
import org.turing.app.controllers.ProgramEditController;
import org.turing.app.controllers.TapeEditController;
import org.turing.app.model.ApplicationModel;
import org.turing.app.views.MainFrameView;
import org.turing.app.views.ProgramFrameView;
import org.turing.support.Logger;

import java.io.IOException;

public class Main {

	private static Application createApplication() {
		final ApplicationModel applicationModel = new ApplicationModel();

        final ProgramEditController programEditController = new ProgramEditController(applicationModel);
        final ExecutionController executionController = new ExecutionController(applicationModel);
		final TapeEditController tapeEditController = new TapeEditController(applicationModel);
		final ImportController importController = new ImportController(applicationModel);

		final MainFrameView mainFrameView = new MainFrameView(executionController, tapeEditController, importController);
        final ProgramFrameView programFrameView = new ProgramFrameView(programEditController, importController);

		mainFrameView.init();
        programFrameView.init();

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
		application.show();
	}

}
