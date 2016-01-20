package org.turing.app.views.panels;

import org.turing.app.controllers.ExecutionController;
import org.turing.app.views.constants.ApplicationConstraints;
import org.turing.app.views.constants.ExecutionStatus;
import org.turing.app.views.elements.RoundButton;
import org.turing.support.ResourceProvider;

import javax.swing.*;
import java.awt.*;

import static javax.swing.SpringLayout.*;
import static org.turing.app.views.constants.ExecutionStatus.STEP;


/**
 * Created by FiFi on 2015-11-09.
 */
//TODO:Implement state changing (online, paused, offline)
public class ControlPanel extends JPanel {
    private final ExecutionController executionController;

    private JButton playButton, pauseButton, forwardButton, backwardButton, resetButton;

    public ControlPanel(ExecutionController executionController) {
        this.executionController = executionController;

        initControlPanel();
        updateStatus(STEP);
    }

    public void updateStatus(ExecutionStatus status) {
        switch (status) {
            case STEP:
                forwardButton.setVisible(true);
                playButton.setVisible(false);
                pauseButton.setVisible(false);
                break;
            case CONTINUOUS_RUN:
                forwardButton.setVisible(false);
                playButton.setVisible(false);
                pauseButton.setVisible(true);
                break;
            case CONTINUOUS_STOP:
                forwardButton.setVisible(false);
                playButton.setVisible(true);
                pauseButton.setVisible(false);
                break;
        }
    }

    private void initControlPanel() {
        createControlPanel();
        setPanelProperties();
        addComponentsToPanel();
        placeButtonsOnPanel();
        setComponentsProperties();
        addListeners();
    }

    private void createControlPanel() {
        //TODO:Replace with proper images
        playButton = new RoundButton(ResourceProvider.getIcon("button_play_active.png"), ResourceProvider.getIcon("button_play_mouse.png"), ResourceProvider.getIcon("button_play_clicked.png"));
        pauseButton = new RoundButton(ResourceProvider.getIcon("button_pause_active.png"), ResourceProvider.getIcon("button_pause_mouse.png"), ResourceProvider.getIcon("button_pause_clicked.png"));
        forwardButton = new RoundButton(ResourceProvider.getIcon("button_forward_active.png"), ResourceProvider.getIcon("button_forward_mouse.png"), ResourceProvider.getIcon("button_forward_clicked.png"));
        backwardButton = new RoundButton(ResourceProvider.getIcon("button_back_active.png"), ResourceProvider.getIcon("button_back_mouse.png"), ResourceProvider.getIcon("button_back_clicked.png"));
        resetButton =  new RoundButton(ResourceProvider.getIcon("button_clear_active.png"), ResourceProvider.getIcon("button_clear_mouse.png"), ResourceProvider.getIcon("button_clear_clicked.png"));
    }

    private void setPanelProperties() {
        setPreferredSize(new Dimension(150, 70));
        //setBackground(new Color(113, 60, 15, 0));
        setOpaque(false);
    }

    private void addComponentsToPanel() {
        add(playButton);
        add(pauseButton);
        add(forwardButton);
        add(backwardButton);
        add(resetButton);
    }

    private void placeButtonsOnPanel() {
        SpringLayout layout = new SpringLayout();

        resetButton.setPreferredSize(new Dimension(ApplicationConstraints.buttonWidth, ApplicationConstraints.buttonHeight));
        layout.putConstraint(EAST, resetButton, -10, EAST, this);
        layout.putConstraint(NORTH, resetButton, 5, NORTH, this);

        forwardButton.setPreferredSize(new Dimension(ApplicationConstraints.buttonWidth, ApplicationConstraints.buttonHeight));
        layout.putConstraint(EAST, forwardButton, -10, WEST, resetButton);
        layout.putConstraint(NORTH, forwardButton, 5, NORTH, this);

        playButton.setPreferredSize(new Dimension(ApplicationConstraints.buttonWidth, ApplicationConstraints.buttonHeight));
        layout.putConstraint(EAST, playButton, -10, WEST, resetButton);
        layout.putConstraint(NORTH, playButton, 5, NORTH, this);

        pauseButton.setPreferredSize(new Dimension(ApplicationConstraints.buttonWidth, ApplicationConstraints.buttonHeight));
        layout.putConstraint(EAST, pauseButton, -10, WEST, resetButton);
        layout.putConstraint(NORTH, pauseButton, 5, NORTH, this);

        backwardButton.setPreferredSize(new Dimension(ApplicationConstraints.buttonWidth, ApplicationConstraints.buttonHeight));
        layout.putConstraint(EAST, backwardButton, -10, WEST, playButton);
        layout.putConstraint(NORTH, backwardButton, 5, NORTH, this);

        setLayout(layout);
    }

    private void setComponentsProperties() {
//        playButton.setMinimumSize(new Dimension(ApplicationConstraints.minimalButtonWidth, ApplicationConstraints.minimalButtonHeight));
//        pauseButton.setMinimumSize(new Dimension(ApplicationConstraints.minimalButtonWidth, ApplicationConstraints.minimalButtonHeight));
//        forwardButton.setMinimumSize(new Dimension(ApplicationConstraints.minimalButtonWidth, ApplicationConstraints.minimalButtonHeight));
//        backwardButton.setMinimumSize(new Dimension(ApplicationConstraints.minimalButtonWidth, ApplicationConstraints.minimalButtonHeight));
//        resetButton.setMinimumSize(new Dimension(ApplicationConstraints.minimalButtonWidth, ApplicationConstraints.minimalButtonHeight));
    }

    private void addListeners() {
        playButton.addActionListener(a -> executionController.play());
        pauseButton.addActionListener(a -> executionController.pause());
        forwardButton.addActionListener(a -> executionController.stepForward());
        backwardButton.addActionListener(a -> executionController.stepBackward());
        resetButton.addActionListener(a -> executionController.clear());
    }
}
