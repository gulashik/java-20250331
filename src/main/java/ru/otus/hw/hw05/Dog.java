package ru.otus.hw.hw05;

public class Dog extends Animal {
    public Dog(String name, double runSpeed, double swimSpeed, int stamina) {
        super(name, runSpeed, true, swimSpeed, 2, stamina);
    }
}