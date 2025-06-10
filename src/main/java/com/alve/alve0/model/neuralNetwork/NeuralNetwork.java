package com.alve.alve0.model.neuralNetwork;

import java.util.*;

public class NeuralNetwork {
    private Layer inputLayer;
    private Layer[] hiddenLayers;
    private Layer outputLayer;

    public NeuralNetwork(int inputCount, int hiddenCount, int outputCount) {
        Neuron.setWeightRange(-1f, 1f); // Initial weight range

        inputLayer = new Layer(new float[inputCount]);
        if (hiddenCount > 0) {
            hiddenLayers = new Layer[1];
            hiddenLayers[0] = new Layer(inputCount, hiddenCount);
            outputLayer = new Layer(hiddenCount, outputCount);
        } else {
            hiddenLayers = new Layer[0];
            outputLayer = new Layer(inputCount, outputCount);
        }
    }

    public float[] forward(float[] inputs) {
        // Set input values
        for (int i = 0; i < inputs.length; i++) {
            inputLayer.neurons[i].value = inputs[i];
        }

        float[] prevOutputs = new float[inputLayer.neurons.length];
        for (int i = 0; i < inputLayer.neurons.length; i++) {
            prevOutputs[i] = inputLayer.neurons[i].value;
        }

        // Hidden layer(s)
        for (Layer hidden : hiddenLayers) {
            float[] newOutputs = new float[hidden.neurons.length];
            for (int i = 0; i < hidden.neurons.length; i++) {
                float sum = hidden.neurons[i].bias;
                for (int j = 0; j < prevOutputs.length; j++) {
                    sum += prevOutputs[j] * hidden.neurons[i].weights[j];
                }
                newOutputs[i] = StatUtil.Sigmoid(sum);
                hidden.neurons[i].value = newOutputs[i];
            }
            prevOutputs = newOutputs;
        }

        // Output layer
        float[] outputs = new float[outputLayer.neurons.length];
        for (int i = 0; i < outputLayer.neurons.length; i++) {
            float sum = outputLayer.neurons[i].bias;
            for (int j = 0; j < prevOutputs.length; j++) {
                sum += prevOutputs[j] * outputLayer.neurons[i].weights[j];
            }
            outputs[i] = StatUtil.Sigmoid(sum);
            outputLayer.neurons[i].value = outputs[i];
        }
        return outputs;
    }

    // Mutazione casuale per evoluzione
    public void mutate(float mutationRate) {
        for (Layer layer : hiddenLayers) {
            for (Neuron neuron : layer.neurons) {
                for (int i = 0; i < neuron.weights.length; i++) {
                    if (Math.random() < mutationRate) {
                        neuron.weights[i] += StatUtil.RandomFloat(-0.5f, 0.5f);
                    }
                }
                if (Math.random() < mutationRate) {
                    neuron.bias += StatUtil.RandomFloat(-0.5f, 0.5f);
                }
            }
        }
        for (Neuron neuron : outputLayer.neurons) {
            for (int i = 0; i < neuron.weights.length; i++) {
                if (Math.random() < mutationRate) {
                    neuron.weights[i] += StatUtil.RandomFloat(-0.5f, 0.5f);
                }
            }
            if (Math.random() < mutationRate) {
                neuron.bias += StatUtil.RandomFloat(-0.5f, 0.5f);
            }
        }
    }

    // Clonazione per ereditarietà genetica
    // src/main/java/com/alve/alve0/model/neuralNetwork/NeuralNetwork.java

    public NeuralNetwork cloneAndMutate(float mutationRate) {
        NeuralNetwork copy = new NeuralNetwork(
                inputLayer.neurons.length,
                hiddenLayers[0].neurons.length,
                outputLayer.neurons.length
        );

        // Deep copy weights and biases
        for (int l = 0; l < hiddenLayers.length; l++) {
            for (int n = 0; n < hiddenLayers[l].neurons.length; n++) {
                System.arraycopy(
                        this.hiddenLayers[l].neurons[n].weights, 0,
                        copy.hiddenLayers[l].neurons[n].weights, 0,
                        this.hiddenLayers[l].neurons[n].weights.length
                );
                copy.hiddenLayers[l].neurons[n].bias = this.hiddenLayers[l].neurons[n].bias;
            }
        }
        for (int n = 0; n < outputLayer.neurons.length; n++) {
            System.arraycopy(
                    this.outputLayer.neurons[n].weights, 0,
                    copy.outputLayer.neurons[n].weights, 0,
                    this.outputLayer.neurons[n].weights.length
            );
            copy.outputLayer.neurons[n].bias = this.outputLayer.neurons[n].bias;
        }

        copy.mutate(mutationRate);
        return copy;
    }

    public Layer[] getAllLayers() {
        Layer[] all = new Layer[hiddenLayers.length + 2];
        all[0] = inputLayer;
        System.arraycopy(hiddenLayers, 0, all, 1, hiddenLayers.length);
        all[all.length - 1] = outputLayer;
        return all;
    }
}