package ru.otus.hw.hw05;

public class Cat extends Animal {
    public Cat(String name, double runSpeed, double swimSpeed, int stamina) {
        super(name, runSpeed, false, swimSpeed, 1, stamina);
    }

    @Override
    public double swim(int distance) {
        System.out.println("Brrr.. I hate swimming");
        return super.swim(distance);
    }
}
