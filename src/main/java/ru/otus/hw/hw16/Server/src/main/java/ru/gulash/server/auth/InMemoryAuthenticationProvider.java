package ru.gulash.server.auth;

import ru.gulash.server.model.Role;
import ru.gulash.server.model.User;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

public class InMemoryAuthenticationProvider implements AuthenticationProvider {
    private final Map<String, User> users = new ConcurrentHashMap<>();

    public InMemoryAuthenticationProvider() {
        // Добавляем администратора по умолчанию
        addUser(new User("admin", "admin123", Role.ADMIN));
        // Добавляем тестового пользователя
        addUser(new User("test", "test123", Role.USER));
    }

    @Override
    public User authenticate(String username, String password) {
        User user = users.get(username);
        if (user != null && user.password().equals(password)) {
            return user;
        }
        return null;
    }

    @Override
    public boolean addUser(User user) {
        if (user == null || user.username() == null || user.password() == null) {
            return false;
        }
        
        if (users.containsKey(user.username())) {
            return false; // Пользователь уже существует
        }
        
        users.put(user.username(), user);
        return true;
    }

    @Override
    public boolean userExists(String username) {
        return users.containsKey(username);
    }

    @Override
    public boolean removeUser(String username) {
        return users.remove(username) != null;
    }
}