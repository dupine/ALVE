package com.alve.alve0.model.neuralNetwork;

public class StatUtil {

    // Random number generation between min and max
    public static float RandomFloat(float min, float max) {
        float a = (float) Math.random();
        float num = min + (float) Math.random() * (max - min);
        if(a < 0.5) {
            return num;
        } else {
            return -num;
        }
    }

    //Sigmoid function
    public static float Sigmoid(float x) {
        return (float) (1 / (1 + Math.pow(Math.E, -x)));
    }

    //Derivate of the sigmoid function
    public static float SigmoidDerivative(float x) {
        return Sigmoid(x)*(1 - Sigmoid(x));
    }

    //Squared error function
    public static float squaredError(float output, float target) {
        return (float) (0.5*Math.pow(2, (output - target)));
    }
    //Overall sum of squared errors for an array of outputs and targets
    public static float sumSquaredError(float[] output, float[] target) {
        float sum = 0;
        for (int i = 0; i < output.length; i++) {
            sum += squaredError(output[i], target[i]);
        }
        return sum; // Mean Squared Error
    }


}
