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

@Slf4j
public class ClientHandler implements Runnable {
    private final Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private String nickname;
    private final ServerImpl server;

    public ClientHandler(Socket socket, ServerImpl server) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);

            // Получение никнейма
            writer.println("Введите ваш никнейм:");
            nickname = reader.readLine();

            while (nickname == null || nickname.trim().isEmpty() || server.isNicknameTaken(nickname)) {
                if (server.isNicknameTaken(nickname)) {
                    writer.println("Никнейм уже занят. Введите другой:");
                } else {
                    writer.println("Никнейм не может быть пустым. Введите никнейм:");
                }
                nickname = reader.readLine();
            }

            server.addClient(nickname, this);
            writer.println("Добро пожаловать в чат, " + nickname + "!");
            writer.println("Доступные команды:");
            writer.println("/w <никнейм> <сообщение> - личное сообщение");
            writer.println("/all - список пользователей");
            writer.println("/name <новый_никнейм> - сменить никнейм");
            writer.println("/exit - выйти из чата");

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
                    server.broadcastMessage(nickname, message);
                }
            }
        } catch (IOException e) {
            log.error("Ошибка в обработчике клиента {}", nickname, e);
        } finally {
            disconnect();
        }
    }

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

    public void sendMessage(String message) {
        if (writer != null) {
            writer.println(message);
        }
    }

    private void disconnect() {
        try {
            if (nickname != null) {
                server.removeClient(nickname);
            }
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            log.error("Ошибка при отключении клиента {}", nickname, e);
        }
    }
}
