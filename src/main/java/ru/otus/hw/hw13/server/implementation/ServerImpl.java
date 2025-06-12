package ru.otus.hw.hw13.server.implementation;

import ru.otus.hw.hw13.server.Server;
import ru.otus.hw.hw13.server.entity.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Сервер калькулятора для обработки математических вычислений.<p>
 * Поддерживает множественные клиентские соединения в многопоточном режиме.
 */
public class ServerImpl implements Server {

    private final int port;

    private final String closeTag;

    /**
     * Пул потоков для обработки клиентских соединений.
     * Использование newCachedThreadPool() создает потоки по требованию и переиспользует существующие для новых задач.
     */
    private final ExecutorService executorService;

    private ServerSocket serverSocket;

    /**
     * Флаг состояния сервера для корректного завершения работы.
     * <pre><strong>Вот тут не уверен</strong>
     * volatile - чтобы не было кэширования в CPU.
     * ServerImpl.java - основной поток сервера while (isRunning) {}
     * Main.java - другой поток вызывает stop()
     * </pre>
     */
    private final AtomicBoolean isRunning = new AtomicBoolean(false);

    /**
     * Создает экземпляр сервера калькулятора с указанным портом и командой закрытия.
     *
     * @param port порт для прослушивания входящих соединений.
     *             Рекомендуется использовать порты 1025-49151 для избежания конфликтов с системными службами
     * @param closeTag команда для завершения соединения с клиентом
     * @throws IllegalArgumentException если порт находится вне допустимого диапазона или closeTag некорректен
     * @see Executors#newCachedThreadPool()
     */
    public ServerImpl(int port, String closeTag) {
        if (port <= 1024 || port >= 49151) {
            throw new IllegalArgumentException("Порт должен быть в диапазоне 1025-49151");
        }
        if (closeTag == null || closeTag.trim().isEmpty()) {
            throw new IllegalArgumentException("CloseTag не может быть null или пустым");
        }
        this.port = port;
        this.closeTag = closeTag;
        this.executorService = Executors.newCachedThreadPool();
    }

    /**
     * {@inheritDoc} <p>
     * Каждое соединение обрабатывается в отдельном потоке с поддержкой математических операций.
     *
     * <p> <strong>Важные моменты реализации:</strong>
     * <ul>
     * <li>Сервер не использует try-with-resources для ServerSocket, так как его нужно закрыть в методе stop()</li>
     * <li>Каждая ошибка accept() не останавливает сервер, а только логируется</li>
     * </ul>
     *
     * @see ServerSocket#accept()
     * @see ExecutorService#submit(Runnable)
     */
    @Override
    public void start() {
        try {
            // Создаем серверный сокет и привязываем к порту
            serverSocket = new ServerSocket(port);
            isRunning.set(true);

            System.out.println("Сервер калькулятора запущен на порту " + port);
            System.out.println("Ожидание подключений клиентов...");

            // Основной цикл сервера - принимаем соединения
            while (isRunning.get()) {
                try {
                    // accept() блокирует выполнение до поступления соединения
                    Socket clientSocket = serverSocket.accept();

                    System.out.println("Подключен клиент калькулятора: " + clientSocket.getRemoteSocketAddress());

                    // Каждого клиента обрабатываем в отдельном потоке
                    executorService.submit(new ClientHandler(clientSocket, closeTag));

                } catch (IOException e) {
                    if (isRunning.get()) {
                        System.err.println("Ошибка при принятии соединения: " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Не удалось запустить сервер калькулятора: " + e.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     *
     * @see ExecutorService#shutdown()
     * @see ExecutorService#awaitTermination(long, TimeUnit)
     * @see ExecutorService#shutdownNow()
     */
    @Override
    public void stop() {
        isRunning.set(false);
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
            executorService.shutdown();

            // Ждем завершения всех задач с таймаутом
            if (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
                System.out.println("Принудительное завершение потоков...");
                executorService.shutdownNow();
            }
            System.out.println("Сервер калькулятора успешно остановлен");

        } catch (IOException | InterruptedException e) {
            System.err.println("Ошибка при остановке сервера: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}