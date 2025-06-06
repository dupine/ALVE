package com.alve.alve0.model;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;

public class Entity {
    public double x, y;
    public int id;
    private static int idCounter = 1;
    private Food targetFood;
    private List<Food> nearbyFoodItems = new ArrayList<>();
    private float energy; // Energia iniziale
    StaticGenome genome;

    public Entity(double x, double y) {
        this.x = x;
        this.y = y;
        this.id = idCounter++;
        genome = new StaticGenome();
        this.energy = genome.getEnergy();
        targetFood = null;
    }

    public void update(World world) {
        consume();

        findAndSetTargetFood(world);

        if (targetFood != null) {
            moveToTargetAndEat(world);
        } else {
            explore(world);
        }

        clampPosition(world.getWidth(), world.getHeight());
    }

    public void consume() {
        this.energy -= genome.getMetabolism();
    }

    public float getEnergy() {
        return energy;
    }

    public void increaseEnergy(float amount) {
        this.energy += amount;
        if (this.energy > 1000) { // Limite massimo di energia
            this.energy = 1000;
        }
    }

    private void findAndSetTargetFood(World world) {
        nearbyFoodItems = world.getNearbyFood(this.x, this.y, genome.getVisionRange());

        Food closestFood = null;
        double minDistanceSquared = Double.POSITIVE_INFINITY;

        if (!nearbyFoodItems.isEmpty()) {
            for (Food food : nearbyFoodItems) {
                double dx = food.getX() - this.x;
                double dy = food.getY() - this.y;
                double distanceSq = (dx * dx) + (dy * dy);

                if (distanceSq < minDistanceSquared) {
                    minDistanceSquared = distanceSq;
                    closestFood = food;
                }
            }
        }
        this.targetFood = closestFood;
    }


    // da cancellare perchè sarà un apsetto da evolvere
    private void moveToTargetAndEat(World world) {
        double dx = targetFood.getX() - this.x;
        double dy = targetFood.getY() - this.y;
        double distance = Math.sqrt(dx * dx + dy * dy);

        double eatThreshold = 5.0;

        if (distance < eatThreshold) {
            eat(targetFood, world);
            this.targetFood = null;
        } else {
            // Muovi verso il cibo
            this.x += (dx / distance) * genome.getSpeed();
            this.y += (dy / distance) * genome.getSpeed();
        }
    }

    public List<Food> getVisibleFoodItems() {
        return nearbyFoodItems;
    }

    private void eat(Food foodItem, World world) {
        // System.out.println("Entity " + id + " is eating food " + foodItem.getId()); // Debug
        increaseEnergy(foodItem.getEnergy());
        world.removeFood(foodItem); // Il mondo gestisce la rimozione dalla griglia
    }

    private Random random = new Random(); // Istanza di Random per l'esplorazione

    private void explore(World world) {
        double angle = random.nextDouble() * 2 * Math.PI; // Angolo casuale
        this.x += Math.cos(angle) * genome.getSpeed() * 0.5; // Esplora un po' più lentamente
        this.y += Math.sin(angle) * genome.getSpeed() * 0.5;
    }

    private void clampPosition(int worldWidth, int worldHeight) {
        // Impedisce all'entità di uscire dai bordi
        if (this.x < 0) this.x = 0;
        if (this.y < 0) this.y = 0;
        if (this.x > worldWidth) this.x = worldWidth;
        if (this.y > worldHeight) this.y = worldHeight;
    }

    private double distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    public boolean isAlive() {
        return energy > 0;
    }

    // getter and setter
    public double getX() { return x; }
    public double getY() { return y; }
    public int getId() { return id; }
    public Food getTargetFood() { return targetFood; }
    public StaticGenome getGenome() { return genome; }
}
