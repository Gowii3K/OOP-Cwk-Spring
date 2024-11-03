package com.oop.cwk;

public class ThreadControl {
    private boolean paused = false;

    public synchronized void pause() {
        paused = true;
    }

    public synchronized void resume() {
        paused = false;
        notifyAll(); // Notify all waiting threads
    }

    public synchronized void checkPaused() throws InterruptedException {
        while (paused) {
            wait(); // Wait until resumed
        }
    }

    public synchronized boolean isPaused() {
        return paused;
    }
}
