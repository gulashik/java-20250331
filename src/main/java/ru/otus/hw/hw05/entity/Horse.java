package ru.otus.hw.hw05.entity;

public class Horse extends Animal {
    private static final boolean CAN_SWIM = true;
    private static final int TIREDNESS_SWIM_FACTOR = 4;
    
    public Horse(String name, double runSpeed, double swimSpeed, int stamina) {
        super(name, runSpeed, CAN_SWIM, swimSpeed, TIREDNESS_SWIM_FACTOR, stamina);
    }
}