package com.alve.alve0.controller;

import com.alve.alve0.model.Entity;
import com.alve.alve0.model.World;

// Questo è il motore di simulazione principale che gestisce il ciclo di vita della simulazione
public class SimulationEngine {
    private World world;
    // Riferimenti ad altri manager (EntityManager, EvolutionManager, etc.)
    private boolean isPaused = true; // Stato iniziale

    public SimulationEngine(World world /*, altri manager */) {
        this.world = world;
    }

    public void tick() {
        if (isPaused) return;

        // 1. Aggiorna ambiente (es. crescita cibo nel mondo)
        world.update();

        for (Entity entity : world.getEntities()) {
            entity.update(world);
        }

        // 3. Gestisci nascite/morti (EvolutionManager?)
        // evolutionManager.handleLifecycle(world.getEntities());

        // 4. Aggiorna statistiche
        // statsManager.updateStats(world);

    }

    public void start() { isPaused = false; }
    public void pause() { isPaused = true; }
    public void resume() { isPaused = false; }
    public void stop() { isPaused = true; /* magari resetta stato */ }
    public boolean isRunning() { return !isPaused; }

}
