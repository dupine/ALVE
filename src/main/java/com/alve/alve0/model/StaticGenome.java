package com.alve.alve0.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class StaticGenome {
    private String sequence;

    // queste caratteristiche sono quelle base di ogni entità, che già dalla nasciata possiedono
    private float energy = 10000;
    private float visionRange;
    private float speed;
    private float size;
    private float metabolism;
    // colore RGB
    private float red;
    private float green;
    private float blue;
    // queste le ho messe public ma solo per fare una prova
    public int MAX_AGE; // or another reasonable value
    public int MAX_FOOD_MEMORY;
    public int MAX_FOOD_VISIBILITY;

    public StaticGenome() {
        Random rand = new Random();
        this.size = 10f + rand.nextFloat() * 10f;          // 0.5 - 2.0
        this.speed = 0.1f + rand.nextFloat() * 1.4f;         // 0.1 - 1.5
        this.visionRange = 10 + rand.nextFloat() * 90;       // 10 - 100
        this.metabolism = 0.05f + rand.nextFloat() * 0.15f;  // 0.05 - 0.2
        this.red = rand.nextInt(256);
        this.green = rand.nextInt(256);
        this.blue = rand.nextInt(256);
        this.MAX_AGE = rand.nextInt(10000)+1000; // 1000 - 11000
        this.MAX_FOOD_MEMORY = rand.nextInt(50)+5;
        this.MAX_FOOD_VISIBILITY = rand.nextInt(30)+1;
    }

    public void mutate(float mutationRate) {
        Random rand = new Random();
        // Mutate size
        if (rand.nextFloat() < mutationRate) {
            size += (rand.nextFloat() - 0.5f) * 2.0f;
            size = Math.max(1f, Math.min(size, 30f));
        }
        // Mutate speed
        if (rand.nextFloat() < mutationRate) {
            speed += (rand.nextFloat() - 0.5f) * 0.3f;
            speed = Math.max(0.01f, Math.min(speed, 3f));
        }
        // Mutate vision range
        if (rand.nextFloat() < mutationRate) {
            visionRange += (rand.nextFloat() - 0.5f) * 20f;
            visionRange = Math.max(1f, Math.min(visionRange, 200f));
        }
        // Mutate metabolism
        if (rand.nextFloat() < mutationRate) {
            metabolism += (rand.nextFloat() - 0.5f) * 0.02f;
            metabolism = Math.max(0.01f, Math.min(metabolism, 1f));
        }
        // Mutate color
        if (rand.nextFloat() < mutationRate) {
            red += rand.nextInt(21) - 10;
            red = Math.max(0, Math.min(red, 255));
        }
        if (rand.nextFloat() < mutationRate) {
            green += rand.nextInt(21) - 10;
            green = Math.max(0, Math.min(green, 255));
        }
        if (rand.nextFloat() < mutationRate) {
            blue += rand.nextInt(21) - 10;
            blue = Math.max(0, Math.min(blue, 255));
        }
        // Mutate limits
        if (rand.nextFloat() < mutationRate) {
            MAX_AGE += rand.nextInt(201) - 100;
            MAX_AGE = Math.max(100, Math.min(MAX_AGE, 20000));
        }
        if (rand.nextFloat() < mutationRate) {
            MAX_FOOD_MEMORY += rand.nextInt(11) - 5;
            MAX_FOOD_MEMORY = Math.max(1, Math.min(MAX_FOOD_MEMORY, 100));
        }
        if (rand.nextFloat() < mutationRate) {
            MAX_FOOD_VISIBILITY += rand.nextInt(5) - 2;
            MAX_FOOD_VISIBILITY = Math.max(1, Math.min(MAX_FOOD_VISIBILITY, 50));
        }
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("Size", size);
        map.put("Speed", speed);
        map.put("Vision", visionRange);
        map.put("Metabolism", metabolism);
        map.put("Red", red);
        map.put("Green", green);
        map.put("Blue", blue);
        return map;
    }

    public float getEnergy() {
        return energy;
    }

    public float getVisionRange() {
        return visionRange;
    }

    public float getSpeed() {
        return speed;
    }

    public float getSize() {
        return size;
    }

    public float getMetabolism() {
        return metabolism;
    }

    public float getRed() {
        return red;
    }

    public float getGreen() {
        return green;
    }

    public float getBlue() {
        return blue;
    }

    @Override
    public String toString() {
        return "StaticGenome{" +
                "sequence='" + sequence + '\'' +
                ", visionRange=" + visionRange +
                ", speed=" + speed +
                ", size=" + size +
                ", metabolism=" + metabolism +
                ", red=" + red +
                ", green=" + green +
                ", blue=" + blue +
                '}';
    }
}
