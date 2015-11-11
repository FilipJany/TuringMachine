package org.turing.app.views;

import org.turing.app.controllers.ImportController;
import org.turing.app.controllers.ProgramEditController;
import org.turing.support.Logger;

import javax.swing.*;

public class ProgramFrameView {

    private final ProgramEditController programEditController;
    private final ImportController importController;

    private JFrame frame;

    public ProgramFrameView(ProgramEditController programEditController, ImportController importController) {
        this.programEditController = programEditController;
        this.importController = importController;
    }

    public void init() {
        frame = new JFrame(ApplicationStrings.ACTIONS_WINDOW_TITLE);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Logger.log("Actions frame initialized.");
    }

    public void show() {
        frame.setVisible(true);
    }
}
