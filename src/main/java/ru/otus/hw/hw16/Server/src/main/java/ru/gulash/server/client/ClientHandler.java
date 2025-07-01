package ru.gulash.server.client;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ru.gulash.server.auth.AuthenticationProvider;
import ru.gulash.server.exception.ExitClientException;
import ru.gulash.server.model.User;
import ru.gulash.server.server.ServerImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

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
    @Getter
    private BufferedReader reader;
    
    /**
     * Поток для отправки данных клиенту.
     */
    @Getter
    private PrintWriter writer;

    @Getter
    private User user;

    /**
     * Ссылка на основной сервер для взаимодействия с другими клиентами.
     */
    @Getter
    private final ServerImpl server;

    private final AuthenticationProvider authenticationProvider;


    /**
     * Флаг состояния соединения с клиентом.
     */

    private boolean isConnected;

    /**
     * Создает новый экземпляр ClientHandler, отвечающий за управление
     * связью между сервером и подключенным клиентом.
     *
     * @param socket сокет, связанный с подключением клиента. Не может быть null.
     * @param server реализация сервера, управляющая состоянием приложения. Не может быть null.
     * @param authenticationProvider провайдер аутентификации для проверки учетных данных клиента. Не может быть null.
     * @throws IllegalArgumentException если любой из параметров равен null.
     */
    public ClientHandler(Socket socket, ServerImpl server, AuthenticationProvider authenticationProvider) {
        if (socket == null) {
            throw new IllegalArgumentException("Socket не может быть null");
        }
        if (server == null) {
            throw new IllegalArgumentException("Server не может быть null");
        }
        if (authenticationProvider == null) {
            throw new IllegalArgumentException("AuthenticationProvider не может быть null");
        }
        this.socket = socket;
        this.server = server;
        this.authenticationProvider = authenticationProvider;
        this.isConnected = true;
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
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            writer = new PrintWriter(socket.getOutputStream(), true, StandardCharsets.UTF_8);

            // Процедура регистрации клиента
            writer.println("Введите логин и пароль в формате 'login пробел password':");
            String[] credential = reader.readLine().split(" ", 2);

            user = authenticationProvider.authenticate(credential[0], credential[1]);

            if (user == null) {
                writer.println("Ошибка аутентификации. Клиентская сессия закрыта.");
                throw new RuntimeException("Ошибка аутентификации.");
            }

            // Регистрация клиента на сервере
            server.addClient(user, this);

            // Создание экземпляра CommandExecutor для обработки клиентских команд.
            CommandExecutor commandExecutor = new CommandExecutor(this);

            // Отправка приветственного сообщения и справки по командам
            writer.println("Добро пожаловать в чат, " + user.username() + "!");
            writer.println("Доступные команды:");
            writer.println("/w <никнейм> <сообщение> - личное сообщение");
            writer.println("/all - список пользователей");
            writer.println("/exit - выйти из чата");
            writer.println("/help - показать справку");

            // Основной цикл обработки сообщений
            String message;
            while (isConnected && (message = reader.readLine()) != null) {
                commandExecutor.execute(message.trim());
            }
        } catch (IOException e) {
            log.error("Ошибка в обработчике клиента {}", user.username(), e);
        } catch (ExitClientException e) {
            log.info("Клиент {} отключился от сервера", (user != null) ? user.username() : "unknown user");
        }
        finally {
            disconnect();
        }
    }

    /**
     * Отправляет сообщение данному клиенту.
     *
     * @param message текст сообщения для отправки клиенту.
     *                Если {@code null}, сообщение не отправляется.
     */
    public void sendMessage(String message) {
        if (isConnected && writer != null && message != null) {
            writer.println(message);
        }
    }

    /**
     * Обрабатывает команду отправки личного сообщения.
     *
     * @param command строка команды
     */
    public void handlePrivateMessage(String command) {
        String[] parts = command.split(" ", 3);
        if (parts.length < 3) {
            writer.println("Использование: /w <никнейм> <сообщение>");
            return;
        }

        String recipient = parts[1];
        String message = parts[2].trim();
        server.sendPrivateMessage(user.username(), recipient, message);
    }

    /**
     * Выполняет отключение клиента и освобождение ресурсов.
     */
    private void disconnect() {
        isConnected = false;

        try {
            if (user != null) {
                server.removeClient(user);
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
            log.error("Ошибка при отключении клиента {}", user, e);
        }
    }
}