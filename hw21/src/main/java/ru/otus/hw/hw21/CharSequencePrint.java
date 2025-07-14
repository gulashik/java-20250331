package ru.otus.hw.hw21;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * Циклический вывод символов из Enum в строгой последовательности.
 */
public class CharSequencePrint {

    private static final int PRINT_COUNT = 5;

    private static final Lock lock = new ReentrantLock();

    /** Переменная для ожидания своей очереди и уведомления других потоков.*/
    private static final Condition condition = lock.newCondition();

    /** Текущий символ для вывода.*/
    private static volatile CharSequence currentChar = CharSequence.values()[0];

    public static void main(String[] args) {
        System.out.println("Начали вывод символов.");
        ExecutorService executor = Executors.newFixedThreadPool(CharSequence.count());

        try {
            Arrays.stream(CharSequence.values())
                .forEach(ch -> executor.execute(() -> printChar(ch)));
        } finally {
            try {
                executor.shutdown();
                if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                    executor.shutdownNow();
                }
            } catch (InterruptedException e) {
                executor.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("\nВывод завершён.");
    }

    /**
     * Выводит заданный символ указанное количество раз.
     *
     * @param targetChar символ, который должен быть выведен этим потоком
     * @throws InterruptedException если поток был прерван во время ожидания
     */
    private static void printChar(CharSequence targetChar) {
        for (int i = 0; i < PRINT_COUNT; i++) {
            lock.lock(); // получаем блокировку для возможности использования await() и signalAll()

            try {
                // ждём своей очереди
                while (currentChar != targetChar) {
                    condition.await();
                }

                System.out.print(targetChar);

                // переходим к следующему символу в последовательности
                currentChar = targetChar.getNext();

                // уведомляем все ожидающие потоки об изменении состояния
                condition.signalAll();

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                lock.unlock(); // снимаем блокировку
            }
        }
    }
}

/**
 * Перечисление символов для последовательного вывода.
 *
 * <p>Определяет порядок символов и предоставляет методы для навигации
 * по последовательности в циклическом режиме.</p>
 */
enum CharSequence {
    A()
    ,B()
    ,C()
    //,D()
    //,E()
    ;

    /**
     * Возвращает общее количество символов в перечислении.
     *
     * @return количество доступных символов
     */
    public static int count() {
        return values().length;
    }

    /**
     * Возвращает следующий символ в циклической последовательности.
     *
     * @return следующий символ в последовательности
     */
    public CharSequence getNext() {
        int nextIndex = (this.ordinal() + 1) % values().length;
        return values()[nextIndex];
    }
}
