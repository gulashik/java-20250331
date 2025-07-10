package ru.gulash.server.auth;

import ru.gulash.server.model.User;

public interface AuthenticationProvider {
    /**
     * Аутентифицирует пользователя по логину и паролю
     *
     * @param username имя пользователя
     * @param password пароль
     * @return пользователь, если аутентификация успешна, иначе null
     */
    User authenticate(String username, String password);
    
    /**
     * Добавляет нового пользователя
     *
     * @param user пользователь для добавления
     * @return true, если пользователь добавлен успешно
     */
    boolean addUser(User user);
    
    /**
     * Проверяет, существует ли пользователь с таким именем
     *
     * @param username имя пользователя
     * @return true, если пользователь существует
     */
    boolean userExists(String username);
    
    /**
     * Удаляет пользователя
     *
     * @param username имя пользователя
     * @return true, если пользователь удален успешно
     */
    boolean removeUser(String username);
    
    /**
     * Останавливает провайдер аутентификации и освобождает ресурсы
     */
    void stop();
}