package com.alve.alve0.model.neuralNetwork;

public class Neuron {
    static float minWeightValue;
    static float maxWeightValue;

    float[] weights;
    float[] cacheWeights;
    float gradient;
    float bias;
    float value = 0;

    // Constructor for hidden/output neurons
    public Neuron(float[] weights, float bias){
        this.weights = weights;
        this.cacheWeights = this.weights;
        this.bias = bias;
        this.gradient = 0;
    }

    // Constructor for input neurons
    public Neuron(float value) {
        this.weights = null;
        this.cacheWeights = this.weights;
        this.bias = -1;
        this.gradient = -1;
        this.value = value;
    }

    // static funtion to set the weight range
    public static void setWeightRange(float min, float max) {
        minWeightValue = min;
        maxWeightValue = max;
    }

    public void updateWeight() {
        this.weights = this.cacheWeights;
    }

    public Neuron getNeuron(int index) {
        if (weights == null || index < 0 || index >= weights.length) {
            throw new IndexOutOfBoundsException("Invalid neuron index.");
        }
        return new Neuron(new float[] { weights[index] }, bias);
    }

    public float[] getWeights() {
        return weights;
    }

    public float getBias() {
        return bias;
    }
}
