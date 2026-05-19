package com.javarush.zimina;

import java.io.IOException;
import java.util.Scanner;

public class ConsoleApp {
    private final Cipher cipher = new Cipher();
    private final FileManager fileManager = new FileManager();
    private final Scanner scanner = new Scanner(System.in);
    private final Validator validator = new Validator();
    private final BruteForce bruteForce = new BruteForce();
    private final StatisticalAnalyzer statisticalAnalyzer = new StatisticalAnalyzer();


    public void run() throws IOException {

        while (true) {
            printMenu();

            int choice = readMenuChoice();

            switch (choice) {
                case 1:
                    handleEncryptFile();
                    break;

                case 2:
                    handleDecryptFile();
                    break;

                case 3:
                    handleBruteForceFile();
                    break;

                case 4:
                    handleStatisticalAnalysis();
                    break;

                case 0:
                    System.out.println("Выход");
                    return;

                default:
                    System.out.println("Неверный пункт меню");
            }
        }
    }

    private void handleStatisticalAnalysis() throws IOException {
        System.out.println("Вы выбрали статистический анализ");

        System.out.println("Введите путь к зашифрованному файлу: ");
        String encryptedFile = scanner.nextLine();

        System.out.println("Куда сохранить результат?");
        String outputFile = scanner.nextLine();

        System.out.println("Введите путь к файлу-примеру: ");
        String sampleFile = scanner.nextLine();

        if (!validator.isFileExists(encryptedFile)) {
            System.out.println("Файл не найден!");
            return;
        }

        if (!validator.isFileExists(sampleFile)) {
            System.out.println("Файл-пример не найден!");
            return;
        }

        int foundKey = statisticalAnalyzer.decryptByStatistics(
                encryptedFile,
                outputFile,
                sampleFile,
                cipher
        );

        System.out.println("Найденный ключ: " + foundKey);
    }

    private void handleEncryptFile() throws IOException {
        System.out.println("Вы выбрали шифрование");

        System.out.println("Введите путь к файлу: ");
        String inputFile = scanner.nextLine();

        System.out.println("Куда сохранить результат? ");
        String outputFile = scanner.nextLine();
        System.out.println("Введите ключ: ");

        int keyEncrypt = readKey();

        if (!validator.isFileExists(inputFile)) {
            System.out.println("Файл не найден");
            return;
        }
        if (!validator.isValidKey(keyEncrypt, cipher.getAlphabetLength())) {
            System.out.println("Неверный ключ!");
            return;
        }
        fileManager.processEncryptFile(inputFile, outputFile, keyEncrypt, cipher);
        System.out.println("Файл успешно зашифрован!");
    }

    private void handleDecryptFile() throws IOException {
        System.out.println("Вы выбрали расшифровку");

        System.out.println("Введите путь к файлу: ");
        String encryptedFile = scanner.nextLine();

        System.out.println("Куда сохранить результат?");
        String decryptedFile = scanner.nextLine();

        System.out.println("Введите ключ:");
        int keyDecrypt = readKey();

        if (!validator.isFileExists(encryptedFile)) {
            System.out.println("Файл не найден!");
            return;
        }
        if (!validator.isValidKey(keyDecrypt, cipher.getAlphabetLength())) {
            System.out.println("Неверный ключ!");
            return;
        }
        fileManager.processDecryptFile(encryptedFile, decryptedFile, keyDecrypt, cipher);
        System.out.println("Файл успешно расшифрован!");
    }


    private void handleBruteForceFile() throws IOException {
        System.out.println("Вы выбрали режим подбора ключа");

        System.out.println("Введите путь к файлу: ");
        String encryptedFileForBruteForce = scanner.nextLine();

        System.out.println("Куда сохранить результат?");
        String bruteForceResultFile = scanner.nextLine();

        String sampleFile = "";
        System.out.println("Есть файл-пример? Yes/No?");
        String answer = scanner.nextLine();

        if (answer.equalsIgnoreCase("Yes")) {
            System.out.println("Введите путь к файлу: ");
            sampleFile = scanner.nextLine();

            if (!validator.isFileExists(sampleFile)) {
                System.out.println("Файл-пример не найден!");
                return;
            }
        }

        if (!validator.isFileExists(encryptedFileForBruteForce)) {
            System.out.println("Файл не найден!");
            return;
        }
        int foundKey = bruteForce.decryptByBruteForce(
                encryptedFileForBruteForce,
                bruteForceResultFile,
                sampleFile,
                cipher);
        System.out.println("Найденный ключ: " + foundKey);

    }


    private int readKey() {
        while (true) {
            String input = scanner.nextLine();

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Ключ должен быть числом. Попробуйте еще раз:");
            }
        }
    }

    private int readMenuChoice() {
        while (true) {
            String input = scanner.nextLine();

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Пункт меню должен быть числом. Попробуйте еще раз:");
            }
        }
    }


    private void printMenu() {
        System.out.println("1. Зашифровать файл");
        System.out.println("2. Расшифровать файл");
        System.out.println("3. BruteForce");
        System.out.println("4. Статистический анализ");
        System.out.println("0. Выйти");
        System.out.println("Выберите режим: ");
    }
}

