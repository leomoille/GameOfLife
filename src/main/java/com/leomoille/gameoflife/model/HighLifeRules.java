package com.leomoille.gameoflife.model;

/**
 * HighLife Rules (B36/S23).
 * Similar to Conway, but cells also come to life with 6 neighbors.
 * Famous for having a replicator pattern.
 */
public class HighLifeRules implements RuleStrategy {
    @Override
    public CellState computeNextState(Grid grid, int x, int y) {
        int aliveNeighbors = grid.getAliveNeighbors(x, y);
        boolean isAlive = grid.getCell(x, y).isAlive();

        if (isAlive) {
            // Survival: 2 or 3
            if (aliveNeighbors == 2 || aliveNeighbors == 3) {
                return CellState.ALIVE;
            } else {
                return CellState.DEAD;
            }
        } else {
            // Birth: 3 or 6
            if (aliveNeighbors == 3 || aliveNeighbors == 6) {
                return CellState.ALIVE;
            } else {
                return CellState.DEAD;
            }
        }
    }

    @Override
    public String getName() {
        return "HighLife (B36/S23)";
    }
}
