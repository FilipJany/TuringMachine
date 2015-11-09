package org.turing.support.platform;

import org.turing.support.Constants;

import java.io.File;

/**
 * @author Patryk Stopyra at Wroclaw University of Technology
 */
public class OSLinux extends OS {


    //STATIC FIELDS
    private static final String SUPPORT_DATA_DIRECTORY = "/usr/lib/" + Constants.APPLICATION_UNIX;

    //FIELDS
    private static OSLinux instance;

    //CONSTRUCTORS
    private OSLinux() {
        super(PlatformFamily.LINUX, System.getProperty("os.name"));
    }

    //STATIC METHODS    
    public static OSLinux getInstance() {
        if (instance != null) {
            return instance;
        } else {
            instance = new OSLinux();
            return instance;
        }
    }

    //METHODS
    @Override
    public File getSupportDataDirectory() {
        return new File(SUPPORT_DATA_DIRECTORY);
    }

}
