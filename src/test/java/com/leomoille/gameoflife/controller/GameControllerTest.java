package com.leomoille.gameoflife.controller;

import com.leomoille.gameoflife.model.*;
import com.leomoille.gameoflife.view.GameView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration Tests for GameController.
 * Verifies interaction between Controller, Model, and Engine.
 */
class GameControllerTest {

    private GameModel model;
    private GameEngine engine;
    private GameController controller;
    private MockGameView mockView;

    // Simple Mock for testing interactions
    static class MockGameView implements GameView {
        boolean refreshCalled = false;

        @Override
        public void refresh() {
            this.refreshCalled = true;
        }
    }

    @BeforeEach
    void setUp() {
        this.model = new GameModel(10, 10);
        this.engine = new GameEngine(this.model);
        this.controller = new GameController(this.model, this.engine);
        this.mockView = new MockGameView();
        this.controller.setView(this.mockView);
        this.engine.setSpeed(10); // Very fast for tests
    }

    @Test
    void testStartStopGame() {
        assertFalse(this.engine.isRunning(), "Engine should be stopped initially");

        this.controller.startGame();

        // Wait for engine to start (robust polling)
        this.awaitCondition(() -> this.engine.isRunning(), 1000, "Engine failed to start");
        assertTrue(this.engine.isRunning(), "Engine should be running");

        this.controller.pauseGame();

        // Wait for engine to stop
        this.awaitCondition(() -> !this.engine.isRunning(), 1000, "Engine failed to stop");
        assertFalse(this.engine.isRunning(), "Engine should be stopped after pause()");
    }

    // Helper for async conditions
    private void awaitCondition(java.util.function.BooleanSupplier condition, int timeoutMs, String failMessage) {
        long start = System.currentTimeMillis();
        while (!condition.getAsBoolean()) {
            if (System.currentTimeMillis() - start > timeoutMs) {
                fail(failMessage);
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                fail("Interrupted while awaiting condition");
            }
        }
    }

    @Test
    void testResetGame() {
        // Set some state
        this.model.getGrid().setCell(0, 0, CellState.ALIVE);
        this.controller.startGame(); // Start generation count

        this.controller.resetGame();

        assertFalse(this.engine.isRunning(), "Engine should stop on reset");
        assertEquals(0, this.model.getGeneration(), "Generation should be 0");
        assertFalse(this.model.getGrid().getCell(0, 0).isAlive(), "Grid should be cleared");
    }

    @Test
    void testChangeRule() {
        // Default is Conway
        assertInstanceOf(ConwayRules.class, this.model.getRuleStrategy(), "Should start with ConwayRules");

        this.controller.changeRule(new HighLifeRules());

        assertInstanceOf(HighLifeRules.class, this.model.getRuleStrategy(), "Should change to HighLifeRules");
    }

    @Test
    void testResizeGrid() {
        assertEquals(10, this.model.getGrid().getWidth());

        this.controller.onResizeGrid(20, 20);

        assertEquals(20, this.model.getGrid().getWidth());
        assertEquals(20, this.model.getGrid().getHeight());
    }

    @Test
    void testTogglePause() {
        assertFalse(this.engine.isRunning());

        this.controller.togglePause();
        this.awaitCondition(() -> this.engine.isRunning(), 1000, "Engine failed to start on toggle");
        assertTrue(this.engine.isRunning());

        this.controller.togglePause();
        this.awaitCondition(() -> !this.engine.isRunning(), 1000, "Engine failed to stop on toggle");
        assertFalse(this.engine.isRunning());
    }

    @Test
    void testRandomize() {
        this.controller.randomize();

        boolean anyAlive = false;
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                if (this.model.getGrid().getCell(x, y).isAlive()) {
                    anyAlive = true;
                    break;
                }
            }
        }
        assertTrue(anyAlive, "Randomize should populate the grid");
    }

    @Test
    void testOnCellClicked() {
        assertFalse(this.model.getGrid().getCell(5, 5).isAlive());

        // First click: Toggle to ALIVE
        this.controller.onCellClicked(5, 5);
        assertTrue(this.model.getGrid().getCell(5, 5).isAlive(), "Cell should be alive after click");
        assertTrue(this.mockView.refreshCalled, "View should be refreshed after click");

        this.mockView.refreshCalled = false; // Reset

        // Second click: Toggle to DEAD
        this.controller.onCellClicked(5, 5);
        assertFalse(this.model.getGrid().getCell(5, 5).isAlive(), "Cell should be dead after second click");
        assertTrue(this.mockView.refreshCalled, "View should be refreshed after second click");
    }
}
