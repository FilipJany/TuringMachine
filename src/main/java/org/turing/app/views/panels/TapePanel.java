package org.turing.app.views.panels;

import org.turing.app.common.Symbol;
import org.turing.app.controllers.TapeEditController;
import org.turing.app.exceptions.TapeException;
import org.turing.app.views.constants.ApplicationConstraints;
import org.turing.support.ResourceProvider;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.List;

import static javax.swing.SpringLayout.*;

/**
 * Created by FiFi on 2015-11-11.
 */
public class TapePanel extends JPanel {
    private final TapeEditController tapeEditController;

    private int width, height;
    private JButton leftButton, rightButton;
    private List<JTextField> tape;
    private JLabel head;
    private SpringLayout layout;

    private boolean interiorModification;

    public TapePanel(TapeEditController tapeEditController) {
        this(ApplicationConstraints.minimalTapePanelWidth,
                ApplicationConstraints.minimalTapePanelHeight,
                tapeEditController);
    }

    public TapePanel(int width, int height, TapeEditController tapeEditController) {
        this.tapeEditController = tapeEditController;
        this.height = height;
        this.width = width;
        initTapePanel();
    }

    public int getVisibleTapeSize() {
        return tape.size();
    }

    public void updateView(List<Symbol> symbols) throws TapeException {
        if (symbols == null || symbols.size() != tape.size())
            throw new TapeException("Improper data to fill");

        for (int idx = 0; idx < tape.size(); idx++) {
            final int i = idx;
            SwingUtilities.invokeLater(() -> {
                interiorModification = true;
                tape.get(i).setText(symbols.get(i).getValue());
                interiorModification = false;
            });
        }
    }

    public void updateView(int headDiff, Symbol symbol) throws TapeException {
        if (symbol == null)
            throw new TapeException("Improper data to fill");

        SwingUtilities.invokeLater(() -> {
            interiorModification = true;
            tape.get(tape.size() / 2 + headDiff).setText(symbol.getValue());
            interiorModification = false;
        });
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
        head = new JLabel(ResourceProvider.getIcon("head.png"));
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
        JTextField middleTextField = tape.get(tape.size() / 2);

        layout.putConstraint(HORIZONTAL_CENTER, head, 0, HORIZONTAL_CENTER, middleTextField);
        layout.putConstraint(NORTH, head, 10, NORTH, this);

        layout.putConstraint(HORIZONTAL_CENTER, middleTextField, 0, HORIZONTAL_CENTER, this);
        layout.putConstraint(NORTH, middleTextField, 5, SOUTH, head);

        for (int i = tape.size() / 2 - 1; i >= 0; i--) {
            layout.putConstraint(EAST, tape.get(i), -10, WEST, tape.get(i + 1));
            layout.putConstraint(BASELINE, tape.get(i), 0, BASELINE, middleTextField);
        }

        for (int i = tape.size() / 2 + 1; i < tape.size(); i++) {
            layout.putConstraint(WEST, tape.get(i), 10, EAST, tape.get(i - 1));
            layout.putConstraint(BASELINE, tape.get(i), 0, BASELINE, middleTextField);
        }

        layout.putConstraint(WEST, leftButton, 10, WEST, this);
        layout.putConstraint(EAST, leftButton, -10, WEST, tape.get(0));
        layout.putConstraint(BASELINE, leftButton, 0, BASELINE, middleTextField);

        layout.putConstraint(EAST, rightButton, -10, EAST, this);
        layout.putConstraint(WEST, rightButton, 10, EAST, tape.get(tape.size() - 1));
        layout.putConstraint(BASELINE, rightButton, 0, BASELINE, middleTextField);
    }

    private void setComponentsProperties() {
        for (JTextField tf : tape)
            tf.setPreferredSize(new Dimension(ApplicationConstraints.textFieldWidth, ApplicationConstraints.textFieldHigh));


        head.setOpaque(false);
        head.setBackground(Color.BLACK);
        head.setPreferredSize(new Dimension(80, 35));

        leftButton.setPreferredSize(new Dimension(50, 30));
        rightButton.setPreferredSize(new Dimension(50, 30));
    }

    private void addListeners() {
        int diffToHead = -(tape.size() / 2);
        for (int i = 0, tapeSize = tape.size(); i < tapeSize; i++) {
            JTextField cell = tape.get(i);
            final int cellDiff = diffToHead;
            cell.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if (!interiorModification)
                        tapeEditController.changeSymbol(cellDiff, cell.getText());
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    if (!interiorModification)
                        tapeEditController.changeSymbol(cellDiff, cell.getText());
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    if (!interiorModification)
                        tapeEditController.changeSymbol(cellDiff, cell.getText());
                }
            });
            addFocusChangingKeyBinding(cell, i - 1, "focus left", KeyEvent.VK_LEFT);
            addFocusChangingKeyBinding(cell, i + 1, "focus right", KeyEvent.VK_RIGHT);
            diffToHead++;
        }

        leftButton.addActionListener(a -> tapeEditController.moveRight());
        rightButton.addActionListener(a -> tapeEditController.moveLeft());
    }

    private void addFocusChangingKeyBinding(JTextField cell, final int cellIndex, String actionName, int keyCode) {
        cell.getInputMap().put(KeyStroke.getKeyStroke(keyCode, 0), actionName);
        cell.getActionMap().put(actionName, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(cellIndex >= 0 && cellIndex < tape.size()) {
                    tape.get(cellIndex).requestFocus();
                }
            }
        });
    }
}