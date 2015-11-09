package org.turing.support.platform;

import org.turing.support.Constants;

import java.io.File;

/**
 * @author Patryk Stopyra at Wroclaw University of Technology
 */
public class OSWindows extends OS {


    //STATIC FIELDS
    private static final String SUPPORT_DATA_DIRECTORY = "C:\\Program Files\\" + Constants.APPLICATION_NAME;

    //FIELDS
    private static OSWindows instance;

    //CONSTRUCTORS
    private OSWindows() {
        super(PlatformFamily.WINDOWS, System.getProperty("os.name"));
    }

    //STATIC METHODS    
    public static OSWindows getInstance() {
        if (instance != null) {
            return instance;
        } else {
            instance = new OSWindows();
            return instance;
        }
    }

    //METHODS
    @Override
    public File getSupportDataDirectory() {
        return new File(SUPPORT_DATA_DIRECTORY);
    }
}
