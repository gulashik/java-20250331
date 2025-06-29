package ru.gulash.client.client;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Реализация клиента чата для подключения к серверу и обмена сообщениями.
 * 
 * <p>Класс использует многопоточность для одновременной обработки входящих сообщений
 * от сервера и пользовательского ввода.
 * 
 * <p>Пример использования:
 * <pre>{@code
 * ChatImpl chatClient = new ChatImpl();
 * chatClient.start(); // Запуск клиента и подключение к серверу
 * }</pre>
 */
@Slf4j
public class ChatImpl {
    
    /** Хост сервера для подключения */
    private static final String SERVER_HOST = "localhost";
    
    /** Порт сервера для подключения */
    private static final int SERVER_PORT = 8080;

    /** TCP сокет для соединения с сервером */
    private Socket socket;
    
    /** Поток для чтения данных от сервера */
    private BufferedReader reader;
    
    /** Поток для отправки данных на сервер */
    private PrintWriter writer;
    
    /** Сканер для чтения пользовательского ввода */
    private Scanner scanner;
    
    /** 
     * Потокобезопастный флаг состояния клиента.
     */
    private final AtomicBoolean running = new AtomicBoolean(false);

    /**
     * Запускает клиент чата и устанавливает соединение с сервером.
     *
     * <p>Используется многопоточность для одновременной обработки входящих сообщений
     * от сервера и пользовательского ввода.
     * 
     * @throws RuntimeException если произошла критическая ошибка при работе
     */
    public void start() {
        scanner = new Scanner(System.in, StandardCharsets.UTF_8);

        try {
            // Подключение к серверу
            socket = new Socket(SERVER_HOST, SERVER_PORT);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            writer = new PrintWriter(socket.getOutputStream(), true, StandardCharsets.UTF_8);
            running.set(true);

            log.info("Подключение к серверу {}:{}", SERVER_HOST, SERVER_PORT);

            // Новый поток для чтения сообщений от сервера
            Thread messageListener = new Thread(this::listenForMessages);
            messageListener.setDaemon(true); // Поток автоматически завершится вместе с Клиентом
            messageListener.start();

            // Текущий поток для отправки сообщений
            handleUserInput();

        } catch (IOException e) {
            log.error("Ошибка подключения к серверу", e);
            log.info("Не удалось подключиться к серверу: " + e.getMessage());
        } finally {
            disconnect();
        }
    }

    /**
     * Прослушивает входящие сообщения от сервера.
     */
    private void listenForMessages() {
        try {
            String message;
            while (running.get() && (message = reader.readLine()) != null) {
                log.info(message);
            }
        } catch (IOException e) {
            if (running.get()) {
                log.error("Ошибка при чтении сообщений от сервера", e);
                log.info("Соединение с сервером потеряно");
            }
        }
    }

    /**
     * Обрабатывает пользовательский ввод и отправляет команды/сообщения на сервер.
     */
    private void handleUserInput() {
       log.info("Добро пожаловать в чат!");

        while (running.get()) {
            try {
                String input = scanner.nextLine();

                if (input == null || input.equals("/exit")) {
                    running.set(false);
                    writer.println("/exit");
                    break;
                }

                if (input.trim().isEmpty()) {
                    continue;
                }

                // Проверка команд
                if (input.startsWith("/w ")) {
                    handlePrivateMessageSendCommand(input);

                } else if (input.equals("/all")) {
                    writer.println("/all");

                } else if (input.startsWith("/name ")) {
                    handleNameCommand(input);

                } else if (input.startsWith("/help")) {
                    showHelp();

                } else {
                    // Обычное сообщение
                    writer.println(input);
                }

            } catch (Exception e) {
                if (running.get()) {
                    log.error("Ошибка при обработке пользовательского ввода", e);
                }
                break;
            }
        }
    }

    /**
     * Обрабатывает команду отправки личного сообщения.
     * 
     * @param input строка команды, введенная пользователем
     */
    private void handlePrivateMessageSendCommand(String input) {
        String[] parts = input.split(" ", 3);
        if (parts.length < 3) {
            log.info("Использование: /w <никнейм> <сообщение>");
            log.info("Пример: /w alice Привет, как дела?");
            return;
        }

        String recipient = parts[1];
        String message = parts[2];

        if (recipient.trim().isEmpty()) {
            log.info("Укажите никнейм получателя");
            return;
        }

        if (message.trim().isEmpty()) {
            log.info("Сообщение не может быть пустым");
            return;
        }

        writer.println(input);
        log.info("Отправлено личное сообщение пользователю {}: {}", recipient, message);
    }

    /**
     * Обрабатывает команду смены никнейма.
     *
     * @param input строка команды, введенная пользователем
     */
    private void handleNameCommand(String input) {
        String[] parts = input.split(" ", 2);
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            log.info("Использование: /name <новый_никнейм>");
            log.info("Пример: /name alice");
            return;
        }

        writer.println(input);
        log.info("Запрос на смену никнейма на: {}", parts[1]);
    }

    /**
     * Выводит подробную справку по всем доступным командам чата.
     */
    private void showHelp() {
        log.info("\n=== Справка по командам ===");
        log.info("/w <никнейм> <сообщение> - отправить личное сообщение пользователю");
        log.info("    Пример: /w tom Привет!");
        log.info("/all - показать список всех подключенных пользователей");
        log.info("/name <новый_никнейм> - сменить свой никнейм");
        log.info("    Пример: /name alice");
        log.info("/exit - выйти из чата");
        log.info("/help - показать эту справку");
        log.info("Чтобы отправить сообщение всем, просто введите текст без команд");
        log.info("=========================\n");
    }

    /**
     * Отключается от сервера и освобождает все ресурсы.
     */
    private void disconnect() {
        running.set(false);
        try {
            if (writer != null) {
                writer.close();
            }
            if (reader != null) {
                reader.close();
            }
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
            if (scanner != null) {
                scanner.close();
            }
        } catch (IOException e) {
            log.error("Ошибка при отключении", e);
        }

        log.warn("Отключение от сервера...");
        log.info("Клиент отключен от {}", socket.getRemoteSocketAddress());
    }
}