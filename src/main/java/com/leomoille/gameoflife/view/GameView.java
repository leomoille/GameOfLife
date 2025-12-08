package com.leomoille.gameoflife.view;

/**
 * Interface representing the abstract View in the MVC pattern.
 * Allows the Controller to interact with the UI without depending
 * on specific implementations
 */
public interface GameView {
    /**
     * Refreshes the visual representation of the game.
     * Typically called when the state changes and immediate feedback is required.
     */
    void refresh();
}
