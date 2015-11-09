package org.turing.support;

import javax.swing.*;
import java.awt.*;


/**
 * GuiLogger is a class to provide unified logging style in whole application
 * and present custom dialog types.
 * <p>
 *
 * @author Patryk Stopyra
 */
public class LoggerGUI {

    //STATIC FIELDS
    public static final int YES = JOptionPane.YES_OPTION;
    public static final int NO = JOptionPane.NO_OPTION;
    public static final int CANCEL = JOptionPane.CANCEL_OPTION;
    public static final int OK = JOptionPane.OK_OPTION;
    public static final int CLOSED = JOptionPane.CLOSED_OPTION;

    private static final String INFO = "info";
    private static final String WARNING = "warning";
    private static final String ERROR = "error";
    private static final String QUESTION = "question";

    private static final String appName = Constants.APPLICATION_REAL;

    //STATIC METHODS
    public static void showInfoDialog(Component owner, Object text, String title) {
        JOptionPane.showMessageDialog(owner, text, getTitle(title, INFO), JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showWarningDialog(Component owner, Object text, String title) {
        JOptionPane.showMessageDialog(owner, text, getTitle(title, WARNING), JOptionPane.WARNING_MESSAGE);
    }

    public static void showErrorDialog(Component owner, Object text, String title) {
        JOptionPane.showMessageDialog(owner, text, getTitle(title, ERROR), JOptionPane.ERROR_MESSAGE);
    }

    public static String showInputDialog(Component owner, Object text, String title) {
        return JOptionPane.showInputDialog(owner, text, getTitle(title, QUESTION), JOptionPane.PLAIN_MESSAGE);
    }

    public static int showQuestionDialogYesNoCancel(Component owner, Object text, String title) {
        Object[] buttonNames = {org.turing.support.ResourceProvider.getLocalisedString("yes"),
                org.turing.support.ResourceProvider.getLocalisedString("no"),
                ResourceProvider.getLocalisedString("cancel")};
        return JOptionPane.showOptionDialog(owner, text, getTitle(title, QUESTION),
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, buttonNames, buttonNames[0]);
    }

    public static int showQuestionDialogYesNo(Component owner, Object text, String title) {
        Object[] buttonNames = {ResourceProvider.getLocalisedString("yes"),
                ResourceProvider.getLocalisedString("no")};
        return JOptionPane.showOptionDialog(owner, text, getTitle(title, QUESTION),
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, buttonNames, buttonNames[0]);
    }

    public static int showCustomDialogOkCancel(Component owner, Object content, String title) {
        Object[] buttonNames = {ResourceProvider.getLocalisedString("ok"),
                ResourceProvider.getLocalisedString("cancel")};
        return JOptionPane.showOptionDialog(owner, content, getTitle(title, QUESTION),
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, buttonNames, buttonNames[0]);
    }

    public static int showLongOutputError(Component owner, String text, String title) {
        Object[] buttonNames = {ResourceProvider.getLocalisedString("ok")};

        JTextArea textArea = new JTextArea(text);
        textArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(800, 200));

        return JOptionPane.showOptionDialog(owner, scrollPane, getTitle(title, QUESTION),
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE,
                null, buttonNames, buttonNames[0]);
    }

    private static String getTitle(String title, String type) {
        if (title != null) {
            return title;
        } else if (appName != null) {
            return appName + " (" + ResourceProvider.getLocalisedString(type) + ")";
        } else {
            return ResourceProvider.getLocalisedString(type);
        }
    }
}
