package org.turing.app.views;

import org.turing.app.controllers.ExecutionController;
import org.turing.app.controllers.ImportController;
import org.turing.app.controllers.TapeEditController;
import org.turing.support.Logger;

import javax.swing.*;

public class MainFrameView {


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
        Logger.log("Actions frame initialized.");
    }

    public void show() {
        frame.setVisible(true);
    }
}
