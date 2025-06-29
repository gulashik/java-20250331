package ru.gulash.server.server;

import lombok.extern.slf4j.Slf4j;
import ru.gulash.server.client.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class ServerImpl {
    private static final int PORT = 8080;
    private static final Map<String, ClientHandler> clients = new ConcurrentHashMap<>();
    private ServerSocket serverSocket;
    private final AtomicBoolean running = new AtomicBoolean(false);

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

    public synchronized void addClient(String nickname, ClientHandler handler) {
        clients.put(nickname, handler);
        log.info("Клиент {} подключился. Всего клиентов: {}", nickname, clients.size());
        broadcastMessage("SERVER", nickname + " присоединился к чату");
    }

    public synchronized void removeClient(String nickname) {
        clients.remove(nickname);
        log.info("Клиент {} отключился. Всего клиентов: {}", nickname, clients.size());
        broadcastMessage("SERVER", nickname + " покинул чат");
    }

    public synchronized boolean isNicknameTaken(String nickname) {
        return clients.containsKey(nickname);
    }

    public synchronized void changeNickname(String oldNickname, String newNickname) {
        ClientHandler handler = clients.remove(oldNickname);
        if (handler != null) {
            clients.put(newNickname, handler);
            log.info("Клиент {} сменил имя на {}", oldNickname, newNickname);
            broadcastMessage("SERVER", oldNickname + " сменил имя на " + newNickname);
        }
    }

    public void broadcastMessage(String sender, String message) {
        log.info("Сообщение от {}: {}", sender, message);
        String fullMessage = "[" + sender + "]: " + message;

        clients.values().forEach(client -> client.sendMessage(fullMessage));
    }

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

    public String getClientsList() {
        if (clients.isEmpty()) {
            return "Нет подключенных пользователей";
        }
        return "Подключенные пользователи: " + String.join(", ", clients.keySet());
    }
}

