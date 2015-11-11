package org.turing.app;

import org.turing.app.views.MainFrameView;
import org.turing.app.views.ProgramFrameView;
import org.turing.support.Constants;
import org.turing.support.Logger;
import org.turing.support.ResourceProvider;

import java.io.IOException;
import java.util.Properties;

public class Application {

    private Properties guiSettings;

    private final MainFrameView mainFrameView;
    private final ProgramFrameView programFrameView;

    public Application(MainFrameView mainFrameView, ProgramFrameView programFrameView) {
        this.mainFrameView = mainFrameView;
        this.programFrameView = programFrameView;
    }

    public void show() {
        mainFrameView.show();
        programFrameView.show();
    }

    public void loadApplicationData() throws IOException {
        ResourceProvider.loadPlatform(System.getProperty("os.name"));

//        try {
//            Logger.setOutput(ResourceProvider.getSupportFile(ResourceProvider.FILE_LOG).getAbsolutePath());
//        } catch (FileNotFoundException e) {
//            Logger.error(e);
//            System.exit(1);
//        }
        Logger.log("Application started.");
        Logger.log("Logger has been set up.");

        try {
            ResourceProvider.loadSettings();
            ResourceProvider.saveSettings();
        } catch (IOException e) {
            Logger.error(e);
            System.exit(1);
        }
        Logger.log("Loaded settings.");

        guiSettings = ResourceProvider.loadProperties(
                ResourceProvider.getSupportFile(ResourceProvider.FILE_GUI_SETTINGS),
                Constants.DEFAULT_GUI_SETTINGS);
        ResourceProvider.saveProperties(
                guiSettings,
                ResourceProvider.getSupportFile(ResourceProvider.FILE_GUI_SETTINGS));
        Logger.log("Loaded Gui Settings.");

        ResourceProvider.loadLocale(ResourceProvider.getSetting("locale"));
        Logger.log("Loaded localised dictionary for: " + ResourceProvider.getSetting("locale") + ".");
    }

    public Properties getGuiSettings() {
        return guiSettings;
    }
}
