package ru.otus.hw.hw07.entity.transport.implementation;

import ru.otus.hw.hw07.entity.TerrainType;
import ru.otus.hw.hw07.entity.transport.Transport;

/**
 * Класс представляет транспортное средство типа "Машина".
 * Машина может передвигаться по определённым типам местности и расходует топливо
 * в зависимости от пройденного расстояния.
 * Машина не способна передвигаться по густому лесу и болотам.
 *
 * @see ru.otus.hw.hw07.entity.transport.Transport
 */
public class Car implements Transport {
    private final String name;
    private double fuel;
    private final double fuelConsumption; // расход топлива на 1 км

    /**
     * Создает новый экземпляр машины с указанными параметрами.
     *
     * @param name имя или модель машины
     * @param initialFuel начальное количество топлива в литрах
     * @param fuelConsumption расход топлива в литрах на 1 км пути
     */
    public Car(String name, double initialFuel, double fuelConsumption) {
        this.name = name;
        this.fuel = initialFuel;
        this.fuelConsumption = fuelConsumption;
    }

    @Override
    public boolean move(double distance, TerrainType terrain) {
        if (cantMoveOn(terrain)) {
            System.out.println("Машина " + name + " не может передвигаться по " + terrain);
            return false;
        }

        double requiredFuel = distance * fuelConsumption;
        if (fuel < requiredFuel) {
            System.out.println("Недостаточно топлива для поездки. Осталось: " + fuel + " л.");
            return false;
        }

        fuel -= requiredFuel;
        System.out.println("Машина " + name + " проехала " + distance + " км по " + terrain +
            ". Осталось топлива: " + fuel + " л.");
        return true;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean cantMoveOn(TerrainType terrain) {
        return terrain == TerrainType.DENSE_FOREST || terrain == TerrainType.SWAMP;
    }

    public void refuel(double amount) {
        fuel += amount;
        System.out.println("Машина " + name + " заправлена. Текущий запас топлива: " + fuel + " л.");
    }
}