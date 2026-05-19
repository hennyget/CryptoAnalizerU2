package com.javarush.zimina;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class StatisticalAnalyzer {
    private void decryptFile(String inputFile, String outputFile, int key, Cipher cipher) throws IOException {
        Path inputPath = Path.of(inputFile);
        Path outputPath = Path.of(outputFile);

        try (BufferedReader reader = Files.newBufferedReader(inputPath);
             BufferedWriter writer = Files.newBufferedWriter(outputPath)
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                String  decryptedLine = cipher.decrypt(line, key);
                writer.write(decryptedLine);
                writer.newLine();
            }
        }
    }

    public int decryptByStatistics(
            String inputFile,
            String outputFile,
            String sampleFile,
            Cipher cipher
    ) throws IOException {
        double[] sampleFrequencies = calculateFrequencies(sampleFile);
        double[] encryptedFrequencies = calculateFrequencies(inputFile);
        int bestKey = 0;
        double bestDeviation = Double.MAX_VALUE;

        for (int key = 0; key < cipher.getAlphabetLength(); key++) {
            double deviation = calculateDeviation(encryptedFrequencies, sampleFrequencies, key);
            if (deviation < bestDeviation) {
                bestDeviation = deviation;
                bestKey = key;
            }
        }
        decryptFile(inputFile, outputFile, bestKey, cipher);
        return bestKey;
    }

    private double[] calculateFrequencies(String filePath) throws IOException {
        double[] frequencies = new double[AppConstants.ALPHABET.length()];
        int totalCharacters = 0;

        Path path = Path.of(filePath);

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.toLowerCase();

                for (int i = 0; i < line.length(); i++) {
                    char character = line.charAt(i);
                    int index = AppConstants.ALPHABET.indexOf(character);
                    if (index != -1) {
                        frequencies[index]++;
                        totalCharacters++;
                    }
                }
            }
        }
        if (totalCharacters > 0) {
            for (int i = 0; i < frequencies.length; i++) {
                frequencies[i] = frequencies[i] / totalCharacters;
            }
        }
        return frequencies;
    }

    private double calculateDeviation(double[] encryptedFrequencies, double[] sampleFrequencies, int key) {
        double deviation = 0;
        int alphabetLength = AppConstants.ALPHABET.length();

        for (int i = 0; i < alphabetLength; i++) {
            int shiftedIndex = (i + key) % alphabetLength;
            double difference = sampleFrequencies[i] - encryptedFrequencies[shiftedIndex];
            deviation += difference * difference;
        }
        return deviation;
    }
}
