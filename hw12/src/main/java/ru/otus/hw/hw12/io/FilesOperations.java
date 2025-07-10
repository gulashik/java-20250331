package ru.otus.hw.hw12.io;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Утилитарный класс для работы с файловыми операциями.<p>
 * Предоставляет методы для чтения, записи и вывода содержимого текстовых файлов.
 */
public class FilesOperations {
    private FilesOperations() {}

    /** Паттерн для поиска текстовых файлов. */
    private static final String FILE_PATTERN = "*.txt";
    
    /** Базовый путь к директории с файлами*/
    private static final Path BASE_PATH = Paths.get("files","hw12");

    /**
     * Получает список всех текстовых файлов из базового каталога проекта.<p>
     * Метод сканирует директорию, указанную в {@link #BASE_PATH}, и возвращает
     * имена всех файлов, соответствующих паттерну {@link #FILE_PATTERN}.
     * 
     * @return список имен текстовых файлов
     * @throws RuntimeException если произошла ошибка при чтении директории
     */
    public static List<String> getTextFiles() {
        List<String> fileNames = new ArrayList<>();

        try (
            DirectoryStream<Path> stream = Files.newDirectoryStream(BASE_PATH, FILE_PATTERN)
        ) {
            for (Path path : stream) {
                if (Files.isRegularFile(path)) {
                    fileNames.add(path.getFileName().toString());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return fileNames;
    }

    /**
     * Выводит содержимое файла в консоль.
     * 
     * @param fileName имя файла для вывода
     * @throws RuntimeException если произошла ошибка при чтении файла
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
     * Метод добавляет строки в конец файла (режим APPEND).<p>
     * Если файл не существует, он будет создан автоматически.<p>
     * После записи выводит содержимое файла.
     * 
     * @param fileName имя файла для записи (будет создан в {@link #BASE_PATH})
     * @param lines список строк для записи в файл
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