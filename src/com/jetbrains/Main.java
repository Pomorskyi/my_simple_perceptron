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

        perceptron_setosa.learnPerceptron(iris_training.getRecords().subList(0, 40));
        perceptron_versicolor.learnPerceptron(iris_training.getRecords().subList(40, 80));
        perceptron_virginica.learnPerceptron(iris_training.getRecords().subList(80, 120));

//        System.out.println("SEFSDF");
//        System.out.println(perceptron_setosa.isTrue(new InputFile.Record("5,4    \t 3,7    \t 1,5    \t 0,2    \tIris-setosa")));
        System.out.println("\n\n\t!!!\tTESTING:");
        perceptron_setosa.testRecords(iris_test.getRecords());
    }
}
