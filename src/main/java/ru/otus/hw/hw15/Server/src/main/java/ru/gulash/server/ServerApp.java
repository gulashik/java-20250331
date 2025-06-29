package ru.gulash.server;

import ru.gulash.server.server.ServerImpl;

public class ServerApp {
    public static void main(String[] args) {
        ServerImpl server = new ServerImpl();
        server.start();
    }
}
