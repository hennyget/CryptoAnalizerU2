package com.javarush.zimina;

public class Cipher {

    public String encrypt(String text, int key) {
        text = text.toLowerCase();

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char symbol = text.charAt(i);
            char shiftedSymbol = shiftCharacter(symbol, key);
            result.append(shiftedSymbol);
        }
        return result.toString();
    }

    public String decrypt(String text, int key) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char symbol = text.charAt(i);
            char shiftedSymbol = shiftCharacter(symbol, -key);
            result.append(shiftedSymbol);
        }
        return result.toString();
    }

    private char shiftCharacter(char character, int key) {
        int oldIndex = findIndex(character);
        if (oldIndex == -1) {
            return character;
        }
        // Берем старый индекс, прибавляем ключ и зацикливаем по длине алфавита
        int newIndex = (oldIndex + key + AppConstants.ALPHABET.length()) % AppConstants.ALPHABET.length();

        return AppConstants.ALPHABET.charAt(newIndex);
    }

    private int findIndex(char character) {
        return AppConstants.ALPHABET.indexOf(character);
    }

    public int getAlphabetLength() {
        return AppConstants.ALPHABET.length();
    }
}
