package ru.otus.hw.hw14;

import ru.otus.hw.hw14.utils.ArrayFill;
import ru.otus.hw.hw14.utils.Measure;

public class Main {
    /**
     * Размер массива для тестирования производительности.
     */
    public static final int ARRAY_SIZE = 100_000_000;

    /**
     * Количество потоков для многопоточного выполнения.
     */
    public static final int THREAD_COUNT = 4;

    public static void main(String[] args) {
        System.out.println("Сравнение однопоточного и многопоточного заполнения массива");
        System.out.println("Размер массива: " + ARRAY_SIZE + " элементов\n");

        // Реализация №1 - однопоточное заполнение
        long singleThreadTime = Measure.executionTime(ArrayFill::singleThreadedFilling);

        // Реализация №2 - многопоточное заполнение
        long multiThreadTime = Measure.executionTime(ArrayFill::multiThreadedFilling);

        // Сравнение результатов
        System.out.println("\n========РЕЗУЛЬТАТЫ========");
        System.out.println("Однопоточное выполнение: " + singleThreadTime + " мс");
        System.out.println("Многопоточное выполнение: " + multiThreadTime + " мс");
        System.out.println("Ускорение: " + String.format("%.2f", (double) singleThreadTime / multiThreadTime) + "x");
        System.out.println("Экономия времени: " + (singleThreadTime - multiThreadTime) + " мс");
    }
}