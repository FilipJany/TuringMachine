package org.turing.app.views.elements;

import org.turing.app.common.MoveDirection;
import org.turing.app.common.State;
import org.turing.app.common.Symbol;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import java.awt.*;

import static org.turing.app.common.BlankSymbol.BLANK;

/**
 * Created by Patryk Stopyra on 31.12.2015.
 */
public class ActionTripleComboBox<T> extends JComboBox<T> {

    public ActionTripleComboBox() {
        super();

        initComponent();
    }

    public ActionTripleComboBox(T[] items) {
        super(items);

        initComponent();
    }

    private void initComponent() {
        setRenderer(new ActionTripleComboBoxRenderer());
    }
}

class ActionTripleComboBoxRenderer extends BasicComboBoxRenderer {
    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        if (value instanceof State)
            setText(((State) value).getName());
        else if (value instanceof Symbol)
            if (value.equals(BLANK))
                setText(BLANK.visibleRep());
            else
                setText(((Symbol) value).getValue());
        else if (value instanceof MoveDirection)
            setText(((MoveDirection) value).getSymbol());

        return this;
    }
}
