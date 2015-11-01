package org.turing;

import org.turing.views.ActionsFrameView;
import org.turing.views.MainFrameView;

public class Application {

    private final MainFrameView mainFrameView;
    private final ActionsFrameView actionsFrameView;

    public Application(MainFrameView mainFrameView, ActionsFrameView actionsFrameView) {
        this.mainFrameView = mainFrameView;
        this.actionsFrameView = actionsFrameView;
    }

    public void show() {
        mainFrameView.show();
        actionsFrameView.show();
    }
}
