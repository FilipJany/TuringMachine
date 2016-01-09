package org.turing.app.views;

import org.turing.app.controllers.ImportController;
import org.turing.app.controllers.ProgramEditController;
import org.turing.app.views.constants.ApplicationConstraints;
import org.turing.app.views.constants.ApplicationStrings;
import org.turing.app.views.panels.ProgramTablePanel;
import org.turing.support.Logger;

import javax.swing.*;
import java.awt.*;

import static javax.swing.SpringLayout.*;

public class ProgramFrameView {

    private final ImportController importController;
    private final ProgramEditController programEditController;

    private ProgramTablePanel tablePanel;
    private MenuBar menuBar;

    private JFrame frame;
    private SpringLayout layout;

    public ProgramFrameView(ImportController importController, ProgramEditController programEditController) {
        this.importController = importController;
        this.programEditController = programEditController;
    }

    public void init() {
        frame = new JFrame(ApplicationStrings.MAIN_WINDOW_TITLE);

        createFrameComponents();
        setFrameSettings();
        updateControllers();

        Logger.log("Program frame initialized.");
    }

    public void show() {
        frame.setVisible(true);
    }

    private void createFrameComponents()
    {
        layout = new SpringLayout();

        createAndInitTablePanel();
        createAndInitMenuBar();
    }

    private void createAndInitMenuBar()
    {
        menuBar = new MenuBar();
        try
        {
            frame.setJMenuBar(menuBar.getMenuBar());
        }
        catch (MenuBarException e)
        {
            e.getMessage();
        }
    }

    private void setFrameSettings()
    {
        frame.setLocation(ApplicationConstraints.programFrameStartLocationX, ApplicationConstraints.programFrameStartLocationY);
        frame.setSize(ApplicationConstraints.programFrameMinimalWidth, ApplicationConstraints.programFrameMinimalHeight);
        frame.setMinimumSize(new Dimension(ApplicationConstraints.programFrameMinimalWidth, ApplicationConstraints.programFrameMinimalHeight));
        frame.setLayout(layout);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void createAndInitTablePanel()
    {
        tablePanel = new ProgramTablePanel(programEditController);

        frame.add(tablePanel);

        layout.putConstraint(NORTH, tablePanel, 10, NORTH, frame.getContentPane());
        layout.putConstraint(EAST, tablePanel, -10, EAST, frame.getContentPane());
        layout.putConstraint(SOUTH, tablePanel, -10, SOUTH, frame.getContentPane());
        layout.putConstraint(WEST, tablePanel, 10, WEST, frame.getContentPane());
    }

    private void updateControllers() {
        programEditController.setProgramFrame(frame);
    }
}
