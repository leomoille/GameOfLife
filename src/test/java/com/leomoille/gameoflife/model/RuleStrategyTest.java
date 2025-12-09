package com.leomoille.gameoflife.model;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RuleStrategyTest {

    @ParameterizedTest(name = "Conway: {0} (Alive: {1}, Neighbors: {2}) -> {3}")
    @CsvSource({
            "Underpopulation, true, 1, DEAD",
            "Survival,        true, 2, ALIVE",
            "Survival,        true, 3, ALIVE",
            "Overpopulation,  true, 4, DEAD",
            "No Birth,        false, 2, DEAD",
            "Reproduction,    false, 3, ALIVE",
            "Overpopulation,  false, 4, DEAD"
    })
    void testConwayRules(String description, boolean isAlive, int neighbors, CellState expectedState) {
        this.verifyRule(new ConwayRules(), isAlive, neighbors, expectedState);
    }

    @ParameterizedTest(name = "HighLife: {0} (Alive: {1}, Neighbors: {2}) -> {3}")
    @CsvSource({
            "Underpopulation, true, 1, DEAD",
            "Survival,        true, 2, ALIVE",
            "Survival,        true, 3, ALIVE",
            "Overpopulation,  true, 4, DEAD",
            "No Birth,        false, 2, DEAD",
            "Reproduction,    false, 3, ALIVE",
            "Birth (6),       false, 6, ALIVE", // HighLife special
            "Overpopulation,  false, 7, DEAD"
    })
    void testHighLifeRules(String description, boolean isAlive, int neighbors, CellState expectedState) {
        this.verifyRule(new HighLifeRules(), isAlive, neighbors, expectedState);
    }

    private void verifyRule(RuleStrategy strategy, boolean isAlive, int neighbors, CellState expected) {
        Grid grid = new Grid(3, 3);
        // Center cell
        Cell center = grid.getCell(1, 1);
        if (isAlive)
            center.setState(CellState.ALIVE);
        else
            center.setState(CellState.DEAD);

        // Set neighbors
        this.setNeighbors(grid, neighbors);

        assertEquals(expected, strategy.computeNextState(grid, 1, 1));
    }

    private void setNeighbors(Grid grid, int count) {
        // Simple heuristic to fill neighbors around 1,1
        // Coordinates around 1,1 are (0,0) to (2,2) excluding (1,1)
        int[][] neighborCoords = {
                {0, 0}, {0, 1}, {0, 2},
                {1, 0}, {1, 2},
                {2, 0}, {2, 1}, {2, 2}
        };

        for (int i = 0; i < count && i < neighborCoords.length; i++) {
            grid.setCell(neighborCoords[i][0], neighborCoords[i][1], CellState.ALIVE);
        }
    }
}
