package com.jakubdziworski.myjshell;

/**
 * Created by kuba on 02.08.16.
 */
public interface CommandScannerListener {

    /**
     * @param command
     * @return String to display back as a response to command
     */
    String onCommandEntered(String command);
}
