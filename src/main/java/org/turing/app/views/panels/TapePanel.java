package org.turing.app.views.panels;

import org.turing.app.views.constants.ApplicationConstraints;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

import static javax.swing.SpringLayout.*;

/**
 * Created by FiFi on 2015-11-11.
 */
public class TapePanel extends JPanel {
    private int width, height;
    private JButton leftButton, rightButton;
    private java.util.List<JTextField> tape;
    private JLabel head;
    private SpringLayout layout;

    public TapePanel() {
        width = ApplicationConstraints.minimalTapePanelWidth;
        height = ApplicationConstraints.minimalTapePanelHeight;
        initTapePanel();
    }

    public TapePanel(int width, int height) throws TapePanelException {
        if (width < ApplicationConstraints.minimalTapePanelWidth || height < ApplicationConstraints.minimalTapePanelHeight)
            throw new TapePanelException();
        this.height = height;
        this.width = width;
        initTapePanel();
    }

    public TapePanel(int width, int height, boolean isVisible) throws TapePanelException {
        if (width < ApplicationConstraints.minimalTapePanelWidth || height < ApplicationConstraints.minimalTapePanelHeight)
            throw new TapePanelException();
        this.width = width;
        this.height = height;
        if (isVisible)
            initTapePanel();
    }

    public void updateView() {

    }

    private void initTapePanel() {
        createTapePanel();
        setPanelProperties();
        addComponentToPanel();
        placeButtonsOnPanel();
        setComponentsProperties();
        addListeners();
    }

    private void createTapePanel() {
        layout = new SpringLayout();
        leftButton = new JButton("<");
        rightButton = new JButton(">");
        head = new JLabel();
        tape = new LinkedList<>();
        for (int i = 0; i < ApplicationConstraints.TapeSize; ++i)
            tape.add(new JTextField("" + i));
    }

    private void setPanelProperties() {
        setSize(new Dimension(width, height));
        setLayout(layout);
    }

    private void addComponentToPanel() {
        add(leftButton);
        for (int i = 0; i < ApplicationConstraints.TapeSize; ++i)
            add(tape.get(i));
        add(rightButton);
        add(head);
    }

    private void placeButtonsOnPanel() {
        JTextField middleTextField = tape.get(tape.size()/2);

        layout.putConstraint(HORIZONTAL_CENTER, head, 0, HORIZONTAL_CENTER, middleTextField);
        layout.putConstraint(NORTH, head, -10, NORTH, this);

        layout.putConstraint(HORIZONTAL_CENTER, middleTextField, 0, HORIZONTAL_CENTER, this);
        layout.putConstraint(NORTH, middleTextField, 5, SOUTH, head);

        for (int i=tape.size()/2-1; i >= 0; i--) {
            layout.putConstraint(EAST, tape.get(i), -10, WEST, tape.get(i+1));
            layout.putConstraint(BASELINE, tape.get(i), 0, BASELINE, middleTextField);
        }

        for (int i=tape.size()/2+1; i < tape.size(); i++) {
            layout.putConstraint(WEST, tape.get(i), 10, EAST, tape.get(i-1));
            layout.putConstraint(BASELINE, tape.get(i), 0, BASELINE, middleTextField);
        }

        layout.putConstraint(WEST, leftButton, 10, WEST, this);
        layout.putConstraint(EAST, leftButton, -10, WEST, tape.get(0));
        layout.putConstraint(BASELINE, leftButton, 0, BASELINE, middleTextField);

        layout.putConstraint(EAST, rightButton, -10, EAST, this);
        layout.putConstraint(WEST, rightButton, 10, EAST, tape.get(tape.size()-1));
        layout.putConstraint(BASELINE, rightButton, 0, BASELINE, middleTextField);
    }

    private void setComponentsProperties() {
        for (JTextField tf : tape)
            tf.setPreferredSize(new Dimension(ApplicationConstraints.textFieldWidth, ApplicationConstraints.textFieldHigh));

        head.setOpaque(true);
        head.setBackground(Color.BLACK);
        head.setPreferredSize(new Dimension(10, 30));

        leftButton.setPreferredSize(new Dimension(50, 30));
        rightButton.setPreferredSize(new Dimension(50, 30));
    }

    private void addListeners() {
        //TODO:Implement
    }
}

class TapePanelException extends Exception {
    public TapePanelException() {
        System.err.println("Minimal ControlPanel dimensions are: " + ApplicationConstraints.minimalTapePanelWidth + " x " + ApplicationConstraints.minimalTapePanelHeight);
    }

    public TapePanelException(String s) {
        System.err.println(s);
    }
}