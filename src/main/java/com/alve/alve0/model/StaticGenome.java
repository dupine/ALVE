package com.alve.alve0.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class StaticGenome {
    private String sequence;

    // queste caratteristiche sono quelle base di ogni entità, che già dalla nasciata possiedono
    private float energy = 1000;
    private float visionRange = 50;
    private float speed = 5;
    private float size = 10;
    private float metabolism = 1.4f;
    // colore RGB
    private float red = 1000;
    private float green = 1000;
    private float blue = 1000;

    public StaticGenome() {
        Random rand = new Random();
        this.size = 5f + rand.nextFloat() * 5f;          // 0.5 - 2.0
        this.speed = 0.1f + rand.nextFloat() * 1.4f;         // 0.1 - 1.5
        this.visionRange = 10 + rand.nextFloat() * 90;       // 10 - 100
        this.metabolism = 0.05f + rand.nextFloat() * 0.15f;  // 0.05 - 0.2
        this.red = rand.nextInt(256);
        this.green = rand.nextInt(256);
        this.blue = rand.nextInt(256);
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
