package ru.otus.hw.hw05.entity;

public abstract class Animal {
    protected final String name;
    protected final double runSpeed;
    protected final boolean canSwim;
    protected final double swimSpeed;
    protected final int tirednessSwimFactor;

    protected int stamina;
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
        this.runSpeed = isPositive(runSpeed, "Run speed");
        this.canSwim = canSwim;
        this.swimSpeed = canSwim ? isPositive(swimSpeed, "Swim speed") : 0;
        this.tirednessSwimFactor = isPositive(tirednessSwimFactor, "Tiredness swim factor");
        this.stamina = isPositive(stamina, "Stamina");
        this.tired = false;
    }

    public double run(int distance) {
        if (stamina >= distance) {
            stamina -= distance;
            System.out.printf("%s runs at a speed of %.2f m/s and achieves a result of %d meters in his run%n", name, runSpeed, distance);
            return distance / runSpeed;
        }
        tired = true;
        System.out.printf("%s is too tired to run %d meters%n", name, distance);
        return -1;
    }

    public double swim(int distance) {

        if (!canSwim) {
            System.out.printf("%s cannot swim!%n", name);
            return -1;
        }

        int staminaCost = distance * tirednessSwimFactor;
        if (stamina >= staminaCost) {
            stamina -= staminaCost;
            System.out.printf("%s is swimming %d meters%n", name, distance);
            return distance / swimSpeed;
        }

        System.out.printf("%s is too tired to swim%n", name);
        tired = true;
        return -1;
    }

    public void info() {
        System.out.printf("%s - Stamina: %d, Tired: %b%n", name, stamina, tired);
    }

    private static <T extends Number> T isPositive(T value, String description) {
        if (value.doubleValue() <= 0) {
            String[] classFullName = new Throwable().getStackTrace()[2].getClassName().split("\\.");
            String SimpleClassName = classFullName[classFullName.length - 1];
            throw new IllegalArgumentException("%s value in '%s' must be positive, but now is %s".formatted(SimpleClassName, description, String.valueOf(value)));
        }
        return value;
    }
}