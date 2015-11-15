package org.turing.app.views;

import javax.swing.*;

/**
 * Created by FiFi on 2015-11-11.
 */
public class MenuBar
{
    private JMenuBar menuBar;
    private JMenu file, run, view, help;
    private JMenuItem fileNewProgram, fileLoadProgram, fileClearTape, fileLoadTape, fileSaveProgram, fileSaveTape, fileRename, fileDeilimiter;
    private JMenuItem runStart, runPause, runBackward, runForward, runSlower, runFaster;
    private JMenuItem viewShiftLeft, viewShiftRight, viewDefault;
    private JMenuItem helpUG, helpAbout, helpLicence;

    public MenuBar()
    {
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
        fileRename = new JMenuItem("Rename");
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
        file.add(fileRename);
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
        viewDefault = new JMenuItem("Default View");
    }

    private void addItemsToViewMenu()
    {
        view.add(viewShiftLeft);
        view.add(viewShiftRight);
        view.addSeparator();
        view.add(viewDefault);
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
        //TODO:Implement
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
