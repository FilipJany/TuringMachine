package org.turing.app.model;

import org.junit.Test;
import org.turing.app.common.MoveDirection;
import org.turing.app.common.State;
import org.turing.app.common.Symbol;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class RevertActionsModelTest {

    @Test
    public void letsSeeIfItWorksAsIThinkItWorks() throws Exception {
        //given
        RevertActionsModel model = new RevertActionsModel();
        final ActionTriple action1 = new ActionTriple(new State("1", false), new Symbol("1"), MoveDirection.LEFT);
        final ActionTriple action2 = new ActionTriple(new State("2", false), new Symbol("2"), MoveDirection.RIGHT);
        model.addNewRevertAction(action1);
        model.addNewRevertAction(action2);

        //when & then
        assertEquals(action2, model.getLatestRevertAction());
        assertEquals(action1, model.getLatestRevertAction());
        assertFalse(model.containsRevertActions());
    }
}