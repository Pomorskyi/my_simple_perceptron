package com.jetbrains;

import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        InputFile iris_test = new InputFile(
                System.getProperty("user.dir") + "\\data\\iris_test.txt"
        );

        InputFile iris_training = new InputFile(
                System.getProperty("user.dir") + "\\data\\iris_training.txt"
        );

        System.out.print("Input k: ");
        Scanner scanner = new Scanner(System.in);
        int k = Integer.parseInt(scanner.nextLine());

        showStatistic(iris_test, iris_training, k);
        System.out.println();

        String line = "";
        do {
            System.out.print("Input new instance for test('float float ... float (k-times) decision'): ");
            line = scanner.nextLine().trim().replaceAll("\\.", ",");
            InputFile.Record record = new InputFile.Record(line);

            testInstance(record, iris_training.getRecords(), k, iris_training.getNumberOfColumns() - 1);
            System.out.println();
        } while (line.compareTo("") != 0);
    }

    public static void showStatistic(InputFile test, InputFile training, int k) {
        if (test.getNumberOfColumns() != training.getNumberOfColumns()) {
            System.out.println("\t\t!!!\tIncorrect files");
            return;
        }

        int counterForTrue = 0;
        List<InputFile.Record> testRecords = test.getRecords();
        List<InputFile.Record> trainingRecords = training.getRecords();

        System.out.println("\t\tTestRecord\t\t\t\t\t|\t\tNeighbourhood\t\t\t\t\t|\tIsCorrect");
        for (InputFile.Record testRecord : testRecords) {
            if (testInstance(testRecord, trainingRecords,
                    k, test.getNumberOfColumns() - 1))
                counterForTrue++;
        }

        System.out.println();
        System.out.println("Correctness of experiment:\t\t\t" + counterForTrue + "\t/\t" + testRecords.size());
        System.out.println("Possibility for correct answer:\t\t" + ((double) counterForTrue * 100 / (double) testRecords.size()) + " %");
    }

    private static boolean testInstance(InputFile.Record testRecord,
                                        List<InputFile.Record> trainingRecords,
                                        int k, int numOfAttr) {
        Map<Integer, Double> distances = new HashMap<Integer, Double>();

        for (int i = 0; i < trainingRecords.size(); i++) {
            distances.put(i,
                    InputFile.distanceBetween(testRecord, trainingRecords.get(i), numOfAttr));
        }
        distances = distances.entrySet().stream().sorted(Map.Entry.comparingByValue())
                .limit(k)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        int numOfTheSame = 0;
        for (int indInDistances = 0; indInDistances < k; indInDistances++) {
            if (trainingRecords.get(
                    (Integer) (((Map.Entry) (distances.entrySet().toArray()[indInDistances])).getKey())
            ).decision.trim().compareTo(testRecord.decision.trim()) == 0) {
                numOfTheSame++;
            }
        }

        double procentOfTheSame = (double) numOfTheSame * 100 / (double) k;

        System.out.println(testRecord
                + "\t|\t" + procentOfTheSame + " % " + testRecord.decision.trim() +
                "\t" + (100d - procentOfTheSame) + " % Other\t|\t" + (procentOfTheSame > 50 ? "true" : "false"));

        return procentOfTheSame > 50d;
    }
}
