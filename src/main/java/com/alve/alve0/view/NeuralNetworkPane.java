package com.alve.alve0.view;
import com.alve.alve0.model.Entity;
import com.alve.alve0.model.neuralNetwork.NeuralNetwork;
import com.alve.alve0.model.neuralNetwork.Layer;
import com.alve.alve0.model.neuralNetwork.Neuron;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class NeuralNetworkPane extends VBox {

    private Label titleLabel;

    public NeuralNetworkPane() {
        setSpacing(8);
        titleLabel = new Label("Neural Network");
        getChildren().add(titleLabel);
    }

    public void displayNetwork(Entity entity) {
        getChildren().clear();
        getChildren().add(titleLabel);

        if (entity == null || entity.getBrain() == null) return;
        NeuralNetwork nn = entity.getBrain();
        Layer[] layers = nn.getAllLayers();

        Pane graphPane = new Pane();
        double width = 400;
        double height = 300;
        graphPane.setPrefSize(width, height);

        double layerSpacing = width / (layers.length + 1);
        double neuronRadius = 12;

        // Store neuron positions for drawing connections
        double[][] neuronPositions = new double[layers.length][];

        for (int l = 0; l < layers.length; l++) {
            int nCount = layers[l].neurons.length;
            neuronPositions[l] = new double[nCount * 2];
            double vSpacing = height / (nCount + 1);

            for (int n = 0; n < nCount; n++) {
                double x = (l + 1) * layerSpacing;
                double y = (n + 1) * vSpacing;
                neuronPositions[l][n * 2] = x;
                neuronPositions[l][n * 2 + 1] = y;

                Circle neuron = new Circle(x, y, neuronRadius);
                neuron.setFill(l == 0 ? Color.LIGHTBLUE : (l == layers.length - 1 ? Color.LIGHTGREEN : Color.LIGHTGRAY));
                neuron.setStroke(Color.DARKGRAY);
                graphPane.getChildren().add(neuron);
            }
        }

        // Draw connections
        for (int l = 1; l < layers.length; l++) {
            Layer prev = layers[l - 1];
            Layer curr = layers[l];
            for (int n = 0; n < curr.neurons.length; n++) {
                float[] weights = curr.neurons[n].getWeights();
                if (weights == null) continue;
                for (int p = 0; p < prev.neurons.length; p++) {
                    double x1 = neuronPositions[l - 1][p * 2];
                    double y1 = neuronPositions[l - 1][p * 2 + 1];
                    double x2 = neuronPositions[l][n * 2];
                    double y2 = neuronPositions[l][n * 2 + 1];
                    Line line = new Line(x1, y1, x2, y2);
                    float w = weights[p];
                    line.setStroke(w >= 0 ? Color.GREEN : Color.RED);
                    line.setStrokeWidth(1 + 2 * Math.abs(w));
                    graphPane.getChildren().add(line);
                }
            }
        }

        getChildren().add(graphPane);
    }
}