package com.leomoille.gameoflife.model;

/**
 * Strategy interface for determining the next state of a cell.
 */
public interface RuleStrategy {
    /**
     *
     * @param grid The current grid state.
     * @param x    X coordinate of the cell.
     * @param y    Y coordinate of the cell.
     * @return the next state of the cell (ALIVE or DEAD).
     */
    CellState computeNextState(Grid grid, int x, int y);

    /**
     * Gets the name of the rule strategy.
     *
     * @return Name of the rule.
     */
    String getName();
}
