package ru.otus.hw.hw12.utils;

import java.util.List;

public class Output {
    private Output() {
    }

    /**
     * Выводит список доступных файлов в консоль
     */
    public static void displayFileList(List<String> textFiles) {
        System.out.println("Доступные текстовые файлы:");
        for (int i = 0; i < textFiles.size(); i++) {
            System.out.printf("%3s %s%n", i + 1, textFiles.get(i));
        }
        System.out.println();
    }
}
