package ru.otus.hw.hw05.entity;

public class Cat extends Animal {
    private static final boolean CAN_SWIM = false;
    private static final double SWIM_SPEED = 0;
    private static final int TIREDNESS_SWIM_FACTOR = 0;
    
    public Cat(String name, double runSpeed, int stamina) {
        super(name, runSpeed, CAN_SWIM, SWIM_SPEED, TIREDNESS_SWIM_FACTOR, stamina);
    }

    @Override
    public double swim(int distance) {
        System.out.println("Brrr.. I hate swimming");
        System.out.printf("%s cannot swim!%n", name);
        return -1;
    }
}