package ru.otus.hw.hw13.client;

import ru.otus.hw.hw13.server.processing.Calculator;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public record Client(String hostname, int port, String closeTag) {
    /**
     * Клиент для подключения к серверу калькулятора и обмена сообщениями.
     *
     * @param hostname Имя хоста или IP-адрес сервера для подключения.
     * @param port     Номер порта(1024-49151) сервера для подключения.
     * @param closeTag Команда для завершения соединения с сервером.
     */
    public Client {
        if (hostname == null || hostname.trim().isEmpty()) {
            throw new IllegalArgumentException("Hostname не может быть null или пустым");
        }
        if (port < 1024 || port > 49151) {
            throw new IllegalArgumentException("Port должен быть в диапазоне 1024-49151");
        }
        if (closeTag == null || closeTag.trim().isEmpty()) {
            throw new IllegalArgumentException("CloseTag не может быть null или пустым");
        }
    }

    /**
     * Устанавливаем соединение с сервером калькулятора и запускаем интерактивный обмен сообщениями.
     *
     * @see Socket
     * @see PrintWriter
     * @see BufferedReader
     */
    public void connectAndInteractWithServerAndUser(Scanner stdIn) {
        // Пользовательское приглашение
        final String USER_PROMPT = "--> ";

        // try-with-resources
        try (
              // Создаем сокет и подключаемся к серверу
              Socket socket = new Socket(hostname, port);

              // Создаем потоки для общения с сервером
              PrintWriter out = new PrintWriter(
                  socket.getOutputStream(), true
              );
              BufferedReader in = new BufferedReader(
                  new InputStreamReader(socket.getInputStream())
              );
        ) {

            System.out.println("Подключен к серверу калькулятора " + hostname + ":" + port);

            System.out.println("=== КАЛЬКУЛЯТОР КЛИЕНТ-СЕРВЕР ===");
            System.out.println(Calculator.getAvailableOperations());
            System.out.println("Примеры использования:");
            System.out.println("    10 + 5");
            System.out.println("    20 - 3");
            System.out.println("    4 * 7");
            System.out.println("    15 / 3");
            System.out.println("Для выхода введите: " + closeTag);
            System.out.println("=====================================");
            System.out.println("\nВведите математические выражения:");
            System.out.print(USER_PROMPT);

            // Цикл отправки сообщений
            String userInput;
            while ((userInput = stdIn.nextLine()) != null) {
                // Отправляем сообщение серверу
                out.println(userInput);

                // Читаем ответ от сервера
                String response = in.readLine();
                System.out.println(response);

                // Выходим, если отправили команду закрытия
                if (closeTag.equalsIgnoreCase(userInput.trim())) {
                    break;
                }

                System.out.print(USER_PROMPT);
            }
        } catch (UnknownHostException e) {
            System.err.println("Неизвестный хост: " + hostname + " " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
        }
    }
}