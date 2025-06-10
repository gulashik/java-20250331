package ru.otus.hw.hw14.utils;

import java.util.ArrayList;
import java.util.List;

import static ru.otus.hw.hw14.Main.ARRAY_SIZE;
import static ru.otus.hw.hw14.Main.THREAD_COUNT;

/**
 * Утилитный класс для заполнения массивов.
 */

public final class ArrayFill {
    private ArrayFill(){}

    /**
     * Реализация №1: Однопоточное заполнение массива.
     *
     * <p>Создает массив размером {@link ru.otus.hw.hw14.Main#ARRAY_SIZE} и последовательно заполняет
     * каждый элемент в одном потоке.
     */
    public static void singleThreadedFilling() {
        System.out.println("Реализация №1: Однопоточное заполнение массива...");

        double[] array = new double[ARRAY_SIZE];

        // Заполнение массива по формуле
        for (int i = 0; i < ARRAY_SIZE; i++) {
            calculation(array, i);
        }
    }

    /**
     * Реализация №2: Многопоточное заполнение массива.
     *
     * <p>Создает массив размером {@link ru.otus.hw.hw14.Main#ARRAY_SIZE} и разделяет его на
     * {@link ru.otus.hw.hw14.Main#THREAD_COUNT} равных сегментов.
     * <p>Каждый сегмент заполняется в отдельном потоке параллельно.
     */
    public static void multiThreadedFilling() {
        System.out.println("Реализация №2: Многопоточное заполнение массива в " + THREAD_COUNT + " потока)...");

        List<Thread> threadCollection = new ArrayList<>(THREAD_COUNT);
        double[] array = new double[ARRAY_SIZE];
        int segmentSize = (ARRAY_SIZE / THREAD_COUNT) - 1;
        int pointer = 0;

        // Создание и запуск потоков
        for (int t = 0; t < THREAD_COUNT; t++) {
            int startIndex = pointer;
            int endIndex = startIndex + segmentSize;
            pointer = endIndex + 1;

            System.out.println("Диапазон заполнения №" + (t+1) + " = (" + startIndex + " - " + endIndex + ")");
            threadCollection.add(
                new Thread(
                    () -> {
                        for (int i = startIndex; i < endIndex; i++) {
                            calculation(array, i);
                        }
                    }
                )
            );
            threadCollection.get(t).start();
        }

        // Ожидание завершения всех потоков
        threadCollection.forEach(
            thread -> {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        );
    }

    /**
     * Выполняет странное математическое вычисление и заполняет им переданный индекс массива.
     *
     * @param array массив для заполнения
     * @param i     индекс элемента для заполнения
     */
    private static void calculation(double[] array, int i) {
        if (i % (ARRAY_SIZE/10) == 0) {
            System.out.println("Thread " + Thread.currentThread().getName() + ": Заполнен " + String.format("%,d", i)
                + " элемент");
        }
        array[i] = 1.14 * Math.cos(i) * Math.sin(i * 0.2) * Math.cos(i / 1.2);
    }
}
