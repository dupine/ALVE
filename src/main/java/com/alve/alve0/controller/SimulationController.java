package com.alve.alve0.controller;

import javafx.animation.AnimationTimer;
import com.alve.alve0.model.World;
import com.alve.alve0.view.SimulationView;
import com.alve.alve0.model.Entity;
import javafx.scene.input.MouseEvent;

public class SimulationController implements SimulationControlListener {

    private SimulationEngine engine;
    private World world;
    private SimulationView view;
    private AnimationTimer gameLoop;
    private Entity selectedEntity = null;
    private boolean isRunning = false;
    private double speedFactor = 1.0;


    public SimulationController(SimulationEngine engine, World world, SimulationView view) {
        this.engine = engine;
        this.world = world;
        this.view = view;
        setupGameLoop();
    }

    private void setupGameLoop() {
        gameLoop = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                // Calcola deltaTime (opzionale ma buono per consistenza)
                if(lastUpdate == 0) {
                    lastUpdate = now;
                    return;
                }
                double deltaTime = (now - lastUpdate) / 1_000_000_000.0; // secondi
                lastUpdate = now;

                view.getEntityInfoPane().displayEntityInfo(selectedEntity);
                view.getControlPanelPane().displayWorldInfo(world);
                view.getNeuralNetworkPane().displayNetwork(selectedEntity);

                if (isRunning) {
                    int steps = (int) Math.max(1, Math.round(deltaTime * 60 * speedFactor));
                    for (int i = 0; i < steps; i++) {
                        engine.tick();
                    }
                }

                view.getWorldCanvas().render(world.getEntities(), world.getFoods(), selectedEntity);
            }
        };
    }

    public void startAnimationLoop() {
        gameLoop.start();
        view.getControlPanelPane().updatePlayPauseButtonState(isRunning);
    }

    @Override
    public void onPlayPauseClicked() {
        isRunning = !isRunning;
        if (isRunning) {
            engine.start();
            System.out.println("Simulation started.");
        } else {
            engine.pause();
            System.out.println("Simulation paused.");
        }
    }

    @Override
    public void onStepClicked() {
        if (!isRunning) {
            engine.start();
            engine.tick();
            engine.pause();

            view.getWorldCanvas().render(world.getEntities(), world.getFoods(), selectedEntity); // Aggiorna subito la vista
        } else {
            isRunning = false;
            engine.pause();
        }
    }

    @Override
    public void onAddEntityClicked() {
        // Logica per aggiungere un'entità casuale nel mondo
        for (int i = 0; i<100; i++) {
            Entity newEntity = new Entity(Math.random() * world.getWidth(), Math.random() * world.getHeight());
            world.addEntity(newEntity);
        }
        System.out.println("Added new entity");
        // Aggiorna subito la vista
        view.getWorldCanvas().render(world.getEntities(), world.getFoods(), selectedEntity);
    }

    @Override
    public void onSpeedChanged(double newSpeedFactor) {
        this.speedFactor = newSpeedFactor;
        System.out.println("Speed factor changed to: " + newSpeedFactor);
    }

    public void handleCanvasClick(MouseEvent event) {
        double clickX = event.getX();
        double clickY = event.getY();

        selectedEntity = findEntityAt(clickX, clickY);
        //view.getWorldCanvas().render(world.getEntities(), world.getFoods(), selectedEntity);
    }

    private Entity findEntityAt(double x, double y) {
        Entity closest = null;
        double minDistanceSq = 10 * 10; // Considera solo click entro 10 pixel dal centro dell'entità (quadrato della distanza)

        for (Entity entity : world.getEntities()) {
            double dx = entity.getX() - x;
            double dy = entity.getY() - y;
            double distanceSq = dx * dx + dy * dy;

            if (distanceSq < minDistanceSq) {
                // Abbiamo trovato un'entità abbastanza vicina (potresti voler prendere la più vicina in assoluto)
                closest = entity;
                minDistanceSq = distanceSq; // Se vuoi la più vicina
            }
        }
        if(closest != null) {
            System.out.println("Selected entity: " + closest.getId());
        } else {
            System.out.println("No entity selected.");
        }
        return closest;
    }
}