package com.alve.alve0.controller;

import com.alve.alve0.model.Food;
import javafx.animation.AnimationTimer;
import com.alve.alve0.model.World;
import com.alve.alve0.view.SimulationView;

public class SimulationController {
    private SimulationView view;
    private World world;
    private long lastFoodAddTime = 0;
    private static final long FOOD_ADD_INTERVAL = 5_000_000_000L; // 5 seconds in nanoseconds

    public SimulationController(SimulationView view) {
        this.view = view;
        this.world = new World();
    }

    public void startSimulation() {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                // Update entities positions and states
                world.update();

                // Add some food occasionally
                if (now - lastFoodAddTime > FOOD_ADD_INTERVAL) {
                    world.addRandomFood(5);
                    lastFoodAddTime = now;
                }

                // Draw everything
                view.clear();
                for (var entity : world.getEntities()) {
                    view.drawEntity(entity.getX(), entity.getY());
                }
                for (Food food : world.getFoods()) {
                    view.drawFood(food.x, food.y);
                }
            }
        };
        timer.start();
    }
}