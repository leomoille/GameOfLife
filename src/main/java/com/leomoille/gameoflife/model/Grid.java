package com.leomoille.gameoflife.model;

import java.util.Random;

/**
 * Represents the 2D grid of cells in the Game of Life.
 * <p>
 * The grid uses a "dead border" strategy: cells outside the bounds are
 * considered DEAD.
 */
public class Grid {
    private int width;
    private int height;
    private Cell[][] cells;
    private final Random random;

    /**
     * Creates a grid with specified dimensions.
     *
     * @param width  Width of the grid.
     * @param height Height of the grid.
     */
    public Grid(int width, int height) {
        this.width = width;
        this.height = height;
        this.random = new Random();
        this.cells = new Cell[height][width];
        this.initializeCells();
    }

    private void initializeCells() {
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                // Cell are DEAD by default
                this.cells[y][x] = new Cell();
            }
        }
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    /**
     * Gets the cell at the specified coordinates.
     *
     * @param x X coordinate of the Cell.
     * @param y Y coordinate of the Cell.
     * @return the actual Cell object (not a copy).
     */
    public Cell getCell(int x, int y) {
        if (this.isValidCoordinate(x, y)) {
            // No copy to keep it simple.
            return this.cells[y][x];
        }
        return null;
    }

    /**
     * Sets the state of a cell at (x, y).
     */
    public void setCell(int x, int y, CellState state) {
        if (this.isValidCoordinate(x, y)) {
            this.cells[y][x].setState(state);
        }
    }

    /**
     * Counts the number of alive neighbors around a specific cell.
     * <p>
     * Uses Moore neighborhood (8 surrounding cells).
     * Out-of-bounds neighbors are treated as DEAD.
     */
    public int getAliveNeighbors(int x, int y) {
        int count = 0;
        int[] dx = {-1, 0, 1};
        int[] dy = {-1, 0, 1};

        for (int i : dx) {
            for (int j : dy) {
                if (i == 0 && j == 0) {
                    continue;
                }

                int nx = x + i;
                int ny = y + j;

                if (this.isValidCoordinate(nx, ny)) {
                    if (this.cells[ny][nx].isAlive()) {
                        count++;
                    }
                }
            }
        }

        return count;
    }

    public void clear() {
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                this.cells[y][x].setState(CellState.DEAD);
            }
        }
    }

    public void randomize(double probability) {
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                if (this.random.nextDouble() < probability) {
                    this.cells[y][x].setState(CellState.ALIVE);
                } else {
                    this.cells[y][x].setState(CellState.DEAD);
                }
            }
        }
    }

    public void resize(int newWidth, int newHeight) {
        Cell[][] newCells = new Cell[newHeight][newWidth];

        for (int y = 0; y < newHeight; y++) {
            for (int x = 0; x < newWidth; x++) {
                // Copy existing if possible
                if (y < this.height && x < this.width) {
                    newCells[y][x] = this.cells[y][x];
                } else {
                    newCells[y][x] = new Cell(CellState.DEAD);
                }
            }
        }

        this.width = newWidth;
        this.height = newHeight;
        this.cells = newCells;
    }

    private boolean isValidCoordinate(int x, int y) {
        return x >= 0 && x < this.width && y >= 0 && y < this.height;
    }
}
