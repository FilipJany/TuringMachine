package org.turing.app.common;

import org.junit.Test;

import static org.turing.app.common.BlankSymbol.BLANK;
import static org.junit.Assert.*;

public class InfiniteSymbolStackTest {

    private final Symbol s1 = new Symbol("λ");
    private final Symbol s2 = new Symbol("π");

    @Test
    public void pushAndPop() {
        InfiniteSymbolStack stack = new InfiniteSymbolStack();

        assertEquals(BLANK, stack.pop());

        stack.push(s1);
        assertEquals(s1, stack.pop());

        stack.push(s1);
        stack.push(s2);
        assertEquals(s2, stack.pop());
        assertEquals(s1, stack.pop());

        stack.push(s1);
        stack.push(BLANK);
        assertEquals(BLANK, stack.pop());
        assertEquals(s1, stack.pop());
    }

    @Test
    public void searchOnEmptyStack() {
        InfiniteSymbolStack stack = new InfiniteSymbolStack();

        assertEquals(-1, stack.search(s1));
        assertEquals(0, stack.search(BLANK));
    }

    @Test
    public void searchOnOneElementStack() {
        InfiniteSymbolStack stack = new InfiniteSymbolStack();
        stack.push(s1);

        assertEquals(-1, stack.search(s2));
        assertEquals(0, stack.search(s1));
        assertEquals(1, stack.search(BLANK));

        stack = new InfiniteSymbolStack();
        stack.push(BLANK);

        assertEquals(-1, stack.search(s1));
        assertEquals(0, stack.search(BLANK));
    }

    @Test
    public void searchOnTwoElementStack() {
        InfiniteSymbolStack stack = new InfiniteSymbolStack();
        stack.push(s1);
        stack.push(BLANK);

        assertEquals(-1, stack.search(s2));
        assertEquals(1, stack.search(s1));
        assertEquals(0, stack.search(BLANK));
    }

    @Test
    public void replaceShouldAddBlanksProperly() {
        InfiniteSymbolStack stack = new InfiniteSymbolStack();

        stack.replace(s1, 0);
        assertEquals(s1, stack.pop());
        assertEquals(BLANK, stack.pop());

        stack.add(s1);
        stack.replace(s2, 0);
        assertEquals(s2, stack.pop());
        assertEquals(BLANK, stack.pop());

        stack.replace(s2, 3);
        assertEquals(BLANK, stack.pop());
        assertEquals(BLANK, stack.pop());
        assertEquals(BLANK, stack.pop());
        assertEquals(s2, stack.pop());
        assertEquals(BLANK, stack.pop());

        stack.add(s1);
        stack.replace(s2, 3);
        assertEquals(s1, stack.pop());
        assertEquals(BLANK, stack.pop());
        assertEquals(BLANK, stack.pop());
        assertEquals(s2, stack.pop());
        assertEquals(BLANK, stack.pop());
    }

    @Test
    public void replaceShouldTrimTailingBlanks() {
        InfiniteSymbolStack stack = new InfiniteSymbolStack();

        stack.replace(s1, 0);
        stack.replace(BLANK, 0);
        assertEquals(0, stack.size());

        stack.replace(s2, 3);
        assertEquals(4, stack.size());
        stack.replace(BLANK, 3);
        assertEquals(0, stack.size());

        stack.push(s1);
        stack.replace(s2, 3);
        assertEquals(4, stack.size());
        stack.replace(BLANK, 3);
        assertEquals(1, stack.size());
    }

}
