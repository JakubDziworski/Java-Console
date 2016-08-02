package com.jakubdziworski.myjshell

import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

/**
 * Created by kuba on 02.08.16.
 */
class CommandScannerTest extends Specification {

    InputStream inputStream
    PrintStream printStream
    @Subject
    CommandScanner scanner

    def setup() {
        CommandScannerListener commandScannerListener = { command -> "You entered: " + command }
        inputStream = new ByteArrayInputStream("".getBytes());
        printStream = Mock(PrintStream)
        scanner = new CommandScanner(commandScannerListener)
    }

    def "should not print anything when stream empty"() {
        given:
            inputStream = new ByteArrayInputStream(commands.bytes);
        when:
            scanner.startScanningForCommands(inputStream, printStream)
        then:
            1 * printStream.println("Welcome to MyJShell");
            0 * _
        where:
            commands   | _
            ""         | _
            "   "      | _
            "   \n   " | _
    }


    def "should print expected output for one line"() {
        given:
            inputStream = new ByteArrayInputStream(commands.bytes);
        when:
            scanner.startScanningForCommands(inputStream, printStream)
        then:
            1 * printStream.println("Welcome to MyJShell")
            1 * printStream.print("MyJshell > ");
            1 * printStream.println(expectedPrint);
            0 * _
        where:
            commands               || expectedPrint
            "wgdf"                 || "You entered: wgdf"
            "one line with spaces" || "You entered: one line with spaces"
    }

    @Unroll
    def "should handle multiple commands"() {
        given:
            inputStream = new ByteArrayInputStream(commands.bytes);
        when:
            scanner.startScanningForCommands(inputStream, printStream)
        then:
            1 * printStream.println('Welcome to MyJShell')
            expectedPrints.size() * printStream.print('MyJshell > ')
            for (expectedPrint in expectedPrints) {
                1 * printStream.println(expectedPrint);
            }
            0 * _
        where:
            commands                  || expectedPrints
            "5+5\nint x"              || ["You entered: 5+5", "You entered: int x"]
            "5+5\nint x\n    "        || ["You entered: 5+5", "You entered: int x"]
    }
}
