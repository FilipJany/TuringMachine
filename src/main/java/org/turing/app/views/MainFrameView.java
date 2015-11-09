package org.turing.app.views;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.turing.app.controllers.ExecutionController;
import org.turing.app.controllers.ImportController;
import org.turing.app.controllers.TapeEditController;

import javax.swing.*;

public class MainFrameView {

    private static final Logger logger = LoggerFactory.getLogger(MainFrameView.class);

    private final ExecutionController executionController;
    private final TapeEditController tapeEditController;
    private final ImportController importController;

    private JFrame frame;

    public MainFrameView(ExecutionController executionController, TapeEditController tapeEditController, ImportController importController) {
        this.executionController = executionController;
        this.tapeEditController = tapeEditController;
        this.importController = importController;
    }

    public void init() {
        frame = new JFrame(ApplicationStrings.MAIN_WINDOW_TITLE);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        logger.info("Actions frame fully initialized");
    }

    public void show() {
        frame.setVisible(true);
    }
}
