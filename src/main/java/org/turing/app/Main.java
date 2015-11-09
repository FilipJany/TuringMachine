package org.turing.app;

import org.turing.app.controllers.ActionsEditController;
import org.turing.app.controllers.ExecutionController;
import org.turing.app.controllers.ImportController;
import org.turing.app.controllers.TapeEditController;
import org.turing.app.model.ApplicationModel;
import org.turing.app.views.ActionsFrameView;
import org.turing.app.views.MainFrameView;

public class Main {

	private static Application createApplication() {
		final ApplicationModel applicationModel = new ApplicationModel();

		final ActionsEditController actionsEditController = new ActionsEditController(applicationModel);
		final ExecutionController executionController = new ExecutionController(applicationModel);
		final TapeEditController tapeEditController = new TapeEditController(applicationModel);
		final ImportController importController = new ImportController(applicationModel);

		final MainFrameView mainFrameView = new MainFrameView(executionController, tapeEditController, importController);
		final ActionsFrameView actionsFrameView = new ActionsFrameView(actionsEditController, importController);

		mainFrameView.init();
		actionsFrameView.init();

		return new Application(mainFrameView, actionsFrameView);
	}
	
	public static void main(String[] args) {
		final Application application = createApplication();
		application.show();
	}

}
