package com.leomoille.gameoflife.model;

/**
 * Handles the game loop and simulation timing.
 * <p>
 * Runs on a dedicated thread to avoid blocking the Swing Event Dispatch Thread
 * (EDT).
 * Controls the speed of the simulation and ensures the model advances
 * generation by generation.
 */
public class GameEngine {
    private final GameModel model;
    private boolean isRunning;
    private int delayMs; // Speed of the game generation
    private Thread gameThread;

    /**
     * Constructs a new GameEngine.
     *
     * @param model The GameModel instance to manage.
     */
    public GameEngine(GameModel model) {
        this.model = model;
        this.isRunning = false;
        // Default 100ms
        this.delayMs = 100;
    }

    public synchronized void start() {
        if (this.isRunning) {
            return;
        }

        this.isRunning = true;

        this.gameThread = new Thread(() -> {
            while (this.isRunning) {
                try {
                    long startTime = System.currentTimeMillis();
                    this.model.nextGeneration();
                    long elapsedTime = System.currentTimeMillis() - startTime;

                    long sleepTime = this.delayMs - elapsedTime;
                    if (sleepTime > 0) {
                        Thread.sleep(sleepTime);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
        this.gameThread.start();
    }

    public synchronized void stop() {
        this.isRunning = false;
        if (this.gameThread != null) {
            this.gameThread.interrupt();
            this.gameThread = null; // Clean up
        }
    }

    public void togglePause() {
        if (this.isRunning) {
            this.stop();
        } else {
            this.start();
        }
    }

    public void setSpeed(int delayMs) {
        this.delayMs = delayMs;
    }

    public boolean isRunning() {
        return this.isRunning;
    }
}
