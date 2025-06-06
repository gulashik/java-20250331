package ru.otus.hw.hw12;

import java.io.IOException;
import java.util.List;

import static ru.otus.hw.hw12.console.Interactions.getFileNameFromUser;
import static ru.otus.hw.hw12.console.Interactions.getLinesToWrite;
import static ru.otus.hw.hw12.io.FilesOperations.*;
import static ru.otus.hw.hw12.utils.Output.displayFileList;

public class Main {


    public static void main(String[] args) {
        System.out.println("=== Приложение для работы с текстовыми файлами ===\n");

            List<String> textFiles = getTextFiles();

            if (textFiles.isEmpty()) {
                System.out.println("В корневом каталоге проекта не найдено текстовых файлов.");
                System.out.println("Создайте файл с расширением .txt для работы с программой.");
                return;
            }

            displayFileList(textFiles);

            // Запрашиваем имя файла у пользователя
            String fileName = getFileNameFromUser(textFiles);

            // Читаем и выводим содержимое файла
            displayFileContent(fileName);

            // Запускаем режим записи в файл
            writeLinesToFile(fileName, getLinesToWrite());

    }
}
