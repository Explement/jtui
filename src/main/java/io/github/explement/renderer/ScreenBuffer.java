package io.github.explement.renderer;

import io.github.explement.Vector2;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

public class ScreenBuffer {
    @Getter
    private Cell[][] cellArray;
    @Getter
    private Set<Vector2> dirtyCells = new HashSet<>();
    @Getter
    private Vector2 size;

    public ScreenBuffer(Vector2 size) {
        if (size == null) {
            throw new IllegalArgumentException("Size cannot be null");
        }
        if (size.getX() <= 0 || size.getY() <= 0) {
            throw new IllegalArgumentException("Size must be greater than 0");
        }
        this.size = size;
        cellArray = new Cell[size.getY()][size.getX()];
        createScreenBuffer();
    }

    public void createScreenBuffer() {
        for (int y = 0; y < size.getY(); y++) {
            for (int x = 0; x < size.getX(); x++) {
                setCell(new Cell(), new Vector2(x, y));
            }
        }
    }

    public void setCell(Cell cell, Vector2 position) {
        if (cell == null) {
            throw new IllegalArgumentException("Cell cannot be null");
        }
        if (position == null) {
            throw new IllegalArgumentException("Position cannot be null");
        }
        if (position.getX() < 0 || position.getX() >= size.getX() ||
            position.getY() < 0 || position.getY() >= size.getY()) {
            throw new IndexOutOfBoundsException("Position " + position + " is out of bounds for ScreenBuffer of size " + size);
        }
        cellArray[position.getY()][position.getX()] = cell;
        setDirty(new Vector2(position.getX(), position.getY()));
    }

    public Cell getCell(Vector2 position) {
        if (position == null) {
            throw new IllegalArgumentException("Position cannot be null");
        }
        if (position.getX() < 0 || position.getX() >= size.getX() ||
            position.getY() < 0 || position.getY() >= size.getY()) {
            throw new IndexOutOfBoundsException("Position " + position + " is out of bounds for ScreenBuffer of size " + size);
        }
        return cellArray[position.getY()][position.getX()];
    }

    public void clearCell(Vector2 position) {
        if (position == null) {
            throw new IllegalArgumentException("Position cannot be null");
        }
        if (position.getX() < 0 || position.getX() >= size.getX() ||
            position.getY() < 0 || position.getY() >= size.getY()) {
            throw new IndexOutOfBoundsException("Position " + position + " is out of bounds for ScreenBuffer of size " + size);
        }
        Cell cell = new Cell();
        cellArray[position.getY()][position.getX()] = cell;
        setDirty(new Vector2(position.getX(), position.getY()));
    }

    public void clearCells() {
        for (int y = 0; y < size.getY(); y++) {
            for (int x = 0; x < size.getX(); x++) {
                setCell(new Cell(), new Vector2(x, y));
            }
        }
    }

    public void clearDirty() {
        dirtyCells.clear();
    }

    public void setDirty(Vector2 position) {
        if (position == null) {
            throw new IllegalArgumentException("Position cannot be null");
        }
        dirtyCells.add(position);
    }

}
