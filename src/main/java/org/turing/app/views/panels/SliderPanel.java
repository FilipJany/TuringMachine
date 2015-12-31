package org.turing.app.views.panels;

import org.turing.app.views.constants.ApplicationConstraints;

import javax.swing.*;

import static javax.swing.SpringLayout.*;

/**
 * Created by FiFi on 2015-11-09.
 */
public class SliderPanel extends JPanel
{
    private JSlider slider;
    private SpringLayout layout;
    private JLabel speedLabel;

    public SliderPanel()
    {
        initSliderPanel();
    }

    public SliderPanel(int width, int height) throws SliderPanelException
    {
        if(width < ApplicationConstraints.sliderPanelMinimalWidth || height < ApplicationConstraints.sliderPanelMinimalHeight)
            throw new SliderPanelException();
        initSliderPanel();
    }

    private void initSliderPanel()
    {
        createSliderPanelComponents();
        setSliderPanelProperties();
        addComponentsToPanel();
        placeSliderPanelComponentsOnPanel();
        setListeners();
    }

    private void createSliderPanelComponents()
    {
        layout = new SpringLayout();
        slider = new JSlider(JSlider.HORIZONTAL);
        speedLabel = new JLabel(ApplicationConstraints.sliderSpeedLabelPrefix + ApplicationConstraints.sliderInitValue/10);
    }

    private void setSliderPanelProperties()
    {
        setLayout(layout);
        slider.setMinimum(ApplicationConstraints.sliderMinValue);
        slider.setMaximum(ApplicationConstraints.sliderMaxValue);
        slider.setValue(ApplicationConstraints.sliderInitValue);
        slider.setMinorTickSpacing(ApplicationConstraints.sliderStep);
        slider.setPaintTicks(true);
    }

    private void addComponentsToPanel()
    {
        add(speedLabel);
        add(slider);
    }

    private void placeSliderPanelComponentsOnPanel()
    {
        //Place label
        layout.putConstraint(EAST, speedLabel, -5, EAST, this);
        layout.putConstraint(NORTH, speedLabel, 5, NORTH, this);
        //Place slider
        layout.putConstraint(WEST, slider, 5, WEST, this);
        layout.putConstraint(NORTH, slider, 5, SOUTH, speedLabel);
        layout.putConstraint(EAST, slider, -5, EAST, this);
        layout.putConstraint(SOUTH, slider, -5, SOUTH, this);
    }

    private void setListeners()
    {
        slider.addChangeListener(e -> speedLabel.setText(ApplicationConstraints.sliderSpeedLabelPrefix + String.valueOf(slider.getValue()/10.0))); //nie bijce mnie za ta lambde :P uwielbiam je wciskac w C# :P
    }
}

class SliderPanelException extends Exception
{
    public SliderPanelException()
    {
        System.err.println("Minimal SliderPanel dimensions are: " + ApplicationConstraints.sliderPanelMinimalWidth + " x " + ApplicationConstraints.sliderPanelMinimalHeight);
    }
    public SliderPanelException(String s)
    {
        System.err.println(s);
    }
}
