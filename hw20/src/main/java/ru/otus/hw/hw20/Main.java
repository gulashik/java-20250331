package ru.otus.hw.hw20;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import static ru.otus.hw.hw20.FileSubstringCounter.countSubstringInFile;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            // Ввод имени файла
            System.out.print("Введите имя файла(00_prolog.txt,01_chapter_01.txt,02_chapter_02.txt): ");
            String fileName = "files/hw20/" + scanner.nextLine().trim();

            // Проверка существования файла
            File file = new File(fileName);
            if (!file.exists()) {
                System.err.println("Ошибка: файл '" + fileName + "' не найден!");
                return;
            }

            if (!file.isFile()) {
                System.err.println("Ошибка: '" + fileName + "' не является файлом!");
                return;
            }

            // Ввод искомой последовательности
            System.out.print("Введите искомую последовательность символов: ");
            String searchSequence = scanner.nextLine();

            if (searchSequence.isEmpty()) {
                System.out.println("Искомая последовательность не может быть пустой!");
                return;
            }

            // Выполнение поиска
            System.out.println("\nВыполняется поиск...");
            long startTime = System.currentTimeMillis();

            //int count = countSubstringInFile(fileName, searchSequence);
            int count = countSubstringInFile(fileName, searchSequence);

            long endTime = System.currentTimeMillis();

            // Вывод результата
            System.out.println("\n=== РЕЗУЛЬТАТ ===");
            System.out.println("Файл: " + fileName);
            System.out.println("Искомая последовательность: \"" + searchSequence + "\"");
            System.out.println("Количество вхождений: " + count);
            System.out.println("Время выполнения: " + (endTime - startTime) + " мс");

        } catch (IOException e) {
            System.err.println("Ошибка при работе с файлом: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Неожиданная ошибка: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
