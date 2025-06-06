package ru.otus.hw.hw12.utils;

import java.util.List;

/**
 * Утилитарный класс для форматированного вывода информации в консоль.
 */
public class Output {

    private Output() {
    }

    /**
     * Выводит пронумерованный список доступных файлов в консоль.
     * Формат вывода: "номер файл_имя"
     * 
     * @param textFiles список имен текстовых файлов для отображения
     */
    public static void displayFileList(List<String> textFiles) {
        System.out.println("Доступные текстовые файлы:");
        for (int i = 0; i < textFiles.size(); i++) {
            System.out.printf("%3s %s%n", i + 1, textFiles.get(i));
        }
        System.out.println();
    }
}