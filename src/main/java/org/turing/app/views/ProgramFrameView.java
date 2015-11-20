package org.turing.app.views;

import org.turing.app.controllers.ExecutionController;
import org.turing.app.controllers.ImportController;
import org.turing.app.controllers.TapeEditController;
import org.turing.app.views.panels.ProgramTablePanel;
import org.turing.support.Logger;

import javax.swing.*;
import java.awt.*;

import static javax.swing.SpringLayout.*;

public class ProgramFrameView {


    private final ExecutionController executionController;
    private final TapeEditController tapeEditController;
    private final ImportController importController;

    private ProgramTablePanel tablePanel;
    private MenuBar menuBar;

    private JFrame frame;
    private SpringLayout layout;

    public ProgramFrameView(ExecutionController executionController, TapeEditController tapeEditController, ImportController importController) {
        this.executionController = executionController;
        this.tapeEditController = tapeEditController;
        this.importController = importController;
    }

    public void init() {
        frame = new JFrame(ApplicationStrings.MAIN_WINDOW_TITLE);

        createFrameComponents();
        setFrameSettings();

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
        tablePanel = new ProgramTablePanel();

        frame.add(tablePanel);

        layout.putConstraint(NORTH, tablePanel, 10, NORTH, frame.getContentPane());
        layout.putConstraint(EAST, tablePanel, -10, EAST, frame.getContentPane());
        layout.putConstraint(SOUTH, tablePanel, -10, SOUTH, frame.getContentPane());
        layout.putConstraint(WEST, tablePanel, 10, WEST, frame.getContentPane());
    }
}
