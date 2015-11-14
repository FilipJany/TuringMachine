package org.turing.app.common;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class BlankSymbolTest {

    @Test
    public void shouldNotBeEqualToAnyOtherSymbol() throws Exception {
        assertFalse(BlankSymbol.BLANK.equals(new Symbol("")));
        assertFalse(new Symbol("").equals(BlankSymbol.BLANK));
    }

    @Test
    public void shouldBeEqualToItself() throws Exception {
        assertEquals(BlankSymbol.BLANK, BlankSymbol.BLANK);
    }
}