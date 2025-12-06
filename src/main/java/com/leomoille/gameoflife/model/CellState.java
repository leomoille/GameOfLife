package com.leomoille.gameoflife.model;

/**
 * Represents the state of a cell.
 */
public enum CellState {
    ALIVE, DEAD;

    public boolean isAlive() {
        return this == ALIVE;
    }
}
