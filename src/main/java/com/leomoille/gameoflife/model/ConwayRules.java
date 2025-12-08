package com.leomoille.gameoflife.model;

/**
 * Standard Conway's Game of Life rules (B3/S23).
 * A cell is born if it has exactly 3 neighbors.
 * A cell survives if it has 2 or 3 neighbors.
 * Otherwise, it dies or stays dead.
 */
public class ConwayRules implements RuleStrategy {

    @Override
    public CellState computeNextState(Grid grid, int x, int y) {
        int aliveNeighbors = grid.getAliveNeighbors(x, y);
        Cell currentCell = grid.getCell(x, y);
        boolean isAlive = currentCell.isAlive();

        if (isAlive) {
            // Survival rules
            if (aliveNeighbors == 2 || aliveNeighbors == 3) {
                return CellState.ALIVE;
            } else {
                // Underpopulation or Overpopulation
                return CellState.DEAD;
            }
        } else {
            // Birth rule
            if (aliveNeighbors == 3) {
                return CellState.ALIVE;
            } else {
                return CellState.DEAD;
            }
        }
    }

    @Override
    public String getName() {
        return "Conway's Standard Rules";
    }
}
