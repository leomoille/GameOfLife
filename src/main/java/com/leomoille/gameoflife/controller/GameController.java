package com.leomoille.gameoflife.controller;

import com.leomoille.gameoflife.model.*;
import com.leomoille.gameoflife.view.GameView;

/**
 * Controller for the Game of Life.
 * Handles user input and mediates between View and Model.
 */
public class GameController {
    private final GameModel model;
    private final GameEngine engine;
    private GameView view;

    public GameController(GameModel model, GameEngine engine) {
        this.model = model;
        this.engine = engine;
    }

    public void setView(GameView view) {
        this.view = view;
    }

    public void startGame() {
        this.engine.start();
    }

    public void pauseGame() {
        this.engine.stop();
    }

    public void togglePause() {
        this.engine.togglePause();
    }

    public void resetGame() {
        this.engine.stop();
        this.model.reset();
    }

    public void changeRule(RuleStrategy rule) {
        this.model.setRuleStrategy(rule);
    }

    public void randomize() {
        // Default 20% probability.
        this.model.randomize(0.2);
    }

    public void setSpeed(int delayMs) {
        this.engine.setSpeed(delayMs);
    }

    /**
     * Handles cell clicks from the view.
     * Toggles the state of the clicked cell and forces a view repaint.
     *
     * @param x X coordinate of the grid cell.
     * @param y Y coordinate of the grid cell.
     */
    public void onCellClicked(int x, int y) {
        // Toggle the state of the specific cell
        Cell cell = this.model.getGrid().getCell(x, y);
        if (cell != null) {
            CellState newState = cell.isAlive() ? CellState.DEAD : CellState.ALIVE;
            this.model.getGrid().setCell(x, y, newState);
        }

        if (this.view != null) {
            this.view.refresh();
        }
    }

    public void onResizeGrid(int width, int height) {
        boolean wasRunning = this.engine.isRunning();
        this.engine.stop();
        this.model.resize(width, height);
        if (wasRunning) {
            this.engine.start();
        }
    }
}
