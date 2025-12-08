package com.leomoille.gameoflife.model;

/**
 * Represents a single cell in the grid.
 */
public class Cell {
    private CellState state;

    /**
     * Creates a cell with a specified initial state.
     *
     * @param initialState The starting state of the cell.
     */
    public Cell(CellState initialState) {
        this.state = initialState;
    }

    /**
     * Default constructor creates a DEAD cell.
     */
    public Cell() {
        this(CellState.DEAD);
    }

    public CellState getState() {
        return this.state;
    }

    public void setState(CellState state) {
        this.state = state;
    }

    public boolean isAlive() {
        return this.state.isAlive();
    }

    @Override
    public String toString() {
        return "Cell{" + "state=" + this.state + '}';
    }
}
