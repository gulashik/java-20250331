package ru.otus.hw.hw08.util;

import ru.otus.hw.hw08.util.exception.AppArrayDataException;
import ru.otus.hw.hw08.util.exception.AppArraySizeException;

import java.util.Arrays;

/**
 * Утилитарный класс для работы с массивами строк.
 * Предоставляет функциональность для валидации и вычисления суммы элементов массива.
 */
public abstract class ArrayUtils {
    
    /**
     * Вычисляет сумму элементов двумерного массива.
     * Метод требует, чтобы массив имел размер 4x4, и все элементы 
     * можно было преобразовать в числа.
     *
     * @param correctArray двумерный массив строк для суммирования
     * @throws AppArraySizeException если размер массива не равен 4x4
     * @throws AppArrayDataException если какой-либо элемент массива не может быть 
     *                             преобразован в целое число
     */
    public static void sum(String[][] correctArray) {
        try {
            int result = validateAndSum(correctArray);
            System.out.println("Sum of array's elements: " + result);
        } catch (Exception e) {
            System.out.printf(
                "Have got an exception: %s cause '%s' %n",
                e.getClass().getSimpleName(),
                e.getMessage() == null ? "no message" : e.getMessage()
            );
        }
    }

    /**
     * Проверяет и суммирует элементы двумерного массива.
     * 
     * @param array двумерный массив строк для валидации и суммирования
     * @return сумма элементов массива
     * @throws AppArraySizeException если размер массива не равен 4x4
     * @throws AppArrayDataException если какой-либо элемент массива не может быть 
     *                             преобразован в целое число
     */
    private static int validateAndSum(String[][] array) {
        return Arrays
            .stream(validateArray(array))
            .flatMap(Arrays::stream)
            .mapToInt(ArrayUtils::validateArrayItem)
            .sum();
    }

    /**
     * Проверяет, что размер массива равен 4x4.
     * 
     * @param array двумерный массив строк для проверки размера
     * @return исходный массив, если он прошел валидацию
     * @throws AppArraySizeException если размер массива не равен 4x4
     */
    private static String[][] validateArray(String[][] array) {
        if (array.length != 4 || array[0].length != 4) {
            throw new AppArraySizeException("Array size must be 4x4");
        }
        return array;
    }

    /**
     * Преобразует строковый элемент массива в целое число.
     * 
     * @param item строковый элемент для преобразования
     * @return целочисленное значение элемента
     * @throws AppArrayDataException если элемент не может быть преобразован в целое число
     */
    private static int validateArrayItem(String item) {
        try {
            return Integer.parseInt(item);
        } catch (NumberFormatException e) {
            throw new AppArrayDataException("Array items must be a number but got: \"" + item + "\"");
        }
    }
}