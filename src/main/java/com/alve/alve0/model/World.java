package com.alve.alve0.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Iterator;

public class World {
    private List<Entity> entities = new ArrayList<>();
    private List<Food> foods = new ArrayList<>();
    private static final int INITIAL_ENTITIES = 100;
    private static final int INITIAL_FOOD = 500;
    private static final int width = 1200;
    private static final int height = 800;
    private static final int cellSize = 50;

    private Map<GridCellKey, List<Food>> foodSpatialGrid = new HashMap<>();

    public World() {
        initializeEntities();
        initializeFood();
    }

    private void initializeEntities() {
        for (int i = 0; i < INITIAL_ENTITIES; i++) {
            double x = Math.random() * width;
            double y = Math.random() * height;
            entities.add(new Entity(x, y));
        }
    }

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

        addRandomFood(5);

        //entities.addAll(newEntities); // solo nuovi vivi
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

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public void addRandomFood(int count) {
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

    public List<Entity> getEntities() {
        return entities;
    }
    public List<Food> getFoods() {
        return foods;
    }
    public int getWidth() {return width;}
    public int getHeight() {return height;}
}