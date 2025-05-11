package ru.otus.hw.hw07.entity.transport.implementation;

import lombok.Getter;
import ru.otus.hw.hw07.entity.TerrainType;
import ru.otus.hw.hw07.entity.transport.TerrainMovementConsumption;
import ru.otus.hw.hw07.entity.transport.Transport;

import java.util.List;
import java.util.Map;

/**
 * Класс Вездеход - представляет транспортное средство, способное передвигаться по любым типам местности.
 * 
 * <p>Особенности вездехода:</p>
 * <ul>
 *     <li>Может перемещаться по любой местности</li>
 *     <li>Имеет повышенный расход топлива при перемещении по труднопроходимым типам местности</li>
 * </ul>
 */
public class AllTerrainVehicle extends TerrainMovementConsumption implements Transport {
    @Getter
    private final String name;

    /**
     * Создает новый экземпляр вездехода с указанными параметрами.
     *
     * @param name имя вездехода для идентификации
     * @param initialFuel начальный запас топлива в литрах
     * @param fuelConsumption базовый расход топлива (литров на км)
     */
    public AllTerrainVehicle(String name, double initialFuel, double fuelConsumption) {
        super(
            initialFuel,
            fuelConsumption,
            Map.of(TerrainType.DENSE_FOREST, 2, TerrainType.SWAMP, 3),
            List.of()
        );

        this.name = name;
    }

    /**
     * Перемещает вездеход на указанное расстояние по заданному типу местности.
     *
     * @param distance расстояние для перемещения в км
     * @param terrain тип местности для перемещения
     * @return {@code true} если перемещение было успешным (достаточно топлива),
     *         {@code false} в противном случае
     */
    @Override
    public boolean move(double distance, TerrainType terrain) {
        boolean moved = super.move(distance, terrain);

        if (moved) {
            System.out.println("Вездеход " + name + " проехал " + distance + " км по " + terrain + ". Осталось топлива: " + currentResourceValue + " л.");
        } else {
            System.out.println("Недостаточно топлива для поездки на вездеходе. Осталось: " + currentResourceValue + " л.");
        }

        return moved;
    }

    /**
     * Проверяет, может ли вездеход передвигаться по указанному типу местности.
     * Вездеход способен перемещаться по любой местности.
     *
     * @param terrain тип местности для проверки
     * @return {@code true} всегда, так как вездеход может перемещаться по любой местности
     */
    @Override
    public boolean cantMoveOn(TerrainType terrain) {
        // Вездеход может перемещаться по любой местности
        return true;
    }

    /**
     * Заправляет вездеход указанным количеством топлива.
     *
     * @param amount количество топлива для заправки в литрах
     */
    public void refuel(double amount) {
        currentResourceValue += amount;
        System.out.println("Вездеход " + name + " заправлен. Текущий запас топлива: " + currentResourceValue + " л.");
    }
}