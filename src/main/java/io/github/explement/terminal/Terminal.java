package io.github.explement.terminal;

import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.Wincon;
import io.github.explement.Vector2;

import java.io.IOException;
import java.io.InterruptedIOException;

public class Terminal { // TODO: Potentially add cross-platform support

    public void clear() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Vector2 getTerminalSize() { // TODO: Review further later on.
        Wincon.CONSOLE_SCREEN_BUFFER_INFO info = new Wincon.CONSOLE_SCREEN_BUFFER_INFO();
        boolean success = Kernel32.INSTANCE.GetConsoleScreenBufferInfo(
                Kernel32.INSTANCE.GetStdHandle(Kernel32.STD_OUTPUT_HANDLE),
                info
        );

        if (success) {
            int columns = info.srWindow.Right - info.srWindow.Left + 1;
            int rows = info.srWindow.Bottom - info.srWindow.Top + 1;
            return new Vector2(columns, rows);
        } else {
            return new Vector2(-1, -1);
        }
    }

    public void print(Object object) {
        System.out.print(object);
    }
}
