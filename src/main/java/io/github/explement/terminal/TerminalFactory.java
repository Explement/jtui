package io.github.explement.terminal;

import io.github.explement.Vector2;
import lombok.experimental.Delegate;

import java.util.Arrays;

/*
Wrapper class.
*/
public class TerminalFactory {

    @Delegate
    public Terminal terminal;

    public StyleManager styleManager;
    @Delegate
    public CursorManager cursorManager;

    public TerminalFactory() {
        this.terminal = new Terminal();
        this.styleManager = new StyleManager();
        this.cursorManager = new CursorManager();
    }
}
