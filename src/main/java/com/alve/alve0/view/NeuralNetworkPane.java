// src/main/java/com/alve/alve0/view/NeuralNetworkPane.java
package com.alve.alve0.view;

import com.alve.alve0.model.Entity;
import com.alve.alve0.model.neuralNetwork.NeuralNetwork;
import com.alve.alve0.model.neuralNetwork.Layer;
import com.alve.alve0.model.neuralNetwork.Neuron;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

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

        if (entity == null) return;
        NeuralNetwork nn = entity.getBrain();
        if (nn == null) return;

        int layerIdx = 0;
        for (Layer layer : nn.getAllLayers()) {
            Label layerLabel = new Label("Layer " + layerIdx++);
            getChildren().add(layerLabel);
            int neuronIdx = 0;
            for (Neuron neuron : layer.neurons) {
                StringBuilder sb = new StringBuilder();
                sb.append("Neuron ").append(neuronIdx++).append(": ");
                if (neuron.getWeights() != null) {
                    sb.append("W: ");
                    for (float w : neuron.getWeights()) sb.append(String.format("%.2f ", w));
                }
                sb.append("B: ").append(String.format("%.2f", neuron.getBias()));
                getChildren().add(new Label(sb.toString()));
            }
        }
    }
}