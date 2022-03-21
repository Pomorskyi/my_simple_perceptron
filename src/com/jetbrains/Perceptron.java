package com.jetbrains;

import java.util.Arrays;
import java.util.List;

public class Perceptron {

    public String expectName;
    public double[] vectorOfWeights;
    public double speedOfLearning;

    public Perceptron(String name, int numberOfFeatures, double speedOfLearning) {
        this.expectName = name;
        this.speedOfLearning = speedOfLearning;
        this.vectorOfWeights = new double[numberOfFeatures];
        Arrays.fill(vectorOfWeights, 0.1);
    }

    public void learnPerceptron(List<InputFile.Record> records) {
        for (int k = 1; k <= records.size(); k++) {
            learningIteration(records.get(k), k);
        }
    }

    private void learningIteration(InputFile.Record record, int k) {
        double predicted_sum = 0;
        int expected_value = record.decision.compareTo(this.expectName) == 0 ? 1 : 0;
        for (int i = 0; i < vectorOfWeights.length; i++) {
            predicted_sum += record.date[i] * vectorOfWeights[i];
        }

        double error = errorValue(predicted_sum, expected_value);
        double[] gradient = new double[this.vectorOfWeights.length];

        //counting gradient
        for(int i = 0; i < gradient.length; i++){
            gradient[i] = 2 * Math.sqrt(error) * record.date[i];
        }

        //refreshing weights
        for(int i = 0; i++)

    }

    private double errorValue(double predicted_sum, int expected_sum) {
        return Math.pow((predicted_sum - expected_sum), 2);
    }
}
