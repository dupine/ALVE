package com.alve.alve0.controller;

import com.alve.alve0.model.Entity;
import com.alve.alve0.model.World;

// Questo è il motore di simulazione principale che gestisce il ciclo di vita della simulazione
public class SimulationEngine {
    private World world;

    private boolean isPaused = true;

    public SimulationEngine(World world /*, altri manager */) {
        this.world = world;
    }

    public void tick() {
        if (isPaused) return;

        world.update();

        for (Entity entity : world.getEntities()) {
            entity.update(world);
        }
    }

    public void start() { isPaused = false; }
    public void pause() { isPaused = true; }
    public void resume() { isPaused = false; }
    public void stop() { isPaused = true; /* magari resetta stato */ }
    public boolean isRunning() { return !isPaused; }

}
