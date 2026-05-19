package com.javarush.zimina;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileManager {

    public void processEncryptFile(String inputFile, String outputFile, int key, Cipher cipher) throws IOException {
        Path inputPath = Path.of(inputFile);
        Path outputPath = Path.of(outputFile);

        try (BufferedReader reader = Files.newBufferedReader(inputPath);
             BufferedWriter writer = Files.newBufferedWriter(outputPath)) {
            String line;

            while ((line = reader.readLine()) != null) {
                String encryptedLine = cipher.encrypt(line, key);
                writer.write(encryptedLine);
                writer.newLine();
            }
        }
    }

    public void processDecryptFile(String inputFile, String outputFile, int key, Cipher cipher) throws IOException {
        Path inputPath = Path.of(inputFile);
        Path outputPath = Path.of(outputFile);

        try (BufferedReader reader = Files.newBufferedReader(inputPath);
             BufferedWriter writer = Files.newBufferedWriter(outputPath)) {
            String line;

            while ((line = reader.readLine()) != null) {
                String decryptedLine = cipher.decrypt(line, key);
                writer.write(decryptedLine);
                writer.newLine();
            }
        }
    }
}
