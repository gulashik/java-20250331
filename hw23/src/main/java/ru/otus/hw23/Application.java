package ru.otus.hw23;

import ru.otus.hw23.server.HttpServer;

public class Application {
    public static void main(String[] args) {
        int port = 8189;
        new HttpServer(port).start();
    }
}
