package com.alve.alve0.model;

public class Food {
    public double x, y;
    private static final int energy = 10;
    private static int idCounter = 1;

    public Food(double x, double y) {
        this.x = x;
        this.y = y;
        idCounter++;
    }

    // getter and setter
    public double getX() { return x; }
    public double getY() { return y; }
    public int getEnergy() { return energy; }
    public int getId() { return idCounter++; } // ID univoco per ogni cibo
}
