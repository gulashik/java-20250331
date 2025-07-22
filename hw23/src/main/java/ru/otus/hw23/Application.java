package ru.otus.hw23;

import ru.otus.hw23.server.HttpServer;

public class Application {
    public static void main(String[] args) {
        int port = 8189;
        System.out.println("http://127.0.0.1:%s/".formatted(port));
        new HttpServer(port).start();
    }
}
