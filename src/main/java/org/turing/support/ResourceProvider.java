package org.turing.support;

import org.turing.support.platform.OS;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.util.*;


/**
 * Universal resource provider for localized strings, images, icons, etc.
 * Created to provide light easy-to-use and easy-to-modify provider for
 * applications.
 * <p>
 * Contains resources paths:
 * <p>
 * <ol>
 * <li>Supported languages</li>
 * <li>Supported platforms</li>
 * <li>Application support directories</li>
 * <li>Application support files</li>
 * <li>Images</li>
 * <li>Icons</li>
 * <li>Audio</li>
 * </ol>
 * <p>
 * Usage:
 * <p>
 * <ul>
 * <li>Application start:</li>
 * <ol>
 * <li>loadPlatform (ommit while debbuging)</li>
 * <li>loadSettings (ommit while debbuging)</li>
 * <li>loadLocale (invoke with value from settings or System.properties)</li>
 * </ol>
 * <li>Runtime:</li>
 * <ul>
 * <li>getSupportFile</li>
 * <li>getLocalisedString</li>
 * <li>getImage</li>
 * <li>getIcon</li>
 * <li>getAudio</li>
 * <li>getSetting</li>
 * <li>setSetting</li>
 * </ul>
 * <li>Application closing:</li>
 * <ol>
 * <li>saveSettings (ommit while debbuging)</li>
 * </ol>
 * </ul>
 *
 * @author Patryk Stopyra at Wroclaw University of Technology
 */
public class ResourceProvider {

    //STATIC FIELDS: Supported languages
    static public final String en_EN = "English";

    public static final HashMap<String, Locale> supportedLocales = new HashMap<>();

    static {
        supportedLocales.put(en_EN, new Locale("EN", "en"));
    }

    //STATIC FIELDS: Application support directories
    static public final String[] appDirectory = new String[]{
            "/properties",
            "/licence",
            "/log"
    };

    //STATIC FIELDS: Application support files

    static public final String DIR_PROPERTIES = "/properties/";
    static public final String FILE_SETTINGS = DIR_PROPERTIES + "Settings.properties";
    static public final String FILE_GUI_SETTINGS = DIR_PROPERTIES + "GuiSettings.properties";

    static public final String DIR_LOGS = "/log/";
    static public final String FILE_LOG = DIR_LOGS + "TuringMachine.log";

    static public final String DIR_LICENCE = "/licence/";

    //STATIC FIELDS: Images
    static public final String APPLICATION_LOGO = "turing_logo_500x500.png";
    static public final String APPLICATION_LOGO_SMALL = "turing_logo_20x20.png";

    //STATIC FIELDS: Icons

    //STATIC FIELDS: Audio

    //STATIC FIELDS
    private static ResourceBundle dictionary;
    private static OS platform;
    private static Properties settings;

    //STATIC 

    /**
     * Returns support file for given name. Allowed names should be listed as
     * <code>final</code> variables under section "Application support files".
     * All files are in specified subdirectory of application's specyfic support
     * directory.
     * <p>
     *
     * @param path file path relative to main app's support directory
     * @return File object representing given pathname.
     */
    public static File getSupportFile(String path) {
        if (platform == null)
            throw new MissingResourceException(
                    "Cannot get application support directory file before platform is set up.",
                    ResourceProvider.class.getName(),
                    path);

        return new File(platform.getSupportDataDirectory() + path);
    }

    /**
     * Returns String denoted by provided key.
     * <p>
     * If dictionary is uninitialised returns empty String. Never returns
     * <code>null</code>.
     * <p>
     *
     * @param key key of sentence
     * @return Sentence denoted by key or empty String.
     */
    public static String getLocalisedString(String key) {
        String localizedString = "";

        if (dictionary == null) {
            Logger.warning("Dictionary is not set up.");
            return localizedString;
        }

        try {
            localizedString = dictionary.getString(key);
        } catch (MissingResourceException ex) {
            Logger.warning("Dictionary doesn't contain any object for key \"" + key + "\".");
        }

        return localizedString;
    }

    /**
     * Returns String denoted by provided key.
     * Additionally replaces every special <code>GAP_MARKER</code> i.e.
     * "<code>{}</code>" with provided arg var-arguments.
     * <p>
     * If dictionary is uninitialised returns empty String. Never returns
     * <code>null</code>.
     * <p>
     *
     * @param key key of sentence
     * @param arg list of arguments to be inserted in gaps
     * @return Sentence denoted by key with filled gaps or empty String.
     */
    public static String getLocalisedString(String key, Object... arg) {
        String localizedString = getLocalisedString(key);

        for (Object o : arg)
            localizedString = localizedString.replaceFirst(Constants.GAP_MARKER, o.toString());

        return localizedString;
    }

    /**
     * Returns image from application internal resources. Allowed names should
     * be listed as <code>final</code> variables under section "Images".
     * <p>
     * If error while reading <code>null</code> image is returned.
     * <p>
     *
     * @param name image name
     * @return Image of given name or <code>null</code>.
     */
    public static Image getImage(String name) {
        URL path = ResourceProvider.class.getResource(
                Constants.IMAGES_PATH + name);
        Image img = null;

        try {
            img = ImageIO.read(path);
        } catch (IllegalArgumentException | IOException ex) {
            Logger.error("Could not read resource: \"" + name + "\" image not found.");
        }

        return img;
    }

    /**
     * Returns ImageIcon from application internal resources. Allowed names
     * should be listed as <code>final</code> variables under section "Icons".
     * <p>
     * If error while reading <code>null</code> icon is returned.
     * <p>
     *
     * @param name image name
     * @return ImageIcon of given name or <code>null</code>.
     */
    public static ImageIcon getIcon(String name) {
        URL path = ResourceProvider.class.getResource(
                Constants.ICONS_PATH + name);

        if (path != null)
            return new ImageIcon(path);
        else
            return null;
    }

    /**
     * Returns Clip from application internal resources. Allowed names
     * should be listed as <code>final</code> variables under section "Audio".
     * <p>
     * If error while reading <code>null</code> clip is returned.
     * <p>
     *
     * @param name audio name
     * @return Clip of given name or <code>null</code>.
     */
    public static Clip getAudio(String name) {
        Clip audio;

        try {
            audio = AudioSystem.getClip();
            audio.open(AudioSystem.getAudioInputStream(ResourceProvider.class.getResourceAsStream(Constants.AUDIO_PATH + name)));
        } catch (IOException ex) {
            Logger.error("Could not read resource: \"" + name + "\" audio not found.");
            audio = null;
        } catch (UnsupportedAudioFileException ex) {
            Logger.error("Unsupported resource format: \"" + name + "\" audio could not be read.");
            audio = null;
        } catch (LineUnavailableException ex) {
            Logger.error("Line is busy: \"" + name + "\" audio could not be read.");
            audio = null;
        }

        return audio;
    }

    /**
     * Returns application setting denoted by key, as String.
     * <p>
     * If there is no setting associated with the key <code>null</code> is
     * returned.
     * <p>
     *
     * @param key key of setting
     * @return String value of ordered setting or <code>null</code>.
     */
    public static String getSetting(String key) {
        return settings.getProperty(key);
    }

    /**
     * Sets application setting denoted by key on given value.
     * <p>
     *
     * @param key   key of setting
     * @param value value of setting
     */
    public static void setSetting(String key, String value) {
        settings.setProperty(key, value);
    }

    /**
     * Selects proper object to support application backend everywhere, where
     * this support is platform-dependent. Providing proper OS-dependent object
     * is done by fitting platformName (which should be intentionally "os.name"
     * System
     * property) to one of supported platforms listed in section "Supported
     * platforms".
     * <p>
     * Moreover if any of items from "Application support directories" section
     * is is not present in proper application support directory, OS-dependent
     * object will set it up.
     * <p>
     * Sets up application support directory and it's subdirectories and
     * provides platform support. If platform is not supported or any of support
     * directories cannot be set up, <code>IOException</code> being thrown.
     * <p>
     *
     * @param platformName common name of platform (which should be
     *                     intentionally representation of "os.name" System property)
     * @throws IOException if platform is not supported or any of support
     *                     directories cannot be set up
     */
    public static void loadPlatform(String platformName) throws IOException {
        OS.PlatformFamily currentPlatform = detectPlatform(platformName);

        if (currentPlatform == null) {
            Logger.error("Unsupported platform \"" + platformName + "\".");
            throw new IOException("Unsupported platform \"" + platformName + "\".");
        }

        platform = OS.getPlatformInstance(currentPlatform);
        if (!platform.setupSupportDataDirectory(appDirectory)) {
            Logger.error("Could not create or find all essential application directories.");
            throw new IOException("Could not create or find all essential application directories.");
        }
    }

    /**
     * Loads default settings from application internal resources.
     * <p>
     * If default settings properties file cannot be loaded
     * <code>IOException</code> is being raised. It should be critical error for
     * application.
     * <p>
     *
     * @throws IOException when default settings file cannot be loaded
     */
    public static void loadDefaultSettings() throws IOException {
        try (InputStream propertiesStream = ResourceProvider.class.getResourceAsStream(Constants.DEFAULT_SETTINGS_PATH)) {
            if (propertiesStream == null) {
                Logger.error("Cannot read default settings file.");
                throw new IOException("Cannot read default settings file.");
            }
            settings = new Properties();
            settings.load(propertiesStream);
        } catch (FileNotFoundException ex) {
            Logger.error("Default settings file is missing.");
            throw new IOException("Default settings file is missing.");
        }
    }

    /**
     * Loads default settings from application internal resources, then loads
     * user's settings.
     * <p>
     * If user's settings cannot be found, default settings are loaded and
     * <code>MissingResourceException</code> is being raised and application can
     * work properly. If default settings properties file cannot be loaded
     * <code>IOException</code> is being raised and application should be
     * stopped.
     * <p>
     *
     * @throws IOException when default settings file cannot be loaded
     */
    public static void loadSettings() throws IOException {
        loadDefaultSettings();

        if (platform == null)
            loadPlatform(System.getProperty("os.name"));

        try (FileInputStream propertiesStream = new FileInputStream(getSupportFile(FILE_SETTINGS))) {
            settings = new Properties(settings);
            settings.load(propertiesStream);
        } catch (Exception ex) {
            Logger.warning("Cannot read user's \"Settings\" file. Loaded default settings.");
//            throw new MissingResourceException(
//                    "Cannot read user's \"Settings\" file. Loaded default settings.",
//                    ResourceProvider.class.getName(),
//                    getSupportFile(FILE_SETTINGS).getPath());
        }
    }

    /**
     * Saves application settings modified (consciously or unconsciously) by
     * user.
     * <p>
     * If, for any reason, application settings cannot been saved to it's
     * destinated loaction in support directory <code>IOException</code> should
     * be thrown. However it is registered by <code>Logger</code> as warning,
     * because this operation is normally done at the end of application
     * workflow and doesn't disturb it.
     * <p>
     *
     * @throws IOException if application settings couldn't be saved to it's
     *                     destinated location
     */
    public static void saveSettings() throws IOException {
        if (platform == null)
            loadPlatform(System.getProperty("os.name"));

        File settingsFile = getSupportFile(FILE_SETTINGS);

        try {
            if (!settingsFile.isFile())
                settingsFile.createNewFile();
        } catch (IOException ex) {
            Logger.warning("Could not write user settings to file. " + ex.getMessage() + "\n " + settingsFile.getAbsolutePath());
            throw new IOException("Could not write user settings to file. " + ex.getMessage());
        }

        try (FileOutputStream propertiesStream = new FileOutputStream(settingsFile)) {
            settings.store(propertiesStream, null);
        } catch (IOException ex) {
            Logger.warning("Could not write user settings to file. " + ex.getMessage() + "\n " + settingsFile.getAbsolutePath());
            throw new IOException("Could not write user settings to file. " + ex.getMessage());
        }
    }

    /**
     * Sets up dictionary proper to support local denoted by localName, which
     * should be one of listed in section "Supported languages".
     * <p>
     * If localeName denotes unsupported language or ResourceBundle cannot load
     * proper dictionary <code>IOException</code> is being thrown, what should
     * be critical error for application.
     * <p>
     *
     * @param localeName name of supported locale (ex. "en_EN", "en_US"
     * @throws IOException ff localeName denotes unsupported language or
     *                     ResourceBundle cannot load proper dictionary
     */
    public static void loadLocale(String localeName) throws IOException {
        Locale locale = supportedLocales.get(localeName);

        if (locale == null) {
            Logger.error("Unsupported language \"" + localeName + "\".");
            throw new IOException("Unsupported language \"" + localeName + "\".");
        }

        try {
            dictionary = ResourceBundle.getBundle(Constants.DICTIONARY_PATH, locale);
        } catch (Exception ex) {
            Logger.error("Cannot obtain language boundle for " + localeName + ". " + ex.getMessage());
            throw new IOException(ex.getMessage());
        }
    }

    /**
     * Loads default file of custom properties from application internal
     * resources.
     * <p>
     * If default file referred by propertiesPath cannot be loaded
     * <code>IOException</code> is being raised. It should be critical error for
     * application.
     * <p>
     *
     * @param propertiesPath path to default properties file
     * @return default properties object
     * @throws IOException when default properties file cannot be loaded
     */
    public static Properties loadDefaultProperties(String propertiesPath) throws IOException {
        Properties defaultProperties;

        try (InputStream propertiesStream = ResourceProvider.class.getResourceAsStream(propertiesPath)) {
            if (propertiesStream == null) {
                Logger.error("Cannot read default properties file \"" + propertiesPath + "\".");
                throw new IOException("Cannot read default properties file \"" + propertiesPath + "\".");
            }
            defaultProperties = new Properties();
            defaultProperties.load(propertiesStream);
        } catch (FileNotFoundException ex) {
            Logger.error("Default properties file \"" + propertiesPath + "\" is missing.");
            throw new IOException("Default properties file \"" + propertiesPath + "\" is missing.");
        }

        return defaultProperties;
    }

    /**
     * Loads default file of custom properties from application internal
     * resources, then loads user's file of custom properties (intentionally
     * from application's support directory).
     * <p>
     * If user's file cannot be found, default properties file are loaded and
     * <code>MissingResourceException</code> is being raised and application can
     * work properly. If default properties file cannot be loaded
     * <code>IOException</code> is being raised and application should be
     * stopped.
     * <p>
     *
     * @param propertiesFile        File of user's properties (intentionally somewhere
     *                              in an Application Support Directory)
     * @param defaultPropertiesPath path to properties file in application
     *                              internal resources
     * @return user's custom properties object
     * @throws IOException when custom properties default file cannot be loaded
     */
    public static Properties loadProperties(File propertiesFile, String defaultPropertiesPath) throws IOException {
        Properties defaultProperties = loadDefaultProperties(defaultPropertiesPath);

        if (propertiesFile == null) {
            Logger.warning("Custom properties file not provided. Loaded default properties instead.");
            return defaultProperties;
        }
        if (!propertiesFile.isFile()) {
            Logger.warning("Properties file " + propertiesFile.getName() + " could not been loaded. Loaded default properties instead.");
            return defaultProperties;
        }

        Properties properties = new Properties(defaultProperties);

        try (FileInputStream propertiesStream = new FileInputStream(propertiesFile)) {
            properties.load(propertiesStream);
        } catch (Exception ex) {
            Logger.warning("Properties file " + propertiesFile.getName() + " could not been loaded. Loaded default properties instead.");
//            throw new MissingResourceException(
//                    "Properties file " + propertiesFile.getName() + " could not been loaded. Loaded default properties instead.",
//                    ResourceProvider.class.getName(),
//                    propertiesFile.getPath());
        }

        return properties;
    }

    /**
     * Saves pointed properties in given propertiesFile if it's possible.
     * <p>
     * If, for any reason, application custom properties cannot been saved to
     * it's destinated loaction in support directory <code>IOException</code>
     * should be thrown. However it is registered by <code>Logger</code> as
     * warning, because this operation normally doesn't disturb application
     * workflow.
     * <p>
     *
     * @param properties     custom Properties object to be written into submited
     *                       file.
     * @param propertiesFile file, into which properties should be written
     * @throws IOException if properties object couldn't be saved to it's
     *                     destinated file
     */
    public static void saveProperties(Properties properties, File propertiesFile) throws IOException {
        if (properties == null) {
            Logger.warning("Attemption to save non existing properties.");
            throw new NullPointerException("Attemption to save non existing properties.");
        }
        if (propertiesFile == null) {
            Logger.warning("Attemption to save properties in undefined file.");
            throw new NullPointerException("Attemption to save properties in undefined file.");
        }
        if (platform == null)
            loadPlatform(System.getProperty("os.name"));

        try {
            if (!propertiesFile.isFile())
                propertiesFile.createNewFile();
        } catch (IOException ex) {
            Logger.warning("Attemption to save properties in undefined file.");
            throw new IOException("Could not write to file \"" + propertiesFile + "\". " + ex.getMessage());
        }

        try (FileOutputStream propertiesStream = new FileOutputStream(propertiesFile)) {
            properties.store(propertiesStream, null);
        } catch (IOException ex) {
            Logger.warning("Attemption to save properties in undefined file.");
            throw new IOException("Could not write to file \"" + propertiesFile + "\". " + ex.getMessage());
        }
    }


    public static void exportFromPackage(String path, File file) throws IOException {
        if (!file.exists())
            return;

        try (InputStream inputStream = ResourceProvider.class.getResourceAsStream(path)) {
            if (inputStream == null) {
                Logger.error("Cannot read " + path + " file.");
                throw new IOException("Cannot read " + path + " file.");
            }
            Files.copy(inputStream, file.toPath());
        } catch (IOException ex) {
            Logger.error("File " + path + " couldn't be exported: " + ex.getMessage());
            throw new IOException("File " + path + " couldn't be exported: " + ex.getMessage());
        }
    }

    public static File getApplicationSupportDirectory() {
        if (platform != null) {
            return platform.getSupportDataDirectory();
        }

        return null;
    }

    private static OS.PlatformFamily detectPlatform(String platform) {
        platform = platform.toLowerCase();

        if (platform.contains("mac")) {
            return OS.PlatformFamily.MAC;
        } else if (platform.contains("nix") || platform.contains("nux") || platform.indexOf("aix") > 0) {
            return OS.PlatformFamily.LINUX;
        } else if (platform.contains("win")) {
            return OS.PlatformFamily.WINDOWS;
        }
        return null;
    }


    //PRIVATE CLASS
    private static class Constants {

        /**
         * Images location relative to provider.
         */
        static final String IMAGES_PATH = "/org/turing/resources/images/";
        /**
         * Icons location relative to provider.
         */
        static final String ICONS_PATH = "/org/turing/resources/icons/";
        /**
         * Sound location relative to provider
         */
        static final String AUDIO_PATH = "/org/turing/resources/audio/";
        /**
         * DefaultSettings location relative to provider
         */
        static final String DEFAULT_SETTINGS_PATH = "/org/turing/resources/data/DefaultSettings.properties";
        /**
         * Dictionary location
         */
        static final String DICTIONARY_PATH = "org.turing.resources.locales.Dictionary";
        /**
         * Special character in localized string that is changed for provided
         * following arguments.
         */
        static final String GAP_MARKER = "\\{\\}";
    }
}
