package com.leomoille.gameoflife.model;

public class Cell {
    private CellState state;

    /**
     * Creates a Cell with a specified initial state.
     *
     * @param initialState Starting state of the cell.
     */
    public Cell(CellState initialState) {
        this.state = initialState;
    }

    /**
     * Default constructor creates DEAD cell.
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
        return "Cell{" + "state=" + state + "}";
    }
}
