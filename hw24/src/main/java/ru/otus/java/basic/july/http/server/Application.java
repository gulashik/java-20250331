package ru.otus.java.basic.july.http.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.java.basic.july.http.server.request.HttpServer;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    // Домашнее задание:
    // 1. Добавьте логирование, не должно остаться никаких System.out.println
    // 2. "Оберните" любые неперехваченные в процессорах исключения в ответ со статус кодом 500
    // 3. * Сделайте парсинг заголовков запроса в Map<String, String>. Например, для заголовка
    // Content-Type: application/json => key = 'Content-Type', value = 'application/json'

    public static void main(String[] args) {
        logger.info("Запуск HTTP сервера на порту 8189");
        try {
            new HttpServer(8189).start();
        } catch (Exception e) {
            logger.error("Ошибка при запуске сервера", e);
        }
    }
}

