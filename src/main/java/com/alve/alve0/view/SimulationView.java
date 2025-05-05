package com.alve.alve0.view;

import com.alve.alve0.model.Food;
import com.alve.alve0.model.World;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class SimulationView {
    private Canvas canvas;
    private GraphicsContext gc;

    public SimulationView() {
        this.canvas = new Canvas(800, 600);
        this.gc = canvas.getGraphicsContext2D();
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void drawEntity(double x, double y) {
        gc.setFill(Color.RED);
        gc.fillOval(x, y, 10, 10);
    }

    public void drawFood(double x, double y) {
        gc.setFill(Color.GREEN);
        gc.fillOval(x, y, 6, 6);
    }

    public void clear() {
        gc.setFill(Color.DARKGRAY);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }
}
