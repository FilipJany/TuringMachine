package org.turing.support.wrappers;

import java.util.Properties;

/**
 * Wrapper is a custom class allowing easier property values reading.
 *
 * @author Patryk Stopyra at Wroclaw University of Technology
 */
public abstract class PropertiesWrapper extends Properties {
    //FIELDS

    //CONSTRUCTORS
    public PropertiesWrapper(Properties properties) {
        super(properties);
    }

    //METHODS
    public int getIntegerProperty(String key) {
        return Integer.parseInt(getProperty(key));
    }
}
