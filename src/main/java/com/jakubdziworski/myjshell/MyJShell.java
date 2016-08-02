package com.jakubdziworski.myjshell;

import jdk.jshell.JShell;
import jdk.jshell.SnippetEvent;

import java.util.List;

/**
 * Created by kuba on 02.08.16.
 */
public class MyJShell implements CommandScannerListener {

    private JShell jShell;
    private SnippetResolver snippetResolver;

    private MyJShell() {
        snippetResolver = new SnippetResolver();
        jShell = JShell.create();
    }

    public static MyJShell create() {
        return new MyJShell();
    }

    public void startSession() {
        new CommandScanner(this).startScanningForCommands();
    }

    @Override
    public String onCommandEntered(String command) {
        return jShell.eval(command).stream()
                .map(snippetResolver::resolve)
                .reduce((prev, current) -> prev + "\n" + current)
                .orElse("Invalid command");
    }
}
