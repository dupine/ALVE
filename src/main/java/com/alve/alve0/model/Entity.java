package com.alve.alve0.model;

import java.util.Random;
import java.util.Iterator;

public class Entity {
    public double x, y;
    public double energy = 500;
    private static final Random random = new Random();
    public int id;
    private static int idCounter = 1; // Contatore per ID sequenziali;

    public Entity(double x, double y) {
        this.x = x;
        this.y = y;
        this.id = idCounter++;
    }

    public void update(World world) {
        x += random.nextDouble() * 4 - 2;
        y += random.nextDouble() * 4 - 2;
        energy -= 0.5;

        // Cerca cibo vicino
        for (Iterator<Food> it = world.getFoods().iterator(); it.hasNext(); ) {
            Food food = it.next();
            if (distance(x, y, food.x, food.y) < 10) {
                energy += 20;
                it.remove();
                break;
            }
        }
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
}
