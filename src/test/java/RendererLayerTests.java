import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import javax.tools.StandardLocation;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import io.github.explement.Vector2;
import io.github.explement.renderer.Cell;
import io.github.explement.renderer.ScreenBuffer;
import io.github.explement.terminal.Style;

public class RendererLayerTests {
    @Nested
    class CellTests {
        @Test
        void equalsReturnsTrueForEqualCells() {
            Cell cell1 = new Cell('A', Style.RED);
            Cell cell2 = new Cell('A', Style.RED);

            assertTrue(cell1.equals(cell2));
        }

        @Test
        void equalsReturnsFalseForDifferentCharacters() {
            Cell cell1 = new Cell('A', Style.RED);
            Cell cell2 = new Cell('B', Style.RED);

            assertFalse(cell1.equals(cell2));
        }

        @Test
        void equalsReturnsFalseForDifferentStyles() {
            Cell cell1 = new Cell('A', Style.RED);
            Cell cell2 = new Cell('A', Style.BLUE);

            assertFalse(cell1.equals(cell2));
        }

        @Test
        void equalsHandlesNullStyles() {
            Cell cell1 = new Cell('A', null);
            Cell cell2 = new Cell('A', null);
            Cell cell3 = new Cell('A', Style.RED);

            assertTrue(cell1.equals(cell2));
            assertFalse(cell1.equals(cell3));
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

            assertEquals(buffer.getCell(position1), new Cell());
            assertEquals(buffer.getCell(position2), new Cell());
            assertEquals(buffer.getCell(position3), new Cell());
            assertEquals(buffer.getCell(position4), new Cell());
            assertEquals(buffer.getCell(position5), new Cell());
        }

        @Test
        void clearCellResetsCellToDefault() {
            ScreenBuffer buffer = new ScreenBuffer(new Vector2(10, 10));
            Cell newCell = new Cell('A', Style.RED);
            Vector2 position = new Vector2(2, 3);

            buffer.setCell(newCell, position);            
            buffer.clearCell(position);

            assertEquals(buffer.getCell(position), new Cell());
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
    class RendererTests { // ? Possibly mock tests?
        
    }
}
