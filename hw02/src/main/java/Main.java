import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        printLine(0, "0 time");
        printLine(3, "3 times");

        arraySum(1, 2, 3, 4, 5);
        arraySum(6, 1, 2, 3, 4, 5, 6);

        fillArray(9, new int[5]);

        increaseArrayElements(2, 1, 2, 3, 4, 5);

        compareArrayHalves(1, 2, 13, 4, 5, 6);
        compareArrayHalves(1, 1, 1, 1, 1, 1);

        hasArrayPoisePoint(1, 2, 3, 6);
        hasArrayPoisePoint(1, 2, 3, 16);

        isSorted(true, 1, 22, 33);
        isSorted(false, 1, 2, 3);
        isSorted(false, 30, 2, 1);

        reverseArray(1, 2, 3, 4, 5);

        sumArrays(
            new int[]{1, 2, 3},
            new int[]{2, 2},
            new int[]{1, 1, 1, 1, 1}
        );
    }

    /**
     * Реализуйте метод, принимающий в качестве аргументов целое число и строку, печатающий в консоль строку указанное количество раз
     */
    private static void printLine(int cnt, String line) {
        printMethodName();

        for (int i = 1; i <= cnt; i++) {
            System.out.println(line);
        }
    }

    /**
     * Реализуйте метод, принимающий в качестве аргумента целочисленный массив, суммирующий все элементы, значение которых больше 5, и печатающий полученную сумму в консоль.
     */
    private static void arraySum(int... ints) {
        printMethodName();

        int summed = Arrays.stream(ints)
            .filter(i -> i > 5)
            .sum();
        System.out.printf("sum of filtered array %s = %s%n", Arrays.toString(ints), summed);
    }

    /**
     * Реализуйте метод, принимающий в качестве аргументов целое число и ссылку на целочисленный массив, метод должен заполниться каждую ячейку массива указанным числом.
     */
    private static void fillArray(int value, int... arr) {
        printMethodName();

        System.out.printf("origin array: %s%n", Arrays.toString(arr));
        Arrays.fill(arr, value);
        System.out.printf("filled array with %d: %s%n", value, Arrays.toString(arr));
    }

    /**
     * Реализуйте метод, принимающий в качестве аргументов целое число и ссылку на целочисленный массив, увеличивающий каждый элемент которого на указанное число.
     */

    private static void increaseArrayElements(int value, int... arr) {
        printMethodName();

        System.out.printf("origin array: %s%n", Arrays.toString(arr));
        for (int i = 0; i < arr.length; i++) {
            arr[i] += value;
        }
        System.out.printf("increased array by %d: %s%n", value, Arrays.toString(arr));
    }

    /**
     * Реализуйте метод, принимающий в качестве аргумента целочисленный массив, печатающий в консоль сумма элементов какой из половин массива больше.
     */
    private static void compareArrayHalves(int... arr) {
        printMethodName();

        int mid = arr.length / 2;
        int firstHalfSum = Arrays.stream(arr, 0, mid).sum();
        int secondHalfSum = Arrays.stream(arr, mid, arr.length).sum();

        System.out.printf("array: %s%n", Arrays.toString(arr));
        System.out.printf("first half sum = %d, second half sum = %d%n", firstHalfSum, secondHalfSum);
        System.out.println(
            firstHalfSum > secondHalfSum
                ? "First half is bigger"
                : firstHalfSum < secondHalfSum
                ? "Second half is bigger"
                : "Halves are equal"
        );
    }

    /**
     * Реализуйте метод, проверяющий, что есть строки в массиве, в которой сумма левой и правой части равны.
     * Точка находится между элементами.
     * Пример: { 1, 1, 1, 1, 1, | 5 }, { 5, | 3, 4, -2 }, { 7, 2, 2, 2 }, { 9, 4 }
     */
    private static void hasArrayPoisePoint(int... ints) {
        printMethodName();

        int total = Arrays.stream(ints).sum();

        System.out.println("Current array: " + Arrays.toString(ints));

        // Array's poise point is (Cumulative total * 2 = Total sum)
        boolean found = false;
        int cumSum = 0;
        for (int i = 0; i < ints.length; i++) {
            cumSum += ints[i];
            if( (cumSum * 2) == total ) {
                found = true;
                System.out.printf(
                    "Found array's poise point. Point between (index-%s value-%s) and (index-%s value-%s)%n",
                    i, ints[i],
                    i + 1, ints[i + 1]
                );
                break;
            }
        }

        if (!found) {
            System.out.println("Not found array's poise point.");
        }
    }


    /**
     * Реализуйте метод, проверяющий, что все элементы массива идут в порядке убывания или возрастания (по выбору пользователя).
     */
    private static void isSorted(boolean ascending, int... ints) {
        printMethodName();

        var res = true;
        for (int i = 0; i < ints.length - 1; i++) {
            // triggers the only check true=ascending, false=descending
            if (ascending && ints[i] > ints[i + 1] || !ascending && ints[i] < ints[i + 1]) {
                res = false;
                break;
            }
        }
        System.out.printf(
            "Is array %s sorted %s? - %s%n",
            Arrays.toString(ints),
            ascending ? "ascending" : "descending",
            res
        );
    }

    /**
     * Реализуйте метод, переворачивающий входящий массив
     * Пример: { 1 2 3 4 } => { 4 3 2 1 }
     */
    private static void reverseArray(int... arr) {
        printMethodName();

        System.out.println("Original array: " + Arrays.toString(arr));
        for (int i = 0; i < arr.length / 2; i++) {
            int temp = arr[i];
            arr[i] = arr[arr.length - 1 - i];
            arr[arr.length - 1 - i] = temp;
        }
        System.out.println("Reversed array: " + Arrays.toString(arr));
    }

    /**
     * Реализуйте метод, принимающий на вход набор целочисленных массивов и получающий новый массив равный сумме входных;
     * Пример: { 1, 2, 3 } + { 2, 2 } + { 1, 1, 1, 1, 1} = { 4, 5, 4, 1, 1 }
     */
    private static void sumArrays(int[]... arrays) {
        printMethodName();

        // Array length like the longest of all input arrays
        int maxLength = Arrays.stream(arrays).mapToInt(arr -> arr.length).max().orElse(0);
        var result = new int[maxLength];

        // Sums of corresponding existing indices
        for (int[] array : arrays) {
            for (int i = 0; i < array.length; i++) {
                result[i] += array[i];
            }
        }

        // Forming an output message
        var builder = new StringBuilder("Input arrays:").append("\n");
        for (var array : arrays) {
            builder.append(Arrays.toString(array)).append("\n");
        }
        builder
            .append("Sum of arrays:").append("\n")
            .append(Arrays.toString(result));

        System.out.println(builder);
    }

    private static void printMethodName() {
        var vignette = "+".repeat(5);
        System.out.println("\n" + vignette + new Throwable().getStackTrace()[1].getMethodName() + vignette);
    }
}