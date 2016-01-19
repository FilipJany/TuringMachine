package org.turing.app.views;

import org.turing.app.controllers.ExecutionController;
import org.turing.app.controllers.ImportExportController;
import org.turing.app.controllers.ProgramEditController;
import org.turing.app.controllers.TapeEditController;
import org.turing.app.model.DataModel;
import org.turing.app.model.ProgramModel;
import org.turing.app.views.constants.ApplicationConstraints;
import org.turing.app.views.constants.ApplicationStrings;
import org.turing.app.views.panels.ControlPanel;
import org.turing.app.views.panels.SliderPanel;
import org.turing.app.views.panels.StatePanel;
import org.turing.app.views.panels.TapePanel;
import org.turing.support.Logger;
import org.turing.support.LoggerGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Date;
import java.util.function.Consumer;

import static javax.swing.SpringLayout.*;

public class MainFrameView {

    private final ExecutionController executionController;
    private final TapeEditController tapeEditController;
    private final ImportExportController importExportController;
    private final ProgramEditController programEditController;
    private final DataModel dataModel;
    private final ProgramModel programModel;

    private ControlPanel controlPanel;
    private SliderPanel sliderPanel;
    private StatePanel statePanel;
    private TapePanel tapePanel;
    private MenuBar menuBar;

    private JFrame frame;
    private SpringLayout layout;


    public MainFrameView(ExecutionController executionController, TapeEditController tapeEditController, ImportExportController importExportController, ProgramEditController programEditController, DataModel dataModel, ProgramModel programModel) {
        this.executionController = executionController;
        this.tapeEditController = tapeEditController;
        this.importExportController = importExportController;
        this.programEditController = programEditController;
        this.dataModel = dataModel;
        this.programModel = programModel;
    }

    public void init() {
        frame = new JFrame(ApplicationStrings.ACTIONS_WINDOW_TITLE);

        createFrameComponents();
        setFrameSettings();
        updateControllers();

        Logger.log("Data frame initialized.");
    }

    public void show() {
        frame.setVisible(true);
    }

    private void setFrameSettings()
    {
        frame.setLocation(ApplicationConstraints.mainFrameStartLocationX, ApplicationConstraints.mainFrameStartLocationY);
        frame.setSize(ApplicationConstraints.mainFrameMinimalWidth, ApplicationConstraints.mainFrameMinimalHeight);
        frame.setMinimumSize(new Dimension(ApplicationConstraints.mainFrameMinimalWidth, ApplicationConstraints.mainFrameMinimalHeight));
        frame.setLayout(layout);
        frame.addWindowListener(quitListener);
    }

    private void createFrameComponents()
    {
        layout = new SpringLayout();

        createAndInitControlPanel();
        createAndInitScrollPane();
        createAndInitStatePanel();
        createAndInitTapePanel();
        createAndInitMenuBar();
    }

    private void createAndInitMenuBar()
    {
        menuBar = new MenuBar(importExportController, programEditController, tapeEditController, executionController, dataModel, programModel, frame);
        try
        {
            frame.setJMenuBar(menuBar.getMenuBar());
        }
        catch (MenuBarException e)
        {
            e.getMessage();
        }
    }

    private void createAndInitControlPanel()
    {
        try
        {
            controlPanel = new ControlPanel(executionController);
            controlPanel.setPreferredSize(new Dimension(ApplicationConstraints.minimalControlPanelWidth, ApplicationConstraints.minimalControlPanelHeight));

            frame.add(controlPanel);

            layout.putConstraint(EAST, controlPanel, -10, EAST, frame.getContentPane());
            layout.putConstraint(NORTH, controlPanel, 10, NORTH, frame.getContentPane());
        }
        catch (Exception e)
        {
            e.getMessage();
        }
    }

    private void createAndInitScrollPane()
    {
        try
        {
            sliderPanel = new SliderPanel(executionController);
            sliderPanel.setPreferredSize(new Dimension(ApplicationConstraints.sliderPanelMinimalWidth, ApplicationConstraints.sliderPanelMinimalHeight));

            frame.add(sliderPanel);

            layout.putConstraint(SOUTH, sliderPanel, -10, SOUTH, frame.getContentPane());
            layout.putConstraint(WEST, sliderPanel, 10, WEST, frame.getContentPane());
            layout.putConstraint(EAST, sliderPanel, -10, EAST, frame.getContentPane());
        }
        catch(Exception e)
        {
            e.getMessage();
        }
    }

    private void createAndInitStatePanel()
    {
        statePanel = new StatePanel(executionController);

        frame.add(statePanel);

        layout.putConstraint(NORTH, statePanel, 10, NORTH, frame.getContentPane());
        layout.putConstraint(WEST, statePanel, 10, WEST, frame.getContentPane());
        layout.putConstraint(EAST, statePanel, -10, EAST, frame.getContentPane());
    }

    private void createAndInitTapePanel()
    {
        tapePanel = new TapePanel(tapeEditController);

        frame.add(tapePanel);

        layout.putConstraint(NORTH, tapePanel, 10, SOUTH, controlPanel);
        layout.putConstraint(SOUTH, tapePanel, 10, NORTH, sliderPanel);
        layout.putConstraint(WEST, tapePanel, 10, WEST, frame.getContentPane());
        layout.putConstraint(EAST, tapePanel, -10, EAST, frame.getContentPane());
    }

    private void updateControllers() {
        tapeEditController.setTapePanel(tapePanel);
        tapeEditController.refreshTapePanel();

        programEditController.setStatePanel(statePanel);
        programEditController.setTapePanel(tapePanel);

        executionController.setControlPanel(controlPanel);
        executionController.setTapePanel(tapePanel);
        executionController.setStatePanel(statePanel);
        executionController.setSliderPanel(sliderPanel);
        executionController.refreshControlPanel();
        executionController.refreshStatePanel();
        executionController.refreshSliderPanel();
    }

    WindowListener quitListener = new WindowAdapter() {
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
