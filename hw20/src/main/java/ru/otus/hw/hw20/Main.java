package ru.otus.hw.hw20;

import ru.otus.hw.hw20.utils.FileSubstringMatcher;

import java.io.File;
import java.util.Scanner;
import java.util.concurrent.Callable;

import static ru.otus.hw.hw20.utils.FileSubstringCounter.countSubstringInFile;

public class Main {
    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(System.in)) {
            // Ввод имени файла
            System.out.print("Введите имя файла(00_prolog.txt,01_chapter_01.txt,02_chapter_02.txt): ");
            String fileName = "files/hw20/" + scanner.nextLine().trim();

            File file = new File(fileName);
            if (!file.exists()) {
                System.err.println("Ошибка: файл '" + fileName + "' не найден!");
                return;
            }

            if (!file.isFile()) {
                System.err.println("Ошибка: '" + fileName + "' не является файлом!");
                return;
            }

            System.out.print("Введите искомую последовательность символов: ");
            String searchSequence = scanner.nextLine();

            if (searchSequence.isEmpty()) {
                System.out.println("Искомая последовательность не может быть пустой!");
                return;
            }

            // Выполнение поиска
            System.out.println("\nВыполняется поиск...");
            System.out.println("\n=== РЕЗУЛЬТАТ Scanner===");
            doSearch(() -> countSubstringInFile(fileName, searchSequence), fileName, searchSequence);

            System.out.println("\n=== РЕЗУЛЬТАТ BufferedReader + InputStreamReader + FileInputStream");
            doSearch(() -> new FileSubstringMatcher(searchSequence).countSubstringMatchInFile(fileName), fileName, searchSequence);

        } catch (Exception e) {
            System.err.println("Неожиданная ошибка: " + e.getMessage());
        }
    }

    private static void doSearch(Callable<Long> block, String fileName, String searchSequence) throws Exception {
        long startTime = System.currentTimeMillis();
        long count = block.call();
        long endTime = System.currentTimeMillis();

        // Вывод результата
        System.out.println("=== Статистика ===");
        System.out.println("Файл: " + fileName);
        System.out.println("Искомая последовательность: \"" + searchSequence + "\"");
        System.out.println("Количество вхождений: " + count);
        System.out.println("Время выполнения: " + (endTime - startTime) + " мс");
    }
}