package ru.gulash.server.model;

public record User(String username, String password, Role role) {
}