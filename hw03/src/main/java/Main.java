import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        System.out.println(
            sumOfPositiveElements(new int[][]{{-1, 2, -3}, {4, -5, -6}})
        );

        printSquare(5);

        int[][] array = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};

        zeroDiagonals(deepClone(array));

        System.out.println("Max element: " + findMax(array));

        System.out.println("Sum of second row: " + sumOfSecondRow(array));
    }

    /**
     * Реализовать метод sumOfPositiveElements(..), принимающий в качестве аргумента целочисленный двумерный массив,
     * метод должен посчитать и вернуть сумму всех элементов массива, которые больше 0;
     */
    private static int sumOfPositiveElements(int[][] arr2d) {
        printMethodName();

        return Arrays.stream(arr2d)
            .flatMapToInt(Arrays::stream)
            .filter(i -> i > 0)
            .sum();
    }

    /**
     * Реализовать метод, который принимает в качестве аргумента int size и печатает в консоль
     * квадрат из символов * со сторонами соответствующей длины;
     */
    private static void printSquare(int size) {
        printMethodName();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.printf("%4s", "*");
            }
            System.out.println();
        }
    }

    /**
     * Реализовать метод, принимающий в качестве аргумента двумерный целочисленный массив,
     * и зануляющий его диагональные элементы (можете выбрать любую из диагоналей, или занулить обе);
     */
    private static void zeroDiagonals(int[][] arr) {
        printMethodName();

        prettyPrintArray(arr, "Before:");
        for (int i = 0; i < arr.length; i++) {
            if (i < arr[i].length) {
                arr[i][i] = 0;
                arr[i][arr[i].length - 1 - i] = 0;
            }
        }
        System.out.println();
        prettyPrintArray(arr, "After:");
    }

    /**
     * Реализовать метод findMax(int[][] array) который должен найти и вернуть максимальный элемент массива;
     */
    private static int findMax(int[][] array) {
        printMethodName();

        prettyPrintArray(array, "Input array:");
        return Arrays.stream(array)
            .flatMapToInt(Arrays::stream)
            .max()
            .orElseThrow();
    }

    /**
     * Реализуйте метод, который считает сумму элементов второй строки двумерного массива,
     * если второй строки не существует, то в качестве результата необходимо вернуть -1
     */
    private static int sumOfSecondRow(int[][] array) {
        printMethodName();

        prettyPrintArray(array, "Input array:");

        if (array.length < 2) return -1;

        return Arrays.stream(array[1]).sum();
    }

    private static void printMethodName() {
        var vignette = "+".repeat(5);
        System.out.println("\n" + vignette + new Throwable().getStackTrace()[1].getMethodName() + vignette);
    }

    public static int[][] deepClone(int[][] original) {
        if (original == null) return null;

        return Arrays.stream(original)
            .map(arr -> arr != null ? Arrays.copyOf(arr, arr.length) : null)
            .toArray(int[][]::new);
    }

    private static void prettyPrintArray(int[][] array, String header) {
        StringBuilder result = new StringBuilder();
        result.append(header).append("\n");

        for (int[] row : array) {
            for (int cell : row) {
                result.append(String.format("%4d", cell));
            }
            result.append("\n");
        }
        System.out.print(result);
    }
}
