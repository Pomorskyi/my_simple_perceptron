package com.jetbrains;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static double speed = 0.1;

    public static void main(String[] args) {
        InputFile iris_test = new InputFile(
                System.getProperty("user.dir") + "\\data2\\iris_test.txt"
        );

        InputFile iris_training = new InputFile(
                System.getProperty("user.dir") + "\\data2\\iris_training.txt"
        );

        Perceptron perceptron_A =
                new Perceptron("A", iris_test.getNumberOfColumns() - 1, speed);
        Perceptron perceptron_B =
                new Perceptron("B", iris_test.getNumberOfColumns() - 1, speed);
        Perceptron perceptron_C =
                new Perceptron("C", iris_test.getNumberOfColumns() - 1, speed);
        Perceptron perceptron_D =
                new Perceptron("D", iris_test.getNumberOfColumns() - 1, speed);

        perceptron_A.learnPerceptron(iris_training.getRecords());
        perceptron_B.learnPerceptron(iris_training.getRecords());
        perceptron_C.learnPerceptron(iris_training.getRecords());
        perceptron_D.learnPerceptron(iris_training.getRecords());

//        System.out.println("SEFSDF");
//        System.out.println(perceptron_setosa.isTrue(new InputFile.Record("5,4    \t 3,7    \t 1,5    \t 0,2    \tIris-setosa")));
        System.out.println("\n\n\t!!!\tTESTING:");
        perceptron_A.testRecords(iris_test.getRecords());
        perceptron_B.testRecords(iris_test.getRecords());
        perceptron_C.testRecords(iris_test.getRecords());
        perceptron_D.testRecords(iris_test.getRecords());
    }

//    public static void main(String[] args) {
//        InputFile iris_test = new InputFile(
//                System.getProperty("user.dir") + "\\data2\\iris_test.txt"
//        );
//
//        InputFile iris_training = new InputFile(
//                System.getProperty("user.dir") + "\\data2\\iris_training.txt"
//        );
//
//        Perceptron perceptron_setosa =
//                new Perceptron("Iris-setosa", iris_test.getNumberOfColumns() - 1, speed);
//        Perceptron perceptron_versicolor =
//                new Perceptron("Iris-versicolor", iris_test.getNumberOfColumns() - 1, speed);
//        Perceptron perceptron_virginica =
//                new Perceptron("Iris-virginica", iris_test.getNumberOfColumns() - 1, speed);
//
//        perceptron_setosa.learnPerceptron(iris_training.getRecords());
//        perceptron_versicolor.learnPerceptron(iris_training.getRecords());
//        perceptron_virginica.learnPerceptron(iris_training.getRecords());
//
////        System.out.println("SEFSDF");
////        System.out.println(perceptron_setosa.isTrue(new InputFile.Record("5,4    \t 3,7    \t 1,5    \t 0,2    \tIris-setosa")));
//        System.out.println("\n\n\t!!!\tTESTING:");
//        perceptron_setosa.testRecords(iris_test.getRecords());
//        perceptron_versicolor.testRecords(iris_test.getRecords());
//        perceptron_virginica.testRecords(iris_test.getRecords());
//    }
}
