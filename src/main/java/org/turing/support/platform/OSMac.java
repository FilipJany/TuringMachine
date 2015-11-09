package org.turing.support.platform;

import org.turing.support.Constants;

import java.io.File;


/**
 * @author Patryk Stopyra at Wroclaw University of Technology
 */
public class OSMac extends OS {

    //STATIC FIELDS
    private static final String SUPPORT_DATA_DIRECTORY = System.getProperty("user.home") + "/Library/Application Support/" + Constants.APPLICATION_NAME;

    //FIELDS
    private static OSMac instance;

    //CONSTRUCTORS
    private OSMac() {
        super(PlatformFamily.MAC, System.getProperty("os.name"));

        System.setProperty("apple.laf.useScreenMenuBar", "true"); // enables default-look screen ScrollBar
    }

    //STATIC METHODS    
    public static OSMac getInstance() {
        if (instance != null) {
            return instance;
        } else {
            instance = new OSMac();
            return instance;
        }
    }

    //METHODS
    @Override
    public File getSupportDataDirectory() {
        return new File(SUPPORT_DATA_DIRECTORY);
    }
}
