package com.alve.alve0.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Iterator;

public class World {
    private List<Entity> entities = new ArrayList<>();
    private List<Food> foods = new ArrayList<>();
    private static final int INITIAL_ENTITIES = 5;
    private static final int INITIAL_FOOD = 50;
    private static final int width = 1200;
    private static final int height = 800;
    private static final int cellSize = 50;

    private int tickCounter = 0;

    private Map<GridCellKey, List<Food>> foodSpatialGrid = new HashMap<>();

    public World() {
        initializeEntities();
        initializeFood();
    }

    // crea n entità in base a INITIAL_ENTITIES
    private void initializeEntities() {
        for (int i = 0; i < INITIAL_ENTITIES; i++) {
            double x = Math.random() * width;
            double y = Math.random() * height;
            entities.add(new Entity(x, y));
        }
    }

    // crea n cibi in base a INITIAL_FOOD
    public void initializeFood() {
        Food food = null;
        for (int i = 0; i < INITIAL_FOOD; i++) {
            double x = Math.random() * width;
            double y = Math.random() * height;
            food = new Food(x, y);
            foods.add(food);

            GridCellKey key = getCellKeyForCoords(food.getX(), food.getY());
            List<Food> foodInCell = this.foodSpatialGrid.computeIfAbsent(key, k -> new ArrayList<>());
            foodInCell.add(food);
        }
    }

    // src/main/java/com/alve/alve0/model/World.java

    public void update() {
        List<Entity> newEntities = new ArrayList<>();

        Iterator<Entity> iterator = entities.iterator();
        while (iterator.hasNext()) {
            Entity e = iterator.next();
            e.update(this);

            if (!e.isAlive()) {
                iterator.remove();
                if (e.getTargetFood() != null) {
                    removeFood(e.getTargetFood());
                }
            } else if (e.canReproduce()) {
                newEntities.add(e.reproduce());
                e.increaseEnergy(-400); // Cost for reproduction
            }
        }

        entities.addAll(newEntities);

        tickCounter++;
        if (tickCounter % 200 == 0) {
            addEntity(new Entity(Math.random() * width, Math.random() * height));
        }

        addRandomFood();
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public void removeEntity(Entity entity) {
        if (entity == null) return;
        entities.remove(entity);
        if (entity.getTargetFood() != null) {
            removeFood(entity.getTargetFood());
        }
    }


    // aggiungo cibo casuale fino a raggiungere INITIAL_FOOD
    public void addRandomFood() {
        if (foods.size() >= INITIAL_FOOD) return; // Controlla se il numero di cibi è già maggiore o uguale al limite

        int count = INITIAL_FOOD - foods.size();

        Food food = null;
        for (int i = 0; i < count; i++) {
            double x = Math.random() * width;
            double y = Math.random() * height;
            food = new Food(x, y);
            foods.add(food);

            GridCellKey key = getCellKeyForCoords(food.getX(), food.getY());
            List<Food> foodInCell = this.foodSpatialGrid.computeIfAbsent(key, k -> new ArrayList<>());
            foodInCell.add(food);
        }
    }

    public void removeFood(Food food) {
        if (food == null) return;
        this.foods.remove(food);

        GridCellKey key = getCellKeyForCoords(food.getX(), food.getY());
        List<Food> foodInCell = this.foodSpatialGrid.get(key);

        if (foodInCell != null) {
            if (foodInCell.remove(food)) {
                if (foodInCell.isEmpty()) {
                    this.foodSpatialGrid.remove(key);
                }
            }
        }
    }

    /**
     * Trova il cibo vicino a una data posizione entro un certo raggio,
     * utilizzando la griglia spaziale per ottimizzare la ricerca.
     * @param queryX La coordinata X del centro della ricerca.
     * @param queryY La coordinata Y del centro della ricerca.
     * @param sensorRange Il raggio della ricerca.
     * @return Una lista di oggetti Food che sono entro il sensorRange.
     */
    public List<Food> getNearbyFood(double queryX, double queryY, double sensorRange) {
        List<Food> candidateFoodItems = new ArrayList<>();
        GridCellKey centerCellKey = getCellKeyForCoords(queryX, queryY);

        // Itera sulle 9 celle: quella centrale e le 8 adiacenti
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                GridCellKey currentCellKey = new GridCellKey(centerCellKey.x + dx, centerCellKey.y + dy);
                List<Food> foodInThisCell = foodSpatialGrid.get(currentCellKey);
                if (foodInThisCell != null) {
                    candidateFoodItems.addAll(foodInThisCell);
                }
            }
        }

        // Ora filtra i candidati per la distanza esatta
        List<Food> actualNearbyFood = new ArrayList<>();
        double sensorRangeSquared = sensorRange * sensorRange; // Confronta con distanze quadrate

        for (Food candidate : candidateFoodItems) {
            //System.out.println("Candidate ("+candidate.getX()+","+candidate.getY()+")");
            double diffX = candidate.getX() - queryX;
            double diffY = candidate.getY() - queryY;
            double distanceSquared = (diffX * diffX) + (diffY * diffY);

            if (distanceSquared <= sensorRangeSquared) {
                actualNearbyFood.add(candidate);
            }
        }
        //System.out.println("Query ("+queryX+","+queryY+"), range "+sensorRange+": Candidates="+candidateFoodItems.size()+", Actual Nearby="+actualNearbyFood.size()); // Debug
        return actualNearbyFood;
    }

    private GridCellKey getCellKeyForCoords(double worldX, double worldY) {
        int cellX = (int) Math.floor(worldX / cellSize);
        int cellY = (int) Math.floor(worldY / cellSize);
        return new GridCellKey(cellX, cellY);
    }

    public List<Entity> getEntities() {
        return entities;
    }
    public List<Food> getFoods() {
        return foods;
    }
    public int getWidth() {return width;}
    public int getHeight() {return height;}
    public int getTickCounter() {return tickCounter;}
}