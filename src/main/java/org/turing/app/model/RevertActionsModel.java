package org.turing.app.model;

import com.google.common.collect.Lists;

import java.util.Deque;

public class RevertActionsModel {

    private final Deque<ActionTriple> revertActionsStack = Lists.newLinkedList();

    public void addNewRevertAction(ActionTriple action) {
        revertActionsStack.push(action);
    }

    public ActionTriple getLatestRevertAction() {
        return revertActionsStack.pop();
    }

    public boolean containsRevertActions() {
        return !revertActionsStack.isEmpty();
    }
}
