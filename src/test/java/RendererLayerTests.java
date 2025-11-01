import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import io.github.explement.Vector2;
import io.github.explement.renderer.Cell;
import io.github.explement.renderer.Renderer;
import io.github.explement.renderer.ScreenBuffer;
import io.github.explement.terminal.CursorManager;
import io.github.explement.terminal.Style;
import io.github.explement.terminal.StyleManager;
import io.github.explement.terminal.Terminal;

public class RendererLayerTests {
    @Nested
    class CellTests {
        @Test
        void equalsReturnsTrueForEqualCells() {
            Cell cell1 = new Cell('A', Style.RED);
            Cell cell2 = new Cell('A', Style.RED);

            assertEquals(cell1, cell2);
        }

        @Test
        void equalsReturnsFalseForDifferentCharacters() {
            Cell cell1 = new Cell('A', Style.RED);
            Cell cell2 = new Cell('B', Style.RED);

            assertNotEquals(cell1, cell2);
        }

        @Test
        void equalsReturnsFalseForDifferentStyles() {
            Cell cell1 = new Cell('A', Style.RED);
            Cell cell2 = new Cell('A', Style.BLUE);

            assertNotEquals(cell1, cell2);
        }

        @Test
        void equalsHandlesNullStyles() {
            Cell cell1 = new Cell('A', null);
            Cell cell2 = new Cell('A', null);
            Cell cell3 = new Cell('A', Style.RED);

            assertEquals(cell1, cell2);
            assertNotEquals(cell1, cell3);
        }

        @Test
        void hashCodeIsConsistentWithEquals() {
            Cell cell1 = new Cell('A', Style.RED);
            Cell cell2 = new Cell('A', Style.RED);
            Cell cell3 = new Cell('B', Style.RED);

            assertEquals(cell1.hashCode(), cell2.hashCode());
            assertNotEquals(cell1.hashCode(), cell3.hashCode());
        }
    }

    @Nested
    class ScreenBufferTests { // TODO: Handle exceptions/edge-cases
        @Test
        void createScreenBufferInitializesCells() {
            ScreenBuffer buffer = new ScreenBuffer(new Vector2(10, 10));
            Cell[][] expectedArray = new Cell[10][10];

            for (int y = 0; y < 10; y++) {
                for (int x = 0; x < 10; x++) {
                    expectedArray[y][x] = new Cell();
                }
            }

            assertTrue(Arrays.deepEquals(expectedArray, buffer.getCellArray()));
        }

        @Test
        void getCellThrowsExceptionForNullPosition() {
            ScreenBuffer screenBuffer = new ScreenBuffer(new Vector2(10, 10));
            Cell cell = new Cell('A', Style.BLUE);
            assertThrows(IllegalArgumentException.class, () -> screenBuffer.getCell(null));
        }

        @Test
        void getCellThrowsExceptionForInvalidPosition() {
            ScreenBuffer screenBuffer = new ScreenBuffer(new Vector2(10, 10));
            Cell cell = new Cell('A', Style.BLUE);
            assertThrows(IndexOutOfBoundsException.class, () -> screenBuffer.getCell(new Vector2(-1, -14)));
        }

        @Test
        void setCellGetCellWorkCorrectly() {
            ScreenBuffer buffer = new ScreenBuffer(new Vector2(10, 10));
            Cell newCell = new Cell('A', Style.GREEN);
            Vector2 position = new Vector2(2, 3);

            buffer.setCell(newCell, position);
            Cell retrievedCell = buffer.getCell(position);

            assertEquals(newCell, retrievedCell);
        }

        @Test
        void setCellThrowsExceptionForNullPosition() {
            ScreenBuffer screenBuffer = new ScreenBuffer(new Vector2(10, 10));
            Cell cell = new Cell('A', Style.BLUE);
            assertThrows(IllegalArgumentException.class, () -> screenBuffer.setCell(cell, null));
        }

        @Test
        void setCellThrowsExceptionForNullCell() {
            ScreenBuffer screenBuffer = new ScreenBuffer(new Vector2(10, 10));
            
            assertThrows(IllegalArgumentException.class, () -> screenBuffer.setCell(null, new Vector2(4, 7)));
        }
        
        @Test
        void setCellThrowsExceptionForInvalidPosition() {
            ScreenBuffer screenBuffer = new ScreenBuffer(new Vector2(10, 10));
            Cell cell = new Cell('A', Style.BLUE);
            assertThrows(IndexOutOfBoundsException.class, () -> screenBuffer.setCell(cell, new Vector2(-1, -14)));
        }

        @Test
        void clearCellsResetsCellToDefault() {
            ScreenBuffer buffer = new ScreenBuffer(new Vector2(10, 10));
            Cell cell1 = new Cell('A', Style.BG_YELLOW);
            Cell cell2 = new Cell('B', Style.CYAN);
            Cell cell3 = new Cell('C', Style.RED);
            Cell cell4 = new Cell('D', Style.GREEN);
            Cell cell5 = new Cell('E', Style.BLACK);

            Vector2 position1 = new Vector2(1, 1);
            Vector2 position2 = new Vector2(2, 2);
            Vector2 position3 = new Vector2(3, 3);
            Vector2 position4 = new Vector2(4, 4);
            Vector2 position5 = new Vector2(5, 5);

            buffer.setCell(cell1, position1);
            buffer.setCell(cell2, position2);
            buffer.setCell(cell3, position3);
            buffer.setCell(cell4, position4);
            buffer.setCell(cell5, position5);

            buffer.clearCells();

            assertEquals(new Cell(), buffer.getCell(position1));
            assertEquals(new Cell(), buffer.getCell(position2));
            assertEquals(new Cell(), buffer.getCell(position3));
            assertEquals(new Cell(), buffer.getCell(position4));
            assertEquals(new Cell(), buffer.getCell(position5));
        }

        @Test
        void clearCellResetsCellToDefault() {
            ScreenBuffer buffer = new ScreenBuffer(new Vector2(10, 10));
            Cell newCell = new Cell('A', Style.RED);
            Vector2 position = new Vector2(2, 3);

            buffer.setCell(newCell, position);            
            buffer.clearCell(position);

            assertEquals(new Cell(), buffer.getCell(position));
        }
        @Test
        void clearCellThrowsExceptionForNullPosition() {
            ScreenBuffer screenBuffer = new ScreenBuffer(new Vector2(10, 10));

            assertThrows(IllegalArgumentException.class, () -> screenBuffer.clearCell(null));
        }
        @Test
        void clearCellThrowsExceptionForInvalidPosition() {
            ScreenBuffer screenBuffer = new ScreenBuffer(new Vector2(10, 10));

            assertThrows(IndexOutOfBoundsException.class, () -> screenBuffer.clearCell(new Vector2(-1, 21)));
        }
        @Test
        void screenBufferThrowsExceptionForNullSize() {
            assertThrows(IllegalArgumentException.class, () -> new ScreenBuffer(null));
        }

        @Test
        void screenBufferThrowsExceptionForInvalidSize() {
            assertThrows(IllegalArgumentException.class, () -> new ScreenBuffer(new Vector2(-1, -4)));
        }


    }

    @Nested
    class RendererTests { 
        private Renderer renderer;
        private ScreenBuffer screenBuffer;
        private Terminal terminal;
        private StyleManager styleManager;
        private CursorManager cursorManager;

        @BeforeEach
        void setup() {
            screenBuffer = new ScreenBuffer(new Vector2(10, 10)); // ! Don't mock ScreenBuffer
            terminal = mock(Terminal.class);
            styleManager = mock(StyleManager.class);
            cursorManager = mock(CursorManager.class);

            screenBuffer.setCell(new Cell('A', null), new Vector2(0, 0));
            screenBuffer.setCell(new Cell('B', null), new Vector2(1, 0));

            // * Return String on applyStyle(), ignoring styles
            when(styleManager.applyStyle(anyString(), any())).thenAnswer(i -> i.getArgument(0));

            renderer = new Renderer(screenBuffer, terminal, styleManager, cursorManager);
        }

        @Test
        void rendererPrintsToTerminal() {
            renderer.render();

            // * Twice for actual movements, once for moving to end
            verify(cursorManager, times(3)).moveTo(any(Vector2.class));

            verify(terminal, times(1)).print("A");
            verify(terminal, times(1)).print("B");
        }

        @Test
        void rendererDoesNotPrintUnchangedCells() {
            renderer.render();

            // Clear for verifications 
            clearInvocations(terminal, cursorManager);

            renderer.render();

            verify(cursorManager, times(1)).moveTo(any(Vector2.class)); // Only for setting cursor to bottom-left
            verify(terminal, never()).print(anyString());
        }

        @Test
        void rendererUpdatesChangedCells() {
            Cell cellA = new Cell('X', null);
            Cell cellB = new Cell('Y', null);

            renderer.render();

            clearInvocations(terminal, cursorManager);

            screenBuffer.setCell(cellA, new Vector2(0, 0));
            screenBuffer.setCell(cellB, new Vector2(1, 0));

            renderer.render();

            verify(cursorManager, times(3)).moveTo(any(Vector2.class));
            verify(terminal).print("Y");
            verify(terminal).print("X");
        }


    }
}
