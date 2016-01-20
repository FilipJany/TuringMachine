package org.turing.app.views;

import org.turing.app.controllers.ExecutionController;
import org.turing.app.controllers.ImportExportController;
import org.turing.app.controllers.ProgramEditController;
import org.turing.app.controllers.TapeEditController;
import org.turing.app.model.DataModel;
import org.turing.app.model.ProgramModel;
import org.turing.support.Logger;
import org.turing.support.LoggerGUI;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.function.Consumer;

/**
 * Created by FiFi on 2015-11-11.
 */
public class MenuBar
{
    private final ExecutionController execController;
    private final ImportExportController importExportController;
    private final DataModel dataModel;
    private final ProgramModel programModel;
    private final JFrame masterFrame;
    private final ProgramEditController programEditController;
    private final TapeEditController tapeEditController;

    private JMenuBar menuBar;
    private JMenu file, run, view, help;
    private JMenuItem fileNewProgram, fileLoadProgram, fileClearTape, fileLoadTape, fileSaveProgram, fileSaveTape, authors, fileDeilimiter;
    private JMenuItem runStart, runPause, runBackward, runForward, runSlower, runFaster;
    private JMenuItem viewShiftLeft, viewShiftRight;
    private JMenuItem helpUG, helpAbout, helpLicence;

    public MenuBar(ImportExportController importExportController, ProgramEditController programEditController, TapeEditController tapeEditController, ExecutionController execController, DataModel dataModel, ProgramModel programModel, JFrame masterFrame)
    {
        this.execController = execController;
        this.importExportController = importExportController;
        this.dataModel = dataModel;
        this.programModel = programModel;
        this.masterFrame = masterFrame;
        this.tapeEditController = tapeEditController;
        this.programEditController = programEditController;

        createMenuBar();

        createFileMenu();
        createRunMenu();
        createViewMenu();
        createHelpMenu();

        addMenusToBar();
        addItemsToFileMenu();
        addItemsToRunMenu();
        addItemsToViewMenu();
        addItemsToHelpMenu();

        addListeners();
    }

    private void createMenuBar()
    {
        menuBar = new JMenuBar();
        file = new JMenu("File");
        run = new JMenu("New");
        view = new JMenu("View");
        help = new JMenu("Help");
    }

    private void addMenusToBar()
    {
        menuBar.add(file);
        menuBar.add(run);
        menuBar.add(view);
        menuBar.add(help);
    }

    private void createFileMenu()
    {
        fileNewProgram = new JMenuItem("New Program");
        fileLoadProgram = new JMenuItem("Load Program");
        fileDeilimiter = new JMenuItem("-------------");
        fileClearTape = new JMenuItem("Clear Tape");
        fileLoadTape = new JMenuItem("Load Tape");
        fileSaveProgram = new JMenuItem("Save Program");
        fileSaveTape = new JMenuItem("Save Tape");
        authors = new JMenuItem("!Read This First!");
    }

    private void addItemsToFileMenu()
    {
        file.add(fileNewProgram);
        file.add(fileLoadProgram);
        file.addSeparator();
        file.add(fileClearTape);
        file.add(fileLoadTape);
        file.addSeparator();
        file.add(fileSaveProgram);
        file.add(fileSaveTape);
        file.add(authors);
    }

    private void createRunMenu()
    {
        runStart = new JMenuItem("Start");
        runPause = new JMenuItem("Pause");
        runBackward = new JMenuItem("Step Backwards");
        runForward = new JMenuItem("Step Forwards");
        runSlower = new JMenuItem("Slower");
        runFaster = new JMenuItem("Faster");
    }

    private void addItemsToRunMenu()
    {
        run.add(runStart);
        run.add(runPause);
        run.add(runBackward);
        run.add(runForward);
        run.addSeparator();
        run.add(runSlower);
        run.add(runFaster);
    }

    private void createViewMenu()
    {
        viewShiftLeft = new JMenuItem("Shift tape to the left");
        viewShiftRight = new JMenuItem("Shift tape to the right");
    }

    private void addItemsToViewMenu()
    {
        view.add(viewShiftLeft);
        view.add(viewShiftRight);
    }

    private void createHelpMenu()
    {
        helpUG = new JMenuItem("User Guide");
        helpAbout = new JMenuItem("About Authors");
        helpLicence = new JMenuItem("Licence");
    }

    private void addItemsToHelpMenu()
    {
        help.add(helpUG);
        help.add(helpAbout);
        help.add(helpLicence);
    }

    private void addListeners()
    {
        addProgramExportListener();
        addTapeExportListener();
        addProgramImportListener();
        addTapeImportListener();
        addNewProgramListener();
        addClearTapeListener();
        addAuthorsListener();

        addRunBackwardListener();
        addRunFasterListener();
        addRunPauseListener();
        addRunForwardListener();
        addRunSlowerListener();
        addRunStartListener();

        addViewShiftLeftListener();
        addViewShiftRightListener();
    }

    private void addViewShiftLeftListener()
    {
        viewShiftLeft.addActionListener(e -> tapeEditController.moveLeft());
    }

    private void addViewShiftRightListener()
    {
        viewShiftRight.addActionListener(e -> tapeEditController.moveRight());
    }

    private void addRunStartListener()
    {
        runStart.addActionListener(e -> execController.play());
    }

    private void addRunPauseListener()
    {
        runPause.addActionListener(e-> execController.pause());
    }

    private void addRunBackwardListener()
    {
        runBackward.addActionListener(e -> execController.stepBackward());
    }

    private void addRunForwardListener()
    {
        runForward.addActionListener(e-> execController.stepForward());
    }

    private void addRunFasterListener()
    {
        runFaster.addActionListener(e -> execController.updateStepDelay(execController.getExecutionDelay() - 1));
    }

    private void addRunSlowerListener()
    {
        runSlower.addActionListener(e -> execController.updateStepDelay(execController.getExecutionDelay() + 1));
    }

    private void addAuthorsListener()
    {
        authors.addActionListener(e -> LoggerGUI.showErrorDialog(masterFrame, "Just kidding - no errors ;) \nCreated by: jHoła, fJany, pStopyra!", "The most important information ever!"));
    }

    private void addNewProgramListener()
    {
        fileNewProgram.addActionListener(e -> {
            programModel.clear();
            programEditController.fullyRefreshProgramTableAndStatePanel();
        });
    }

    private void addClearTapeListener()
    {
        fileClearTape.addActionListener(e -> {
            dataModel.clear();
            tapeEditController.refreshTapePanel();
        });
    }

    private void addProgramExportListener() {
        fileSaveProgram.addActionListener(createActionWithSaveDialog(
                filename -> {
                    importExportController.exportProgram(filename);
                }
        ));
    }

    private void addTapeExportListener() {
        fileSaveTape.addActionListener(createActionWithSaveDialog(
                filename -> {
                    importExportController.exportTape(filename);
                }
        ));
    }

    private void addProgramImportListener() {
        fileLoadProgram.addActionListener(createActionWithOpenDialog(
                filename -> {
                    importExportController.importProgram(filename);
                }
        ));
    }

    private void addTapeImportListener() {
        fileLoadTape.addActionListener(createActionWithOpenDialog(
                filename -> {
                    importExportController.importTape(filename);
                }
        ));
    }

    private ActionListener createActionWithOpenDialog(Consumer<File> actionExecutor) {
        return e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Please specify file");
            if(fileChooser.showOpenDialog(fileLoadTape) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    actionExecutor.accept(file);
                } catch (RuntimeException ex) {
                    Logger.error(ex);
                    JOptionPane.showMessageDialog(null, "An error occurred: " + ex.getMessage(), "error", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
    }

    private ActionListener createActionWithSaveDialog(Consumer<File> actionExecutor) {
        return e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Please specify file");
            if(fileChooser.showSaveDialog(fileLoadTape) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    actionExecutor.accept(file);
                } catch (RuntimeException ex) {
                    Logger.error(ex);
                    JOptionPane.showMessageDialog(null, "An error occurred: " + ex.getMessage(), "error", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
    }

    public JMenuBar getMenuBar() throws MenuBarException
    {
        if(menuBar == null)
            throw new MenuBarException();
        return menuBar;
    }
}

class MenuBarException extends Exception
{
    public MenuBarException()
    {
        System.err.println("Menu Bar has not been initiated!");
    }
}
