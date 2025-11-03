import static org.mockito.Mockito.verify;

import io.github.explement.Vector2;
import io.github.explement.terminal.CursorManager;
import io.github.explement.terminal.Style;
import io.github.explement.terminal.StyleManager;
import io.github.explement.terminal.Terminal;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class TerminalLayerTests {
    @Nested
    class TerminalTests {
        @Test
        public void printOutputsCorrectly() {
            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outContent));

            String text = "test";

            Terminal terminal = new Terminal(false);
            terminal.print(text);

            assertEquals(text, outContent.toString()); // ? Handle clear ANSI codes
        }

        @Test
        public void getTerminalSizeReturnsVector() {
            Terminal terminal = new Terminal(false);
            assertNotNull(terminal.getTerminalSize());
            assertInstanceOf(Vector2.class, terminal.getTerminalSize());
        }

        @Test
        public void printAtOutputsAtCorrectPosition() {
            Terminal  terminal = new Terminal(false);
            CursorManager cursorManager = Mockito.mock(CursorManager.class);
            terminal.printAt("A", new Vector2(0, 0), cursorManager);

            verify(cursorManager).moveTo(new Vector2(0, 0));
        }
    }

    @Nested
    class CursorManagerTests {

        @Test
        void showPrintsVisibleEscapeCode() {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            System.setOut(new PrintStream(out));
            CursorManager cursorManager = new CursorManager();

            cursorManager.show();

            assertEquals("\u001B[?25h", out.toString());
        }

        @Test
        void hidePrintsHiddenEscapeCode() {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            System.setOut(new PrintStream(out));
            CursorManager cm = new CursorManager();

            cm.hide();

            assertEquals("\u001B[?25l", out.toString());
        }

        @Test
        void moveToUpdatesPosition() {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            System.setOut(new PrintStream(out));
            CursorManager cm = new CursorManager();

            cm.moveTo(new Vector2(6, 7));

            assertEquals("\u001B[7;6H", out.toString());
        }
    }

    @Nested
    class StyleManagerTests {
        @Test
        void appliesSingleStyle() {
            StyleManager styleManager = new StyleManager();
            String result = styleManager.applyStyle("test", Style.RED);
            assertTrue(result.startsWith(Style.RED.getCode()));
            assertTrue(result.endsWith(Style.RESET.getCode()));
        }

        @Test
        void appliesMultipleStyles() {
            StyleManager styleManager = new StyleManager();
            String result = styleManager.applyStyle("test", Style.RED, Style.BG_BLUE);
            assertTrue(result.startsWith(Style.RED.getCode() + Style.BG_BLUE.getCode()));
            assertTrue(result.endsWith(Style.RESET.getCode()));
        }
    }
}
