package org.turing.app.views.panels;

import org.turing.app.views.constants.ApplicationConstraints;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

import static javax.swing.SpringLayout.*;

/**
 * Created by FiFi on 2015-11-11.
 */
public class TapePanel extends JPanel
{
    private int width, height;
    private JButton leftButton, rightButton;
    private java.util.List<JTextField> tape;
    private SpringLayout layout;

    public TapePanel()
    {
        width = ApplicationConstraints.minimalTapePanelWidth;
        height = ApplicationConstraints.minimalTapePanelHeight;
        initTapePanel();
    }

    public TapePanel(int width, int height) throws TapePanelException
    {
        if(width < ApplicationConstraints.minimalTapePanelWidth || height < ApplicationConstraints.minimalTapePanelHeight)
            throw new TapePanelException();
        this.height = height;
        this.width = width;
        initTapePanel();
    }

    public TapePanel(int width, int height, boolean isVisible) throws TapePanelException
    {
        if(width < ApplicationConstraints.minimalTapePanelWidth || height < ApplicationConstraints.minimalTapePanelHeight)
            throw new TapePanelException();
        this.width = width;
        this.height = height;
        if(isVisible)
            initTapePanel();
    }


    private void initTapePanel()
    {
        createTapePanel();
        setPanelProperties();
        addComponentToPanel();
        placeButtonsOnPanel();
        setComponentsProperties();
        addListeners();
    }

    private void createTapePanel()
    {
        layout = new SpringLayout();
        leftButton = new JButton("<");
        rightButton = new JButton(">");
        tape = new LinkedList<>();
        for (int i = 0; i < ApplicationConstraints.TapeSize; ++i)
            tape.add(new JTextField("" + i));
    }

    private void setPanelProperties()
    {
        setSize(new Dimension(width, height));
    }

    private void addComponentToPanel()
    {
        add(leftButton);
        for (int i = 0; i < ApplicationConstraints.TapeSize; ++i)
            add(tape.get(i));
        add(rightButton);
    }

    private void placeButtonsOnPanel()
    {
        layout.putConstraint(WEST, leftButton, 10, WEST, this);
        layout.putConstraint(NORTH, leftButton, 10, NORTH, this);
        layout.putConstraint(SOUTH, leftButton, -10, SOUTH, this);
        JTextField currentTextField = null;
        for (int i = 0; i < ApplicationConstraints.TapeSize; ++i)
        {
            currentTextField = tape.get(i);
            if(i == 0)
                layout.putConstraint(WEST, currentTextField, 10, EAST, leftButton);
            else
            {
                JTextField previousTextField = tape.get(i-1);
                layout.putConstraint(WEST, currentTextField, 10, EAST, previousTextField);
            }
            layout.putConstraint(NORTH, currentTextField, 10, NORTH, this);
            layout.putConstraint(SOUTH, currentTextField, -10, SOUTH, this);
        }
        layout.putConstraint(WEST, rightButton, 10, EAST, currentTextField);
        layout.putConstraint(NORTH, rightButton, 10, NORTH, this);
        layout.putConstraint(SOUTH, rightButton, -10, SOUTH, this);
    }

    private void setComponentsProperties()
    {
        for (JTextField tf : tape)
            tf.setPreferredSize(new Dimension(ApplicationConstraints.textFieldWidth, ApplicationConstraints.textFieldHigh));
    }

    private void addListeners()
    {
        //TODO:Implement
    }
}

class TapePanelException extends Exception
{
    public TapePanelException()
    {
        System.err.println("Minimal ControlPanel dimensions are: " + ApplicationConstraints.minimalTapePanelWidth + " x " + ApplicationConstraints.minimalTapePanelHeight);
    }
    public TapePanelException(String s)
    {
        System.err.println(s);
    }
}