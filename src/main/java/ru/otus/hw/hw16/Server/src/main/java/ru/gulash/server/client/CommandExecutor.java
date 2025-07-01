package ru.gulash.server.client;

import ru.gulash.server.exception.ExitClientException;
import ru.gulash.server.model.User;
import ru.gulash.server.server.ServerImpl;

import java.io.BufferedReader;
import java.io.PrintWriter;

public class CommandExecutor {

    private final ClientHandler clientHandler;
    private final ServerImpl server;
    private final User user;
    PrintWriter writer;
    BufferedReader reader;
    public CommandExecutor(ClientHandler clientHandler) {
        if (clientHandler == null) {
            throw new IllegalArgumentException("ClientHandler is null");
        }
        this.clientHandler = clientHandler;

        if(clientHandler.getServer() == null) {
            throw new IllegalArgumentException("Server is null");
        }
        this.server = clientHandler.getServer();

        if(clientHandler.getUser() == null) {
            throw new IllegalArgumentException("User is null");
        }
        this.user = clientHandler.getUser();

        if(clientHandler.getReader() == null) {
            throw new IllegalArgumentException("Reader is null");
        }
        this.reader = clientHandler.getReader();

        if(clientHandler.getWriter() == null) {
            throw new IllegalArgumentException("Writer is null");
        }
        this.writer = clientHandler.getWriter();
    }

    public void execute(String message) {

        if (message.equals("/exit")) {
            throw new ExitClientException("Отключился клиент: " + user.username());
        } else if (message.equals("/all")) {
            writer.println(server.getClientsList());
        } else if (message.startsWith("/w ")) {
            clientHandler.handlePrivateMessage(message);
        } else if (message.startsWith("/kick ")) {
            clientHandler.handleKickMessage(message);
        } else {
            // Обычное сообщение для всех пользователей
            server.broadcastMessage(user.username(), message);
        }
    }
}
