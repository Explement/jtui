package io.github.explement.terminal;

import io.github.explement.renderer.ScreenBuffer;
import lombok.Getter;
import lombok.experimental.Delegate;

/*
Wrapper class.
*/
@Getter
public class TerminalFactory {

    @Delegate
    public Terminal terminal;
    @Delegate
    public StyleManager styleManager;
    @Delegate
    public CursorManager cursorManager;
    @Delegate
    public ScreenBuffer screenBuffer;

    public TerminalFactory() {
        this.terminal = new Terminal();
        this.styleManager = new StyleManager();
        this.cursorManager = new CursorManager();
        this.screenBuffer = new ScreenBuffer(terminal.getTerminalSize());
    }
}
