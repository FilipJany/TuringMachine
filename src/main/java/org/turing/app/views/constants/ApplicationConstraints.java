package org.turing.app.views.constants;

import org.turing.app.model.ExecutionModel;

import java.awt.*;

/**
 * Created by fifi on 15.11.2015.
 */
public  class ApplicationConstraints
{
    //General
    public final static Color background = new Color(217, 205, 188);//new Color(173, 157, 139);
    //MainFrame
    public final static int mainFrameMinimalWidth = 770, mainFrameMinimalHeight = 300;
    public final static int mainFrameStartLocationX = 30, mainFrameStartLocationY = 30;
    //ControlPanel
    public final static int minimalControlPanelWidth = 350, minimalControlPanelHeight = 80, buttonHeight = 30, buttonWidth = 80, minimalButtonHeight = 20, minimalButtonWidth = 50;
    //SliderPanel
    public final static int sliderMinValue = ExecutionModel.MIN_DELAY, sliderInitValue = ExecutionModel.MAX_DELAY /2, sliderMaxValue = ExecutionModel.MAX_DELAY, sliderStep = 5, sliderSteppie = 1;
    public static int sliderPanelMinimalWidth = 350, sliderPanelMinimalHeight = 80;
    public static String sliderSpeedLabelPrefix = "Current speed multiplier: x";
    //Tape Panel
    public final static int minimalTapePanelWidth = 500, minimalTapePanelHeight = 200, textFieldHigh = 30, textFieldWidth = 30;
    public static int TapeSize = 15;
    //ProgramFrame
    public final static int programFrameMinimalWidth = 600, programFrameMinimalHeight = 300;
    public final static int programFrameStartLocationX = 810, programFrameStartLocationY = 30;
    //TablePanel
    public final static int tablePanelButtonHeight = 18, tablePanelButtonWidth = 18;
    public final static int tableMinimalHeight = 300, tableMinimalWidth = 500;
    public final static int columnWidth = 80, selectedColumnWidth = 250;
}
