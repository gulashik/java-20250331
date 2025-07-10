package ru.otus.hw.hw20;

import java.io.*;
import java.nio.charset.StandardCharsets;


public class FileSubstringCounter {

    /**
     * Метод для подсчета количества вхождений подстроки в файле
     * @param fileName имя файла для поиска
     * @param searchSequence искомая последовательность символов
     * @return количество найденных вхождений
     * @throws IOException при ошибке чтения файла
     */
    public static int countSubstringInFile(String fileName, String searchSequence) throws IOException {
        if (searchSequence == null || searchSequence.isEmpty()) {
            return 0;
        }

        int count = 0;

        // Используем BufferedReader для эффективного чтения файла с кодировкой UTF-8
        try (
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8)
            )
        )
        {

            String line;
            while ((line = reader.readLine()) != null) {
                count += countSubstringInLine(line, searchSequence);
            }
        }

        return count;
    }

    /**
     * Метод для подсчета вхождений подстроки в строке
     * @param text текст для поиска
     * @param searchSequence искомая последовательность
     * @return количество найденных вхождений
     */
    private static int countSubstringInLine(String text, String searchSequence) {
        int count = 0;
        int index = 0;

        while ((index = text.indexOf(searchSequence, index)) != -1) {
            count++;
            index += 1; // Сдвигаемся на 1 позицию для поиска перекрывающихся вхождений
        }

        return count;
    }

    /**
     * Альтернативный метод для больших файлов - чтение посимвольно
     * Полезен для файлов, где искомая последовательность может находиться
     * на границе строк
     */
    public static int countSubstringInFileAdvanced(String fileName, String searchSequence) throws IOException {
        if (searchSequence == null || searchSequence.isEmpty()) {
            return 0;
        }

        int count = 0;
        int sequenceLength = searchSequence.length();
        StringBuilder buffer = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(
            new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8))) {

            int ch;
            while ((ch = reader.read()) != -1) {
                buffer.append((char) ch);

                // Проверяем, если буфер достиг нужной длины
                if (buffer.length() >= sequenceLength) {
                    // Проверяем последние символы на совпадение
                    if (buffer.substring(buffer.length() - sequenceLength).equals(searchSequence)) {
                        count++;
                    }

                    // Ограничиваем размер буфера для эффективности
                    if (buffer.length() > sequenceLength * 2) {
                        buffer.delete(0, buffer.length() - sequenceLength + 1);
                    }
                }
            }
        }

        return count;
    }
}
