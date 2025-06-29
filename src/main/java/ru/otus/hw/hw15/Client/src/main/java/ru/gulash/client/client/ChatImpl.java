package ru.gulash.client.client;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class ChatImpl {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8080;

    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private Scanner scanner;
    private final AtomicBoolean running = new AtomicBoolean(false);

    public void start() {
        scanner = new Scanner(System.in);

        try {
            // Подключение к серверу
            socket = new Socket(SERVER_HOST, SERVER_PORT);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);
            running.set(true);

            log.info("Подключение к серверу {}:{}", SERVER_HOST, SERVER_PORT);
            log.info("Подключение к серверу " + SERVER_HOST + ":" + SERVER_PORT);

            // Поток для чтения сообщений от сервера
            Thread messageListener = new Thread(this::listenForMessages);
            messageListener.setDaemon(true);
            messageListener.start();

            // Основной поток для отправки сообщений
            handleUserInput();

        } catch (IOException e) {
            log.error("Ошибка подключения к серверу", e);
            System.err.println("Не удалось подключиться к серверу: " + e.getMessage());
        } finally {
            disconnect();
        }
    }

    private void listenForMessages() {
        try {
            String message;
            while (running.get() && (message = reader.readLine()) != null) {
                System.out.println(message);
            }
        } catch (IOException e) {
            if (running.get()) {
                log.error("Ошибка при чтении сообщений от сервера", e);
                System.err.println("Соединение с сервером потеряно");
            }
        }
    }

    private void handleUserInput() {
       log.info("Добро пожаловать в чат!");
       log.info("Доступные команды:");
       log.info("/w <никнейм> <сообщение> - отправить личное сообщение");
       log.info("/all - показать список всех пользователей");
       log.info("/name <новый_никнейм> - сменить свой никнейм");
       log.info("/exit - выйти из чата");
       log.info("Или просто введите сообщение для отправки всем");
       log.info("");

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
                    handleWhisperCommand(input);
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

    private void handleWhisperCommand(String input) {
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

        log.info("Отключение от сервера...");
        log.info("Клиент отключен");
    }
}