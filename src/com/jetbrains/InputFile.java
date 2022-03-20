package com.jetbrains;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class InputFile {
    private static final int bufSize = 255;
    private final String path;
    private static int numberOfColumns;
    private List<Record> records;

    public InputFile(String path) {
        this.path = path;
        records = new LinkedList<Record>();
        numberOfColumns = 1;

        if (readContent() > 0) {
            System.out.println("Successfully read " + path);
        } else {
            System.out.println("\t!\tCould not read " + path);
        }
    }

    private int readContent() {
        try (RandomAccessFile inAccessFile
                     = new RandomAccessFile(path, "r");
             FileChannel inChannel = inAccessFile.getChannel();
             ByteArrayOutputStream in = new ByteArrayOutputStream()) {

            ByteBuffer inBuff = bufSize > inChannel.size() ?
                    ByteBuffer.allocate((int) inChannel.size()) :
                    ByteBuffer.allocate(bufSize);

            while (inChannel.read(inBuff) > 0) {
                in.write(inBuff.array(), 0, inBuff.position());
                inBuff.clear();
            }
            String fileContent = new String(in.toByteArray(), StandardCharsets.UTF_8);
            numberOfColumns = countNumberOfCols(fileContent.split("\n")[0]);
            this.records
                    .addAll(Arrays.stream(fileContent.split("\n"))
                            .map(Record::new)
                            .collect(Collectors.toList()));


            return numberOfColumns;
        } catch (IOException | IllegalArgumentException e) {
            return -1;
        }
    }

    private static int countNumberOfCols(String line) throws IllegalArgumentException {
        if (line.length() > 0) {
            Pattern p = Pattern.compile("([0-9]+,?[0-9]*)|(\\w+-?\\w*)");
            Matcher m = p.matcher(line);

            int counter = 0;
            while (m.find())
                counter++;

            return counter;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public int getNumberOfColumns() {
        return numberOfColumns;
    }

    public List<Record> getRecords() {
        return records;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (Record record : records) {
            str.append(record).append("\n");
        }
        return path + ": \n" +
                "Number of columns: " + numberOfColumns + "\n" +
                str.toString();
    }

    public static double distanceBetween(InputFile.Record a, InputFile.Record b, int numOfAttr) {
        double sum = 0;
        for (int i = 0; i < numOfAttr; i++) {
            sum += Math.pow((a.date[i] - b.date[i]), 2);
        }
        return Math.pow(sum, (double) 1 / 2);
    }

    public static class Record {
        public float[] date;
        public String decision;

        public Record(String line) {
            line = line.trim();
            date = new float[numberOfColumns - 1];
            Scanner scanner = new Scanner(line);

            for (int i = 0; i < numberOfColumns - 1; i++) {
                date[i] = scanner.nextFloat();
            }

            decision = line.substring(line.lastIndexOf(" ") + 1);
        }

        @Override
        public String toString() {
            return Arrays.toString(date) + "\t" + decision;
        }
    }
}

