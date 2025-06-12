package ru.otus.hw.hw13;

import ru.otus.hw.hw13.client.Client;
import ru.otus.hw.hw13.server.Server;
import ru.otus.hw.hw13.server.implementation.ServerImpl;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    private static final int PORT = 8080;

    private static final String HOST_NAME = "localhost";

    private static final String CLOSE_TAG = "close";

    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) throws IOException {

        Server server = new ServerImpl(PORT, CLOSE_TAG);
        Thread serverThread = new Thread(server::start);
        serverThread.start();

        new Client(HOST_NAME, PORT, CLOSE_TAG).connectAndInteractWithServerAndUser(SCANNER);

        server.stop();
    }
}