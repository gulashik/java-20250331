import java.util.Random;

public class Main {

    public static void main(String[] args) {
        greetings();

        checkSign(
            getRandomIntegerInRange(-10, 10),
            getRandomIntegerInRange(-10, 10),
            getRandomIntegerInRange(-10, 10)
        );

        selectColor();

        compareNumbers();

        addOrSubtractAndPrint(
            getRandomIntegerInRange(0, 10),
            getRandomIntegerInRange(0, 10),
            new Random().nextBoolean()
        );
    }

    // Реализуйте метод greetings(), который при вызове должен отпечатать в столбец 4 слова: Hello, World, from, Java;
    static void greetings() {
        printMethodName();

        System.out.print(
            """
                Hello
                World
                from
                Java
                """
        );
    }

    // Реализуйте метод checkSign(..), принимающий в качестве аргументов 3 int переменные a, b и c.
    //  Метод должен посчитать их сумму, и если она больше или равна 0, то вывести в консоль сообщение “Сумма положительная”, в противном случае - “Сумма отрицательная”;
    public static void checkSign(int a, int b, int c) {
        printMethodName();

        int sum = a + b + c;
        System.out.printf(
            """
                a = %d b = %d c = %d
                sum = %d
                %s
                """,
            a, b, c,
            sum,
            sum >= 0 ? "Сумма положительная" : "Сумма отрицательная"
        );
    }

    // Реализуйте метод selectColor() в теле которого задайте int переменную data с любым начальным значением.
    //  Если data меньше 10 включительно, то в консоль должно быть выведено сообщение “Красный”, если от 10 до 20 включительно, то “Желтый”, если больше 20 - “Зеленый”;
    public static void selectColor() {
        printMethodName();

        int data = getRandomIntegerInRange(0, 30);

        System.out.printf("data = %d\n", data);
        if (data <= 10) {
            System.out.println("Красный");
        } else if (data <= 20) {
            System.out.println("Желтый");
        } else {
            System.out.println("Зеленый");
        }
    }

    // Реализуйте метод compareNumbers(), в теле которого объявите две int переменные a и b с любыми начальными значениями.
    //  Если a больше или равно b, то необходимо вывести в консоль сообщение “a >= b”, в противном случае “a < b”;
    public static void compareNumbers() {
        printMethodName();

        int a = getRandomIntegerInRange(0, 30);
        int b = getRandomIntegerInRange(0, 30);
        System.out.printf(
            """
                a = %d, b = %d
                %s
                """,
            a, b,
            a >= b ? "a >= b" : "a < b"
        );
    }

    // Создайте метод addOrSubtractAndPrint(int initValue, int delta, boolean increment).
    //  Если increment = true, то метод должен к initValue прибавить delta и отпечатать в консоль результат, в противном случае - вычесть;
    public static void addOrSubtractAndPrint(int initValue, int delta, boolean increment) {
        printMethodName();

        System.out.printf(
            """
                initValue = %d
                delta = %d
                increment = %s
                result = %d
                """,
            initValue,
            delta,
            increment,
            increment ? initValue + delta : initValue - delta
        );
    }

    private static void printMethodName() {
        String vignette = "+".repeat(5);
        System.out.println("\n" + vignette + new Throwable().getStackTrace()[1].getMethodName() + vignette);
    }

    private static int getRandomIntegerInRange(int min, int max) {
        return new Random().nextInt(min, max);
    }
}