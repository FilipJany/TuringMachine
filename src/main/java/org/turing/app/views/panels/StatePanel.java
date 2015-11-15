package org.turing.app.views.panels;

import javax.swing.*;
import java.awt.*;

/**
 * Created by FiFi on 2015-11-09.
 */
public class StatePanel extends JPanel
{
    private JLabel stateLabel;

    public StatePanel()
    {
        initStatePanel();
    }

    public StatePanel(int width, int height) { initStatePanel(); }


    private void initStatePanel()
    {
        createStatePanel();
        setPanelProperties();
        addComponentToPanel();
    }

    private void createStatePanel()
    {
        stateLabel = new JLabel("State");
    }

    private void setPanelProperties()
    {
    }

    private void addComponentToPanel()
    {
        add(stateLabel);
    }
}
