package ru.otus.hw.hw07.entity.transport;

import ru.otus.hw.hw07.entity.TerrainType;

import java.util.List;

/**
 * Абстрактный класс, определяющий ограничения перемещения транспортных средств по различным типам местности.
 *
 * @see TerrainType
 */
public abstract class TerrainMovementLimit {
    protected final List<TerrainType> CANT_MOVE_TERRAINS;

    /**
     * cantMoveTerrains - список местностей, где НЕ МОЖЕТ двигаться сущность
     * @see TerrainType
     */
    public TerrainMovementLimit(List<TerrainType> cantMoveTerrains) {
        CANT_MOVE_TERRAINS = cantMoveTerrains;
    }

    /**
     * Проверяет, может ли транспортное средство перемещаться по заданной местности
     *
     * @param terrain тип местности для проверки
     * @return true, если транспортное средство НЕ МОЖЕТ двигаться по этой местности,
     *         false - если может
     */
    public boolean cantMoveOn(TerrainType terrain) {
        return CANT_MOVE_TERRAINS.contains(terrain);
    }
}