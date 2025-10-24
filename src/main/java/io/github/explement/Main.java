package io.github.explement;

import io.github.explement.terminal.Style;
import io.github.explement.terminal.TerminalFactory;

public class Main {
    public static void main(String[] args) {

        System.out.println(Style.CYAN.getCode() + Style.BG_RED.getCode() + "John the cohn" + Style.RESET.getCode());
        TerminalFactory terminalFactory = new TerminalFactory();

        terminalFactory.clear();

        String s = terminalFactory.applyStyle("Hello!", new Style[] {Style.CYAN});
        terminalFactory.print(s);


    }
}