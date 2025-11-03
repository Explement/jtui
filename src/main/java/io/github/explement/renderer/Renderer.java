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
        int sbSizeX = screenBuffer.getSize().getX();
        int sbSizeY = screenBuffer.getSize().getY();

        for (Vector2 position : screenBuffer.getDirtyCells()) {
            int x = position.getX();
            int y = position.getY();

            Cell cell = screenBuffer.getCell(position);

            terminal.printAt(
                    styleManager.applyStyle(
                            String.valueOf(cell.getCharacter()), cell.getStyle()
                    )
                    ,
                    new Vector2(x + 1, y + 1)
                    ,
                    cursorManager
            );
        }

        screenBuffer.clearDirty();

        // Bottom-left cursor position
        cursorManager.moveTo(new Vector2(1, sbSizeY + 1));
    }
}
