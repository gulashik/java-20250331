package ru.gulash.server.auth;

import ru.gulash.server.db.DatabaseManager;
import ru.gulash.server.model.Role;
import ru.gulash.server.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class H2AuthenticationProvider implements AuthenticationProvider {

    private final DatabaseManager dbManager;

    public H2AuthenticationProvider() {
        this.dbManager = DatabaseManager.getInstance();
    }

    @Override
    public User authenticate(String username, String password) {
        String sql = "SELECT username, password, role FROM users WHERE username = ? AND password = ?";

        try (PreparedStatement stmt = dbManager.getConnection().prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                        rs.getString("username"),
                        rs.getString("password"),
                        Role.valueOf(rs.getString("role"))
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при аутентификации: " + e.getMessage());
        }

        return null;
    }

    @Override
    public boolean addUser(User user) {
        if (user == null || user.username() == null || user.password() == null) {
            return false;
        }

        if (userExists(user.username())) {
            return false; // Пользователь уже существует
        }

        String sql = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = dbManager.getConnection().prepareStatement(sql)) {

            stmt.setString(1, user.username());
            stmt.setString(2, user.password());
            stmt.setString(3, user.role().name());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Ошибка при добавлении пользователя: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean userExists(String username) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";

        try (PreparedStatement stmt = dbManager.getConnection().prepareStatement(sql)) {

            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при проверке существования пользователя: " + e.getMessage());
        }

        return false;
    }

    @Override
    public boolean removeUser(String username) {
        String sql = "DELETE FROM users WHERE username = ?";

        try (PreparedStatement stmt = dbManager.getConnection().prepareStatement(sql)) {

            stmt.setString(1, username);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Ошибка при удалении пользователя: " + e.getMessage());
            return false;
        }
    }

    @Override
    public void stop() {
        DatabaseManager.getInstance().close();
    }
}