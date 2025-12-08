package com.leomoille.gameoflife.model;

public class GameEngine {
    private final GameModel model;
    private boolean isRunning;
    private int delayMs;
    private Thread gameThread;

    public GameEngine(GameModel model) {
        this.model = model;
        this.isRunning = false;
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
                    model.nextGeneration();
                    long elapsedTime = System.currentTimeMillis() - startTime;

                    long sleepTime = delayMs - elapsedTime;
                    if (sleepTime > 0) {
                        Thread.sleep(sleepTime);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
        gameThread.start();
    }

    public synchronized void stop() {
        isRunning = false;
        if (gameThread != null) {
            this.gameThread.interrupt();
            // Clean up thread
            this.gameThread = null;
        }
    }

    public void togglePause() {
        if (isRunning) {
            stop();
        } else {
            start();
        }
    }

    public void setSpeed(int delayMs) {
        this.delayMs = delayMs;
    }

    public boolean isRunning() {
        return isRunning;
    }
}
