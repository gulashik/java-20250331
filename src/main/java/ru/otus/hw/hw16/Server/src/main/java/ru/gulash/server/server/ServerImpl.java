package ru.gulash.server.server;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ru.gulash.server.auth.AuthenticationProvider;
import ru.gulash.server.auth.InMemoryAuthenticationProvider;
import ru.gulash.server.client.ClientHandler;
import ru.gulash.server.model.User;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Основная реализация сервера для чат-приложения.
 */
@Slf4j
public class ServerImpl {
    
    /**
     * Порт, на котором работает сервер.
     */
    private static final int PORT = 8080;
    
    /**
     * Потокобезопасная Map активных клиентов.
     */
    private static final Map<User, ClientHandler> clients = new ConcurrentHashMap<>();
    
    /**
     * Серверный сокет для принятия входящих подключений.
     */
    private ServerSocket serverSocket;

    private final AuthenticationProvider authenticationProvider = new InMemoryAuthenticationProvider();

    /**
     * Атомарный флаг состояния сервера.
     * <p>true - сервер работает, false - сервер остановлен.</p>
     */
    private final AtomicBoolean running = new AtomicBoolean(false);

    /**
     * Запускает сервер и начинает принимать входящие подключения.
     * 
     * @throws RuntimeException если не удается создать серверный сокет.
     */
    public void start() {
        try {
            serverSocket = new ServerSocket(PORT);
            running.set(true);
            log.info("Сервер запущен на порту {}", PORT);

            while (running.get()) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    log.info("Новое подключение: {}", clientSocket.getInetAddress());

                    ClientHandler clientHandler = new ClientHandler(clientSocket, this, authenticationProvider);
                    new Thread(clientHandler).start();
                } catch (IOException e) {
                    if (running.get()) {
                        log.error("Ошибка при принятии соединения", e);
                    }
                }
            }
        } catch (IOException e) {
            log.error("Ошибка запуска сервера", e);
        } finally {
            stop();
        }
    }

    /**
     * Останавливает сервер и освобождает все ресурсы.
     */
    public void stop() {
        running.set(false);
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            log.error("Ошибка при остановке сервера", e);
        }
    }

    /**
     * Добавляет нового клиента в список активных пользователей.
     * <p>Метод синхронизирован для обеспечения потокобезопасности.</p>
     *
     * @param user    объект пользователя, представляющий подключающегося клиента.
     * @param handler объект обработчика клиентского подключения, связанный с данным.
     */
    public synchronized void addClient(User user, ClientHandler handler) {
        clients.put(user, handler);
        log.info("Клиент {} подключился. Всего клиентов: {}", user.username(), clients.size());
        broadcastMessage("SERVER", user.username() + " присоединился к чату");
    }

    /**
     * Удаляет клиента из списка подключенных пользователей.
     * <p>Метод синхронизирован для обеспечения потокобезопасности.</p>
     *
     * @param user объект пользователя, представляющий отключающегося клиента.
     */
    public synchronized void removeClient(User user) {
        clients.remove(user);
        log.info("Клиент {} отключился. Всего клиентов: {}", user.username(), clients.size());
        broadcastMessage("SERVER", user.username() + " покинул чат");
    }

    /**
     * Отправляет сообщение всем подключенным клиентам.
     * 
     * @param sender  никнейм отправителя сообщения, не должен быть null
     * @param message текст сообщения, не должен быть null
     * @throws IllegalArgumentException если любой из параметров равен null
     */
    public void broadcastMessage(String sender, String message) {
        log.info("Сообщение от {}: {}", sender, message);
        String fullMessage = "[" + sender + "]: " + message;

        clients.values().forEach(client -> client.sendMessage(fullMessage));
    }

    /**
     * Отправляет личное сообщение между двумя клиентами.
     * 
     * @param sender    никнейм отправителя, должен существовать в списке клиентов
     * @param recipient никнейм получателя, может не существовать
     * @param message   текст личного сообщения, не должен быть null или пустым
     * @throws IllegalArgumentException если любой из параметров равен null
     */
    public void sendPrivateMessage(String sender, String recipient, String message) {
        ClientHandler recipientHandler = findUser(recipient);
        ClientHandler senderHandler = findUser(sender);

        if (recipientHandler != null) {
            String privateMessage = "[Личное от " + sender + "]: " + message;
            recipientHandler.sendMessage(privateMessage);

            if (senderHandler != null) {
                senderHandler.sendMessage("[Вы -> " + recipient + "]: " + message);
            }

            log.info("Личное сообщение от {} к {}: {}", sender, recipient, message);
        } else {
            if (senderHandler != null) {
                senderHandler.sendMessage("Пользователь " + recipient + " не найден");
            }
        }
    }

    /**
     * Возвращает список всех подключенных пользователей.
     * 
     * @return строка с перечислением подключенных пользователей или
     *         сообщение об их отсутствии
     */
    public String getClientsList() {
        if (clients.isEmpty()) {
            return "Нет подключенных пользователей";
        }
        return "Подключенные пользователи: " +
            String.join(", ", clients.keySet().stream().map(User::username).toList());
    }

    /**
     * Ищет обработчик клиента по логину пользователя.
     *
     * @param login логин пользователя для поиска
     * @return обработчик клиента, если пользователь найден, или null если пользователь не найден
     */
    private ClientHandler findUser(String login) {
        return clients.entrySet().stream()
            .filter(entry -> entry.getKey().username().equals(login))
            .findFirst()
            .map(Map.Entry::getValue)
            .orElse(null);
    }
}