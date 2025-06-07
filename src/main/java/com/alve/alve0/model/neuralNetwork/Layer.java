package com.alve.alve0.model.neuralNetwork;

public class Layer {

    public Neuron[] neurons;

    //Contructor for hidden/output layer
    public Layer(int inNeurons, int outNeurons) {
        this.neurons = new Neuron[outNeurons];

        for (int i = 0; i < outNeurons; i++) {
            float[] weights = new float[inNeurons];
            for (int j = 0; j < inNeurons; j++) {
                weights[j] = StatUtil.RandomFloat(Neuron.minWeightValue, Neuron.maxWeightValue);
            }
            neurons[i] = new Neuron(weights, StatUtil.RandomFloat(0, 1));
        }
    }

    // Contructor for input layer
    public Layer(float input[]){
        this.neurons = new Neuron[input.length];
        for (int i = 0; i < input.length; i++) {
            neurons[i] = new Neuron(input[i]);
        }
    }
}
