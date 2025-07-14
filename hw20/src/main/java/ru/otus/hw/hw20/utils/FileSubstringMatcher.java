package ru.otus.hw.hw20.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * Утилита для поиска и подсчета совпадений подстрок в файле.
 * Использует скользящее окно для эффективного поиска паттерна в больших файлах.
 */
public class FileSubstringMatcher {
    private final StringBuilder sb = new StringBuilder();
    private final int MAX_LENGTH;
    private final String PATTERN;

    /**
     * Создает новый экземпляр с заданным паттерном.
     *
     * @param pattern паттерн для поиска в файле (не может быть null или пустым)
     * @throws IllegalArgumentException если паттерн равен null или пустой строке
     */
    public FileSubstringMatcher(String pattern) {
        if (pattern == null || pattern.isEmpty()) {
            throw new IllegalArgumentException("Pattern cannot be null or empty");
        }
        PATTERN = pattern;
        MAX_LENGTH = pattern.length();
    }

    /**
     * Подсчитывает количество совпадений паттерна в указанном файле.
     *
     * @param fileName путь к файлу для анализа
     * @return количество найденных совпадений паттерна
     * @throws RuntimeException если произошла ошибка при чтении файла
     */
    public long countSubstringMatchInFile(String fileName) {

        try (
            BufferedReader bufferedReader =
                 new BufferedReader(
                     new InputStreamReader(
                         new FileInputStream(fileName), StandardCharsets.UTF_8
                     )
                 )
        ) {
            return bufferedReader.lines()
                .flatMapToInt(String::chars)
                .map(value -> addAndCount((char) value))
                .sum();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Добавляет символ в скользящее окно и проверяет совпадение с паттерном.
     *
     * @param ch символ для добавления в окно
     * @return 1 если текущее содержимое окна совпадает с паттерном, иначе 0
     */
    private int addAndCount(char ch) {
        if (sb.length() >= MAX_LENGTH) {
            sb.deleteCharAt(0);
        }
        sb.append(ch);
        return sb.toString().matches(PATTERN) ? 1 : 0;
    }

    /**
     * Возвращает текущее содержимое скользящего окна.
     *
     * @return строковое представление текущего состояния окна
     */
    @Override
    public String toString() {
        return sb.toString();
    }
}