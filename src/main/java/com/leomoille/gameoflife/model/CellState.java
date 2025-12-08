package com.leomoille.gameoflife.model;

/**
 * Represents the state of a cell.
 */
public enum CellState {
    ALIVE, DEAD;

    /**
     * Checks if the state is considered "alive".
     *
     * @return true if ALIVE, false otherwise.
     */
    public boolean isAlive() {
        return this == ALIVE;
    }
}
