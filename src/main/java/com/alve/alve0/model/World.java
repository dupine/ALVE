package com.alve.alve0.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class World {
    private List<Entity> entities = new ArrayList<>();
    private List<Food> foods = new ArrayList<>();
    private static final int INITIAL_ENTITIES = 20;
    private static final int INITIAL_FOOD = 1000;

    public World() {
        initializeEntities(); // metodo per inizializzare le entità
        initializeFood(); // metodo per inizializzare il cibo
        // ho separato i metodi per gestire meglio le cose
    }

    private void initializeEntities() {
        for (int i = 0; i < INITIAL_ENTITIES; i++) {
            double x = Math.random() * 800;
            double y = Math.random() * 600;
            System.out.println("Creating entity at: " + x + ", " + y);
            entities.add(new Entity(x, y));
        }
    }

    public void initializeFood() {
        for (int i = 0; i < INITIAL_FOOD; i++) {
            double x = Math.random() * 800;
            double y = Math.random() * 600;
            foods.add(new Food(x, y));
        }
    }

    public void update() {
        List<Entity> newEntities = new ArrayList<>();
        Iterator<Entity> it = entities.iterator();

        while (it.hasNext()) {
            Entity e = it.next();
            e.update(this);

            if (e.energy <= 0) {
                it.remove();
            } else if (e.energy > 300) {
                e.energy /= 2;
                newEntities.add(new Entity(e.x, e.y));
            }
        }

        //entities.addAll(newEntities); // solo nuovi vivi
    }

    // Occasionally add new food
    public void addRandomFood(int count) {
        for (int i = 0; i < count; i++) {
            double x = Math.random() * 800;
            double y = Math.random() * 600;
            foods.add(new Food(x, y));
        }
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public List<Food> getFoods() {
        return foods;
    }
}