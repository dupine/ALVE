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

    private final double ENERGY_REQUIRED_TOREPRODUCE = 0.2f; // 50 percento

    private int foodEaten = 0;
    private int tickSinceLastMeal = 0;
    private float energy;
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

        brain = new NeuralNetwork(12, 16, 3);
    }

    public void update(World world) {
        consume();
        float[] inputs = sense(world);
        float[] outputs = brain.forward(inputs);
        act(outputs, world);
        clampPosition(world.getWidth(), world.getHeight());

        double prevDist = targetFood != null ? distance(x, y, targetFood.getX(), targetFood.getY()) : 0;
        findAndSetTargetFood(world);
        double newDist = targetFood != null ? distance(x, y, targetFood.getX(), targetFood.getY()) : 0;
        if (targetFood != null && newDist < prevDist) {
            updateFitness(0.05); // Reward for getting closer
        }
        if (Math.abs(x) > 0.1 || Math.abs(y) > 0.1) {
            updateFitness(0.01); // Reward for moving
        }

        lifeTick++;
        tickSinceLastMeal++;

        if (targetFood != null) {
            double diffX = x - targetFood.getX();
            double diffY = y - targetFood.getY();
            double distanceSquared = (diffX * diffX) + (diffY * diffY);

            if (distanceSquared <= genome.getVisionRange()/2) {
                updateFitness(0.25);
            }
        }
    }

    public void updateFitness(double n) {
        fitness += n;
    }

    public void consume() {
        this.energy -= genome.getMetabolism();
    }

    public void increaseEnergy(float amount) {
        this.energy += amount;
        if (this.energy > 1000) this.energy = 1000;
    }

    public Entity reproduce() {
        Entity child = new Entity(this.x, this.y);
        child.brain = this.brain.cloneAndMutate(0.1f);
        child.genome = new StaticGenome();
        // Copy parent's genome values
        child.genome = this.genome; // or implement a copy constructor
        child.genome.mutate(0.1f);  // Mutate genome
        child.energy = child.getGenome().getEnergy();
        return child;
    }

    private float[] sense(World world) {
        float normEnergy = getEnergy() / 1000f;
        float dx = 0f, dy = 0f, dist = 1f, angle = 0f, foodVisible = 0f;
        if (targetFood != null) {
            dx = (float) (targetFood.getX() - this.x);
            dy = (float) (targetFood.getY() - this.y);
            dist = (float) Math.sqrt(dx * dx + dy * dy) / (float) Math.hypot(world.getWidth(), world.getHeight());
            angle = (float) Math.atan2(dy, dx) / (float) Math.PI; // normalized [-1,1]
            foodVisible = 1f;
        }
        float distToWall = Math.min(Math.min((float)x, (float)y), Math.min(world.getWidth()-(float)x, world.getHeight()-(float)y)) / Math.max(world.getWidth(), world.getHeight());
        float randomNoise = (float) (Math.random() * 2 - 1); // [-1,1]

        float normAge = (float) lifeTick / genome.MAX_AGE;
        float normReproAge = (float) reproduceAge() / genome.MAX_AGE;
        float hunger = 1f - normEnergy;
        float fertility = canReproduce() ? 1f : 0f;
        float normFoodVisible = Math.min(nearbyFoodItems.size(), genome.MAX_FOOD_VISIBILITY) / (float) genome.MAX_FOOD_VISIBILITY;

        return new float[] {
                normEnergy, dist, angle, foodVisible, distToWall, randomNoise,
                normAge, normReproAge, hunger, fertility, normFoodVisible, 1f
        };
    }

    private void act(float[] outputs, World world) {
        // Map outputs[0] and outputs[1] from [0,1] to [-1,1]
        double dx = ((outputs[0] * 2) - 1) * genome.getSpeed();
        double dy = ((outputs[1] * 2) - 1) * genome.getSpeed();
        this.x += dx;
        this.y += dy;

        // Try to eat if output[2] > 0.5 and food is close
        if (outputs[2] > 0.5 && targetFood != null) {
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
        foodEaten++;
        tickSinceLastMeal = 0;
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

    // ==========================================
    //            stati dell'entità
    // ==========================================

    public double getHunger() {
        double ageFactor = 1.0 - ((double) lifeTick / reproduceAge()); // più è giovane più è affamata
        double speedFactor = genome.getSpeed() / 10.0; // fattore per la velocità
        double energyFactor = 1.0 - (energy / genome.getEnergy()); // fattore per l'energia, in proporzione a quella massima
        return (genome.getMetabolism() * ageFactor * speedFactor * energyFactor);
    }

    public boolean canReproduce() {
        return energy > (ENERGY_REQUIRED_TOREPRODUCE * genome.getEnergy()) && isMature();
    }

    public boolean isMature() {
        return lifeTick > reproduceAge();
    }

    public int reproduceAge() {
        return (int) ((genome.getEnergy() / genome.getMetabolism()) * 0.7);
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
    public float getEnergy() {
        return energy;
    }
    public StaticGenome getGenome() { return genome; }
    public NeuralNetwork getBrain() { return brain; }
}
