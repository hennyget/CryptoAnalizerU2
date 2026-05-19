package com.javarush.zimina;

import java.io.IOException;

public class MainApp {
    public static void main(String[] args) {
        ConsoleApp app = new ConsoleApp();

        try {
            app.run();
        } catch (IOException e) {
            System.out.println("Ошибка при работе с файлом: " + e.getMessage());
        }
    }
}