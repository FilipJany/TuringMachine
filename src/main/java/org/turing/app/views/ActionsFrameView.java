package org.turing.app.views;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.turing.app.controllers.ActionsEditController;
import org.turing.app.controllers.ImportController;

import javax.swing.*;

public class ActionsFrameView {

    private static final Logger logger = LoggerFactory.getLogger(ActionsFrameView.class);

    private final ActionsEditController actionsEditController;
    private final ImportController importController;

    private JFrame frame;

    public ActionsFrameView(ActionsEditController actionsEditController, ImportController importController) {
        this.actionsEditController = actionsEditController;
        this.importController = importController;
    }

    public void init() {
        frame = new JFrame(ApplicationStrings.ACTIONS_WINDOW_TITLE);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        logger.info("Actions frame fully initialized");
    }

    public void show() {
        frame.setVisible(true);
    }
}
