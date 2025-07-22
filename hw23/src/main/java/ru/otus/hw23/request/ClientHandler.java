package ru.otus.hw23.request;

import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private final Dispatcher dispatcher;

    public ClientHandler(Socket socket, Dispatcher dispatcher) {
        this.socket = socket;
        this.dispatcher = dispatcher;
    }

    @Override
    public void run() {
        try (Socket clientSocket = socket) {
            byte[] buffer = new byte[8192];
            int n = clientSocket.getInputStream().read(buffer);

            if (n > 0) {
                String rawRequest = new String(buffer, 0, n);
                HttpRequest request = new HttpRequest(rawRequest);
                request.info(true);
                dispatcher.execute(request, clientSocket.getOutputStream());
            }
        } catch (Exception e) {
            System.err.println("Неожиданная ошибка при обработке запроса: " + e.getMessage());
            e.printStackTrace();
        }
    }
}