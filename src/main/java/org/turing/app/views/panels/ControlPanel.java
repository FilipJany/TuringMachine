package org.turing.app.views.panels;

import org.turing.app.views.ApplicationConstraints;

import javax.swing.*;

import java.awt.*;

import static javax.swing.SpringLayout.*;

/**
 * Created by FiFi on 2015-11-09.
 */
//TODO:Implement state changing (online, paused, offline)
public class ControlPanel extends JPanel
{
    private int width, height;
    private JButton playButton, forwardButton, backwardButton, resetButton;
    private SpringLayout layout;

    public ControlPanel()
    {
        width = ApplicationConstraints.minimalControlPanelWidth;
        height = ApplicationConstraints.minimalControlPanelHeight;
        initControlPanel();
    }

    public ControlPanel(int width, int height) throws ControlPanelException
    {
        if(width < ApplicationConstraints.minimalControlPanelHeight || height < ApplicationConstraints.minimalControlPanelWidth)
            throw new ControlPanelException();
        this.height = height;
        this.width = width;
        initControlPanel();
    }

    public ControlPanel(int width, int height, boolean isVisible) throws ControlPanelException
    {
        if(width < ApplicationConstraints.minimalControlPanelHeight || height < ApplicationConstraints.minimalControlPanelWidth)
            throw new ControlPanelException();
        this.width = width;
        this.height = height;
        if(isVisible)
            initControlPanel();
    }


    private void initControlPanel()
    {
        createControlPanel();
        setPanelProperties();
        addComponentToPanel();
        placeButtonsOnPanel();
        setComponentsProperties();
        addListeners();
    }

    private void createControlPanel()
    {
        layout = new SpringLayout();
        //TODO:Replace with proper images
        playButton = new JButton("|>");
        forwardButton = new JButton(">>");
        backwardButton = new JButton("<<");
        resetButton = new JButton("C");
    }

    private void setPanelProperties()
    {
        setLayout(layout);
        setSize(new Dimension(width, height));
        setPreferredSize(new Dimension(width, height));
    }

    private void addComponentToPanel()
    {
        add(playButton);
        add(forwardButton);
        add(backwardButton);
        add(resetButton);
    }

    private void placeButtonsOnPanel()
    {
        //Place forwardButton
        forwardButton.setPreferredSize(new Dimension(ApplicationConstraints.buttonWidth, ApplicationConstraints.buttonHeight));
        layout.putConstraint(EAST, forwardButton, -10, EAST, this);
        layout.putConstraint(NORTH, forwardButton, 5, NORTH, this);
        //Place backwardButton
        backwardButton.setPreferredSize(new Dimension(ApplicationConstraints.buttonWidth, ApplicationConstraints.buttonHeight));
        layout.putConstraint(EAST, backwardButton, -10, WEST, forwardButton);
        layout.putConstraint(NORTH, backwardButton, 5, NORTH, this);
        //Place playButton
        playButton.setPreferredSize(new Dimension(ApplicationConstraints.buttonWidth, ApplicationConstraints.buttonHeight));
        layout.putConstraint(EAST, playButton, -10, WEST, backwardButton);
        layout.putConstraint(NORTH, playButton, 5, NORTH, this);
        //Place resetButton
        resetButton.setPreferredSize(new Dimension(ApplicationConstraints.buttonWidth, ApplicationConstraints.buttonHeight));
        layout.putConstraint(NORTH, resetButton, 5, SOUTH, backwardButton);
        layout.putConstraint(WEST, resetButton, 0, WEST, backwardButton);
    }

    private void setComponentsProperties()
    {
        playButton.setMinimumSize(new Dimension(ApplicationConstraints.minimalButtonWidth, ApplicationConstraints.minimalButtonHeight));
        forwardButton.setMinimumSize(new Dimension(ApplicationConstraints.minimalButtonWidth, ApplicationConstraints.minimalButtonHeight));
        backwardButton.setMinimumSize(new Dimension(ApplicationConstraints.minimalButtonWidth, ApplicationConstraints.minimalButtonHeight));
        resetButton.setMinimumSize(new Dimension(ApplicationConstraints.minimalButtonWidth, ApplicationConstraints.minimalButtonHeight));
    }

    private void addListeners()
    {
        //TODO:Implement
    }
}

class ControlPanelException extends Exception
{
    public ControlPanelException()
    {
        System.err.println("Minimal ControlPanel dimensions are: " + ApplicationConstraints.minimalControlPanelWidth + " x " + ApplicationConstraints.minimalControlPanelHeight);
    }
    public ControlPanelException(String s)
    {
        System.err.println(s);
    }
}
