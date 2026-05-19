package com.javarush.zimina;

import java.nio.file.Files;
import java.nio.file.Path;

public class Validator {
    public boolean isFileExists(String filePath) {
        Path path = Path.of(filePath);
        return Files.isRegularFile(path);
    }

    public boolean isValidKey(int key, int alphabetLength) {
        return key >= 0 && key < alphabetLength;
    }
}
