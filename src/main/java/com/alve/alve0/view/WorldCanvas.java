package com.alve.alve0.view;

import com.alve.alve0.model.Entity;
import com.alve.alve0.model.Food;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import java.util.List;
import java.util.function.Consumer;

public class WorldCanvas extends Canvas {

    private GraphicsContext gc;
    private Consumer<MouseEvent> mouseClickHandler;

    public WorldCanvas(double width, double height) {
        super(width, height);
        this.gc = getGraphicsContext2D();

        this.setOnMouseClicked(event -> {
            if (mouseClickHandler != null) {
                mouseClickHandler.accept(event); // Passa l'evento al gestore esterno (Controller)
            }
        });
    }

    public void render(List<Entity> entities, List<Food> foods, Entity selectedEntity) {
        clear();

        gc.setFill(Color.GREEN);
        for (Food food : foods) {
            gc.fillOval(food.getX() - 3, food.getY() - 3, 6, 6); // -3 per centrare
        }

        for (Entity entity : entities) {

            double size = entity.getGenome().getSize();
            gc.setFill(Color.rgb((int) entity.getGenome().getRed(), (int) entity.getGenome().getGreen(), (int) entity.getGenome().getBlue()));
            if (entity.equals(selectedEntity)) {
                // Evidenzia l'entità selezionata
                gc.setFill(Color.YELLOW);
                gc.fillOval(entity.getX() - (size/2.0), entity.getY() - (size/2.0), size, size);
                gc.setStroke(Color.BLACK);
                gc.strokeOval(entity.getX() - (size/2.0), entity.getY() - (size/2.0), size, size);
                gc.setFill(Color.RED);
            } else {
                gc.fillOval(entity.getX() - (size/2.0), entity.getY() - (size/2.0), size, size);
            }
        }

        if (selectedEntity != null) {
            drawEntityVision(gc, selectedEntity);
        }
    }

    private void drawEntityVision(GraphicsContext gc, Entity entity) {
        double entityX = entity.getX();
        double entityY = entity.getY();
        double sensorRange = entity.getGenome().getVisionRange();

        // A. Disegna il cerchio del raggio sensoriale
        gc.setStroke(Color.LIGHTBLUE);
        gc.setLineWidth(1);
        gc.strokeOval(entityX - sensorRange, entityY - sensorRange, sensorRange * 2, sensorRange * 2);

        // B. Disegna linee verso il cibo visibile
        List<Food> visibleFood = entity.getVisibleFoodItems(); // Ottieni cibo visibile dall'entità
        if (visibleFood != null) {
            gc.setStroke(Color.CYAN); // Colore per il cibo genericamente visibile
            gc.setLineWidth(0.5);
            for (Food foodItem : visibleFood) {
                // Non disegnare una linea verso il targetFood qui, lo faremo dopo con un colore diverso
                if (foodItem != entity.getTargetFood()) {
                    gc.strokeLine(entityX, entityY, foodItem.getX(), foodItem.getY());
                }
            }
        }

        // C. Disegna una linea evidenziata verso il targetFood
        Food target = entity.getTargetFood();
        if (target != null) {
            gc.setStroke(Color.ORANGE); // Colore per il target attuale
            gc.setLineWidth(2); // Linea più spessa
            gc.strokeLine(entityX, entityY, target.getX(), target.getY());

            // Opzionale: evidenzia anche l'oggetto cibo target
            gc.setStroke(Color.ORANGERED);
            gc.setLineWidth(1.5);
            gc.strokeOval(target.getX() - 3, target.getY() - 3, 6, 6); // Cerchio attorno al cibo target
        }
    }

    private void clear() {
        gc.setFill(Color.DARKGRAY); // Colore dello sfondo
        gc.fillRect(0, 0, getWidth(), getHeight());
    }

    public void setOnCanvasMouseClicked(Consumer<MouseEvent> handler) {
        this.mouseClickHandler = handler;
    }
}