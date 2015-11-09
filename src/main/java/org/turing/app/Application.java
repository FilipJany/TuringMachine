package org.turing.app;

import org.turing.app.views.ActionsFrameView;
import org.turing.app.views.MainFrameView;

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
