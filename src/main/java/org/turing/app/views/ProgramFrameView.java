package org.turing.app.views;

import org.turing.app.controllers.ExecutionController;
import org.turing.app.controllers.ImportExportController;
import org.turing.app.controllers.ProgramEditController;
import org.turing.app.controllers.TapeEditController;
import org.turing.app.model.DataModel;
import org.turing.app.model.ProgramModel;
import org.turing.app.views.constants.ApplicationConstraints;
import org.turing.app.views.constants.ApplicationStrings;
import org.turing.app.views.panels.ProgramTablePanel;
import org.turing.support.Logger;
import org.turing.support.LoggerGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.function.Consumer;

import static javax.swing.SpringLayout.*;

public class ProgramFrameView {

    private final ExecutionController execController;
    private final ImportExportController importExportController;
    private final DataModel dataModel;
    private final ProgramModel programModel;
    private final ProgramEditController programEditController;
    private final TapeEditController tapeEditController;

    private ProgramTablePanel tablePanel;
    private MenuBar menuBar;

    private JFrame frame;
    private SpringLayout layout;

    public ProgramFrameView(ImportExportController importExportController, ProgramEditController programEditController, TapeEditController tapeEditController, ExecutionController execController, DataModel dataModel, ProgramModel programModel) {
        this.execController = execController;
        this.importExportController = importExportController;
        this.programEditController = programEditController;
        this.tapeEditController = tapeEditController;
        this.dataModel = dataModel;
        this.programModel = programModel;
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
        menuBar = new MenuBar(importExportController, programEditController, tapeEditController, execController, dataModel, programModel, frame);
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
        frame.addWindowListener(quitListener);
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
        programEditController.setProgramTable(tablePanel.getTable());
    }

    WindowListener quitListener = new WindowAdapter()
    {
        @Override
        public void windowClosing(WindowEvent e)
        {
            int confirm = LoggerGUI.showQuestionDialogYesNo(frame, "Do you want to save data before exit?", "Question");
            if(confirm == 0)
            {
                String programFileName = LoggerGUI.showInputDialog(frame, "Program file name:", "Program Save Dialog");
                String tapeFileName = LoggerGUI.showInputDialog(frame, "Data file name:", "Data Save Dialog");
                if(programFileName != null)
                    importExportController.exportProgram(programFileName);
                if(tapeFileName != null)
                    importExportController.exportTape(tapeFileName);
            }
            System.exit(0);
        }
    };
}
