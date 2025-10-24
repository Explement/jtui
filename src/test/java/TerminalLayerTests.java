import io.github.explement.Vector2;
import io.github.explement.terminal.CursorManager;
import io.github.explement.terminal.Style;
import io.github.explement.terminal.StyleManager;
import io.github.explement.terminal.Terminal;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

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

            Terminal terminal = new Terminal();
            terminal.print(text);

            assertEquals(text, outContent.toString());
        }

        @Test
        public void getTerminalSizeReturnsVector() {
            assertNotNull(Terminal.getTerminalSize());
            assertInstanceOf(Vector2.class, Terminal.getTerminalSize());
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
            // TODO: Write test.
            assertTrue(true);
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
            String result = styleManager.applyStyle("test", new Style[] {Style.RED, Style.BG_BLUE});
            assertTrue(result.startsWith(Style.RED.getCode() + Style.BG_BLUE.getCode()));
            assertTrue(result.endsWith(Style.RESET.getCode()));
        }
    }
}
