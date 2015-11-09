package org.turing.support;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;


/**
 * Logger to provide internal info about program execution.
 *
 * @author Patryk Stopyra
 */
public class Logger {

    //STATIC FIELDS
    private static PrintStream output = System.out;
    private static DateFormat formatter = new SimpleDateFormat("EEE-DD-yyyy HH:mm:ss:SSS ", Locale.US);
    private static DateFormat nameFormatter = new SimpleDateFormat("_dd_MM_yyyy_HH_mm", Locale.US);

    //STATIC METHODS
    public static void log(String s) {
        if (output != null)
            output.println(time() + "\u001B[34m" + "[INFO] " + "\u001B[0m" + s);
    }

    public static void warning(String s) {
        if (output != null)
            output.println(time() + "\u001B[35m" + "[WARNING] " + "\u001B[0m" + s);
    }

    public static void warning(Exception e) {
        if (output != null)
            output.println(time() + "\u001B[35m" + "[WARNING] " + "\u001B[0m" + e + "\n\t"
                    + Arrays.toString(
                    e.getStackTrace()).replace(", ", "]\n\t\t["));
    }

    public static void error(String s) {
        if (output != null)
            output.println(time() + "\u001B[31m" + "[ERROR] " + "\u001B[0m" + s);
    }

    public static void error(Exception e) {
        if (output != null)
            output.println(time() + "\u001B[31m" + "[ERROR] " + "\u001B[0m" + e + "\n\t"
                    + Arrays.toString(
                    e.getStackTrace()).replace(", ", "]\n\t\t["));
    }

    public static void setDateFormat(DateFormat f) {
        formatter = f;
    }

    public static void setOutput(String path) throws FileNotFoundException {
        if (path != null)
            output = new PrintStream(new File(path + nameFormatter.format(new Date())));
    }

    public static void close() {
        if (!output.equals(System.out) && !output.equals(System.err)) {
            output.close();
        }
    }

    private static String time() {
        return formatter.format(new Date());
    }
}
