package com.jetbrains;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static double speed = 0.1;

    public static void main(String[] args) {
        InputFile iris_test = new InputFile(
                System.getProperty("user.dir") + "\\data\\iris_test.txt"
        );

        InputFile iris_training = new InputFile(
                System.getProperty("user.dir") + "\\data\\iris_training.txt"
        );

        Perceptron perceptron_setosa =
                new Perceptron("Iris-setosa", iris_test.getNumberOfColumns() - 1, speed);
        Perceptron perceptron_versicolor =
                new Perceptron("Iris-versicolor", iris_test.getNumberOfColumns() - 1, speed);
        Perceptron perceptron_virginica =
                new Perceptron("Iris-virginica", iris_test.getNumberOfColumns() - 1, speed);


    }
}
