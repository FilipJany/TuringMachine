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
import java.awt.event.*;
import java.io.File;

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
    private JPanel contentPanel;
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
        createKeyBindings();
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
        frame.getContentPane().setBackground(ApplicationConstraints.background);
//        frame.getContentPane().setBackground(new Color(146, 128, 101));


        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                contentPanel.requestFocus();
            }
        });
    }

    private void createKeyBindings() {
        addActionForKey(KeyEvent.VK_LEFT, InputEvent.ALT_DOWN_MASK, "move tape right", e -> {tapeEditController.moveRight();});
        addActionForKey(KeyEvent.VK_RIGHT, InputEvent.ALT_DOWN_MASK, "move tape left", e -> {tapeEditController.moveLeft();});

        addActionForKey(KeyEvent.VK_ENTER, "play or pause", e -> {executionController.playOrPauseDependingOnStatus();});

        addActionForKey(KeyEvent.VK_LEFT, InputEvent.SHIFT_DOWN_MASK, "step backward", e -> {executionController.stepBackward();});
        addActionForKey(KeyEvent.VK_RIGHT, InputEvent.SHIFT_DOWN_MASK, "step forward", e -> {executionController.stepForward();});
    }

    private void addActionForKey(int keyCode, String actionName, ActionListener action) {
        addActionForKey(keyCode, 0, actionName, action);
    }

    private void addActionForKey(int keyCode, int keyMask, String actionName, ActionListener action) {
        contentPanel.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
                .put(KeyStroke.getKeyStroke(keyCode, keyMask), actionName);
        contentPanel.getActionMap().put(actionName, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                action.actionPerformed(e);
            }
        });
    }

    private void createFrameComponents()
    {
        layout = new SpringLayout();
        contentPanel = new JPanel();
        frame.setContentPane(contentPanel);

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
                    importExportController.exportProgram(new File(programFileName));
                if(tapeFileName != null)
                    importExportController.exportTape(new File(tapeFileName));
            }
            System.exit(0);
        }
    };
}
