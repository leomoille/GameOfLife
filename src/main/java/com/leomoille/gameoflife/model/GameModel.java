package com.leomoille.gameoflife.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * The main model class for the Game of Life.
 * Manages the grid, rules, and game state.
 * Notifies observers when the grid changes.
 */
public class GameModel {
    private Grid grid;
    private RuleStrategy ruleStrategy;
    private int generation;
    private final PropertyChangeSupport support;

    public GameModel(int width, int height) {
        this.grid = new Grid(width, height);
        this.ruleStrategy = new ConwayRules();
        this.generation = 0;
        this.support = new PropertyChangeSupport(this);
    }

    /**
     * Advances the game by one generation.
     */
    public void nextGeneration() {
        int width = this.grid.getWidth();
        int height = this.grid.getHeight();
        Grid nextGrid = new Grid(width, height);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                CellState nextState = this.ruleStrategy.computeNextState(this.grid, x, y);
                nextGrid.setCell(x, y, nextState);
            }
        }

        Grid oldGrid = this.grid;
        this.grid = nextGrid;
        this.generation++;

        this.support.firePropertyChange("grid", oldGrid, this.grid);
        this.support.firePropertyChange("generation", this.generation - 1, this.generation);
    }

    public void reset() {
        this.grid.clear();
        this.generation = 0;
        this.support.firePropertyChange("grid", null, this.grid);
        this.support.firePropertyChange("generation", null, 0);
    }

    public void randomize(double probability) {
        this.grid.randomize(probability);
        this.support.firePropertyChange("grid", null, this.grid);
    }

    public void resize(int width, int height) {
        this.grid.resize(width, height);
        this.support.firePropertyChange("grid", null, this.grid);
    }

    public void setRuleStrategy(RuleStrategy ruleStrategy) {
        this.ruleStrategy = ruleStrategy;
    }

    public Grid getGrid() {
        return this.grid;
    }

    public int getGeneration() {
        return this.generation;
    }

    public RuleStrategy getRuleStrategy() {
        return this.ruleStrategy;
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        this.support.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        this.support.removePropertyChangeListener(pcl);
    }
}
