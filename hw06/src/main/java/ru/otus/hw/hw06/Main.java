package ru.otus.hw.hw06;

import ru.otus.hw.hw06.enity.Cat;
import ru.otus.hw.hw06.enity.Plate;

public class Main {
    public static void main(String[] args) {
        Cat[] cats = {
            new Cat("Barsik", 5),
            new Cat("Murzik", 7),
            new Cat("Pushok", 3)
        };
        printArray(cats);

        Plate plate = new Plate(10);
        System.out.println(plate + "\n");

        for (Cat cat : cats) {
            cat.eat(plate);
            System.out.println(cat.getName() + " is " + (cat.isSatisfied() ? "satisfied" : "hungry"));
        }
        printArray(cats);
        System.out.println(plate);

        System.out.println("Adding too much food\n");

        plate.addFood(999999999);
        System.out.println("Food amount is correct");
        System.out.println(plate);
    }

    private static void printArray(Cat[] array) {
        System.out.println();
        for (var item : array) {
            System.out.println(item);
        }
        System.out.println();
    }
}