package ru.otus.hw.hw12.console;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Interactions {
    private Interactions() {}

    private static final Scanner SCANNER = new Scanner(System.in);

    public static List<String> getLinesToWrite() {
        ArrayList<String> lines = new ArrayList<>();

        System.out.println("Введите строки для записи в файл (для выхода введите 'exit'):");
        String input;
        while (!(input = SCANNER.nextLine()).equals("exit")) {
            lines.add(input);
        }
        return lines;
    }

    /**
     * Запрашивает у пользователя имя файла для работы
     */
    public static String getFileNameFromUser(List<String> textFiles) {
        while (true) {
            System.out.print("Введите имя файла (или номер из списка): ");
            String input = SCANNER.nextLine().trim();

            // Проверяем на номер файла
            try {
                int fileIndex = Integer.parseInt(input) - 1;
                if (fileIndex >= 0 && fileIndex < textFiles.size()) {
                    return textFiles.get(fileIndex);
                } else {
                    System.out.println("Неверный номер файла. Попробуйте снова.");
                    continue;
                }
            } catch (NumberFormatException e) {
                // Пользователь ввел имя файла, а не номер
            }

            // Проверяем на имя файла
            if (textFiles.contains(input)) {
                return input;
            } else {
                System.out.println("Файл '" + input + "' не найден. Попробуйте снова.");
            }
        }
    }
}
