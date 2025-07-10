package ru.gulash.server.db;

import lombok.SneakyThrows;
import org.h2.tools.Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Управляет подключениями к базе данных и запускает консоль H2.
 */
public class DatabaseManager implements AutoCloseable {
    private static final String DB_URL = "jdbc:h2:mem:authdb;DB_CLOSE_DELAY=-1";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "";

    private static DatabaseManager instance;
    private Connection connection;
    private Server webServer;

    private DatabaseManager() {
        try {
            connection = openConnection();
            initializeDatabase();
            startH2Console();

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка подключения к базе данных", e);
        }
    }

    /**
     * Возвращает единственный экземпляр DatabaseManager.
     *
     * @return Единственный экземпляр DatabaseManager
     */
    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    /**
     * Возвращает текущее подключение к базе данных. Если подключение закрыто,
     * создает новое подключение.
     *
     * @return Активное подключение к базе данных
     */
    @SneakyThrows
    public Connection getConnection() {
       // перекоткрываем если закрыто
        if(connection.isClosed()) {
            connection = openConnection();
        }
        return connection;
    }

    /**
     * Открывает новое подключение к базе данных.
     *
     * @return Новое подключение к базе данных
     */
    @SneakyThrows
    private Connection openConnection() {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    /**
     * Запускает веб-консоль H2.
     */
    private void startH2Console() {
        try {
            // Запуск H2 Console на порту 8082
            Server.createWebServer(
                "-web",
                "-webAllowOthers",
                "-webPort", "8082"
            ).start();

        } catch (SQLException e) {
            System.err.println("Ошибка запуска H2 Console: " + e.getMessage());
        }
    }

    /**
     * Инициализирует структуру базы данных.
     *
     * @throws SQLException если произошла ошибка при выполнении SQL-запросов
     */
    private void initializeDatabase() throws SQLException {
        String createTableSQL = """
            CREATE TABLE IF NOT EXISTS users (
                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                username VARCHAR(255) NOT NULL UNIQUE,
                password VARCHAR(255) NOT NULL,
                role VARCHAR(50) NOT NULL,
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            )
            """;

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createTableSQL);

            // Добавляем пользователей по умолчанию
            insertDefaultUsers();
        }
    }

    private void insertDefaultUsers() throws SQLException {
        String insertSQL = """
            INSERT INTO users (username, password, role) 
            VALUES (?, ?, ?)
            """;

        try (var stmt = connection.prepareStatement(insertSQL)) {
            // Администратор
            stmt.setString(1, "admin");
            stmt.setString(2, "admin123");
            stmt.setString(3, "ADMIN");
            stmt.executeUpdate();

            // Тестовые пользователи
            stmt.setString(1, "user1");
            stmt.setString(2, "user123");
            stmt.setString(3, "USER");
            stmt.executeUpdate();

            stmt.setString(1, "user2");
            stmt.setString(2, "user123");
            stmt.setString(3, "USER");
            stmt.executeUpdate();

            stmt.setString(1, "user3");
            stmt.setString(2, "user123");
            stmt.setString(3, "USER");
            stmt.executeUpdate();
        }
    }

    /**
     * Закрывает все ресурсы базы данных, включая веб-сервер и подключение.
     */
    @Override
    public void close() {
        if (webServer != null && webServer.isRunning(false)) {
            webServer.stop();
        }

        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}