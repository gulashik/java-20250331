package ru.otus.hw23.server;

import ru.otus.hw23.request.ClientHandler;
import ru.otus.hw23.request.Dispatcher;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {
    private final int port;
    private final Dispatcher dispatcher;
    private final ExecutorService threadPool;
    private volatile boolean running = false;

    public HttpServer(int port) {
        this.port = port;
        this.dispatcher = new Dispatcher();
        this.threadPool = Executors.newFixedThreadPool(10);
    }

    public void start() {
        running = true;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Сервер запущен на порту: " + port);
            
            // Основной цикл сервера для принятия подключений
            while (running) {
                try {
                    Socket socket = serverSocket.accept();
                    // Передаем обработку запроса в отдельный поток
                    threadPool.execute(new ClientHandler(socket, dispatcher));
                } catch (IOException e) {
                    if (running) {
                        System.err.println("Ошибка при принятии соединения: " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка при запуске сервера: " + e.getMessage());
            e.printStackTrace();
        } finally {
            stop();
        }
    }

    public void stop() {
        running = false;
        if (threadPool != null && !threadPool.isShutdown()) {
            threadPool.shutdown();
            System.out.println("Сервер остановлен");
        }
    }
}