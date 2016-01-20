package org.turing.app.views.panels;

import org.turing.app.common.State;
import org.turing.app.controllers.ExecutionController;
import org.turing.app.views.elements.ActionTripleComboBox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

import static javax.swing.SpringLayout.VERTICAL_CENTER;
import static javax.swing.SpringLayout.WEST;

/**
 * Created by FiFi on 2015-11-09.
 */
public class StatePanel extends JPanel {
    private final ExecutionController executionController;

    private JLabel stateLabel;
    private ActionTripleComboBox<State> stateCombobox;

    private boolean interiorModification;

    public StatePanel(ExecutionController executionController) {
        this.executionController = executionController;
        initStatePanel();
    }

    public void updateState(State newState) {
        interiorModification = true;

        stateLabel.setText(newState.getName());
        stateCombobox.setSelectedItem(newState);

        interiorModification = false;
    }

    public void updateAvailableStates(List<State> availableStates) {
        interiorModification = true;

        stateCombobox.removeAllItems();
        for (State s : availableStates)
            stateCombobox.addItem(s);

        interiorModification = false;
    }

    private void initStatePanel() {
        createPanelComponents();
        setPanelProperties();
        setComponentsProperties();
        addComponentsToPanel();
        putLayout();
        addListeners();
    }

    private void createPanelComponents() {
        stateLabel = new JLabel();
        stateCombobox = new ActionTripleComboBox<>();
    }

    private void setPanelProperties() {
        setPreferredSize(new Dimension(150, 70));
        setOpaque(false);
    }

    private void setComponentsProperties() {
        stateLabel.setFocusable(true);
        stateLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        stateLabel.setVisible(true);

        stateCombobox.setFont(new Font("Arial", Font.PLAIN, 18));
        stateCombobox.setVisible(false);
    }

    private void addComponentsToPanel() {
        add(stateLabel);
        add(stateCombobox);
    }

    private void putLayout() {
        SpringLayout layout = new SpringLayout();

        layout.putConstraint(WEST, stateLabel, 10, WEST, this);
        layout.putConstraint(VERTICAL_CENTER, stateLabel, 0, VERTICAL_CENTER, this);

        layout.putConstraint(WEST, stateCombobox, 10, WEST, this);
        layout.putConstraint(VERTICAL_CENTER, stateCombobox, 0, VERTICAL_CENTER, this);

        setLayout(layout);
    }

    public void addListeners() {
        stateLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                stateLabel.requestFocusInWindow();
            }
        });
        stateLabel.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                stateLabel.setVisible(false);
                stateCombobox.setVisible(true);
            }
        });
        stateCombobox.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                stateLabel.setVisible(true);
                stateCombobox.setVisible(false);

            }
        });
        stateCombobox.addItemListener(e -> {
            if (!interiorModification && e.getStateChange() == ItemEvent.SELECTED) {
                executionController.updateState((State) stateCombobox.getSelectedItem());
            }
        });
    }
}
