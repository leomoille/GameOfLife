package com.leomoille.gameoflife.model;

/**
 * Strategy interface for determining the next state of a cell.
 */
public interface RuleStrategy {
    /**
     * Computes the next state of a cell at (x, y) based on the grid context.
     *
     * @param grid The current grid state.
     * @param x    The x-coordinate of the cell.
     * @param y    The y-coordinate of the cell.
     * @return The next state of the cell (ALIVE or DEAD).
     */
    CellState computeNextState(Grid grid, int x, int y);

    /**
     * Gets the name of the rule strategy.
     *
     * @return Name of the rule.
     */
    String getName();
}
