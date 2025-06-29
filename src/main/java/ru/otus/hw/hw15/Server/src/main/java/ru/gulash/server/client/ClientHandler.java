package ru.gulash.server.client;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.gulash.server.server.ServerImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Обработчик клиентских подключений чат-сервера.
 */
@Slf4j
public class ClientHandler implements Runnable {
    
    /**
     * TCP-сокет для связи с клиентом.
     */
    private final Socket socket;
    
    /**
     * Буферизированный поток для чтения входящих данных от клиента.
     */
    private BufferedReader reader;
    
    /**
     * Поток для отправки данных клиенту.
     */
    private PrintWriter writer;
    
    /**
     * Текущий никнейм клиента в чате.
     */
    private String nickname;
    
    /**
     * Ссылка на основной сервер для взаимодействия с другими клиентами.
     */
    private final ServerImpl server;

    /**
     * Создает новый обработчик клиента.
     * 
     * @param socket TCP-сокет установленного соединения с клиентом.
     *               Не должен быть {@code null}.
     * @param server экземпляр сервера для взаимодействия с другими клиентами.
     *               Не должен быть {@code null}.
     * @throws IllegalArgumentException если socket или server равны {@code null}
     */
    public ClientHandler(Socket socket, ServerImpl server) {
        if (socket == null) {
            throw new IllegalArgumentException("Socket не может быть null");
        }
        if (server == null) {
            throw new IllegalArgumentException("Server не может быть null");
        }
        this.socket = socket;
        this.server = server;
    }

    /**
     * Основной метод выполнения потока обработчика клиента.
     * 
     * <p>Метод блокирующий и выполняется до тех пор, пока клиент не отключится
     * или не произойдет ошибка ввода/вывода.</p>
     */
    @Override
    public void run() {
        try {
            // Инициализация потоков ввода/вывода
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);

            // Процедура регистрации клиента
            writer.println("Введите ваш никнейм:");
            nickname = reader.readLine();

            // Валидация и обеспечение уникальности никнейма
            while (nickname == null || nickname.trim().isEmpty() || server.isNicknameTaken(nickname)) {
                if (server.isNicknameTaken(nickname)) {
                    writer.println("Никнейм уже занят. Введите другой:");
                } else {
                    writer.println("Никнейм не может быть пустым. Введите никнейм:");
                }
                nickname = reader.readLine();
            }

            // Регистрация клиента на сервере
            server.addClient(nickname, this);
            
            // Отправка приветственного сообщения и справки по командам
            writer.println("Добро пожаловать в чат, " + nickname + "!");
            writer.println("Доступные команды:");
            writer.println("/w <никнейм> <сообщение> - личное сообщение");
            writer.println("/all - список пользователей");
            writer.println("/name <новый_никнейм> - сменить никнейм");
            writer.println("/exit - выйти из чата");

            // Основной цикл обработки сообщений
            String message;
            while ((message = reader.readLine()) != null) {
                if (message.equals("/exit")) {
                    break;
                } else if (message.equals("/all")) {
                    writer.println(server.getClientsList());
                } else if (message.startsWith("/name ")) {
                    handleNameChange(message);
                } else if (message.startsWith("/w ")) {
                    handlePrivateMessage(message);
                } else {
                    // Обычное сообщение для всех пользователей
                    server.broadcastMessage(nickname, message);
                }
            }
        } catch (IOException e) {
            log.error("Ошибка в обработчике клиента {}", nickname, e);
        } finally {
            disconnect();
        }
    }

    /**
     * Обрабатывает команду смены никнейма клиента.
     * 
     * @param command строка команды
     */
    private void handleNameChange(String command) {
        String[] parts = command.split(" ", 2);
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            writer.println("Использование: /name <новый_никнейм>");
            return;
        }

        String newNickname = parts[1].trim();
        if (server.isNicknameTaken(newNickname)) {
            writer.println("Никнейм " + newNickname + " уже занят");
            return;
        }

        String oldNickname = nickname;
        nickname = newNickname;
        server.changeNickname(oldNickname, newNickname);
        writer.println("Ваш никнейм изменен на " + newNickname);
    }

    /**
     * Обрабатывает команду отправки личного сообщения.
     *
     * @param command строка команды
     */
    private void handlePrivateMessage(String command) {
        String[] parts = command.split(" ", 3);
        if (parts.length < 3) {
            writer.println("Использование: /w <никнейм> <сообщение>");
            return;
        }

        String recipient = parts[1];
        String message = parts[2];
        server.sendPrivateMessage(nickname, recipient, message);
    }

    /**
     * Отправляет сообщение данному клиенту.
     * 
     * @param message текст сообщения для отправки клиенту.
     *                Если {@code null}, сообщение не отправляется.
     */
    public void sendMessage(String message) {
        if (writer != null && message != null) {
            writer.println(message);
        }
    }

    /**
     * Выполняет отключение клиента и освобождение ресурсов.
     */
    private void disconnect() {
        try {
            if (nickname != null) {
                server.removeClient(nickname);
            }
            if (writer != null) {
                writer.close();
            }
            if (reader != null) {
                reader.close();
            }
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            log.error("Ошибка при отключении клиента {}", nickname, e);
        }
    }
}