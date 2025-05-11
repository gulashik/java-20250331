package ru.otus.hw.hw07.entity.transport;

import ru.otus.hw.hw07.entity.TerrainType;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Абстрактный класс, расширяющий функционал {@link TerrainMovementLimit},
 * моделирующий расход ресурсов при движении транспортного средства
 * по различным типам местности.
 * 
 * <p>Отслеживает текущий уровень ресурса и его расход в зависимости
 * от типа местности и пройденной дистанции.</p>
 * 
 * @see TerrainMovementLimit
 * @see TerrainType
 */
public abstract class TerrainMovementConsumption extends TerrainMovementLimit {

    protected final double resourceConsumption;
    
    /** Коэффициенты расхода ресурса в зависимости от типа местности */
    protected final EnumMap<TerrainType, Integer> terrainMultipliers = new EnumMap<>(TerrainType.class);

    protected double currentResourceValue;

    /**
     * Конструктор, инициализирующий базовые параметры расхода ресурсов при движении.
     *
     * @param initialResourceValue начальное значение ресурса
     * @param resourceConsumption базовый расход ресурса на единицу дистанции
     * @param terrainMultipliers коэффициенты расхода ресурса в зависимости от типа местност
     * @param cantMoveTerrains список типов местности, по которым транспорт не может двигаться
     */
    public TerrainMovementConsumption(
        double initialResourceValue,
        double resourceConsumption,
        Map<TerrainType, Integer> terrainMultipliers,
        List<TerrainType> cantMoveTerrains
    ) {
        super(cantMoveTerrains);

        this.currentResourceValue = initialResourceValue;
        this.resourceConsumption = resourceConsumption;
        this.terrainMultipliers.putAll(terrainMultipliers);
    }

    /**
     * Моделирует перемещение на указанное расстояние по заданному типу местности
     * с расходом соответствующего количества ресурса.
     *
     * @param distance расстояние для перемещения
     * @param terrain тип местности, по которой осуществляется перемещение
     * @return true, если перемещение успешно (достаточно ресурса),
     *         false - если ресурса недостаточно для перемещения
     */
    public boolean move(double distance, TerrainType terrain) {

        double requiredResource =
            distance
            * resourceConsumption
            // Транспорт может потреблять больше энергии на сложной местности
            * terrainMultipliers.getOrDefault(terrain, 1);

        if (currentResourceValue < requiredResource) {
            return false;
        }

        currentResourceValue -= requiredResource;
        return true;
    }
}