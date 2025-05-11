package ru.otus.hw.hw07.entity.transport;

import ru.otus.hw.hw07.entity.TerrainType;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public abstract class TerrainMovementConsumption extends TerrainMovementLimit {

    protected final double initialResourceValue;
    protected final double resourceConsumption;
    protected final EnumMap<TerrainType, Integer> terrainMultipliers = new EnumMap<>(TerrainType.class);

    protected double currentResourceValue;

    public TerrainMovementConsumption(
        double initialResourceValue,
        double resourceConsumption,
        Map<TerrainType, Integer> terrainMultipliers,
        List<TerrainType> cantMoveTerrains
    ) {
        super(cantMoveTerrains);

        this.initialResourceValue = initialResourceValue;
        this.resourceConsumption = resourceConsumption;
        this.terrainMultipliers.putAll(terrainMultipliers);
    }

    public boolean move(double distance, TerrainType terrain) {

        double requiredResource =
            distance
            * resourceConsumption
            // Транспорт может потреблять больше эенергии на сложной местности
            * terrainMultipliers.getOrDefault(terrain, 1);

        if (currentResourceValue < requiredResource) {
            return false;
        }

        currentResourceValue -= requiredResource;
        return true;
    }
}
