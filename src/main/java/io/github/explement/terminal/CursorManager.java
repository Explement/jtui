package io.github.explement.terminal;

import io.github.explement.Vector2;
import lombok.Getter;
import lombok.Setter;

public class CursorManager {
    @Getter
    @Setter
    private Vector2 cursorPosition;

    public void show() {
        System.out.print("\u001B[?25h");
    }

    public void hide() {
        System.out.print("\u001B[?25l");
    }

    public void moveTo(Vector2 position) {
        System.out.printf("\u001B[%d;%dH", position.getY(), position.getX());
        setCursorPosition(position);
    }
}
