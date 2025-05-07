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

        Plate plate = new Plate(10);

        for (Cat cat : cats) {
            cat.eat(plate);
            System.out.println(cat.getName() + " is " + (cat.isSatisfied() ? "satisfied" : "hungry"));
        }
    }
}