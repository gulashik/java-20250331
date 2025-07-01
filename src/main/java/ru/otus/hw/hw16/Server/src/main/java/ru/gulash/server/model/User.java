package ru.gulash.server.model;


/**
 * Запись, представляющая пользователя в системе.
 */
public record User(
    String username,
    String password,
    Role role
) {}