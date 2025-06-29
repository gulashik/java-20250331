package ru.gulash.server.server;

import lombok.extern.slf4j.Slf4j;
import ru.gulash.server.client.ClientHandler;

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
     * 
     * <p>Ключ - никнейм клиента (String), значение - обработчик клиента (ClientHandler).</p>
     */
    private static final Map<String, ClientHandler> clients = new ConcurrentHashMap<>();
    
    /**
     * Серверный сокет для принятия входящих подключений.
     */
    private ServerSocket serverSocket;
    
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

                    ClientHandler clientHandler = new ClientHandler(clientSocket, this);
                    new Thread(clientHandler).start();
                } catch (IOException e) {
                    if (running.get()) {
                        log.error("Ошибка при принятии соединения", e);
                    }
                }
            }
        } catch (IOException e) {
            log.error("Ошибка запуска сервера", e);
        }
    }

    /**
     * Останавливает сервер и освобождает все ресурсы.
     * <p style="color: red">Пока не используется.</p>
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
     * 
     * <p>Метод синхронизирован для обеспечения потокобезопасности при
     * одновременном добавлении клиентов из разных потоков.</p>
     * 
     * @param nickname уникальный никнейм клиента не может быть null
     * @param handler  обработчик клиентского соединения не может быть null
     * @throws IllegalArgumentException если nickname или handler равны null
     */
    public synchronized void addClient(String nickname, ClientHandler handler) {
        clients.put(nickname, handler);
        log.info("Клиент {} подключился. Всего клиентов: {}", nickname, clients.size());
        broadcastMessage("SERVER", nickname + " присоединился к чату");
    }

    /**
     * Удаляет клиента из списка активных пользователей.
     *
     * <p>Метод синхронизирован для обеспечения потокобезопасности при
     * одновременном удалении клиентов из разных потоков.</p>
     * 
     * @param nickname никнейм клиента для удаления, может быть null
     *                (в этом случае операция игнорируется)
     */
    public synchronized void removeClient(String nickname) {
        clients.remove(nickname);
        log.info("Клиент {} отключился. Всего клиентов: {}", nickname, clients.size());
        broadcastMessage("SERVER", nickname + " покинул чат");
    }

    /**
     * Проверяет, занят ли указанный никнейм.
     *
     * <p>Метод синхронизирован для обеспечения атомарности операции
     * смены никнейма.</p>
     *
     * @param nickname никнейм для проверки, не должен быть null
     * @return true, если никнейм уже используется; false в противном случае
     * @throws IllegalArgumentException если nickname равен null
     */
    public synchronized boolean isNicknameTaken(String nickname) {
        return clients.containsKey(nickname);
    }

    /**
     * Изменяет никнейм существующего клиента.
     * 
     * <p>Метод синхронизирован для обеспечения атомарности операции
     * смены никнейма.</p>
     * 
     * @param oldNickname текущий никнейм клиента, не должен быть null
     * @param newNickname новый никнейм клиента, не должен быть null или пустым
     * @throws IllegalArgumentException если любой из параметров равен null
     */
    public synchronized void changeNickname(String oldNickname, String newNickname) {
        ClientHandler handler = clients.remove(oldNickname);
        if (handler != null) {
            clients.put(newNickname, handler);
            log.info("Клиент {} сменил имя на {}", oldNickname, newNickname);
            broadcastMessage("SERVER", oldNickname + " сменил имя на " + newNickname);
        }
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
        ClientHandler recipientHandler = clients.get(recipient);
        ClientHandler senderHandler = clients.get(sender);

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
        return "Подключенные пользователи: " + String.join(", ", clients.keySet());
    }
}