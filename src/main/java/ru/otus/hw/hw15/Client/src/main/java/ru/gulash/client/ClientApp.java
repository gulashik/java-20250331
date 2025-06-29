package ru.gulash.client;

import ru.gulash.client.client.ChatImpl;

public class ClientApp {
    public static void main(String[] args) {
        ChatImpl client = new ChatImpl();
        client.start();
    }
}
