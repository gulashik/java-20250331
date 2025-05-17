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
     * Константа, определяющая требуемый размер массива.
     */
    private final static int ARRAY_DIMENSION_SIZE = 4;
    
    /**
     * Вычисляет сумму элементов двумерного массива.
     * Метод требует, чтобы массив имел одинаковые измерения {@link #ARRAY_DIMENSION_SIZE}, и все элементы
     * можно было преобразовать в числа.
     *
     * @param correctArray двумерный массив строк для суммирования
     * @throws AppArraySizeException если размер массива имеет не одинаковые измерения {@link #ARRAY_DIMENSION_SIZE}
     * @throws AppArrayDataException если какой-либо элемент массива не может быть преобразован в целое число
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
     * @throws AppArraySizeException если размер массива имеет не одинаковые измерения {@link #ARRAY_DIMENSION_SIZE}
     * @throws AppArrayDataException если какой-либо элемент массива не может быть преобразован в целое число
     */
    private static int validateAndSum(String[][] array) {
        return Arrays
            .stream(validateArray(array))
            .flatMap(Arrays::stream)
            .mapToInt(ArrayUtils::validateArrayItem)
            .sum();
    }
    
    /**
     * Проверяет корректность измерений массива.
     * 
     * @param array двумерный массив строк для проверки размера
     * @return исходный массив, если он прошел валидацию
     * @throws AppArraySizeException если размер массива имеет не одинаковые измерения {@link #ARRAY_DIMENSION_SIZE}
     */
    private static String[][] validateArray(String[][] array) {
        if (array.length != ARRAY_DIMENSION_SIZE || !allArrayRowsLength(array, ARRAY_DIMENSION_SIZE)) {
            throw new AppArraySizeException("Array size must be %1$d x %1$d".formatted(ARRAY_DIMENSION_SIZE));
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

    /**
     * Проверяет, что все строки двумерного массива имеют одинаковую длину.
     *
     * @param arrays двумерный массив строк для проверки
     * @param rowLength ожидаемая длина каждой строки массива
     * @return true, если все строки имеют указанную длину, иначе false
     */
    private static boolean allArrayRowsLength(String[][] arrays, int rowLength) {
        return Arrays.stream(arrays)
            .map(curArray -> curArray.length)
            .allMatch(length -> length == rowLength);
    }
}