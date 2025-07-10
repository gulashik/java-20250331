package ru.otus.hw.hw13.server.entity;

import ru.otus.hw.hw13.server.processing.Calculator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Класс для обработки клиентских соединений в отдельном потоке.
 *
 * @see Runnable
 * @see Socket
 */
public class ClientHandler implements Runnable {

    private final Socket clientSocket;

    private final String closeTag;

    /**
     * Создает обработчик для клиентского соединения.
     *
     * @param clientSocket установленное соединение с клиентом
     * @param closeTag     метка для завершения соединения с клиентом
     * @throws IllegalArgumentException если clientSocket и closeTag не корректны
     */
    public ClientHandler(Socket clientSocket, String closeTag) {
        if (clientSocket == null) {
            throw new IllegalArgumentException("Client socket не может быть null");
        }
        if (closeTag == null || closeTag.trim().isEmpty()) {
            throw new IllegalArgumentException("CloseTag не может быть null или пустым");
        }
        this.clientSocket = clientSocket;
        this.closeTag = closeTag;
    }

    /**
     * Основной метод обработки клиентского соединения. Реализация Runnable.
     *
     * @see BufferedReader#readLine()
     * @see PrintWriter#println(String)
     * @see Socket#getInputStream()
     * @see Socket#getOutputStream()
     */
    @Override
    public void run() {
        String clientAddress = clientSocket.getRemoteSocketAddress().toString();

        // Try-with-resources автоматически закроет ресурсы
        try (
            // Создаем потоки для чтения и записи
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        ) {

            // Читаем сообщения от клиента до получения closeTag
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Получено от " + clientAddress + ": " + inputLine);

                // Команда для завершения соединения
                if (closeTag.equalsIgnoreCase(inputLine.trim())) {
                    out.println("До свидания!");
                    System.out.println("Клиент " + clientAddress + " отправил команду завершения");
                    break;
                }

                // Обрабатываем математическое выражение
                out.println(Calculator.processCalculation(inputLine));
            }

        } catch (IOException e) {
            System.err.println("Ошибка при обработке клиента " + clientAddress + ": " + e.getMessage());

        } finally {
            // Закрываем сокет клиента
            try {
                if (!clientSocket.isClosed()) {
                    clientSocket.close();
                    System.out.println("Клиент отключен: " + clientAddress);
                }
            } catch (IOException e) {
                System.err.println("Ошибка при закрытии сокета " + clientAddress + ": " + e.getMessage());
            }
        }
    }
}