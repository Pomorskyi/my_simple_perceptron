package com.jetbrains;

import java.util.Arrays;
import java.util.List;

public class Perceptron {

    public static final double delta = 0.98;
    public String expectName;
    public double[] vectorOfWeights;
    public double speedOfLearning;

    public Perceptron(String name, int numberOfFeatures, double speedOfLearning) {
        this.expectName = name;
        this.speedOfLearning = speedOfLearning;
        this.vectorOfWeights = new double[numberOfFeatures];
        Arrays.fill(vectorOfWeights, 0.1);
    }

    public void testRecords(List<InputFile.Record> records) {
        int size = records.size();
        int numberOfTrue = 0;
        for (int i = 0; i < records.size(); i++) {
            if (isTrue(records.get(i)))
                numberOfTrue++;
        }
        System.out.println("Correctness of experiment: " + numberOfTrue + " / " + size);
        System.out.println("Probability of success: " + ((double) numberOfTrue * 100 / size) + " %");
    }

    public boolean isTrue(InputFile.Record record) {
        double predicted_sum = 0;
        for (int i = 0; i < vectorOfWeights.length; i++) {
            predicted_sum += record.date[i] * vectorOfWeights[i];
        }
        predicted_sum = activisationFun(predicted_sum);
        return predicted_sum >= delta;
    }

    public void learnPerceptron(List<InputFile.Record> records) {
        System.out.println();
        System.out.println("Teaching perceptron: " + expectName);
//        System.out.println("\tPREDICTED_VALUE\t\t|\tWEIGHTS\t\t\t\t\t\t|\tERROR");
        for (int k = 1; k <= records.size(); k++) {
//            System.out.print(k + ".\t");
            learningIteration(records.get(k - 1));
//            System.out.println();
        }
        System.out.println("Teaching perceptron " + expectName + " finished");
    }

    private void learningIteration(InputFile.Record record) {
        double predicted_sum = 0;
        int expected_value = record.decision.compareTo(this.expectName) == 0 ? 1 : 0;
        for (int i = 0; i < vectorOfWeights.length; i++) {
            predicted_sum += record.date[i] * vectorOfWeights[i];
        }
        predicted_sum = activisationFun(predicted_sum);

//        System.out.print("\t" + predicted_sum + "\t\t|");

//        System.out.print("\t" + Arrays.toString(vectorOfWeights) + "\t|");
        double error = errorValue(predicted_sum, expected_value);
        double[] gradient = new double[this.vectorOfWeights.length];

        //counting gradient
        for (int i = 0; i < gradient.length; i++) {
            gradient[i] = 2 * (predicted_sum - expected_value) * record.date[i];
        }
//        System.out.print("\t" + error + "\t\t|");

        //refreshing weights
        for (int i = 0; i < this.vectorOfWeights.length; i++) {
            vectorOfWeights[i] = vectorOfWeights[i] - gradient[i] * speedOfLearning;
        }
    }

    private double errorValue(double predicted_sum, int expected_sum) {
        return Math.pow((predicted_sum - expected_sum), 2);
    }

    private double activisationFun(double val) {
        //Sigmoid function
        return (double) 1 / (1 + Math.pow(Math.E, val * (-1)));
    }
}
