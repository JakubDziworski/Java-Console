package com.jakubdziworski.myjshell;

import jdk.jshell.Snippet;
import jdk.jshell.Snippet.Status;
import jdk.jshell.SnippetEvent;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

/**
 * Created by jdziworski on 02.08.16.
 */
public class SnippetResolver {

    public String resolve(SnippetEvent snippet) {
        boolean isValidSnippet = snippet.value() != null && snippet.status() == Status.VALID;
        if(isValidSnippet) {
            return snippet.value();
        }
        boolean snippetThrownException = snippet.exception() != null;
        if (snippetThrownException) {
            return "Exception occured : " + snippet.exception();
        }
        return String.format("'%s' : %s",snippet.snippet().source(),snippet.status());
    }
}
