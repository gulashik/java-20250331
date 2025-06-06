package ru.otus.hw.hw12.io;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class FilesOperations {
    private FilesOperations() {}

    private static final String FILE_PATTERN = "*.txt";
    private static final Path BASE_PATH = Paths.get("files","hw12");

    /**
     * Получает список всех текстовых файлов из корневого каталога проекта
     */
    public static List<String> getTextFiles() {
        List<String> textFiles = new ArrayList<>();

        try (
            DirectoryStream<Path> stream = Files.newDirectoryStream(BASE_PATH, FILE_PATTERN)
        ) {
            for (Path path : stream) {
                if (Files.isRegularFile(path)) {
                    textFiles.add(path.getFileName().toString());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return textFiles;
    }

    /**
     * Читает и выводит содержимое файла в консоль
     */
    public static void displayFileContent(String fileName) {
        System.out.println("\n--- Содержимое файла '" + fileName + "' ---");

        try {
            Files
                .readAllLines(BASE_PATH.resolve(fileName))
                .forEach(System.out::println);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("--- Конец файла ---\n");
    }

    /**
     * Запускает режим записи строк в файл
     */
    public static void writeLinesToFile(String fileName, List<String> lines) {
        System.out.println("Пишем в файл.");

        try (
            BufferedWriter writer = Files.newBufferedWriter(
                BASE_PATH.resolve(fileName),
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
            )
        ) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
            writer.flush();

        } catch (IOException e) {
            System.err.println("Ошибка при записи в файл: " + e.getMessage());
        }

        System.out.println("\nРабота с файлом завершена.");

        displayFileContent(fileName);
    }
}
