package org.turing.app.views.elements;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * Created by Patryk Stopyra on 19.01.2016.
 *
 * Basing on http://java-swing-tips.blogspot.com
 */
public class RoundButton extends JButton {

    protected Shape shape, base;

    public RoundButton(Icon iconActive, Icon iconMouseOver, Icon iconPressed) {
        setModel(new DefaultButtonModel());
        init(null, iconActive);

        setRolloverIcon(iconMouseOver);
        setPressedIcon(iconPressed);
        setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
        setBackground(Color.BLACK);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setAlignmentY(Component.TOP_ALIGNMENT);
        setAlignmentX(Component.CENTER_ALIGNMENT);
        initShape();
    }


    protected void initShape() {
        if(!getBounds().equals(base)) {
            Dimension s = getPreferredSize();
            base = getBounds();
            shape = new Ellipse2D.Float(0, 0, s.width-1, s.height-1);
        }
    }

    @Override public Dimension getPreferredSize() {
        Icon icon = getIcon();
        Insets i = getInsets();
        int iw = Math.max(icon.getIconWidth(), icon.getIconHeight());
        return new Dimension(iw+i.right+i.left, iw+i.top+i.bottom);
    }

    @Override public boolean contains(int x, int y) {
        initShape();
        return shape.contains(x, y);
        //or return super.contains(x, y) && ((image.getRGB(x, y) >> 24) & 0xff) > 0;
    }
}
