package io.github.explement.renderer;

import io.github.explement.Vector2;
import io.github.explement.terminal.CursorManager;
import io.github.explement.terminal.StyleManager;
import io.github.explement.terminal.Terminal;
import io.github.explement.terminal.TerminalFactory;

public class Renderer {
    private final TerminalFactory terminalFactory;
    private final ScreenBuffer screenBuffer;
    private final Terminal terminal;
    private final StyleManager styleManager;
    private final CursorManager cursorManager;

    private final ScreenBuffer previousScreenBuffer;

    public Renderer(TerminalFactory terminalFactory) {
        this.terminalFactory = terminalFactory;
        this.screenBuffer = terminalFactory.getScreenBuffer();
        this.terminal = terminalFactory.getTerminal();
        this.styleManager = terminalFactory.getStyleManager();
        this.cursorManager = terminalFactory.getCursorManager();
        this.previousScreenBuffer = new ScreenBuffer(screenBuffer.getSize());
    }

    public Renderer(ScreenBuffer screenBuffer, Terminal terminal, StyleManager styleManager, CursorManager cursorManager) {
        this.terminalFactory = null;
        this.screenBuffer = screenBuffer;
        this.terminal = terminal;
        this.styleManager = styleManager;
        this.cursorManager = cursorManager;
        this.previousScreenBuffer = new ScreenBuffer(screenBuffer.getSize());
    }

    public void render() { // TODO: Diff-based rendering
        int sbX = screenBuffer.getSize().getX();
        int sbY = screenBuffer.getSize().getY();

        for (int y = 0; y < sbY; y++) {
            for (int x = 0; x < sbX; x++) {
                Cell current = screenBuffer.getCell(new Vector2(x, y));
                Cell previous = previousScreenBuffer.getCell(new Vector2(x, y));

                if (!current.equals(previous)) {
                    cursorManager.moveTo(new Vector2(x + 1, y + 1));

                    terminal.print(
                            styleManager.applyStyle(
                                    String.valueOf(current.getCharacter()), current.getStyle()
                            )
                    );

                    previousScreenBuffer.setCell(current, new Vector2(x, y));
                }
            }
        }
        
        // Bottom-left cursor position
        cursorManager.moveTo(new Vector2(1, sbY + 1));
    }
}
