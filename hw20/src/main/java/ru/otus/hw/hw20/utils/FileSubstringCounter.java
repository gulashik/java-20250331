package ru.otus.hw.hw20.utils;

import java.io.*;
import java.util.Scanner;

public class FileSubstringCounter {

    /**
     * Метод для подсчета количества вхождений подстроки в файле
     * @param fileName имя файла для поиска
     * @param searchSequence искомая последовательность символов
     * @return количество найденных вхождений
     */
    public static long countSubstringInFile(String fileName, String searchSequence) {
        if (searchSequence == null || searchSequence.isEmpty()) {
            return 0;
        }

        try (Scanner scanner = new Scanner(new File(fileName)).useDelimiter("")) {
            return scanner.findAll(searchSequence).count();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
