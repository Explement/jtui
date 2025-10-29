package io.github.explement.renderer;

import io.github.explement.terminal.Style;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Cell {
    private char character;
    private Style style;

    public Cell() {
        this.character = ' ';

    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        Cell cell = (Cell) object;
        if (cell.getCharacter() != this.character) return false;

        if (cell.getStyle() == null && this.style == null) return true;
        if (cell.getStyle() == null || this.style == null) return false;

        return style.equals(cell.style);
    }

    @Override
    public int hashCode() {
        int result = Character.hashCode(character);
        result = 31 * result + (style != null ? style.hashCode() : 0);
        return result;
    }
}
