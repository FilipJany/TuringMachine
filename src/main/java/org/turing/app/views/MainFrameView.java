package org.turing.app.views;

import org.turing.app.controllers.ExecutionController;
import org.turing.app.controllers.ImportController;
import org.turing.app.controllers.ProgramEditController;
import org.turing.app.controllers.TapeEditController;
import org.turing.app.views.constants.ApplicationConstraints;
import org.turing.app.views.constants.ApplicationStrings;
import org.turing.app.views.panels.ControlPanel;
import org.turing.app.views.panels.SliderPanel;
import org.turing.app.views.panels.StatePanel;
import org.turing.app.views.panels.TapePanel;
import org.turing.support.Logger;

import javax.swing.*;
import java.awt.*;

import static javax.swing.SpringLayout.*;

public class MainFrameView {

    private final ExecutionController executionController;
    private final TapeEditController tapeEditController;
    private final ImportController importController;
    private final ProgramEditController programEditController;

    private ControlPanel controlPanel;
    private SliderPanel sliderPanel;
    private StatePanel statePanel;
    private TapePanel tapePanel;
    private MenuBar menuBar;

    private JFrame frame;
    private SpringLayout layout;


    public MainFrameView(ExecutionController executionController, TapeEditController tapeEditController, ImportController importController, ProgramEditController programEditController) {
        this.executionController = executionController;
        this.tapeEditController = tapeEditController;
        this.importController = importController;
        this.programEditController = programEditController;
    }

    public void init() {
        frame = new JFrame(ApplicationStrings.ACTIONS_WINDOW_TITLE);

        createFrameComponents();
        setFrameSettings();

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
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void createFrameComponents()
    {
        layout = new SpringLayout();

        createAndInitControlPanel();
        createAndInitScrollPane();
        createAndInitStatusPanel();
        createAndInitTapePanel();
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

    private void createAndInitControlPanel()
    {
        try
        {
            controlPanel = new ControlPanel();
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
            sliderPanel = new SliderPanel();
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

    private void createAndInitStatusPanel()
    {
        statePanel = new StatePanel();

        frame.add(statePanel);

        layout.putConstraint(NORTH, statePanel, 10, NORTH, frame.getContentPane());
        layout.putConstraint(WEST, statePanel, 10, WEST, frame.getContentPane());
    }

    private void createAndInitTapePanel()
    {
        tapePanel = new TapePanel();

        frame.add(tapePanel);

        layout.putConstraint(NORTH, tapePanel, 10, SOUTH, controlPanel);
        layout.putConstraint(SOUTH, tapePanel, 10, NORTH, sliderPanel);
        layout.putConstraint(WEST, tapePanel, 10, WEST, frame.getContentPane());
        layout.putConstraint(EAST, tapePanel, -10, EAST, frame.getContentPane());
    }
}
