package ru.otus.hw.hw05;

import ru.otus.hw.hw05.entity.Animal;
import ru.otus.hw.hw05.entity.Cat;
import ru.otus.hw.hw05.entity.Dog;
import ru.otus.hw.hw05.entity.Horse;

public class Main {
    public static void main(String[] args) {

        var cat = new Cat("Murka", 5, 100);
        var dog = new Dog("Bobik", 4, 1, 100);
        var horse = new Horse("Spirit", 8, 2, 100);

        showInfo(cat, dog, horse);

        System.out.println("Cat running 20m: " + cat.run(20) + " seconds\n");
        System.out.println("Dog running 20m: " + dog.run(20) + " seconds\n");
        System.out.println("Horse running 20m: " + horse.run(20) + " seconds\n");

        showInfo(cat, dog, horse);

        System.out.println("Cat swimming 20m: " + cat.swim(20) + " seconds\n");
        System.out.println("Dog swimming 20m: " + dog.swim(20) + " seconds\n");
        System.out.println("Horse swimming 20m: " + horse.swim(20) + " seconds\n");

        showInfo(cat, dog, horse);

        System.out.println("Cat running 20m: " + cat.run(20) + " seconds\n");
        System.out.println("Dog running 20m: " + dog.run(20) + " seconds\n");
        System.out.println("Horse running 20m: " + horse.run(20) + " seconds\n");

        showInfo(cat, dog, horse);
    }

    private static void showInfo(Animal... animals) {
        System.out.println("Info");
        for (Animal animal : animals) {
            animal.info();
        }
        System.out.println();
    }
}