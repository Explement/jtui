package io.github.explement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Vector2 {
    private final int x;
    private final int y;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Vector2 vector2 = (Vector2) obj;
        return x == vector2.x && y == vector2.y;
    }

    @Override
    public int hashCode() {
        return 31 * x + y;
    }
}
