package org.turing.app.common;

import org.turing.app.exceptions.OperationNotAllowedException;

import java.util.ArrayList;
import java.util.Stack;

import static org.turing.app.common.BlankSymbol.BLANK;

public class InfiniteSymbolStack extends Stack<Symbol> {

    public InfiniteSymbolStack() {
        super();
    }

    @Override
    public synchronized Symbol pop() {
        if (empty())
            return BLANK;

        return super.pop();
    }

    @Override
    public synchronized Symbol peek() {
        if (empty())
            return BLANK;

        return super.peek();
    }

    @Override
    public synchronized int search(Object o) {
        if (super.search(o) == -1) {
            if (o instanceof BlankSymbol)
                return size();
            else
                return -1;
        }

        return super.search(o) - 1;
    }

    @Override
    public synchronized void setElementAt(Symbol obj, int index) {
        throw new OperationNotAllowedException(); // For safety reasons
    }

    public synchronized void replace(Symbol symbol, int index) {
        if (index < size()) {
            super.setElementAt(symbol, size() - 1 - index);
        } else {
            ArrayList<Symbol> insertionList = new ArrayList<>(index - size() + 1);

            insertionList.add(symbol);
            for (int i = size(); i < index; i++)
                insertionList.add(BLANK);

            addAll(0, insertionList);
        }

        trimBlanks();
    }

    private void trimBlanks() {
        int i = 0;
        int toRemove = 0;

        while (i < size() && get(i++).equals(BLANK))
            toRemove++;

        removeRange(0, toRemove);
    }
}
