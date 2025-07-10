package ru.otus.hw.hw14.utils;

/**
 * Утилитный класс для измерения времени выполнения задач.
 */
public final class Measure {
    private Measure() {}

    /**
     * Измеряет время выполнения переданной задачи.
     *
     * <p>Запускает переданную задачу и возвращает время её выполнения
     * в миллисекундах с использованием {@link System#currentTimeMillis()}.
     *
     * @param task задача для измерения времени выполнения, не должна быть {@code null}
     * @return время выполнения в миллисекундах
     * @see System#currentTimeMillis()
     */
    public static long executionTime(Runnable task) {
        if (task == null) throw new IllegalArgumentException("task must not be null");

        long startTime = System.currentTimeMillis();
        task.run();
        return System.currentTimeMillis() - startTime;
    }
}