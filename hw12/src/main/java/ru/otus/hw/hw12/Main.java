package ru.otus.hw.hw12;

import java.util.List;

import static ru.otus.hw.hw12.console.Interactions.getFileNameFromUser;
import static ru.otus.hw.hw12.console.Interactions.getLinesToWrite;
import static ru.otus.hw.hw12.io.FilesOperations.*;
import static ru.otus.hw.hw12.utils.Output.displayFileList;

public class Main {


    public static void main(String[] args) {
            List<String> textFiles = getTextFiles();

            if (textFiles.isEmpty()) {
                System.out.println("В каталоге не найдено текстовых файлов.");
                System.out.println("Создайте файл с расширением .txt и запустите снова.");
                return;
            }

            displayFileList(textFiles);

            String fileName = getFileNameFromUser(textFiles);

            displayFileContent(fileName);

            writeLinesToFile(fileName, getLinesToWrite());

    }
}
