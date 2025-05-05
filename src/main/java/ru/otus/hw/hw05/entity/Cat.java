package ru.otus.hw.hw05.entity;

public class Cat extends Animal {
    private static final boolean CAN_SWIM = false;
    private static final int TIREDNESS_SWIM_FACTOR = 1;
    
    public Cat(String name, double runSpeed, int stamina) {
        super(name, runSpeed, CAN_SWIM, 0, TIREDNESS_SWIM_FACTOR, stamina);
    }

    @Override
    public double swim(int distance) {
        System.out.println("Brrr.. I hate swimming");
        return super.swim(distance);
    }
}