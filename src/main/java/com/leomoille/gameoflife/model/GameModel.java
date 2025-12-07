package com.leomoille.gameoflife.model;

public class GameModel {
    private Grid grid;
    private RuleStrategy ruleStrategy;
    private int generation;

    public GameModel(int width, int height) {
        this.grid = new Grid(width, height);
        this.ruleStrategy = new ConwayRules();
        this.generation = 0;
    }

    public void nextGeneration() {
        int width = grid.getWidth();
        int height = grid.getHeight();
        Grid nextGrid = new Grid(width, height);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                CellState nextState = ruleStrategy.computeNextState(grid, x, y);
                nextGrid.setCell(x, y, nextState);
            }
        }

        Grid oldGrid = this.grid;
        this.grid = nextGrid;
        this.generation++;
    }
}
