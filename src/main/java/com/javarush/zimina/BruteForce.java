package com.javarush.zimina;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BruteForce {
    public int decryptByBruteForce(String inputFile, String outputFile, String sampleFile, Cipher cipher) throws IOException {

        Path inputPath = Paths.get(inputFile);
        Path outputPath = Paths.get(outputFile);

        int bestKey = 0;
        int bestScore = Integer.MIN_VALUE;
        String[] wordsForScore;

        if (sampleFile.isBlank()) {
            wordsForScore = AppConstants.COMMON_WORDS;
        } else {
            wordsForScore = loadWordsFromSampleFile(sampleFile);
        }


        for (int key = 0; key < cipher.getAlphabetLength(); key++) {
            int score = 0;
            try (BufferedReader reader = Files.newBufferedReader(inputPath)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String decryptedLine = cipher.decrypt(line, key);
                    score += calculateScore(decryptedLine, wordsForScore);
                }
            }
            if (score > bestScore) {
                bestScore = score;
                bestKey = key;
            }
        }
        try (
             BufferedReader reader = Files.newBufferedReader(inputPath);
             BufferedWriter writer = Files.newBufferedWriter(outputPath)
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                String decryptedLine = cipher.decrypt(line, bestKey);
                writer.write(decryptedLine);
                writer.newLine();
            }
        }
        return bestKey;
    }

    private String[] loadWordsFromSampleFile(String sampleFile) throws IOException {
        Path samplePath = Paths.get(sampleFile);
        String sampleText = Files.readString(samplePath).toLowerCase();
        String[] words = sampleText.split("\\s+");
        for (int i = 0; i < words.length; i++) {
            words[i] = " " + words[i] + " ";
        }
        return words;
    }

    private int calculateScore(String text, String[] wordsForScore) {
        int score = 0;
        String lowerText = " " + text.toLowerCase() + " ";

        for (String word : wordsForScore) {
            int index = lowerText.indexOf(word);
            while (index != -1) {
                score += word.length();

                index = lowerText.indexOf(word, index + word.length());
            }
        }
        return score;
    }
}






