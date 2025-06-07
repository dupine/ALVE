package com.alve.alve0.model;

import com.alve.alve0.model.neuralNetwork.NeuralNetwork;

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
    private double fitness;
    private int lifeTick = 0;

    StaticGenome genome;
    private NeuralNetwork brain;

    public Entity(double x, double y) {
        this.x = x;
        this.y = y;
        this.id = idCounter++;
        genome = new StaticGenome();
        this.energy = genome.getEnergy();
        targetFood = null;
        fitness = 0;

        brain = new NeuralNetwork(5, 0, 4);
    }

    public void update(World world) {
        consume();
        findAndSetTargetFood(world);
        float[] inputs = sense(world);
        float[] outputs = brain.forward(inputs);
        act(outputs, world);
        clampPosition(world.getWidth(), world.getHeight());

        lifeTick++;

        if (targetFood != null) {
            double diffX = x - targetFood.getX();
            double diffY = y - targetFood.getY();
            double distanceSquared = (diffX * diffX) + (diffY * diffY);

            if (distanceSquared <= genome.getVisionRange()/2) {
                updateFitness(0.25);
            }
        }

        /*if (targetFood != null) {
            moveToTargetAndEat(world);
        } else {
            explore(world);
        }*/
    }

    public void updateFitness(double n) {
        fitness += n;
    }

    public void consume() {
        this.energy -= genome.getMetabolism();
    }

    public float getEnergy() {
        return energy;
    }

    public void increaseEnergy(float amount) {
        this.energy += amount;
        if (this.energy > 1000) this.energy = 1000;
    }

    public boolean canReproduce() {
        return energy > 800 && lifeTick > reproduceAge();
    }

    public int reproduceAge() {
        return (int) ((genome.getEnergy() / genome.getMetabolism()) * 0.7);
    }

    public Entity reproduce() {
        Entity child = new Entity(this.x, this.y);
        child.brain = this.brain.cloneAndMutate(0.1f);
        // Optionally mutate genome too
        child.energy = child.getGenome().getEnergy();
        return child;
    }

    private float[] sense(World world) {
        float normEnergy = getEnergy() / 1000f;
        float dist = 1f, dx = 0f, dy = 0f;
        if (targetFood != null) {
            dx = (float) (targetFood.getX() - this.x) / world.getWidth();
            dy = (float) (targetFood.getY() - this.y) / world.getHeight();
            dist = (float) Math.sqrt(dx * dx + dy * dy);
        }
        return new float[] { normEnergy, dist, dx, dy, 1f };
    }

    private void act(float[] outputs, World world) {
        // Output 0: move forward
        if (outputs[0] > 0.5) {
            this.x += genome.getSpeed();
        }
        // Output 1: move left
        if (outputs[1] > 0.5) {
            this.x -= genome.getSpeed();
        }
        // Output 2: move up
        if (outputs[2] > 0.5) {
            this.y -= genome.getSpeed();
        }
        // Output 3: try to eat
        if (outputs[3] > 0.5 && targetFood != null) {
            double d = distance(this.x, this.y, targetFood.getX(), targetFood.getY());
            if (d < 5.0) {
                eat(targetFood, world);
                targetFood = null;
            }
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
        increaseEnergy(foodItem.getEnergy());
        world.removeFood(foodItem);
        updateFitness(0.5);
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
    public double getFitness() { return fitness; }
    public StaticGenome getGenome() { return genome; }
    public NeuralNetwork getBrain() { return brain; }
}
