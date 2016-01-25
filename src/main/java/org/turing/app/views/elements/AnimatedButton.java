package org.turing.app.views.elements;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Patryk Stopyra on 25.01.2016.
 */
public class AnimatedButton extends JButton {

    public AnimatedButton(Icon iconActive, Icon iconMouseOver, Icon iconPressed) {

            init(null, iconActive);

            setRolloverIcon(iconMouseOver);
            setPressedIcon(iconPressed);
            setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
            setBackground(Color.BLACK);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setAlignmentY(Component.TOP_ALIGNMENT);
            setAlignmentX(Component.CENTER_ALIGNMENT);
    }
}
