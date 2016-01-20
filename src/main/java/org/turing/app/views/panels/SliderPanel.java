package org.turing.app.views.panels;

import org.turing.app.controllers.ExecutionController;
import org.turing.app.views.constants.ApplicationConstraints;

import javax.swing.*;

import static javax.swing.SpringLayout.*;

/**
 * Created by FiFi on 2015-11-09.
 */
public class SliderPanel extends JPanel {
    private final ExecutionController executionController;

    private JSlider slider;
    private JLabel speedLabel;

    public SliderPanel(ExecutionController executionController) {
        this.executionController = executionController;
        initSliderPanel();
    }

    public void updateView(int value, String text) {
        slider.setValue(value);
        speedLabel.setText(text);
    }


    private void initSliderPanel() {
        createSliderPanelComponents();
        setSliderPanelProperties();
        addComponentsToPanel();
        placeSliderPanelComponentsOnPanel();
        setListeners();
    }

    private void createSliderPanelComponents() {
        slider = new JSlider(JSlider.HORIZONTAL);
        speedLabel = new JLabel(ApplicationConstraints.sliderSpeedLabelPrefix);
    }

    private void setSliderPanelProperties() {
        slider.setMinimum(ApplicationConstraints.sliderMinValue);
        slider.setMaximum(ApplicationConstraints.sliderMaxValue);
        slider.setValue(ApplicationConstraints.sliderInitValue);
        slider.setMajorTickSpacing(ApplicationConstraints.sliderStep);
        slider.setMinorTickSpacing(ApplicationConstraints.sliderSteppie);
        slider.setPaintTicks(true);
        slider.setInverted(true);
        slider.setSnapToTicks(true);
        slider.setOpaque(false);
        setOpaque(false);
    }

    private void addComponentsToPanel() {
        add(speedLabel);
        add(slider);
    }

    private void placeSliderPanelComponentsOnPanel() {
        SpringLayout layout = new SpringLayout();

        layout.putConstraint(WEST, speedLabel, -190, EAST, this);
        layout.putConstraint(NORTH, speedLabel, 10, NORTH, this);

        layout.putConstraint(WEST, slider, 5, WEST, this);
        layout.putConstraint(NORTH, slider, 0, SOUTH, speedLabel);
        layout.putConstraint(EAST, slider, -5, EAST, this);
        layout.putConstraint(SOUTH, slider, -5, SOUTH, this);

        setLayout(layout);
    }

    private void setListeners() {
        slider.addChangeListener(c -> executionController.updateStepDelay(slider.getValue()));
        //nie bijce mnie za ta lambde :P uwielbiam je wciskac w C# :P
        //easy, nie jesteś sam Fifi :P
    }
}
