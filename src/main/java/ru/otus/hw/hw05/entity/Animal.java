package ru.otus.hw.hw05.entity;

import lombok.ToString;

@ToString(onlyExplicitlyIncluded = true)
public abstract class Animal {
    private static final int TIREDNESS_RUN_FACTOR = 1;
    private static final int UNDEFINED_VALUE = -1;

    @ToString.Include
    protected final String name;
    protected final double runSpeed;
    protected final boolean canSwim;
    protected final double swimSpeed;
    protected final int tirednessSwimFactor;

    @ToString.Include
    protected int stamina;
    @ToString.Include
    protected boolean tired;

    public Animal(
        String name,
        double runSpeed,
        boolean canSwim,
        double swimSpeed,
        int tirednessSwimFactor,
        int stamina
    ) {
        this.name = name;
        this.runSpeed = checkPositive(runSpeed, "Run speed");
        this.canSwim = canSwim;
        this.swimSpeed = canSwim ? checkPositive(swimSpeed, "Swim speed") : 0;
        this.tirednessSwimFactor = canSwim ? checkPositive(tirednessSwimFactor, "Tiredness swim factor") : 0;
        this.stamina = checkPositive(stamina, "Stamina");
        this.tired = false;
    }

    public double run(int distance) {

        int staminaCost = distance * TIREDNESS_RUN_FACTOR;

        if(isTiredAndCorrectTired(staminaCost)) return UNDEFINED_VALUE;

        double time = calcTimeAndCorrectStamina(distance, staminaCost, runSpeed);
        System.out.printf(
            "%s runs at a speed of %.2f m/s and achieves the result of %d meters in his run by time %.2f sec%n",
            name, runSpeed, distance, time
        );
        return time;
    }

    public double swim(int distance) {

        int staminaCost = distance * tirednessSwimFactor;

        if(isTiredAndCorrectTired(staminaCost)) return -1;

        double time = calcTimeAndCorrectStamina(distance, staminaCost, swimSpeed);
        System.out.printf(
            "%s is swimming at a speed of %.2f m/s and achieves the result of %d meters in his run by time %.2f sec%n",
            name, swimSpeed, distance, time
        );
        return time;
    }

    public void info() {
        System.out.println(this);
    }

    private boolean isTiredAndCorrectTired(int staminaCost) {
        if(stamina < staminaCost) {
            System.out.printf("%s is too tired to move%n", name);
            tired = true;
        }
        return tired;
    }

    private double calcTimeAndCorrectStamina(int distance, int staminaCost, double speed) {
        stamina -= staminaCost;
        return distance / speed;
    }

    private static <T extends Number> T checkPositive(T value, String description) {
        if (value.doubleValue() <= 0) {
            String[] classFullName = new Throwable().getStackTrace()[2].getClassName().split("\\.");
            String simpleClassName = classFullName[classFullName.length - 1];
            throw new IllegalArgumentException(
                "%s value in '%s' must be positive, but now is %s"
                    .formatted(simpleClassName, description, String.valueOf(value))
            );
        }
        return value;
    }
}