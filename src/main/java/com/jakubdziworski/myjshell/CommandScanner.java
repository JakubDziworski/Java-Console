package com.jakubdziworski.myjshell;


import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * Created by kuba on 02.08.16.
 */
public class CommandScanner {

    private final CommandScannerListener commandScannerListener;

    public CommandScanner(CommandScannerListener commandScannerListener) {
        this.commandScannerListener = commandScannerListener;
    }

    public void startScanningForCommands() {
        startScanningForCommands(System.in, System.out);
    }

    public void startScanningForCommands(InputStream inputStream, PrintStream outputStream) {
        Scanner reader = new Scanner(inputStream);
        outputStream.println("Welcome to MyJShell");
        while (reader.hasNextLine()) {
            String command = reader.nextLine();
            if(StringUtils.isBlank(command)) continue;
            String result = commandScannerListener.onCommandEntered(command);
            outputStream.println(result);
        }
        reader.close();
    }

}
