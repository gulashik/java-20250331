package ru.otus.hw.hw05.entity;

public class Dog extends Animal {
    private static final boolean CAN_SWIM = true;
    private static final int TIREDNESS_SWIM_FACTOR = 2;
    
    public Dog(String name, double runSpeed, double swimSpeed, int stamina) {
        super(name, runSpeed, CAN_SWIM, swimSpeed, TIREDNESS_SWIM_FACTOR, stamina);
    }
}